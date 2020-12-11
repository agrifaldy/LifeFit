package com.example.lifefit.CardSlide;

public class ModelCardSlide {
    Integer langLogo;
    String langName;
    String langKal;

    public ModelCardSlide(Integer langLogo, String langName, String langKal) {
        this.langLogo = langLogo;
        this.langName = langName;
        this.langKal = langKal;
    }

    public Integer getLangLogo() {
        return langLogo;
    }

    public String getLangName() {
        return langName;
    }

    public String getLangKal() {return langKal;}
}
