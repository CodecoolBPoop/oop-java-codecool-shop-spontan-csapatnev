package com.codecool.shop.model;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private static final String SHOPPING_CART = "ShoppingCart";
    private Database db = Database.getInstance();
    private ProductDao productDao = ProductDaoJDBC.getInstance();

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
            productList = new ArrayList<>();
            productList.add(product);
            session.setAttribute(SHOPPING_CART, productList);
        } else {
            productList = (ArrayList)session.getAttribute(SHOPPING_CART);
            int i = 0;
            while (i < productList.size()) {
                Product p = productList.get(i);
                if (p.getId() == product.getId()) {
                    p.setShoppingCartQuantity(p.getShoppingCartQuantity() + 1);
                    break;
                }
                i++;
            }
            if (i == productList.size()) {
                product.setShoppingCartQuantity(1);
                productList.add(product);
            }
            System.out.println(productList);
            session.setAttribute(SHOPPING_CART, productList);
        }
    }

    public static void remove(HttpSession session, int id, boolean removeAll){
        ArrayList<Product> productList = (ArrayList) session.getAttribute(SHOPPING_CART);
        Product productToRemove = getProductById(id);
        int i = 0;
        while (i < productList.size()){
            if (productList.get(i).getId() == productToRemove.getId()) {
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public static List<Product> getAllProduct(HttpSession session) {
        return (ArrayList)session.getAttribute(SHOPPING_CART);
    }

    @SuppressWarnings("unchecked")
    public void saveCartToDB(HttpSession session) {
        ArrayList<Product> productList = (ArrayList)session.getAttribute(SHOPPING_CART);
        String username = (String) session.getAttribute("username");
        int userId = getUserId(username);
        System.out.println(username);
        System.out.println(userId);
        if (productList != null) {
            for (Product prod : productList) {
                try {
                    db.executeUpdate(String.format(
                            "INSERT INTO shopping_cart " +
                                    "VALUES (%d, %d, %d) ",
                            userId, prod.getId(), prod.getShoppingCartQuantity()));
                } catch (SQLException se) {
                    System.err.println(se.getMessage());
                }
            }
        }
        session.removeAttribute(SHOPPING_CART);
        session.setAttribute(SHOPPING_CART, getCartFromDB(session));
    }

    private List<Product> getCartFromDB (HttpSession session) {
        List<Product> result = new ArrayList<>();
        int userId = getUserIdFromSession(session);

        try (ResultSet rs = db.executeQuery(String.format(
                "SELECT product_id, product_quantity FROM shopping_cart " +
                "WHERE user_id = %d", userId))) {
            while(rs.next()) {
                try {
                    Product prod = productDao.find(rs.getInt("product_id"));
                    prod.setShoppingCartQuantity(rs.getInt("product_quantity"));
                    result.add(prod);
                } catch (ElementNotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return result;
    }

    public void deleteCartFromDB(HttpSession session) {
        int userId = getUserIdFromSession(session);

        System.err.println(userId);

        try  {
            db.executeUpdate(String.format(
                    "DELETE FROM shopping_cart WHERE user_id = %d", userId));
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }

    public void setCartFromDBToSession (HttpSession session) {
        List<Product> products = getCartFromDB(session);
        session.setAttribute(SHOPPING_CART, products);
    }

    private int getUserId(String username) {
        int userId = 0;
        try (ResultSet rs = db.executeQuery(String.format(
                "SELECT id FROM users " +
                "WHERE name = '%s'", username))) {
            while (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return userId;
    }

    private int getUserIdFromSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return getUserId(username);
    }
}

