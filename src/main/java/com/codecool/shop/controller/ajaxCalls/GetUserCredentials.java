package com.codecool.shop.controller.ajaxCalls;

import com.codecool.shop.controller.PasswordHash;
import com.codecool.shop.model.Database;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/get-user-credentials"})
public class GetUserCredentials extends HttpServlet {

    private static Database db = Database.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("username");
    }

    private String getString(String username, String query, String returnField) {
        String returnString = null;
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                returnString = rs.getString(returnField);
            }
            connection.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return returnString;
    }
}
