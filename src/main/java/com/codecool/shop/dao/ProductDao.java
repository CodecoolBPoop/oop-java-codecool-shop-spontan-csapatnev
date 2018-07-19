package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import javax.xml.bind.Element;
import java.util.List;

public interface ProductDao {

    void add(Product product);
    Product find(int id) throws ElementNotFoundException;
    void remove(int id);

    List<Product> getAll();
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);

}
