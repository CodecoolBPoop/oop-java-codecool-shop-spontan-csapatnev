package com.codecool.shop.model;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {


    public static final String SHOPPING_CART = "ShoppingCart";

    public static Product getProductById(int id) {
        try {
            return ProductDaoMem.getInstance().find(id);
        } catch (ElementNotFoundException e) {
            return null;
        }
    }

    public static void add(HttpSession session, int id) {
        ArrayList<Product> productList;
        Product product = getProductById(id);

        if (session.getAttribute(SHOPPING_CART) == null) {
            productList = new ArrayList<Product>();
            productList.add(product);
            session.setAttribute(SHOPPING_CART, productList);
        } else {
            productList = (ArrayList)session.getAttribute(SHOPPING_CART);
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
                product.setShoppingCartQuantity(1);
                productList.add(product);
            }
            session.setAttribute(SHOPPING_CART, productList);
        }
    }

    public static void remove(HttpSession session, int id, boolean removeAll){
        ArrayList<Product> productList = (ArrayList) session.getAttribute(SHOPPING_CART);
        Product productToRemove = getProductById(id);
        int i = 0;
        while (i < productList.size()){
            if(productList.get(i).equals(productToRemove)) {
                Product foundProduct = productList.get(i);
                foundProduct.setShoppingCartQuantity(foundProduct.getShoppingCartQuantity()-1);
                if( foundProduct.getShoppingCartQuantity() == 0 || removeAll)
                    productList.remove(i);
                break;
            }
            i++;
        }
        session.setAttribute(SHOPPING_CART,productList);
    }

    public static float sumOfPrices(HttpSession session) {
        ArrayList<Product> productList = (ArrayList) session.getAttribute(SHOPPING_CART);
        if (productList == null) {
            return 0;
        }
        float sum = 0;
        for (Product p : productList) {
            sum += p.getShoppingCartQuantity() * p.getDefaultPrice();
        }
        return sum;
    }

    public static int sumOfProducts(HttpSession session){
        ArrayList<Product> productList = (ArrayList)session.getAttribute(SHOPPING_CART);
        if (productList == null) {
            return 0;
        }
        int sum= 0;
        for(Product p : productList) {
            sum += p.getShoppingCartQuantity();
        }
        return sum;
    }

    public static List<Product> getAllProduct(HttpSession session) {
        return (ArrayList)session.getAttribute(SHOPPING_CART);
    }

    public void saveToDatabase(HttpSession session) {
        ArrayList<Product> productList = (ArrayList)session.getAttribute(SHOPPING_CART);
        Database db = Database.getInstance();

        String username = (String) session.getAttribute("username");
        int userId = db.baseQuery("id", "users", "name", "username");

        ResultSet rs;
        Connection conn = db.connectToDatabase();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(String.format("INSERT INTO shopping_cart (user_id) VALUES (%d)", userId));
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        int shoppingCartId = db.baseQuery("id", "shopping_cart", "user_id", userId);

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO cart_content (user_id) VALUES (" + userId + ")");
            conn.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

}

