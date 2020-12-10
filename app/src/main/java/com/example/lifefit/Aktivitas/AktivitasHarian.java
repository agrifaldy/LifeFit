package com.example.lifefit.Aktivitas;

import com.google.firebase.auth.FirebaseAuth;

public class AktivitasHarian {

    private String id;
    private String makan;
    private String minum;
    private String tidur;
    private String olahraga;
    private String tanggal;
    private String keterangan;
    private FirebaseAuth mAuth;

    public AktivitasHarian() {
    }

    public AktivitasHarian(String id, String makan, String minum, String tidur, String olahraga, String tanggal, String keterangan) {
        mAuth = FirebaseAuth.getInstance();
        this.id = mAuth.getCurrentUser().getUid();
        this.makan = makan;
        this.minum = minum;
        this.tidur = tidur;
        this.olahraga = olahraga;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMakan() {
        return makan;
    }

    public void setMakan(String makan) {
        this.makan = makan;
    }

    public String getMinum() {
        return minum;
    }

    public void setMinum(String minum) {
        this.minum = minum;
    }

    public String getTidur() {
        return tidur;
    }

    public void setTidur(String tidur) {
        this.tidur = tidur;
    }

    public String getOlahraga() {
        return olahraga;
    }

    public void setOlahraga(String olahraga) {
        this.olahraga = olahraga;
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
