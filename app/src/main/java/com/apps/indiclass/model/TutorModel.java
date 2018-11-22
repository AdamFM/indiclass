package com.apps.indiclass.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell_Cleva on 01/11/2018.
 */

public class TutorModel implements Parcelable {
    private String sNameTutor;
    private String sImageTutor;
    private String sPriceTutor;
    private String sExpTutor;
    private String sSubjectTutor;

    public TutorModel() {
        // Constructur
    }

    protected TutorModel(Parcel in) {
        sNameTutor = in.readString();
        sImageTutor = in.readString();
        sPriceTutor = in.readString();
        sExpTutor = in.readString();
        sSubjectTutor = in.readString();
    }

    public static final Creator<TutorModel> CREATOR = new Creator<TutorModel>() {
        @Override
        public TutorModel createFromParcel(Parcel in) {
            return new TutorModel(in);
        }

        @Override
        public TutorModel[] newArray(int size) {
            return new TutorModel[size];
        }
    };

    @Override
    public String toString() {
        return "TutorModel{" +
                "sNameTutor='" + sNameTutor + '\'' +
                ", sImageTutor=" + sImageTutor +
                ", sPriceTutor='" + sPriceTutor + '\'' +
                ", sExpTutor='" + sExpTutor + '\'' +
                ", sSubjectTutor='" + sSubjectTutor + '\'' +
                '}';
    }

    public String getsImageTutor() {
        return sImageTutor;
    }

    public void setsImageTutor(String sImageTutor) {
        this.sImageTutor = sImageTutor;
    }

    public String getsSubjectTutor() {
        return sSubjectTutor;
    }

    public void setsSubjectTutor(String sSubjectTutor) {
        this.sSubjectTutor = sSubjectTutor;
    }

    public String getsNameTutor() {
        return sNameTutor;
    }

    public void setsNameTutor(String sNameTutor) {
        this.sNameTutor = sNameTutor;
    }


    public String getsPriceTutor() {
        return sPriceTutor;
    }

    public void setsPriceTutor(String sPriceTutor) {
        this.sPriceTutor = sPriceTutor;
    }

    public String getsExpTutor() {
        return sExpTutor;
    }

    public void setsExpTutor(String sExpTutor) {
        this.sExpTutor = sExpTutor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sNameTutor);
        dest.writeString(sImageTutor);
        dest.writeString(sPriceTutor);
        dest.writeString(sExpTutor);
        dest.writeString(sSubjectTutor);
    }
}
