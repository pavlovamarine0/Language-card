package com.maryna.LanguageCard.Models;

public class SentanceModel {
    int id;
    String text;
    String translate;
    public int getId(){
        return id;
    }
    public String getText(){
        return text;
    }
    public String getTranslate(){
        return translate;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setTranslate(String translate){
        this.translate = translate;
    }
}
