package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ShoppingCart;
import jdk.nashorn.internal.ir.debug.JSONWriter;
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
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCartController extends BaseController {

    private static final java.util.stream.Collectors Collectors = ;
    private final String ACTION_ADD = "add";
    private final String ACTION_REMOVE = "remove";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }

        if (action.equals(ACTION_ADD)) {
            ShoppingCart.add(session, productId);
        } else if (action.equals(ACTION_REMOVE)) {
            if (req.getParameter("all") != null) {
                if (req.getParameter("all").equals("true")) {
                    ShoppingCart.remove(session, productId, true);
                    return;
                }
            }
            ShoppingCart.remove(session, productId, false);
        }*/

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONObject answer = new JSONObject();

        resp.getWriter().write(answer.toString());
    }
}

/*    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        context.setVariable("products", productDataStore.getAll());
        String action = req.getParameter("action");
        int productId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        if (action != null) {
            if (action.equals(ACTION_ADD)) {
                ShoppingCart.add(session, productId);
            } else if (action.equals(ACTION_REMOVE)) {
                if (req.getParameter("all") != null) {
                    if (req.getParameter("all").equals("true")) {
                        ShoppingCart.remove(session, productId, true);
                        return;
                    }
                }
                ShoppingCart.remove(session, productId, false);
            }
        }
    }
}*/
