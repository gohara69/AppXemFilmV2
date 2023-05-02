package com.example.appxemfilm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class ChuDe implements Parcelable {

    private int id;
    private String name;

    public ChuDe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChuDe(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected ChuDe(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Creator<ChuDe> CREATOR = new Creator<ChuDe>() {
        @Override
        public ChuDe createFromParcel(Parcel in) {
            return new ChuDe(in);
        }

        @Override
        public ChuDe[] newArray(int size) {
            return new ChuDe[size];
        }
    };


}
