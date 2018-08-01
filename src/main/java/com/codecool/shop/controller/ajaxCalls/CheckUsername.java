package com.codecool.shop.controller.ajaxCalls;

import com.codecool.shop.model.Database;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/check-username"})
public class CheckUsername extends HttpServlet {

    private static Database db = Database.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().readLine();
        JSONObject data = (JSONObject) JSONValue.parse(json);
        String username = (String) data.get("username");
        if (usernameIsFree(username)) {
            resp.getWriter().write("ok");
        } else {
            resp.getWriter().write("not ok");
        }

    }

    private boolean usernameIsFree(String username){
        String name = null;
        Connection connection = db.connectToDatabase();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE name=?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
            connection.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

        return name == null;

    }
}
