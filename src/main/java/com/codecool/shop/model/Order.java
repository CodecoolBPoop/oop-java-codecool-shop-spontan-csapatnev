package com.codecool.shop.model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private static Date currentDate = new Date();
    private int id;
    private String date;
    private String name;
    private String email;
    private String phoneNumber;
    private String billingCountry;
    private String billingCity;
    private String billingZipCode;
    private String billingAddress;
    private String shippingCountry;
    private String shippingCity;
    private String shippingZipCode;
    private String shippingAddress;
    private float totalPrice;

    private List<Product> orderedItems;

    public Order() {
        AdminLog logger = AdminLog.getInstance();
        this.id = Integer.parseInt(logger.readLatestOrderId());
        logger.increaseLatestOrderId();
        this.date = currentDate.toString();
        orderedItems = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipCode) {
        this.billingZipCode = billingZipCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingZipCode() {
        return shippingZipCode;
    }

    public void setShippingZipCode(String shippingZipCode) {
        this.shippingZipCode = shippingZipCode;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getId() {
        return id;
    }

    public List<Product> getOrderedItems() {
        return orderedItems;
    }

    public void add(Product product) {
        orderedItems.add(product);
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void logOrderDetails(HttpSession session, AdminLog logger){
        System.out.println(this);
        logger.addLog(session, "Name: " + this.name);
        logger.addLog(session, "Email: " + this.email);
        logger.addLog(session, "Phone Number: " + this.phoneNumber);
        logger.addLog(session, "Billing Country: " + this.billingCountry);
        logger.addLog(session, "Billing City: " + this.billingCity);
        logger.addLog(session, "Billing Zip code: " + this.billingZipCode);
        logger.addLog(session, "Billing Address: " + this.billingAddress);
        logger.addLog(session, "Shipping Country: " + this.shippingAddress);
        logger.addLog(session, "Shipping City: " + this.shippingCity);
        logger.addLog(session, "Shipping Zip code: " + this.shippingZipCode);
        logger.addLog(session, "Shipping Address: " + this.shippingAddress);
        logger.addLog(session, "Total Price: " + Float.toString(this.totalPrice));
    }

    public void logPaymentMethod(HttpSession session, AdminLog logger, String paymentMethod){
        logger.addLog(session, "Payment method: " + paymentMethod);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", billingCountry='" + billingCountry + '\'' +
                ", billingCity='" + billingCity + '\'' +
                ", billingZipCode='" + billingZipCode + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", shippingCountry='" + shippingCountry + '\'' +
                ", shippingCity='" + shippingCity + '\'' +
                ", shippingZipCode='" + shippingZipCode + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderedItems=" + orderedItems +
                '}';
    }

    public void logPaymentResult(HttpSession session, AdminLog logger, String paymentResult){
        logger.addLog(session, "Payment result: " + paymentResult);
    }

    public void logPaymentError(HttpSession session, AdminLog logger, String paymentError){
        logger.addLog(session, "Payment error:" + paymentError);
    }

}
