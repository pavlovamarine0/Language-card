package com.maryna.LanguageCard.Models;

public class SentanceModel {
    int id;
    String sentence;
    String translate;
    public int getId(){
        return id;
    }
    public String getSentence(){
        return sentence;
    }
    public String getTranslate(){
        return translate;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setSentence(String sentence){
        this.sentence = sentence;
    }
    public void setTranslate(String translate){
        this.translate = translate;
    }
}
