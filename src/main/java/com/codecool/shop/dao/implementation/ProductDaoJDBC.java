package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Database;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoJDBC implements ProductDao {
    private List<Product> data = new ArrayList<>();
    private static ProductDaoJDBC instance = null;
    Database db = Database.getInstance();

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
        //TODO
    }

    @Override
    public Product find(int id) throws ElementNotFoundException {
        return null;
        //TODO
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public List<Product> getAll() {
        return null;
        //TODO
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
        //TODO
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
        //TODO
    }

}
