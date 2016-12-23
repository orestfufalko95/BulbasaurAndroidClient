package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

/**
 * Created by orestfufalko on 03.12.2016.
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDTO implements Parcelable {

    public String getTokenResponse() {
        return tokenResponse;
    }

    private String tokenResponse;
    private UserDTO userDTO;
    private List<UserDTO> friends = new ArrayList<>();
    private List<GameDTO> games = new ArrayList<>();

    public UserProfileDTO(){}

    public void setTokenResponse(String tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<UserDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDTO> friends) {
        this.friends = friends;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }


    protected UserProfileDTO(Parcel in) {
        tokenResponse = in.readString();
        userDTO = (UserDTO) in.readValue(UserDTO.class.getClassLoader());
        if (in.readByte() == 0x01) {
            friends = new ArrayList<>();
            in.readList(friends, UserDTO.class.getClassLoader());
        } else {
            friends = null;
        }
        if (in.readByte() == 0x01) {
            games = new ArrayList<>();
            in.readList(games, GameDTO.class.getClassLoader());
        } else {
            games = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tokenResponse);
        dest.writeValue(userDTO);
        if (friends == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(friends);
        }
        if (games == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(games);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserProfileDTO> CREATOR = new Parcelable.Creator<UserProfileDTO>() {
        @Override
        public UserProfileDTO createFromParcel(Parcel in) {
            return new UserProfileDTO(in);
        }

        @Override
        public UserProfileDTO[] newArray(int size) {
            return new UserProfileDTO[size];
        }
    };
}
