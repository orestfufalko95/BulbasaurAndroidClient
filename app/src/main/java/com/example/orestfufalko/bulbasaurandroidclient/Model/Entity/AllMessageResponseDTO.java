package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import java.util.ArrayList;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class AllMessageResponseDTO {
    private String friendName;
    private String currentUserName;
    private String friendSurame;
    private String friendImageUrl;
    private String currentUserImageUrl;
    private ArrayList<Message> messages;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getFriendSurame() {
        return friendSurame;
    }

    public void setFriendSurame(String friendSurame) {
        this.friendSurame = friendSurame;
    }

    public String getFriendImageUrl() {
        return friendImageUrl;
    }

    public void setFriendImageUrl(String friendImageUrl) {
        this.friendImageUrl = friendImageUrl;
    }

    public String getCurrentUserImageUrl() {
        return currentUserImageUrl;
    }

    public void setCurrentUserImageUrl(String currentUserImageUrl) {
        this.currentUserImageUrl = currentUserImageUrl;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
