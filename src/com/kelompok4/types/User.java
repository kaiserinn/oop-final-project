package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;

public class User extends Account {
    public static String role = "user";

    public User(String username, String password) {
        super(0, username, password);
    }

    public User(int id, String username, String password) {
        super(id, username, password);
    }

    public int getId() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM akun WHERE username = ? AND password = ?");
            System.out.println(this.getUsername());
            System.out.println(this.getPassword());
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

    public boolean tambahAkun() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("INSERT INTO akun (username, password, role) VALUES (?, ?, ?)");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            statement.setString(3, role);
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

    public void hapusAkun() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = DB.prepareStatement("DELETE FROM akun WHERE id = ?");
            statement.setInt(1, this.getId());
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

    public boolean login() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM akun WHERE username = ? AND password = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("username").equals(this.getUsername()) && resultSet.getString("password").equals(this.getPassword())) {
                    return true;
                } else {
                    return false;
                }
            }
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