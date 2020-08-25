package com.example.aplikasikelolaasetpudam.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelAset implements Parcelable {

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
    private int harga, tahun, tarif;
    private String kode, nama, satuan, volume, sumberdana, golongan,
        status, kondisi, lokasi, keterangan, image_url;

    public ModelAset() {
    }

    public ModelAset(int harga, int tahun, int tarif, String kode, String nama, String satuan, String volume, String sumberdana, String golongan, String status, String kondisi, String lokasi, String keterangan, String image_url) {
        this.harga = harga;
        this.tahun = tahun;
        this.tarif = tarif;
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.volume = volume;
        this.sumberdana = sumberdana;
        this.golongan = golongan;
        this.status = status;
        this.kondisi = kondisi;
        this.lokasi = lokasi;
        this.keterangan = keterangan;
        this.image_url = image_url;
    }

    protected ModelAset(Parcel in) {
        kode = in.readString();
        nama = in.readString();
        satuan = in.readString();
        volume = in.readString();
        harga = in.readInt();
        tahun = in.readInt();
        sumberdana = in.readString();
        tarif = in.readInt();
        golongan = in.readString();
        status = in.readString();
        kondisi = in.readString();
        lokasi = in.readString();
        keterangan = in.readString();
        image_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kode);
        dest.writeString(nama);
        dest.writeString(satuan);
        dest.writeString(volume);
        dest.writeInt(harga);
        dest.writeInt(tahun);
        dest.writeString(sumberdana);
        dest.writeInt(tarif);
        dest.writeString(golongan);
        dest.writeString(status);
        dest.writeString(kondisi);
        dest.writeString(lokasi);
        dest.writeString(keterangan);
        dest.writeString(image_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter
    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
    // Setter

    public String getSumberdana() {
        return sumberdana;
    }

    public void setSumberdana(String sumberdana) {
        this.sumberdana = sumberdana;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
