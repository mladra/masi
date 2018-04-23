package pl.lodz.p.it.masi.stp.chatbot.services;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public interface ConversationService {

  MessageResponse helloWorld(String message);
}
