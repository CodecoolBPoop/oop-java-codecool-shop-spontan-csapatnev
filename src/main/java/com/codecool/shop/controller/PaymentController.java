package com.codecool.shop.controller;

import com.braintreegateway.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        AdminLog logger = AdminLog.getInstance();

        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = gateway.clientToken().generate(clientTokenRequest);
        String nonceFromTheClient = req.getParameter("payment_method_nonce");

        HttpSession session = req.getSession();

        try {
            String username;
            username = session.getAttribute("username").toString();
            context.setVariable("username", username);
        } catch (NullPointerException e) {
        }

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
        Order currentOrder;
        currentOrder = (Order)session.getAttribute("currentOrder");
        if (result.isSuccess()) {
            System.out.println("Paying success!!!");
            EmailUtil.createEmailOfOrder(req, resp);

//            context.setVariable("shoppingCartProducts", ShoppingCart.getAllProduct(session));
            context.setVariable("sumOfProducts", 0);
//            context.setVariable("sumOfPrices", ShoppingCart.sumOfPrices(session));

            currentOrder.logPaymentMethod(session, logger, "Card");
            currentOrder.logOrderDetails(session, logger);
            currentOrder.logPaymentResult(session, logger, "Success!");
            logger.writeLogsToFile(session);

            session.removeAttribute("ShoppingCart");
            engine.process("product/paying_success.html", context, resp.getWriter());
        } else {
            System.out.println("ERROR");
            System.out.println(result);

            currentOrder.logPaymentMethod(session, logger, "Card");
            currentOrder.logOrderDetails(session, logger);
            currentOrder.logPaymentResult(session, logger, "Failed!");
            currentOrder.logPaymentError(session, logger, result.getMessage());


            context.setVariable("error", result.getMessage());
            context.setVariable("shoppingCartProducts", ShoppingCart.getAllProduct(session));
            context.setVariable("sumOfProducts", ShoppingCart.sumOfProducts(session));
            context.setVariable("sumOfPrices", ShoppingCart.sumOfPrices(session));
            engine.process("product/paying_error.html", context, resp.getWriter());
        }
    }

}
