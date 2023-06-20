package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

import com.kelompok4.DB;

public class Sewa {
    private int id;
    private LocalDate tanggal;
    private String sesi;
    private int userId;
    private int lapanganId;

    public Sewa(int id, LocalDate tanggal, String sesi, int userId, int lapanganId) {
        this.id = id;
        this.tanggal = tanggal;
        this.sesi = sesi;
        this.userId = userId;
        this.lapanganId = lapanganId;
    }

    public int getId() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT id FROM sewa WHERE tanggal = ? AND sesi = ? AND id_lapangan = ?");
            statement.setDate(1, Date.valueOf(this.tanggal));
            statement.setString(2, this.sesi);
            statement.setInt(3, this.lapanganId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return -1;
    }

    public boolean sewaLapangan() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(this.tanggal + " " + this.sesi + " " + this.userId + " " + this.lapanganId);
            PreparedStatement statement = DB.prepareStatement("INSERT INTO sewa (tanggal, sesi, id_user, id_lapangan) VALUES (?, ?, ?, ?)");
            statement.setDate(1, Date.valueOf(this.tanggal));
            statement.setString(2, this.sesi);
            statement.setInt(3, this.userId);
            statement.setInt(4, this.lapanganId);
            statement.executeUpdate();
            System.out.println("Sewa berhasil!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return false;
    }

    public boolean hapus(int id) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM sewa WHERE id = ?");
            stm.setInt(1, id);
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return false;
    }

    public LocalDate getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getSesi() {
        return this.sesi;
    }

    public void setSesi(String sesi) {
        this.sesi = sesi;
    }

    public int getLapanganId() {
        return this.lapanganId;
    }

    public void setLapanganId(int lapanganId) {
        this.lapanganId = lapanganId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
