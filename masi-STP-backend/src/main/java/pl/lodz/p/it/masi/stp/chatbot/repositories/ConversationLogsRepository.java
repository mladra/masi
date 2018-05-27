package pl.lodz.p.it.masi.stp.chatbot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lodz.p.it.masi.stp.chatbot.model.collections.logs.ConversationLog;

public interface ConversationLogsRepository extends MongoRepository<ConversationLog, String> {

}
