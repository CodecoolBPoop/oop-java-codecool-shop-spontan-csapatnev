package com.codecool.shop.controller.ajaxCalls;

import com.codecool.shop.model.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/save-shopping-cart"})
public class SaveShoppingCart extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.saveCartToDB(session);
    }

}
