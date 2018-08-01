package com.codecool.shop.controller;

import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet(urlPatterns = {"/"})
public class ProductController extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        context.setVariable("products", productDataStore.getAll());
    }

}
