package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {


    private Product getProductById(int id) {
        return ProductDaoMem.getInstance().find(id);
    }

    public void add(HttpSession session, int id) {
        ArrayList<Product> productList;

        Product product = getProductById(id);


        if (session.getAttribute("ShoppingCart") == null) {
            productList = new ArrayList<Product>();
            productList.add(product);
            session.setAttribute("ShoppingCart", productList);
        } else {
            productList = (ArrayList)session.getAttribute("ShoppingCart");
            //System.out.println(productList.toString());
            int i = 0;
            while (i < productList.size()) {
                Product p = productList.get(i);
                if (p.equals(product)) {
                    p.setShoppingCartQuantity(p.getShoppingCartQuantity() + 1);
                    break;
                }
                i++;
            }
            if (i == productList.size()) {
                productList.add(product);
            }
            session.setAttribute("ShoppingCart", productList);

        }

    }

}
