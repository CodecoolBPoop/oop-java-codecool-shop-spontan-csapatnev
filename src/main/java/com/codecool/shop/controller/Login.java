package com.codecool.shop.controller;

import com.codecool.shop.model.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String email = req.getParameter("email");
        String name = getName(email);

        HttpSession session = req.getSession();
        session.setAttribute("username", name);
        resp.sendRedirect("/");
    }

    private String getName(String email) {
        String name = null;
        Database db = Database.getInstance();
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT name FROM users WHERE email=?;");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
            connection.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return name;
    }
}

