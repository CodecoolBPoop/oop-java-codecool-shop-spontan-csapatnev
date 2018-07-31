package com.codecool.shop.model;

import java.sql.*;
import java.util.*;

public class Database {
    private static Database ourInstance = new Database();
    private final String DB_USERNAME = System.getenv("USERNAME");
    private final String DB_PASSWORD = System.getenv("PASSWORD");
    private final String DB_URL = System.getenv("DB_URL");
    private final String DB_DRIVER = System.getProperty("DB_DRIVER");


    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    private Connection connectToDatabase() {
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

    //// SQL QUERY SCHEME \\\\
/*    public String getName() {
        ResultSet rs = null;
        String name = null;
        Connection conn = connectToDatabase();
        try {
            Statement st = conn.createStatement();
            String sql;
            sql = "SELECT name FROM test WHERE name = 'tibi'";
            rs = st.executeQuery(sql);
            while (rs.next()) {name = rs.getString("name");}
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return name;
    }*/

    public String getName() {
        ResultSet rs = null;
        String name = null;
        Connection conn = connectToDatabase();
        try {
            Statement st = conn.createStatement();
            String sql;
            sql = "SELECT name FROM test WHERE name = 'tibi'";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return name;
    }
}
