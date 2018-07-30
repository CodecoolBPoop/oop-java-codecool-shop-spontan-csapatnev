package com.codecool.shop.model;

import java.sql.*;
import java.util.*;

public class Database {
    private static Database ourInstance = new Database();
    Connection conn;

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    private Connection connectToDatabase() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/codecoolshop";
            conn = DriverManager.getConnection(url, "tibi", "tibi");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return conn;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            String sql;
            sql = query;
            rs = st.executeQuery(sql);
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return rs;
    }
}
