package com.pizzashop.data.services.implementations;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.repositories.IOrderRepository;
import com.pizzashop.data.services.IOrderDataService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    public List<Order> getOrdersByUser(int id) {
        List<Order> orders = repository.findBy("user", id);
        return orders.isEmpty() ? null : orders;
    }

    @Override
    public boolean createOrder(Order order) {
        LocalDate localDate = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalTime localTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String currentDate = localDate.format(dateFormatter).toString();
        String currentTime = localTime.format(timeFormatter).toString();

        order.setDate(currentDate);
        order.setTime(currentTime);

        Optional<Integer> lastGeneratedId = repository.getAll().stream().map(o -> o.getId()).max(Integer::compareTo);
        if (lastGeneratedId.isPresent()) {
            order.setId(lastGeneratedId.get() + 1);
        } else {
            order.setId(1);
        }

        return repository.create(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        return repository.delete(order);
    }

    @Override
    public boolean deleteOrderById(int id) {
        Optional<Order> optionalPizza = repository.findBy("id", id).stream().findFirst();
        return optionalPizza.isPresent() ? deleteOrder(optionalPizza.get()) : false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return repository.update(order);
    }
}
