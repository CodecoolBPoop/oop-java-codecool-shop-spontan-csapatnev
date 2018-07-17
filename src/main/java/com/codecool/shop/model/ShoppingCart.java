package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class ShoppingCart {


    private Product getProductById(int id) {
        return ProductDaoMem.getInstance().find(id);
    }

    public void add(HttpSession session, int id) {
        Product product = getProductById(id);
        session.setAttribute("ShoppingCart", product);
        Object currentCart = session.getAttribute("ShoppingCart");
    }





}
