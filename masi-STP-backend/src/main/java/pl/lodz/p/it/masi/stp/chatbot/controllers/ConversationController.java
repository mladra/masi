package pl.lodz.p.it.masi.stp.chatbot.controllers;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.masi.stp.chatbot.entities.MessageDto;
import pl.lodz.p.it.masi.stp.chatbot.services.ConversationService;

@RestController
@RequestMapping(value = "/chat")
public class ConversationController {

  private final ConversationService conversationService;

  @Autowired
  public ConversationController(ConversationService conversationService) {
    this.conversationService = conversationService;
  }

  @RequestMapping(value = "/send", method = RequestMethod.POST)
  public MessageDto sendMessage(@RequestBody MessageDto message) {
    return conversationService.helloWorld(message);
  }
}
