package com.example.lifefit.IndeksMassaTubuh;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Bmi {
    private String id;
    private String key;
    private String berat;
    private String tinggi;
    private String tanggal;
    private String imt;
    private String keterangan;
    private FirebaseAuth mAuth;

    public Bmi() {

    }

    public Bmi(String id, String key, String berat, String tinggi, String tanggal, String imt, String keterangan) {
        this.id = id;
        this.key = key;
        this.berat = berat;
        this.tinggi = tinggi;
        this.tanggal = tanggal;
        this.imt = imt;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getImt() {
        return imt;
    }

    public void setImt(String imt) {
        this.imt = imt;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
