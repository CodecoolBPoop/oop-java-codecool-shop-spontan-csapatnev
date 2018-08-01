package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class EmailUtil {

    private static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("jakabgipsz1983@gmail.com", "Gipsz Jakab - GadgetBazár"));

            msg.setReplyTo(InternetAddress.parse("jakabgipsz1983@gmail.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8", "html");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createEmailOfOrder(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Order currentOrder;
        currentOrder = (Order)session.getAttribute("currentOrder");
        String toEmail = currentOrder.getEmail();
        Date date = new Date();
        context.setVariable("orderDate", date.toString());
        context.setVariable("products", currentOrder.getOrderedItems());
        context.setVariable("totalPrice", currentOrder.getTotalPrice());
        String mailBody = engine.process("product/email_template.html", context);
        String subject = "Thank you for your purchase " + currentOrder.getName();

        final String fromEmail = "jakabgipsz1983@gmail.com";
        final String password = "gipsz.j4k4b";

        System.out.println("TLSEmail Start");
        Session mailSession = getMailSession(fromEmail, password);

        EmailUtil.sendEmail(mailSession, toEmail, subject, mailBody);
    }

    static void createWelcomeEmail(String toEmail, String name) {
        final String fromEmail = "jakabgipsz1983@gmail.com";
        final String password = "gipsz.j4k4b";
        final String subject = "Welcome to Codecoolshop!";

        String mailBody = String.format("Thank you for registering at Codecoolshop, %s", name);

        Session mailSession = getMailSession(fromEmail, password);
        EmailUtil.sendEmail(mailSession, toEmail, subject, mailBody);

    }

    private static Session getMailSession(String fromEmail, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        return Session.getInstance(props, auth);
    }
}
