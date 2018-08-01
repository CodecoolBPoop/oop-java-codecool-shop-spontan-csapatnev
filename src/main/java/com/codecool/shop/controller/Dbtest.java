package com.codecool.shop.controller;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
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


        SupplierDao test = SupplierDaoJDBC.getInstance();
        test.getAll();

    }

    @Override
    String getHTML() {
        return "product/db";
    }
}
