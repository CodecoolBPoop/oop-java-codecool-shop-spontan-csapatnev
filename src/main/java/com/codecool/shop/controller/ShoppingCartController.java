package com.codecool.shop.controller;

import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.ShoppingCart;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCartController extends BaseController {

    private final String ACTION_ADD = "add";
    private final String ACTION_REMOVE = "remove";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String json = req.getReader().readLine();

        JSONObject answer = (JSONObject) JSONValue.parse(json);
        int productId = Integer.parseInt((String) answer.get("id"));
        answer.put("name", ShoppingCart.getProductById(productId).getName());
        answer.put("price", ShoppingCart.getProductById(productId).getPriceNum());
        String action = (String) answer.get("action");
        boolean removeAll = (boolean) answer.get("removeAll");

        //admin logging
        AdminLog logger = AdminLog.getInstance();
        if (session.getAttribute("AdminLog") == null) {
            session.setAttribute("AdminLog", new LinkedHashMap<Long, String>());
        }

        if (action.equals(ACTION_ADD)) {
            ShoppingCart.add(session, productId);
            logger.addLog(session, "Product ID: " + Integer.toString(productId) + " added to cart");
        } else if (action.equals(ACTION_REMOVE)) {
            if (removeAll) {
                ShoppingCart.remove(session, productId, true);
                logger.addLog(session, "Removed everything from cart");

            } else {
                ShoppingCart.remove(session, productId, false);
                logger.addLog(session, "Product ID: " + Integer.toString(productId) + " removed from cart");
            }
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(answer.toJSONString());
    }


    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
    }
}



