package com.codecool.shop.controller;

import com.codecool.shop.model.Database;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

@WebServlet(urlPatterns = {"/dbtest"})
public class Dbtest extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        Database db = Database.getInstance();


        context.setVariable("name", db.getName());
        renderHtml = "product/db";
    }

}
