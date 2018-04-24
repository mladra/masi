package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;

@Service
public class ConversationServiceImpl implements ConversationService {

  private Conversation conversation;

  private static Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);

  @Autowired
  public ConversationServiceImpl() {
    // HERE INITIALIZE CONVERSATION
    conversation.setEndPoint("https://gateway.watsonplatform.net/assistant/api");
  }

  @Override
  public MessageDto processMessage(MessageDto message) {
    String workspaceId = "fb1afa02-f113-446c-ba28-a86992500910";
    InputData input = new InputData.Builder(message.getMessage()).build();
    MessageOptions options = new MessageOptions.Builder(workspaceId)
        .input(input)
        .context(message.getContext())
        .build();
    MessageResponse response = conversation.message(options).execute();
    MessageDto output = new MessageDto(response.getContext(), "http://amazon.com", response.getOutput().getText());
    logger.info(output.toString());
    return output;
  }
}
