package com.pizzashop.data.services.implementations;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.repositories.IOrderRepository;
import com.pizzashop.data.services.IOrderDataService;

import java.util.List;
import java.util.Optional;

public class OrderDataService implements IOrderDataService {
    private IOrderRepository repository;

    public OrderDataService(IOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = repository.getAll();
        return orders.isEmpty() ? null : orders;
    }

    @Override
    public Order getOrderById(int id) {
        Optional<Order> order = repository.findBy("id", id).stream().findFirst();
        return order.isPresent() ? order.get() : null;
    }

    @Override
    public boolean createOrder(Order order) {
        return repository.create(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        return deleteOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return repository.update(order);
    }
}
