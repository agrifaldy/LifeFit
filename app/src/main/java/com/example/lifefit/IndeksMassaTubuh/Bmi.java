package com.example.lifefit.IndeksMassaTubuh;

public class Bmi {
    private String id;
    private String berat;
    private String tinggi;
    private String tanggal;
    private String imt;
    private String keterangan;

    public Bmi() {
    }

    public Bmi(String id, String berat, String tinggi, String tanggal, String imt, String keterangan) {
        this.id = id;
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
