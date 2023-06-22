package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;

public class Admin extends Account {
    public static final String ROLE = "admin";

    public Admin(String username, String password) {
        super(0, username, password);
    }

    public Admin(int id, String username, String password) {
        super(id, username, password);
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

    // ketika user register, method ini yang akan dipanggil
    public boolean tambahAkun() {
        return tambahAkun("user", 0);
    }

    public boolean tambahAkun(String role, int balance) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stm = DB.prepareStatement("SELECT username FROM akun WHERE username = ?");
            stm.setString(1, this.getUsername());
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                System.out.println("Username sudah terdaftar!");
                return false;
            }

            PreparedStatement statement = DB.prepareStatement("INSERT INTO akun (username, password, role, balance) VALUES (?, ?, ?, ?)");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            statement.setString(3, role);
            statement.setInt(4, balance);
            statement.executeUpdate();
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

    public boolean editAkun(int id, int balance) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("UPDATE akun SET username = ?, password = ?, balance = ? WHERE id = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            statement.setInt(3, balance);
            statement.setInt(4, id);
            statement.executeUpdate();
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

    public boolean hapusAkun(int id) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = DB.prepareStatement("DELETE FROM akun WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
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
