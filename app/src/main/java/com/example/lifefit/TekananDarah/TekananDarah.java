package com.example.lifefit.TekananDarah;

import com.google.firebase.auth.FirebaseAuth;

public class TekananDarah {
    private String id;
    private int tekananAtas;
    private String tekananBawah;
    private long tanggal;
    private String keterangan;
    private FirebaseAuth mAuth;

    public TekananDarah() {
    }

    public TekananDarah(String id, int tekananAtas, String tekananBawah, long tanggal, String keterangan) {
        mAuth = FirebaseAuth.getInstance();
        this.id = mAuth.getCurrentUser().getUid();
        this.tekananAtas = tekananAtas;
        this.tekananBawah = tekananBawah;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public TekananDarah(long tanggal, int tekananAtas) {
        this.tanggal = tanggal;
        this.tekananAtas = tekananAtas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTekananAtas() {
        return tekananAtas;
    }

    public void setTekananAtas(int tekananAtas) {
        this.tekananAtas = tekananAtas;
    }

    public String getTekananBawah() {
        return tekananBawah;
    }

    public void setTekananBawah(String tekananBawah) {
        this.tekananBawah = tekananBawah;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
