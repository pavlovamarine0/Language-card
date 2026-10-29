package com.maryna.LanguageCard.Models;

import java.util.LinkedList;
import java.util.List;

public class CardModel {
    private int id;
    private String word;
    private String translation;
    private String plural;
    private LinkedList<Integer> themaIds = new LinkedList<>();
    private List<Integer> sentenceIds = List.of();
    private List<SentanceModel> sentences;

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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String trans_word) {
        this.translation = trans_word;
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

    public List<Integer> getSentenceIds() {
        return sentenceIds;
    }

    public void setSentenceIds(List<Integer> sentenceIds) {
        this.sentenceIds = sentenceIds;
    }

    public List<SentanceModel> getSentences() {
        return sentences;
    }

    public void setSentences(List<SentanceModel> sentences) {
        this.sentences = sentences;
    }
}
