package com.codecool.shop.controller;

import com.codecool.shop.model.Database;

import javax.servlet.ServletException;
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

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = PasswordHash.hashPassword(req.getParameter("password"));

        saveUser(name, password, email);
        EmailUtil.createWelcomeEmail(email, name);
        HttpSession session = req.getSession();
        session.setAttribute("username", name);
        resp.sendRedirect("/");
    }

    private void saveUser(String name, String password, String email) {
        Database db = Database.getInstance();
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (name, password, email) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }
}
