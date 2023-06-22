package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.kelompok4.DB;

public class User extends Account {
    private String role;
    private int balance;
    private ArrayList<Sewa> sewa = new ArrayList<Sewa>();

    public User(String username, String password) {
        super(0, username, password);
        this.balance = 0;
        this.role = "user";
    }

    public User(int id, String username, String password, int balance) {
        super(id, username, password);
        this.balance = balance;
        this.role = "user";
    }

    public User(int id, String username, String password, String role, int balance) {
        super(id, username, password);
        this.role = role;
        this.balance = balance;
    }

    @Override
    public int getDatabaseId() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT id, username, password FROM akun WHERE username = ? AND password = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
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

    public int getDatabaseBalance() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT balance FROM akun WHERE username = ? AND password = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("balance");
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

    public void setDatabaseBalance(int balance, int id) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("UPDATE akun SET balance = ? WHERE id = ?");
            statement.setInt(1, balance);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Sewa> getListSewa() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM sewa WHERE id_user = ?");
            statement.setInt(1, this.getDatabaseId());
            ResultSet resultSet = statement.executeQuery();

            int sewaId, akunId, lapanganId;
            LocalDate tanggalSewa;
            String sesi;
            while (resultSet.next()) {
                sewaId = resultSet.getInt("id");
                tanggalSewa = resultSet.getDate("tanggal").toLocalDate();
                sesi = resultSet.getString("sesi");
                akunId = resultSet.getInt("id_user");
                lapanganId = resultSet.getInt("id_lapangan");
                this.sewa.add(new Sewa(sewaId, tanggalSewa, sesi, akunId, lapanganId));
            }
            return this.sewa;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return this.sewa;
    }

    public String getRole() {
        return role;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}