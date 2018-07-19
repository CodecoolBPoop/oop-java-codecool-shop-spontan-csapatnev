package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.OrdersListDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class OrdersListDaoMem implements OrdersListDao {

    private List<Order> orderList = new ArrayList<>();
    private static OrdersListDaoMem instance = null;

    private OrdersListDaoMem() {

    }

    public static OrdersListDaoMem getInstance() {
        if (instance == null) {
            instance = new OrdersListDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        orderList.add(order);
    }

    @Override
    public Order find(int id) {
        return orderList.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order find(String name) throws ElementNotFoundException {
        return orderList.stream()
                .filter(order -> order.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void remove(int id) {
        orderList.remove(find(id));
    }

    @Override
    public List<Order> getAll() {
        return orderList;
    }
}
