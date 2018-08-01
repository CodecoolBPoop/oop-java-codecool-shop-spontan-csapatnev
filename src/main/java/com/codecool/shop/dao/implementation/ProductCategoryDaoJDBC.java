package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Database;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoJDBC instance = null;
    private Database db = Database.getInstance();

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoJDBC() {
    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO product_categories (name, department, description) VALUES ("
                    + "'" + category.getName() + "', "
                    + "'" + category.getDepartment() + "', "
                    + "'" + category.getDescription() + "')");
            conn.close();
            System.out.println("Added category: " + category.getName() + "to the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    @Override
    public ProductCategory find(int id) {
        String name = null;
        String description = null;
        String department = null;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, department, description FROM product_categories " +
                    "WHERE id = " + id);
            while (rs.next()) {
                name = rs.getString("name");
                department = rs.getString("department");
                description = rs.getString("description");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        ProductCategory category = new ProductCategory(name, department, description);
        category.setId(id);

        return category;
    }

    @Override
    public ProductCategory find(String name) {
        String description = null;
        String department = null;
        int id = 0;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT id, department, description FROM product_categories " +
                    "WHERE name = " + "'" + name + "'");
            while (rs.next()) {
                department = rs.getString("department");
                description = rs.getString("description");
                id = rs.getInt("id");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        ProductCategory category = new ProductCategory(name, department, description);
        category.setId(id);

        return category;
    }

    @Override
    public void remove(int id) {
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM product_categories WHERE id = " + id);
            conn.close();
            System.out.println("Deleted category with id: " + id + "from the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        String name;
        String description;
        String department;
        int id;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM product_categories");
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                department = rs.getString("department");
                description = rs.getString("description");

                ProductCategory category = new ProductCategory(name, department, description);
                category.setId(id);

                data.add(category);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return data;
    }
}
