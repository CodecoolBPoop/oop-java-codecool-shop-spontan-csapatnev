package com.codecool.shop.controller;


import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;



@WebServlet(urlPatterns = {"/cartTest"})
public class Dbtest extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
/*        HttpSession session = req.getSession();
        ShoppingCart sc = new ShoppingCart();
        sc.saveCartToDB(session);
        sc.setCartFromDBToSession(session, 1);
        sc.deleteCartFromDB(1);*/
    }

}
