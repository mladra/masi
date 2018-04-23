package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {

  private Conversation conversation;

  @Autowired
  public ConversationServiceImpl() {
    // HERE INITIALIZE CONVERSATION
    conversation.setEndPoint("https://gateway.watsonplatform.net/assistant/api");
  }

  @Override
  public MessageResponse helloWorld(String message) {
    String workspaceId = "fb1afa02-f113-446c-ba28-a86992500910";
    InputData input = new InputData.Builder(message).build();
    MessageOptions options = new MessageOptions.Builder(workspaceId)
        .input(input)
        .build();
    MessageResponse response = conversation.message(options).execute();
    System.out.println(response);
    return response;
  }
}
