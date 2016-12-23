package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

/**
 * Created by Vitalii Khorobchuk on 18.12.2016.
 */

public class MessageInputDTO {
    public String receiverId;
    public String message;

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
