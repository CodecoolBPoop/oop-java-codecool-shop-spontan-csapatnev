package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Database;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private List<Product> data = new ArrayList<>();
    private static ProductDaoJDBC instance = null;
    private Database db = Database.getInstance();
    private ProductCategoryDao categoryDao = ProductCategoryDaoJDBC.getInstance();
    private SupplierDao supplierDao = SupplierDaoJDBC.getInstance();

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJDBC() {
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        Connection conn = db.connectToDatabase();

        Supplier supplier = null;
        int supplier_id = 0;

        ProductCategory category = null;
        int category_id = 0;

        try {

            supplier = supplierDao.find(product.getSupplier().getName());
            supplier_id = supplier.getId();

            category = categoryDao.find(product.getProductCategory().getName());
            category_id = category.getId();

        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }


        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO product (name, description, image, supplier_id, currency, price, category_id) VALUES ("
                    + "'" + product.getName() + "', "
                    + "'" + product.getDescription() + "', "
                    + "'product_" + product.getId() + ".png', "
                    + supplier_id + ", "
                    + "'" + product.getDefaultCurrency() + "', "
                    + product.getDefaultPrice() + ", "
                    + category_id + ")");
            conn.close();
            System.out.println("Added product: " + product.getName() + " to the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    @Override
    public Product find(int id) {
        String name = null;
        String description = null;
        Supplier supplier = null;
        ProductCategory productCategory = null;
        String currency = null;
        int defaultPrice = 0;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, description, supplier_id, " +
                    "category_id, currency, price FROM product " +
                    "WHERE id = " + id);
            while (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
                supplier = supplierDao
                        .find(rs.getInt("supplier_id"));
                productCategory = categoryDao
                        .find(rs.getInt("category_id"));
                currency = rs.getString("currency");
                defaultPrice = rs.getInt("price");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        Product prod = new Product(name, defaultPrice, currency, description, productCategory, supplier);
        prod.setId(id);
        return prod;
    }

    @Override
    public void remove(int id) {
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM product WHERE id = " + id);
            conn.close();
            System.out.println("Deleted product with id: " + id + " from the database.");
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {
        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, id, description, supplier_id, " +
                    "category_id, currency, price FROM product");
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = supplierDao
                        .find(rs.getInt("supplier_id"));
                ProductCategory productCategory = categoryDao
                        .find(rs.getInt("category_id"));
                String currency = rs.getString("currency");
                int defaultPrice = rs.getInt("price");
                int id = rs.getInt("id");
                Product prod = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                prod.setId(id);
                data.add(prod);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, id, description, supplier_id, " +
                    "category_id, currency, price FROM product WHERE supplier_id = " + supplier.getId());
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                ProductCategory productCategory = categoryDao
                        .find(rs.getInt("category_id"));
                String currency = rs.getString("currency");
                int defaultPrice = rs.getInt("price");
                int id = rs.getInt("id");
                Product prod = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                prod.setId(id);
                data.add(prod);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return data;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT name, id, description, supplier_id, " +
                    "category_id, currency, price FROM product WHERE category_id = " + productCategory.getId());
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = supplierDao
                        .find(rs.getInt("supplier_id"));
                String currency = rs.getString("currency");
                int defaultPrice = rs.getInt("price");
                int id = rs.getInt("id");
                Product prod = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                prod.setId(id);
                data.add(prod);
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return data;
    }

    public String getProductImageName(Product product) {
        String imageName = null;

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT image FROM product " +
                    "WHERE id = " + product.getId());
            while (rs.next()) {
                imageName = rs.getString("image");
            }
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return imageName;
    }

}
