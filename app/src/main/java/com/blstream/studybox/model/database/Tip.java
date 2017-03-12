package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Tip implements Parcelable {

    @Expose
    public String idCard;

    @Expose
    public String essence;

    @Expose
    public int difficult;

    public Tip(String idCard, String essence, int difficult) {
        this.idCard = idCard;
        this.essence = essence;
        this.difficult = difficult;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEssence() {
        return essence;
    }

    public void setEssence(String essence) {
        this.essence = essence;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCard);
        dest.writeString(essence);
        dest.writeInt(difficult);
    }

    public static final Creator<Tip> CREATOR = new Creator<Tip>() {
        @Override
        public Tip createFromParcel(Parcel source) {
            return new Tip(source);
        }

        @Override
        public Tip[] newArray(int size) {
            return new Tip[size];
        }
    };

    private Tip(Parcel source) {
        idCard = source.readString();
        essence = source.readString();
        difficult = source.readInt();
    }
}
