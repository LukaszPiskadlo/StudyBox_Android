package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Card implements Parcelable {

    @Expose
    public String id;

    @Expose
    public String question;

    @Expose
    public String answer;

    @Expose
    public String deckId;

    public Card(String id, String question, String answer, String deckId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.deckId = deckId;
    }

    public String getDeckId() {
        return deckId;
    }

    public String getCardId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(deckId);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    private Card(Parcel source) {
        id = source.readString();
        question = source.readString();
        answer = source.readString();
        deckId = source.readString();
    }
}
