package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseController extends HttpServlet {

    ProductDao productDataStore = ProductDaoMem.getInstance();
    ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    abstract void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        HttpSession session = req.getSession();
        ShoppingCart shoppingCart = new ShoppingCart();
        context.setVariable("shoppingCartProducts", shoppingCart.getAllProduct(session));
        context.setVariable("sumOfProducts", shoppingCart.sumOfProducts(session));
        context.setVariable("sumOfPrices", shoppingCart.sumOfPrices(session));
        try {
            addPlusContext(context, req);
            engine.process("product/index.html", context, resp.getWriter());
        } catch(ElementNotFoundException|IndexOutOfBoundsException e) {
            engine.process("product/404.html", context, resp.getWriter());
        }
    }

}
