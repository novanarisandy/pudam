package com.example.aplikasikelolaasetpudam;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelAset implements Parcelable {
    String nama;

    protected ModelAset(Parcel in) {
        nama = in.readString();
    }

    public static final Creator<ModelAset> CREATOR = new Creator<ModelAset>() {
        @Override
        public ModelAset createFromParcel(Parcel in) {
            return new ModelAset(in);
        }

        @Override
        public ModelAset[] newArray(int size) {
            return new ModelAset[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
    }
}
