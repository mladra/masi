package pl.lodz.p.it.masi.stp.chatbot.collections;

import java.time.LocalDateTime;
import java.util.List;

public class MessageLog {

    private LocalDateTime angularRequestTimestamp;
    private LocalDateTime angularResponseTimestamp;
    private LocalDateTime watsonRequestTimestamp;
    private LocalDateTime watsonResponseTimestamp;
    private LocalDateTime amazonRequestTimestamp;
    private LocalDateTime amazonResponseTimestamp;

    private String userInput;
    private String watsonIntent;
    private List<String> watsonOutput;
    private long resultsCount;

    public MessageLog(LocalDateTime angularRequestTimestamp, LocalDateTime angularResponseTimestamp,
                      LocalDateTime watsonRequestTimestamp, LocalDateTime watsonResponseTimestamp,
                      LocalDateTime amazonRequestTimestamp, LocalDateTime amazonResponseTimestamp,
                      String userInput, String watsonIntent, List<String> watsonOutput,
                      long resultsCount) {
        this.angularRequestTimestamp = angularRequestTimestamp;
        this.angularResponseTimestamp = angularResponseTimestamp;
        this.watsonRequestTimestamp = watsonRequestTimestamp;
        this.watsonResponseTimestamp = watsonResponseTimestamp;
        this.amazonRequestTimestamp = amazonRequestTimestamp;
        this.amazonResponseTimestamp = amazonResponseTimestamp;
        this.userInput = userInput;
        this.watsonIntent = watsonIntent;
        this.watsonOutput = watsonOutput;
        this.resultsCount = resultsCount;
    }

    public MessageLog() {

    }

    public LocalDateTime getAngularRequestTimestamp() {
        return angularRequestTimestamp;
    }

    public void setAngularRequestTimestamp(LocalDateTime angularRequestTimestamp) {
        this.angularRequestTimestamp = angularRequestTimestamp;
    }

    public LocalDateTime getAngularResponseTimestamp() {
        return angularResponseTimestamp;
    }

    public void setAngularResponseTimestamp(LocalDateTime angularResponseTimestamp) {
        this.angularResponseTimestamp = angularResponseTimestamp;
    }

    public LocalDateTime getWatsonRequestTimestamp() {
        return watsonRequestTimestamp;
    }

    public void setWatsonRequestTimestamp(LocalDateTime watsonRequestTimestamp) {
        this.watsonRequestTimestamp = watsonRequestTimestamp;
    }

    public LocalDateTime getWatsonResponseTimestamp() {
        return watsonResponseTimestamp;
    }

    public void setWatsonResponseTimestamp(LocalDateTime watsonResponseTimestamp) {
        this.watsonResponseTimestamp = watsonResponseTimestamp;
    }

    public LocalDateTime getAmazonRequestTimestamp() {
        return amazonRequestTimestamp;
    }

    public void setAmazonRequestTimestamp(LocalDateTime amazonRequestTimestamp) {
        this.amazonRequestTimestamp = amazonRequestTimestamp;
    }

    public LocalDateTime getAmazonResponseTimestamp() {
        return amazonResponseTimestamp;
    }

    public void setAmazonResponseTimestamp(LocalDateTime amazonResponseTimestamp) {
        this.amazonResponseTimestamp = amazonResponseTimestamp;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getWatsonIntent() {
        return watsonIntent;
    }

    public void setWatsonIntent(String watsonIntent) {
        this.watsonIntent = watsonIntent;
    }

    public List<String> getWatsonOutput() {
        return watsonOutput;
    }

    public void setWatsonOutput(List<String> watsonOutput) {
        this.watsonOutput = watsonOutput;
    }

    public long getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(long resultsCount) {
        this.resultsCount = resultsCount;
    }
}
