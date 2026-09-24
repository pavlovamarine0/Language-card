package com.maryna.LanguageCard.Models;

import java.util.LinkedList;

public class CardModel {
    private int id;
    private String word;
    private String transWord;
    private String plural;
    private LinkedList<Integer> themaIds = new LinkedList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTransWord() {
        return transWord;
    }

    public void setTransWord(String trans_word) {
        this.transWord = trans_word;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public LinkedList<Integer> getThemaIds() {
        return themaIds;
    }

    public void setThemaIds(LinkedList<Integer> themaIds) {
        this.themaIds = themaIds;
    }
}
