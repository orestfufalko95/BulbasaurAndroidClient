package com.example.orestfufalko.bulbasaurandroidclient.Model.Entity;

import android.os.Parcel;
import android.os.Parcelable;



public class GameDTO implements Parcelable {
    private int id;
    private String name;
    private String picture;
    private boolean isChosenByUser;

    public GameDTO(){}

    protected GameDTO(Parcel in) {
        id = in.readInt();
        name = in.readString();
        picture = in.readString();
        isChosenByUser = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(picture);
        parcel.writeByte((byte) (isChosenByUser ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GameDTO> CREATOR = new Parcelable.Creator<GameDTO>() {
        @Override
        public GameDTO createFromParcel(Parcel in) {
            return new GameDTO(in);
        }

        @Override
        public GameDTO[] newArray(int size) {
            return new GameDTO[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public boolean isChosenByUser() {
        return isChosenByUser;
    }
}
