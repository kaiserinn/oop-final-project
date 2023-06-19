package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;

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

    // public int getIdByName(String username) {
    //     try {
    //         DB.loadJDBCDriver();
    //         DB.connect();
    //     } catch (ClassNotFoundException | SQLException e) {
    //         e.printStackTrace();
    //     }

    //     try {
    //         PreparedStatement statement = DB.prepareStatement("SELECT * FROM sewa WHERE id_lapangan = ? AND sesi = ? AND tanggal = ?");
    //         statement.setInt(1, lapanganId);
    //         statement.setString(2, sesi);
    //         statement.setDate(3, java.sql.Date.valueOf(tanggal));
    //         ResultSet resultSet = statement.executeQuery();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             DB.disconnect();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

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
