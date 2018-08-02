package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Database;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SupplierDaoJDBC implements SupplierDao {
    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoJDBC instance = null;
    private Database db = Database.getInstance();

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoJDBC() {
    }

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            String sql;
            sql = "INSERT INTO suppliers (name, description) VALUES ("
                    + "'" + supplier.getName() + "', "
                    + "'" + supplier.getDescription() + "')";
            st.executeUpdate(sql);
            conn.close();
            System.out.println("Added supplier: " + supplier.getName() + " to the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }


    @Override
    public Supplier find(int id) {
        String name = null;
        String description = null;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, description FROM suppliers WHERE id = " + id);
            while (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        Supplier supplier = new Supplier(name, description);
        supplier.setId(id);

        return supplier;
    }

    @Override
    public Supplier find(String name) throws ElementNotFoundException {
        String description = null;
        int id = 0;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT id, description FROM suppliers WHERE name = " + "'" + name + "'");
            while (rs.next()) {
                description = rs.getString("description");
                id = rs.getInt("id");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        Supplier supplier = new Supplier(name, description);
        supplier.setId(id);

        return supplier;
    }

    @Override
    public void remove(int id) {
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM suppliers WHERE id = " + id);
            conn.close();
            System.out.println("Deleted supplier with id: " + id + " from the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    @Override
    public List<Supplier> getAll() {
        String description;
        int id;
        String name;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        data.clear();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM suppliers");
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                description = rs.getString("description");

                Supplier supplier = new Supplier(name, description);
                supplier.setId(id);

                data.add(supplier);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return data;
    }


}
