package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import java.sql.Date;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class LastMessageDTO {
    private int friendId;
    private String name;
    private String surName;
    private String time;
    private String photoUrl;
    private String message;
    private boolean isRead;
    private MessageSenderShortInfo senderShortInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public MessageSenderShortInfo getSenderShortInfo() {
        return senderShortInfo;
    }

    public void setSenderShortInfo(MessageSenderShortInfo senderShortInfo) {
        this.senderShortInfo = senderShortInfo;
    }
    public class MessageSenderShortInfo {
        private String senderName;
        private String senderSurname;
        private String senderPhotoUrl;

        public String getSenderSurname() {
            return senderSurname;
        }

        public void setSenderSurname(String senderSurname) {
            this.senderSurname = senderSurname;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderPhotoUrl() {
            return senderPhotoUrl;
        }

        public void setSenderPhotoUrl(String senderPhotoUrl) {
            this.senderPhotoUrl = senderPhotoUrl;
        }
    }
}
