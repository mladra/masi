package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.lodz.p.it.masi.stp.chatbot.amazon.*;
import pl.lodz.p.it.masi.stp.chatbot.collections.ConversationLog;
import pl.lodz.p.it.masi.stp.chatbot.collections.MessageLog;
import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;
import pl.lodz.p.it.masi.stp.chatbot.repositories.ConversationLogsRepository;
import pl.lodz.p.it.masi.stp.chatbot.utils.EnumUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationLogsRepository conversationLogsRepository;

    private static Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);

    private Conversation conversation;

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

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
        MessageDto responseMsg = new MessageDto();
        MessageLog messageLog = new MessageLog();
        messageLog.setUserInput(requestMsg.getMessage());
        if (requestMsg.getContext() != null) {
            ConversationLog conversationLog = conversationLogsRepository.findByConversationId(requestMsg.getContext().getConversationId());
            if (conversationLog == null) {
                conversationLog = new ConversationLog();
                conversationLog.setConversationId(requestMsg.getContext().getConversationId());
                conversationLog.setQuestionsCounter(1L);
                conversationLog.setMisunderstoodQuestionsCounter(0L);
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                conversationLog.setUserIp(getClientIpAddress(request));
                conversationLog.setMessagesLogs(new ArrayList<>());
            }
            MessageResponse watsonResponse = getWatsonResponse(requestMsg, responseMsg, conversationLog, messageLog);
            getAmazonResponse(requestMsg, responseMsg, watsonResponse, messageLog);
            conversationLog.getMessagesLogs().add(messageLog);
            conversationLogsRepository.save(conversationLog);
        } else {
            MessageResponse watsonResponse = getWatsonResponse(requestMsg, responseMsg, null, null);
            getAmazonResponse(requestMsg, responseMsg, watsonResponse, null);
        }
        return responseMsg;
    }

    public void getAmazonResponse(MessageDto request, MessageDto response, MessageResponse watsonResponse, MessageLog messageLog) {
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
            logger.info(amazonResponse.toString());
            List<Items> receivedItems = amazonResponse.getItems();
            if (receivedItems != null && receivedItems.size() > 0) {
                if (messageLog != null) {
                    if (receivedItems.get(0).getTotalResults() != null) {
                        messageLog.setResultsCount(receivedItems.get(0).getTotalResults());
                    } else {
                        messageLog.setResultsCount(BigInteger.ZERO);
                    }
                }
                response.setUrl(receivedItems.get(0).getMoreSearchResultsUrl());
            }
        }
    }

    public MessageResponse getWatsonResponse(MessageDto request, MessageDto response, ConversationLog conversationLog, MessageLog messageLog) {
        String workspaceId = "fb1afa02-f113-446c-ba28-a86992500910";
        InputData input = new InputData.Builder(request.getMessage()).build();
        MessageOptions options = new MessageOptions.Builder(workspaceId)
                .input(input)
                .context(request.getContext())
                .build();
        MessageResponse watsonResponse = conversation.message(options).execute();

        if (messageLog != null && conversationLog != null) {
            List<String> intents = new ArrayList<>();
            for (RuntimeIntent intent :
                    watsonResponse.getIntents()) {
                intents.add(intent.getIntent());
            }
            messageLog.setWatsonIntent(intents);
            messageLog.setWatsonOutput(watsonResponse.getOutput().getText());

            List<String> nodesVisited = watsonResponse.getOutput().getNodesVisited();
            if (nodesVisited.size() == 1 && nodesVisited.get(0).equals("Anything else")) {
                conversationLog.incrementMisunderstoodQuestionsCounter();
            } else if (conversationLog.getMessagesLogs().size() != 0) {
                conversationLog.incrementQuestionsCounter();
            }
        }

        response.setContext(watsonResponse.getContext());
        response.setResponse(watsonResponse.getOutput().getText());
        logger.info(response.toString());
        return watsonResponse;
    }

    private static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
