package pl.lodz.p.it.masi.stp.chatbot.masiSTPbackend;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lodz.p.it.masi.stp.chatbot.dtos.MessageDto;
import pl.lodz.p.it.masi.stp.chatbot.services.ConversationServiceImpl;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatsonTests {
	
	@Autowired
	private ConversationServiceImpl conversationService;
	
	@Test
	public void sendExampleEmptyRequest() {
		MessageDto testMessage = new MessageDto();
		testMessage.setMessage("");
		conversationService.initialize();
		MessageResponse response = conversationService.getWatsonResponse(testMessage, testMessage);
		assertNotNull(response);
		assertNotNull(testMessage.getContext());
		assertNotNull(testMessage.getResponse());
		assertTrue(testMessage.getResponse().size() > 0);
		assertEquals(testMessage.getContext().get("is_historical"), false);
		assertEquals(testMessage.getContext().get("is_biography"), false);
		assertEquals(testMessage.getContext().get("genre_selected"), false);
		assertNotEquals(testMessage.getContext().getConversationId(), null);
		assertNotEquals(testMessage.getContext().getConversationId(), "");
	}
	
	@Test
	public void getAfricaBoobs() {
		MessageDto testMessage = new MessageDto();
		testMessage.setMessage("I want something about Africa");
		conversationService.initialize();
		MessageResponse response = conversationService.getWatsonResponse(testMessage, testMessage);
		assertNotNull(response);
		assertNotNull(testMessage.getContext());
		assertNotNull(testMessage.getResponse());
		assertTrue(testMessage.getResponse().size() > 0);
		assertTrue(response.getEntities().stream().filter(l -> l.getValue().equals("AFRICA")).collect(Collectors.toList()).size() > 0);
		assertTrue(response.getEntities().stream().filter(l -> l.getValue().equals("AFRICA")).findFirst().get().getConfidence() > 0.0);
	}
	
	@Test
	public void getInfo() {
		MessageDto testMessage = new MessageDto();
		testMessage.setMessage("What kind of books do you have?");
		conversationService.initialize();
		MessageResponse response = conversationService.getWatsonResponse(testMessage, testMessage);
		assertNotNull(response);
		assertNotNull(testMessage.getContext());
		assertNotNull(testMessage.getResponse());
		assertTrue(testMessage.getResponse().size() > 0);
		assertTrue(testMessage.getResponse().get(0).contains("biography"));
		assertTrue(testMessage.getResponse().get(0).contains("memories"));
		assertTrue(testMessage.getResponse().get(0).contains("history"));
	}
	
	@Test
	public void setGenre() {
		MessageDto testMessage = new MessageDto();
		testMessage.setMessage("I want historical book");
		conversationService.initialize();
		MessageResponse response = conversationService.getWatsonResponse(testMessage, testMessage);
		assertNotNull(response);
		assertNotNull(testMessage.getContext());
		assertNotNull(testMessage.getResponse());
		assertTrue(testMessage.getResponse().size() > 0);
		assertTrue(testMessage.getResponse().get(0).contains("HISTORY genre"));
		assertEquals(testMessage.getContext().get("is_historical"), true);
		assertEquals(testMessage.getContext().get("is_biography"), false);
		assertEquals(testMessage.getContext().get("genre_selected"), true);
	}
	
	@Test
	public void getSubs() {
		MessageDto testMessage = new MessageDto();
		testMessage.setMessage("biography book");
		conversationService.initialize();
		MessageResponse response = conversationService.getWatsonResponse(testMessage, testMessage);
		assertNotNull(response);
		assertNotNull(testMessage.getContext());
		assertNotNull(testMessage.getResponse());
		assertTrue(testMessage.getResponse().size() > 0);
		String[] subs = new String[]{"arts and literature", "ethnic national", "historical", "leaders and notable people", "memoirs", "professionals academics", "reference collections", "regional Canada", "regional USA", "specific groups"};
		for (String sub : subs) {
			assertTrue(testMessage.getResponse().stream().filter(l -> l.contains(sub)).findAny().get().contains(sub));
		}
		assertEquals(testMessage.getContext().get("is_historical"), false);
		assertEquals(testMessage.getContext().get("is_biography"), true);
		assertEquals(testMessage.getContext().get("genre_selected"), true);
	}
}
