package com.apps.indiclass.model;

/**
 * Created by Dell_Cleva on 16/10/2018.
 */

public class ProgamModel {
    private String sProgamName;
    private String sPrice;
    private String sImage;

    public ProgamModel() {

    }

    @Override
    public String toString() {
        return "ProgamsModel{" +
                "sProgamName='" + sProgamName + '\'' +
                ", sPrice='" + sPrice + '\'' +
                ", sImage='" + sImage + '\'' +
                '}';
    }

    public String getsProgamName() {
        return sProgamName;
    }

    public void setsProgamName(String sProgamName) {
        this.sProgamName = sProgamName;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }
}
