package com.kelompok4.types;

public class Lapangan {
    private int id;
    private String nama;
    private String harga;

    public Lapangan() {
        this.id = 0;
        this.nama = "";
        this.harga = "";
    }

    public Lapangan(int id, String nama, String harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return this.harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
