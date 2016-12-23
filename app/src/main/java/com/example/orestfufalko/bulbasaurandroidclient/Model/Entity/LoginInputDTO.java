package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

/**
 * Created by Виталий on 06.12.2016.
 */

public class LoginInputDTO {
    private String email;
    private String password;
    private final String clientSecret;
    private final String clientId;
    private final String scope;
    private final String returnUrl;

    public LoginInputDTO() {
        this.clientSecret = "secret";
        this.clientId = "ro.client";
        this.scope = "api1";
        this.returnUrl = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
