package com.codecool.shop.model;

import java.sql.*;

public class Database {
    private static Database Instance = new Database();
    private final String DB_USERNAME = System.getenv("USERNAME");
    private final String DB_PASSWORD = System.getenv("PASSWORD");
    private final String DB_URL = System.getenv("DB_URL");
    private final String DB_DRIVER = System.getenv("DB_DRIVER");

    public static Database getInstance() {
        return Instance;
    }

    private Database() {
    }

    public Connection connectToDatabase() {
        Connection conn = null;
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return conn;
    }

    public String baseQuery(String target, String tableName, String where, String what) {

        ResultSet rs;
        Connection conn = connectToDatabase();
        String result = null;

        try {
            Statement st = conn.createStatement();
            String sql = String.format("SELECT %s FROM %s WHERE %s = %s", target, tableName, where, what);
            rs = st.executeQuery(sql);
            while (rs.next()) {
                result = rs.getString(target);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return result;
    }

    public int baseQuery(String target, String tableName, String where, int what) {

        ResultSet rs;
        Connection conn = connectToDatabase();
        int result = 0;

        try {
            Statement st = conn.createStatement();
            String sql = String.format("SELECT %s FROM %s WHERE %s = %d", target, tableName, where, what);
            rs = st.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt(target);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return result;
    }
}