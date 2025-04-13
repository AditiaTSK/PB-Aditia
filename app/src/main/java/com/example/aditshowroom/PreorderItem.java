package com.example.aditshowroom;

public class PreorderItem {
    private String mobil;
    private String pembayaran;
    private String uangMuka;
    private String warna;

    public PreorderItem() {
    }

    public PreorderItem(String mobil, String pembayaran, String uangMuka, String warna) {
        this.mobil = mobil;
        this.pembayaran = pembayaran;
        this.uangMuka = uangMuka;
        this.warna = warna;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getUangMuka() {
        return uangMuka;
    }

    public void setUangMuka(String uangMuka) {
        this.uangMuka = uangMuka;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }
}
