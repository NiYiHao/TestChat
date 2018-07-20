package com.example.administrator.testchat;

import android.content.Context;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String userPhotoUrl;

    public ChatMessage( String messageText, String messageUser,String userPhotoUrl) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.userPhotoUrl = userPhotoUrl;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}

