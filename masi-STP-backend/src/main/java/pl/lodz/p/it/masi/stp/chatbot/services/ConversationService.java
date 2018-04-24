package pl.lodz.p.it.masi.stp.chatbot.services;

import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;

public interface ConversationService {

  MessageDto helloWorld(MessageDto message);
}
