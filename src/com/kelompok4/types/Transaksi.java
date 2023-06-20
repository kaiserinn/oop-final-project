package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok4.DB;

public class Transaksi {
    private int id;
    private int nominal;
    private LocalDate tanggal;
    private int sewaId;

    public Transaksi(int nominal, LocalDate tanggal, int sewaId) {
        this(0, nominal, tanggal, sewaId);
    }
    
    public Transaksi(int id, int nominal, LocalDate tanggal, int sewaId) {
        this.id = id;
        this.nominal = nominal;
        this.tanggal = tanggal;
        this.sewaId = sewaId;
    }

    public int getId() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT id FROM transaksi WHERE sewaId = ?");
            statement.setInt(1, this.sewaId);
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

    public boolean tambah() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("INSERT INTO transaksi (nominal, tanggal, id_sewa) VALUES (?, ?, ?)");
            stm.setInt(1, this.nominal);
            stm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stm.setInt(3, this.sewaId);
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

    public boolean hapus(int id) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement stm = DB.prepareStatement("DELETE FROM transaksi WHERE id = ?");
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
}
