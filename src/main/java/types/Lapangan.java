package main.java.types;

import java.util.HashMap;

public class Lapangan {
    private int id;
    private String nama;
    private String harga;
    private String status;

    public Lapangan() {
        this.id = 0;
        this.nama = "";
        this.harga = "";
        this.status = "";
    }

    public Lapangan(int id, String nama, String harga, String status) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.status = status;
    }

    public void tambahLapangan() {
        
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
