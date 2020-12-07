package com.example.lifefit.DenyutJantung;

import com.google.firebase.auth.FirebaseAuth;

public class DenyutJantung {

    private String id;
    private String denyutJantung;
    private String tanggal;
    private String keterangan;
    private FirebaseAuth mAuth;

    public DenyutJantung() {
    }

    public DenyutJantung(String id, String denyutJantung, String tanggal, String keterangan) {
        mAuth = FirebaseAuth.getInstance();
        this.id = mAuth.getCurrentUser().getUid();
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
