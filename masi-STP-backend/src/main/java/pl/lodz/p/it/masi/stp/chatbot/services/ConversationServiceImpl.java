package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.RuntimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.masi.stp.chatbot.amazon.*;
import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;
import pl.lodz.p.it.masi.stp.chatbot.utils.EnumUtils;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private static Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);

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

    @PostConstruct
    public void initialize() {
        conversation = new Conversation(watsonVersionDate, watsonUsername, watsonPassword);
        conversation.setEndPoint(watsonEndpoint);
    }

    @Override
    public MessageDto processMessage(MessageDto requestMsg) {
        MessageDto responseMsg = new MessageDto();
        MessageResponse watsonResponse = getWatsonResponse(requestMsg, responseMsg);
        getAmazonResponse(requestMsg, responseMsg, watsonResponse);
        return responseMsg;
    }

    private void getAmazonResponse(MessageDto request, MessageDto response, MessageResponse watsonResponse) {
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
                response.setUrl(receivedItems.get(0).getMoreSearchResultsUrl());
            }
        }
    }

    private MessageResponse getWatsonResponse(MessageDto request, MessageDto response) {
        String workspaceId = "fb1afa02-f113-446c-ba28-a86992500910";
        InputData input = new InputData.Builder(request.getMessage()).build();
        MessageOptions options = new MessageOptions.Builder(workspaceId)
                .input(input)
                .context(request.getContext())
                .build();
        MessageResponse watsonResponse = conversation.message(options).execute();
        response.setContext(watsonResponse.getContext());
        response.setResponse(watsonResponse.getOutput().getText());
        logger.info(response.toString());
        return watsonResponse;
    }
}
