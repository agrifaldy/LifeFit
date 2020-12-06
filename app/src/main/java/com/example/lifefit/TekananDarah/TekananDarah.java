package com.example.lifefit.TekananDarah;

import com.google.firebase.auth.FirebaseAuth;

public class TekananDarah {
    private String id;
    private String tekananAtas;
    private String tekananBawah;
    private String tanggal;
    private String keterangan;
    private FirebaseAuth mAuth;

    public TekananDarah() {
    }

    public TekananDarah(String id, String tekananAtas, String tekananBawah, String tanggal, String keterangan) {
        mAuth = FirebaseAuth.getInstance();
        this.id = mAuth.getCurrentUser().getUid();
        this.tekananAtas = tekananAtas;
        this.tekananBawah = tekananBawah;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTekananAtas() {
        return tekananAtas;
    }

    public void setTekananAtas(String tekananAtas) {
        this.tekananAtas = tekananAtas;
    }

    public String getTekananBawah() {
        return tekananBawah;
    }

    public void setTekananBawah(String tekananBawah) {
        this.tekananBawah = tekananBawah;
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
