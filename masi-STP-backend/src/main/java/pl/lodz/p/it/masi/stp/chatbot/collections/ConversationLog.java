package pl.lodz.p.it.masi.stp.chatbot.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "logs")
public class ConversationLog {

    @Id
    private String id;

    private String userIp;
    private String conversationId;
    private ConversationEndStatusEnum endStatus;
    private List<MessageLog> messagesLogs;
    private Long questionsCounter;
    private Long misunderstoodQuestionsCounter;
    private String chatbotUsabilityScore;
    private String chatbotEffectivenessScore;

    public ConversationLog(ConversationEndStatusEnum endStatus, List<MessageLog> messagesLogs) {
        this.endStatus = endStatus;
        this.messagesLogs = messagesLogs;
    }

    public ConversationLog() {
    }

    public ConversationLog incrementQuestionsCounter() {
        this.questionsCounter += 1;
        return this;
    }

    public ConversationLog incrementMisunderstoodQuestionsCounter() {
        this.misunderstoodQuestionsCounter += 1;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationEndStatusEnum getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(ConversationEndStatusEnum endStatus) {
        this.endStatus = endStatus;
    }

    public List<MessageLog> getMessagesLogs() {
        return messagesLogs;
    }

    public void setMessagesLogs(List<MessageLog> messagesLogs) {
        this.messagesLogs = messagesLogs;
    }

    public Long getQuestionsCounter() {
        return questionsCounter;
    }

    public void setQuestionsCounter(Long questionsCounter) {
        this.questionsCounter = questionsCounter;
    }

    public Long getMisunderstoodQuestionsCounter() {
        return misunderstoodQuestionsCounter;
    }

    public void setMisunderstoodQuestionsCounter(Long misunderstoodQuestionsCounter) {
        this.misunderstoodQuestionsCounter = misunderstoodQuestionsCounter;
    }

    public String getChatbotUsabilityScore() {
        return chatbotUsabilityScore;
    }

    public void setChatbotUsabilityScore(String chatbotUsabilityScore) {
        this.chatbotUsabilityScore = chatbotUsabilityScore;
    }

    public String getChatbotEffectivenessScore() {
        return chatbotEffectivenessScore;
    }

    public void setChatbotEffectivenessScore(String chatbotEffectivenessScore) {
        this.chatbotEffectivenessScore = chatbotEffectivenessScore;
    }
}
