package com.codecool.shop.controller;

import com.braintreegateway.*;
import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "29ccczmbyzw2ywt8",
            "2bvkksxz7d3jrztq",
            "e6ad226cd0d2f0222ce5e18172e42663"
    );

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//        WebContext context = new WebContext(req, resp, req.getServletContext());
//
//        engine.process("product/payment.html", context, resp.getWriter());
//
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = gateway.clientToken().generate(clientTokenRequest);
        String nonceFromTheClient = req.getParameter("payment_method_nonce");

        HttpSession session = req.getSession();
        List<Product> cartItems;
        cartItems = (ArrayList)session.getAttribute("ShoppingCart");
        float totalPrice = CheckoutController.calculateTotalPrice(cartItems);
        BigDecimal amount = new BigDecimal(totalPrice);

        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .paymentMethodNonce(nonceFromTheClient)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);
        if (result.isSuccess()) {
            // See result.getTarget() for details
            System.out.println("Paying success!!!");
            Order currentOrder;
            currentOrder = (Order)session.getAttribute("currentOrder");
            String toEmail = currentOrder.getEmail();
            String mailBody = engine.process("product/paying_success.html", context);
            String subject = "Thank you for your purchase " + currentOrder.getName();
            sendEmail(toEmail, mailBody, subject);

            context.setVariable("shoppingCartProducts", ShoppingCart.getAllProduct(session));
            context.setVariable("sumOfProducts", ShoppingCart.sumOfProducts(session));
            context.setVariable("sumOfPrices", ShoppingCart.sumOfPrices(session));
            engine.process("product/paying_success.html", context, resp.getWriter());

            session.invalidate();
        } else {
            // Handle errors
            System.out.println("ERROR");
            System.out.println(result);
            context.setVariable("error", result.getMessage());
            context.setVariable("shoppingCartProducts", ShoppingCart.getAllProduct(session));
            context.setVariable("sumOfProducts", ShoppingCart.sumOfProducts(session));
            context.setVariable("sumOfPrices", ShoppingCart.sumOfPrices(session));
            engine.process("product/paying_error.html", context, resp.getWriter());
        }
    }

    private void sendEmail(String toEmail, String mailBody, String subject) {
        final String fromEmail = "jakabgipsz1983@gmail.com";
        final String password = "gipsz.j4k4b";

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, subject, mailBody);

    }
}
