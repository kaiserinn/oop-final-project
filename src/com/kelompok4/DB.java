package com.kelompok4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
    private static final String url = "jdbc:mysql://localhost:3306/tubes_oop";
    private static final String user = "root";
    private static final String password = "";

    private static Connection conn;

    public static void loadJDBCDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    public static void disconnect() throws SQLException {
        conn.close();
    }

    public static PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }
}