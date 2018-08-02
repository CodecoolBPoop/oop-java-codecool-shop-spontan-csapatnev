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
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/verify-user"})
public class VerifyUser extends HttpServlet {

    private static Database db = Database.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().readLine();
        JSONObject data = (JSONObject) JSONValue.parse(json);
        String email = (String) data.get("email");
        String password = (String) data.get("password");
        if (!validEmailAddress(email)) {
            resp.getWriter().write("not ok");
        } else {
            String hashedPassword = getHashedPassword(email);
            if (PasswordHash.checkPassword(password, hashedPassword)) {
                resp.getWriter().write("ok");
            } else {
                resp.getWriter().write("not ok");
            }
        }
    }

    private String getHashedPassword(String email) {
        String pw = null;
        pw = getString(email, "SELECT password FROM users WHERE email=?;", "password");
        return pw;
    }

    private boolean validEmailAddress(String email) {
        String loginEmail = null;
        loginEmail = getString(email, "SELECT * FROM users WHERE email=?;", "email");
        return loginEmail != null;
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
