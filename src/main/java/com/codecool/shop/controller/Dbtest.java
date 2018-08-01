package com.codecool.shop.controller;

import com.codecool.shop.config.BaseDatabaseFiller;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;


@WebServlet(urlPatterns = {"/adminFillBaseDatabase"})
public class Dbtest extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) {
        BaseDatabaseFiller dbFiller = new BaseDatabaseFiller();
        dbFiller.fillDatabase();
    }

}
