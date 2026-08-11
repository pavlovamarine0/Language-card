package com.maryna.LanguageCard.Models;

public class CardModel {
    int id;
    String word;
    String trans_word;
    String plural;

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
        return trans_word;
    }

    public void setTransWord(String trans_word) {
        this.trans_word = trans_word;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }
}
