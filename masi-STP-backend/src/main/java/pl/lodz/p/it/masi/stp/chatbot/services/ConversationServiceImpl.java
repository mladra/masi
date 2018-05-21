package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.RuntimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.masi.stp.chatbot.amazon.*;
import pl.lodz.p.it.masi.stp.chatbot.collections.ConversationLog;
import pl.lodz.p.it.masi.stp.chatbot.collections.MessageLog;
import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;
import pl.lodz.p.it.masi.stp.chatbot.repositories.ConversationLogsRepository;
import pl.lodz.p.it.masi.stp.chatbot.utils.EnumUtils;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private static Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);

    private final ConversationLogsRepository conversationLogsRepository;

    private Conversation conversation;

    @Value("${amazon.secret.key}")
    private String amazonSecretKey;

    @Value("${amazon.access.key}")
    private String amazonAccessKey;

    @Value("${amazon.associate.tag}")
    private String amazonAssociateTag;

    @Value("${watson.version.date}")
    private String watsonVersionDate;

    @Value("${watson.username}")
    private String watsonUsername;

    @Value("${watson.password}")
    private String watsonPassword;

    @Value("${watson.endpoint}")
    private String watsonEndpoint;

    @Autowired
    public ConversationServiceImpl(ConversationLogsRepository conversationLogsRepository) {
        this.conversationLogsRepository = conversationLogsRepository;
    }

    @PostConstruct
    public void initialize() {
        conversation = new Conversation(watsonVersionDate, watsonUsername, watsonPassword);
        conversation.setEndPoint(watsonEndpoint);
    }

    @Override
    public MessageDto processMessage(MessageDto requestMsg) {
        MessageLog messageLog = new MessageLog();
        messageLog.setAngularRequestTimestamp(LocalDateTime.now());
        messageLog.setUserInput(requestMsg.getMessage());

        MessageDto responseMsg = new MessageDto();

        MessageResponse watsonResponse = getWatsonResponse(requestMsg, responseMsg, messageLog);

        getAmazonResponse(requestMsg, responseMsg, watsonResponse, messageLog);

        Optional<ConversationLog> conversationLog = conversationLogsRepository.findByWatsonConversationId(requestMsg.getContext().getConversationId());
        if (!conversationLog.isPresent()) {
            ConversationLog conversationLogToSave = new ConversationLog();
            conversationLogToSave.setWatsonConversationId(watsonResponse.getContext().getConversationId());
            List<MessageLog> messageLogs = new ArrayList<>();
            messageLogs.add(messageLog);
            conversationLogToSave.setMessagesLogs(messageLogs);
            conversationLogsRepository.save(conversationLogToSave);
        } else {
            conversationLog.get().getMessagesLogs().add(messageLog);
            conversationLogsRepository.save(conversationLog.get());
        }
        return responseMsg;
    }

    public void getAmazonResponse(MessageDto request, MessageDto response, MessageResponse watsonResponse, MessageLog messageLog) {
        messageLog.setAmazonRequestTimestamp(LocalDateTime.now());
        AWSECommerceService service = new AWSECommerceService();
        service.setHandlerResolver(new AwsHandlerResolver(amazonSecretKey));

        AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

        ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
        itemSearchRequest.setSearchIndex(CategoriesEnum.BOOKS.getName());

        if (watsonResponse.getEntities().size() >= 1) {
            List<String> entities = watsonResponse.getEntities().stream()
                .map(RuntimeEntity::getValue).collect(Collectors.toList());

            for (String entity : entities) {
                CategoriesEnum categoriesEnum = EnumUtils.lookupByName(entity);
                if (categoriesEnum != null) {
                    itemSearchRequest.setBrowseNode(categoriesEnum.getBrowseNodeId());
                    break;
                }
            }
        }

        ItemSearch ItemElement= new ItemSearch();
        ItemElement.setAWSAccessKeyId(amazonAccessKey);
        ItemElement.setAssociateTag(amazonAssociateTag);
        ItemElement.getRequest().add(itemSearchRequest);

        ItemSearchResponse amazonResponse = null;
        try {
            amazonResponse = port.itemSearch(ItemElement);
        } catch (WebServiceException exc) {
            logger.error(exc.toString());
        }

        if (amazonResponse != null) {
            messageLog.setAmazonResponseTimestamp(LocalDateTime.now());
            logger.info(amazonResponse.toString());
            List<Items> receivedItems = amazonResponse.getItems();
            if (receivedItems != null) {
                messageLog.setResultsCount(receivedItems.size());
                if (receivedItems.size() > 0) {
                    response.setUrl(receivedItems.get(0).getMoreSearchResultsUrl());
                }
            }
        }
    }

    public MessageResponse getWatsonResponse(MessageDto request, MessageDto response, MessageLog messageLog) {
        String workspaceId = "fb1afa02-f113-446c-ba28-a86992500910";
        InputData input = new InputData.Builder(request.getMessage()).build();
        MessageOptions options = new MessageOptions.Builder(workspaceId)
                .input(input)
                .context(request.getContext())
                .build();

        messageLog.setWatsonRequestTimestamp(LocalDateTime.now());

        MessageResponse watsonResponse = conversation.message(options).execute();

        messageLog.setWatsonResponseTimestamp(LocalDateTime.now());
        messageLog.setWatsonIntent(watsonResponse.getIntents().toString());
        messageLog.setWatsonOutput(watsonResponse.getOutput().getText());

        response.setContext(watsonResponse.getContext());
        response.setResponse(watsonResponse.getOutput().getText());
        logger.info(response.toString());
        return watsonResponse;
    }
}
