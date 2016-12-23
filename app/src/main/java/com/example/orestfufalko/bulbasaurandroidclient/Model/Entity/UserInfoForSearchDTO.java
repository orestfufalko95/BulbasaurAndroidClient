package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class UserInfoForSearchDTO implements Parcelable {

    public UserInfoForSearchDTO(){};

    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String surName;

    public String getSurName() { return this.surName; }

    public void setSurName(String surName) { this.surName = surName; }

    private String photoUrl;

    public String getPhotoUrl() { return this.photoUrl; }

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public static ArrayList<UserInfoForSearchDTO> getFromUserDTOList(ArrayList<UserDTO> list){

        ArrayList<UserInfoForSearchDTO> userInfoForSearchDTOs = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            UserInfoForSearchDTO searchDTO = new UserInfoForSearchDTO();
            UserDTO userDTO = list.get(i);

            searchDTO.setId(userDTO.getId());
            searchDTO.setName(userDTO.getName());
            searchDTO.setSurName(userDTO.getSurname());
            searchDTO.setPhotoUrl(userDTO.getPicture());

            userInfoForSearchDTOs.add(searchDTO);
        }

        return userInfoForSearchDTOs;
    }

    protected UserInfoForSearchDTO(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surName = in.readString();
        photoUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surName);
        dest.writeString(photoUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserInfoForSearchDTO> CREATOR = new Parcelable.Creator<UserInfoForSearchDTO>() {
        @Override
        public UserInfoForSearchDTO createFromParcel(Parcel in) {
            return new UserInfoForSearchDTO(in);
        }

        @Override
        public UserInfoForSearchDTO[] newArray(int size) {
            return new UserInfoForSearchDTO[size];
        }
    };
}