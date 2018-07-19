package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.ShoppingCart;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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


        if (action.equals(ACTION_ADD)) {
            ShoppingCart.add(session, productId);
        } else if (action.equals(ACTION_REMOVE)) {
            if (removeAll) {
                ShoppingCart.remove(session, productId, true);
                return;
            }
            ShoppingCart.remove(session, productId, false);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(answer.toJSONString());
    }


    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
    }
}
