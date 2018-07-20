package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface OrdersListDao {

    void add(Order order);
    Order find(int id);
    Order find(String name) throws ElementNotFoundException;
    void remove(int id);

    List<Order> getAll();

}
