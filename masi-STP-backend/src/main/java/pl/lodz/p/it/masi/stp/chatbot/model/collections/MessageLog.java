package pl.lodz.p.it.masi.stp.chatbot.model.collections;

import java.sql.Timestamp;

public class MessageLog {

    private Timestamp angularRequestTimestamp;
    private Timestamp angularResponseTimestamp;
    private Timestamp watsonRequestTimestamp;
    private Timestamp watsonResponseTimestamp;
    private Timestamp amazonRequestTimestamp;
    private Timestamp amazonResponseTimestamp;

    private String userInput;
    private String watsonIntent;
    private String watsonOutput;
    private long resultsCount;

    public MessageLog(Timestamp angularRequestTimestamp, Timestamp angularResponseTimestamp,
                      Timestamp watsonRequestTimestamp, Timestamp watsonResponseTimestamp,
                      Timestamp amazonRequestTimestamp, Timestamp amazonResponseTimestamp,
                      String userInput, String watsonIntent, String watsonOutput, long resultsCount) {
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

    public Timestamp getAngularRequestTimestamp() {
        return angularRequestTimestamp;
    }

    public void setAngularRequestTimestamp(Timestamp angularRequestTimestamp) {
        this.angularRequestTimestamp = angularRequestTimestamp;
    }

    public Timestamp getAngularResponseTimestamp() {
        return angularResponseTimestamp;
    }

    public void setAngularResponseTimestamp(Timestamp angularResponseTimestamp) {
        this.angularResponseTimestamp = angularResponseTimestamp;
    }

    public Timestamp getWatsonRequestTimestamp() {
        return watsonRequestTimestamp;
    }

    public void setWatsonRequestTimestamp(Timestamp watsonRequestTimestamp) {
        this.watsonRequestTimestamp = watsonRequestTimestamp;
    }

    public Timestamp getWatsonResponseTimestamp() {
        return watsonResponseTimestamp;
    }

    public void setWatsonResponseTimestamp(Timestamp watsonResponseTimestamp) {
        this.watsonResponseTimestamp = watsonResponseTimestamp;
    }

    public Timestamp getAmazonRequestTimestamp() {
        return amazonRequestTimestamp;
    }

    public void setAmazonRequestTimestamp(Timestamp amazonRequestTimestamp) {
        this.amazonRequestTimestamp = amazonRequestTimestamp;
    }

    public Timestamp getAmazonResponseTimestamp() {
        return amazonResponseTimestamp;
    }

    public void setAmazonResponseTimestamp(Timestamp amazonResponseTimestamp) {
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

    public String getWatsonOutput() {
        return watsonOutput;
    }

    public void setWatsonOutput(String watsonOutput) {
        this.watsonOutput = watsonOutput;
    }

    public long getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(long resultsCount) {
        this.resultsCount = resultsCount;
    }
}
