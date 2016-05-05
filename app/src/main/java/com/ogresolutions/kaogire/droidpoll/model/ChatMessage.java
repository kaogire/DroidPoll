package com.ogresolutions.kaogire.droidpoll.model;

/**
 * Created by Njuguna on 4/22/2016.
 */
public class ChatMessage {
    public String smsBody;
    public String smsTime;
    public String smsAddress;
    public String smsPerson;

    public ChatMessage(String smsBody, String smsAddress, String smsTime, String smsPerson){
        this.smsBody = smsBody;
        this.smsAddress = smsAddress;
        this.smsTime = smsTime;
        this.smsPerson = smsPerson;
    }
    public ChatMessage(){}
}
