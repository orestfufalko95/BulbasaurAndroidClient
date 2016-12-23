package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDTO implements Parcelable {

    private String email;
    private String name;
    private String surname;
    private int id;
    private String picture;
    private String address;


    private String about;
    private boolean online;
    private String sex;

    public UserDTO(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPicture() {
        return picture;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }


    public boolean isOnline() {
        return online;
    }


    public void setOnline(boolean online) {
        this.online = online;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    protected UserDTO(Parcel in) {
        email = in.readString();
        name = in.readString();
        surname = in.readString();
        id = in.readInt();
        picture = in.readString();
        address = in.readString();
        about = in.readString();
        online = in.readByte() != 0x00;
        sex = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeInt(id);
        parcel.writeString(picture);
        parcel.writeString(address);
        parcel.writeString(about);
        parcel.writeByte((byte) (online ? 0x01 : 0x00));
        parcel.writeString(sex);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };
}