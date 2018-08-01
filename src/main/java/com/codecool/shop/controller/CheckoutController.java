package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.OrdersListDao;
import com.codecool.shop.dao.implementation.OrdersListDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends BaseController {

    @Override
    String getHTML() {
        return "product/checkout";
    }

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException {

        HttpSession session = req.getSession();
        List<Product> cartItems;
        cartItems = (ArrayList)session.getAttribute("ShoppingCart");
        float totalPrice = 0;
        if (cartItems != null) {
            totalPrice = calculateTotalPrice(cartItems);
        }

        context.setVariable("cartItems", cartItems);
        context.setVariable("totalPrice", totalPrice);

    }

    static float calculateTotalPrice(List<Product> productList) {
        return (float) productList
                .parallelStream()
                .mapToDouble(p -> p.getDefaultPrice()*p.getShoppingCartQuantity())
                .sum();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrdersListDao ordersDataStore = OrdersListDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Order order = ensembleOrder(req);

        HttpSession session = req.getSession();
        List<Product> cartItems;
        cartItems = (ArrayList)session.getAttribute("ShoppingCart");
        float totalPrice = Float.parseFloat(req.getParameter("total-price"));
        if (cartItems != null) {
            for (Product product : cartItems) {
                order.add(product);
            }
        }
        order.setTotalPrice(totalPrice);

        session.setAttribute("currentOrder", order);
        ordersDataStore.add(order);

        context.setVariable("totalPrice", totalPrice);
        context.setVariable("shoppingCartProducts", ShoppingCart.getAllProduct(session));
        context.setVariable("sumOfProducts", ShoppingCart.sumOfProducts(session));
        context.setVariable("sumOfPrices", ShoppingCart.sumOfPrices(session));
        engine.process("product/payment.html", context, resp.getWriter());
    }

    private Order ensembleOrder(HttpServletRequest req) {
        Order order = new Order();
        order.setName(req.getParameter("name"));
        order.setEmail(req.getParameter("email"));
        order.setPhoneNumber(req.getParameter("phone-number"));
        order.setBillingCountry(req.getParameter("billing-country"));
        order.setBillingCity(req.getParameter("billing-city"));
        order.setBillingZipCode(req.getParameter("billing-zip"));
        order.setBillingAddress(req.getParameter("billing-address"));
        order.setShippingCountry(req.getParameter("shipping-country"));
        order.setShippingCity(req.getParameter("shipping-city"));
        order.setShippingZipCode(req.getParameter("shipping-zip"));
        order.setShippingAddress(req.getParameter("shipping-address"));
        return order;
    }
}
