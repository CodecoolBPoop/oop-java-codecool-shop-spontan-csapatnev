package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    public void remove(HttpSession session, int id, boolean removeAll){
        ArrayList<Product> productList = (ArrayList) session.getAttribute("ShoppingCart");
        Product productToRemove = getProductById(id);
        int i = 0;
        while (i < productList.size()){
            if(productList.get(i).equals(productToRemove)) {
                Product foundProduct = productList.get(i);
                foundProduct.setShoppingCartQuantity(foundProduct.getShoppingCartQuantity()-1);
                if( foundProduct.getShoppingCartQuantity() == 0 || removeAll)
                    productList.remove(i);
            }
            i++;
        }
        session.setAttribute("ShoppingCart",productList);
    }

    public float sumOfPrices(HttpSession session) {
        ArrayList<Product> productList = (ArrayList) session.getAttribute("ShoppingCart");
        float sum = 0;
        for (Product p : productList) {
            sum += p.getShoppingCartQuantity() * p.getDefaultPrice();
        }
        return sum;
    }

    public int sumOfProducts(HttpSession session){
        ArrayList<Product> productList = (ArrayList)session.getAttribute("ShoppingCart");
        int sum= 0;
        for(Product p : productList) {
            sum += p.getShoppingCartQuantity();
        }
        return sum;
    }
}

