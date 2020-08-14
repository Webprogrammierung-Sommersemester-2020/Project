package com.pizzashop.data.services;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.models.User;

import java.util.List;

public interface IOrderDataService {
    public List<Order> getAllUsers();
    public Order getOrderById(int id);
    public boolean createOrder(Order order);
    public boolean deleteOrder(Order order);
    public boolean updateOrder(Order order);
}
