package com.kelompok4.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kelompok4.DB;
import com.kelompok4.controllers.Global;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
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
            statement.setString(1, this.username);
            statement.setString(2, this.password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("username").equals(this.username) 
                        && resultSet.getString("password").equals(this.username)) {
                    Global.loginRole = resultSet.getString("role");
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
