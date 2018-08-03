package com.codecool.shop.controller;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.model.Database;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/addresses"})
public class SaveBillingShippingInfo extends BaseController {

    @Override
    String getHTML() { return "product/save_billing_info"; }

    @Override
    void addPlusContext(WebContext context, HttpServletRequest req) throws ElementNotFoundException, IndexOutOfBoundsException {
        Database db = Database.getInstance();
        HttpSession session = req.getSession();
        HashMap userCredentials = new HashMap();
        try {
            ResultSet rs = db.executeQuery("SELECT * FROM billing_shipping_addresses WHERE username='" + session.getAttribute("username") + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    userCredentials.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
                }
            }
            context.setVariable("userAddresses", userCredentials);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (null == session.getAttribute("username")) {
            response.sendRedirect("/");
        } else {

            HashMap credentials = new HashMap();
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                credentials.put(paramName, request.getParameter(paramName));
            }

            String username = (String) session.getAttribute("username");
            saveUserCredentials(credentials, username);

            response.sendRedirect("/");
        }
    }

    private void saveUserCredentials(HashMap credentials, String username){
        Database db = Database.getInstance();
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM billing_shipping_addresses WHERE username=?;");
            stmt.setString(1, username);
            stmt.executeUpdate();

            stmt = connection.prepareStatement(
                    "INSERT INTO billing_shipping_addresses " +
                    "(username, " +
                    "phone_number, " +
                    "billing_country, " +
                    "billing_city, " +
                    "billing_zip, " +
                    "billing_address, " +
                    "shipping_country, " +
                    "shipping_zip, " +
                    "shipping_city, " +
                    "shipping_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            stmt.setString(1, username);
            stmt.setString(2, (String) credentials.get("phone-number"));
            stmt.setString(3, (String) credentials.get("billing-country"));
            stmt.setString(4, (String) credentials.get("billing-city"));
            stmt.setString(5, (String) credentials.get("billing-zip"));
            stmt.setString(6, (String) credentials.get("billing-address"));
            stmt.setString(7, (String) credentials.get("shipping-country"));
            stmt.setString(8, (String) credentials.get("shipping-zip"));
            stmt.setString(9, (String) credentials.get("shipping-city"));
            stmt.setString(10, (String) credentials.get("shipping-address"));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
