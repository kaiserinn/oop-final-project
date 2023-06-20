package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;

public class User extends Account {
    private String role;

    public User(String username, String password) {
        super(0, username, password);
    }

    public User(int id, String username, String password) {
        super(id, username, password);
    }

    public User(int id, String username, String password, String role) {
        super(id, username, password);
        this.role = role;
    }

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

    public boolean tambahAkun() {
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

            PreparedStatement statement = DB.prepareStatement("INSERT INTO akun (username, password, role) VALUES (?, ?, ?)");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            statement.setString(3, "user");
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

    public boolean editAkun(int id) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("UPDATE akun SET username = ?, password = ? WHERE id = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            statement.setInt(3, id);
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

    public boolean login() {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = DB.prepareStatement("SELECT username, password, role FROM akun WHERE username = ? AND password = ?");
            statement.setString(1, this.getUsername());
            statement.setString(2, this.getPassword());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("username").equals(this.getUsername()) && resultSet.getString("password").equals(this.getPassword())) {
                    this.role = resultSet.getString("role");
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

    public String getRole() {
        return role;
    }
}