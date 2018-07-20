package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.Order;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/paypal-success"})
public class PaypalConfirmation extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException {
        renderHtml = "product/paying_success";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        EmailUtil.createEmail(req, resp);
        session.invalidate();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("ok");
    }
}
