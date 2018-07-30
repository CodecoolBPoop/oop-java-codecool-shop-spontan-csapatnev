package com.codecool.shop.controller;

import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = {"/dbtest"})
public class Dbtest extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        context.setVariable("dbtest", "asd");
        renderHtml = "product/db";
    }

}
