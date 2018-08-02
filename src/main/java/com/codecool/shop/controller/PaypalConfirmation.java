package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/paypal-success"})
public class PaypalConfirmation extends BaseController {

    @Override
    String getHTML() {
        return "product/paying_success";
    }

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        EmailUtil.createEmailOfOrder(req, resp);
        session.removeAttribute("ShoppingCart");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("ok");
    }
}
