package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public class SignUpInputDTO {

    private String email;
    private String name;
    private String surname;
    private String password;

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     * The Surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
