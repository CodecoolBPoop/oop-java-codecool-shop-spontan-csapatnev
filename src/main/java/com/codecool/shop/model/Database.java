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

    public ResultSet executeQuery(String query) throws SQLException {
        try (Connection connection = connectToDatabase()) {

            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();

        } catch (SQLTimeoutException se) {
            System.err.println(se.getMessage());
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {
        try (Connection connection = connectToDatabase()) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLTimeoutException se) {
            System.err.println(se.getMessage());
        }
    }
}