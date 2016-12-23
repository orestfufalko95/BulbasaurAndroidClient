package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import java.io.File;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class EditProfileDTO {
    private String Email;
    private String Name;
    private String Surname;
    private File Photo;
    private String Address;
    private String About;
    private byte Sex;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public File getPhoto() {
        return Photo;
    }

    public void setPhoto(File photo) {
        Photo = photo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public byte isSex() {
        return Sex;
    }

    public void setSex(byte sex) {
        Sex = sex;
    }
}
