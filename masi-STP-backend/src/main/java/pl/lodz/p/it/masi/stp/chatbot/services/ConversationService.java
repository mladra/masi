package pl.lodz.p.it.masi.stp.chatbot.services;

import pl.lodz.p.it.masi.stp.chatbot.dtos.MessageDto;

import javax.servlet.http.HttpServletRequest;

public interface ConversationService {

    MessageDto processMessage(MessageDto message);
}
