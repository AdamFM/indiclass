package com.apps.indiclass.model;

/**
 * Created by Dell_Cleva on 17/10/2018.
 */

public class MyClassModel {

    private String sClassID;
    private String sNama;
    private String sSubject;
    private String sTime;
    private String sImage;
    private String sDateStart;
    private String sDateEnd;
    private long expiredTime;

    public MyClassModel() {

    }

    @Override
    public String toString() {
        return "MyClassModel{" +
                "sClassID='" + sClassID + '\'' +
                ", sNama='" + sNama + '\'' +
                ", sSubject='" + sSubject + '\'' +
                ", sTime='" + sTime + '\'' +
                ", sImage='" + sImage + '\'' +
                ", sDateStart='" + sDateStart + '\'' +
                ", sDateEnd='" + sDateEnd + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }

    public String getsDateStart() {
        return sDateStart;
    }

    public void setsDateStart(String sDateStart) {
        this.sDateStart = sDateStart;
    }

    public String getsDateEnd() {
        return sDateEnd;
    }

    public void setsDateEnd(String sDateEnd) {
        this.sDateEnd = sDateEnd;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getsClassID() {
        return sClassID;
    }

    public void setsClassID(String sClassID) {
        this.sClassID = sClassID;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getsNama() {
        return sNama;
    }

    public void setsNama(String sNama) {
        this.sNama = sNama;
    }

    public String getsSubject() {
        return sSubject;
    }

    public void setsSubject(String sSubject) {
        this.sSubject = sSubject;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }
}
