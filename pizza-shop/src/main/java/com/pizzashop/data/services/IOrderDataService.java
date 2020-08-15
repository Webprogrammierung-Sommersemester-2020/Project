package com.pizzashop.data.services;

import com.pizzashop.data.models.Order;

import java.util.List;

public interface IOrderDataService {
    public List<Order> getAllOrders();
    public Order getOrderById(int id);
    public List<Order> getOrdersByUser(int id);
    public boolean createOrder(Order order);
    public boolean deleteOrder(Order order);
    public boolean deleteOrderById(int id);
    public boolean updateOrder(Order order);
}
