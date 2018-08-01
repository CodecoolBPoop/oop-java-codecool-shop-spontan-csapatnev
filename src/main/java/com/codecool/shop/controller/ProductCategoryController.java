package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet(urlPatterns = {"/product-categories/*"})
public class ProductCategoryController extends BaseController {

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException {
        String[] urlSplitted = req.getRequestURI().split("/");
        if (urlSplitted.length != 3) {
            throw new ElementNotFoundException("Too long many parameters for the url.");
        }
        ProductCategory productCategory = productCategoryDataStore.find(urlSplitted[2]);
        context.setVariable("products", productDataStore.getBy(productCategory));
    }

}
