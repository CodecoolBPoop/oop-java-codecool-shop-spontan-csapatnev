package com.codecool.shop.controller.ajaxCalls;

import com.codecool.shop.model.Database;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/check-username"})
public class CheckUsername extends HttpServlet {

    private static Database db = Database.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().readLine();
        JSONObject data = (JSONObject) JSONValue.parse(json);
        String username = (String) data.get("username");
        String email = (String) data.get("email");
        if (usernameIsTaken(username) && emailIsTaken(email)) {
            resp.getWriter().write("both");
        } else if (usernameIsTaken(username)) {
            resp.getWriter().write("username");
        } else if (emailIsTaken(email)){
            resp.getWriter().write("email");
        } else {
            resp.getWriter().write("ok");
        }

    }

    private boolean usernameIsTaken(String username){
        String name = null;
        name = getString(username, name, "SELECT * FROM users WHERE name=?", "name");

        return name != null;
    }

    private boolean emailIsTaken(String email) {
        String userEmail = null;
        userEmail = getString(email, userEmail, "SELECT * FROM users WHERE email=?", "email");

        return userEmail != null;

    }

    private String getString(String username, String name, String query, String returnField) {
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                name = rs.getString(returnField);
            }
            connection.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return name;
    }
}
