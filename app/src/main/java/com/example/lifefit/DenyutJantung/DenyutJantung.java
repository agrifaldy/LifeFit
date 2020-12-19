package com.example.lifefit.DenyutJantung;

import com.google.firebase.auth.FirebaseAuth;

public class DenyutJantung {

    private String id;
    private String key;
    private String denyutJantung;
    private String tanggal;
    private String keterangan;
    private FirebaseAuth mAuth;

    public DenyutJantung() {
    }

    public DenyutJantung(String id, String key, String denyutJantung, String tanggal, String keterangan) {
        this.id = id;
        this.key = key;
        this.denyutJantung = denyutJantung;
        this.tanggal = tanggal;
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

    public String getDenyutJantung() {
        return denyutJantung;
    }

    public void setDenyutJantung(String denyutJantung) {
        this.denyutJantung = denyutJantung;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
