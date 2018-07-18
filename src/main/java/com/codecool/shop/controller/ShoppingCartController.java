package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCartController extends BaseController {

    private final String ACTION_ADD = "add";
    private final String ACTION_REMOVE = "remove";

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        context.setVariable("products", productDataStore.getAll());
        String action = req.getParameter("action");
        int productId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        if (action != null) {
            if (action.equals(ACTION_ADD)) {
                ShoppingCart.add(session, productId);
            } else if (action.equals(ACTION_REMOVE)) {
                ShoppingCart.remove(session, productId, false);
            }
        }
    }
}
