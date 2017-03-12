package com.blstream.studybox.model.database;

import com.google.gson.annotations.Expose;

import java.text.Collator;
import java.util.Locale;

public class Deck implements Comparable<Deck> {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private Boolean isPublic;
    @Expose
    private String creatorEmail;
    @Expose
    private int flashcardsCount;

    private static final Collator collator = Collator.getInstance(Locale.getDefault());

    public Deck(String id, String name, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getDeckId() {
        return id;
    }

    public void setDeckId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public int getFlashcardsCount() {
        return flashcardsCount;
    }

    public void setFlashcardsCount(int flashcardsCount) {
        this.flashcardsCount = flashcardsCount;
    }

    @Override
    public int compareTo(Deck another) {
        return collator.compare(name, another.getName());
    }
}

