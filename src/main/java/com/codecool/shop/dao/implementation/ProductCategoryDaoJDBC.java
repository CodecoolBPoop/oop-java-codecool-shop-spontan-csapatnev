package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoJDBC instance = null;

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
        //TODO
    }

    @Override
    public ProductCategory find(int id) {
        return null;
        //TODO
    }

    @Override
    public ProductCategory find(String name) throws ElementNotFoundException {
        return null;
        //TODO
    }

    @Override
    public void remove(int id) {
        //TODO
    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
        //TODO
    }
}
