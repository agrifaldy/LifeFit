package com.example.lifefit.CardSlide;

public class ModelCardSlide {
    Integer langLogo;
    String langName;

    public ModelCardSlide(Integer langLogo, String langName) {
        this.langLogo = langLogo;
        this.langName = langName;
    }

    public Integer getLangLogo() {
        return langLogo;
    }

    public String getLangName() {
        return langName;
    }
}
