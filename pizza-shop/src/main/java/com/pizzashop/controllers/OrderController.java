package com.pizzashop.controllers;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.repositories.implementations.OrderRepository;
import com.pizzashop.data.services.IOrderDataService;
import com.pizzashop.data.services.implementations.OrderDataService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Path("/order")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class OrderController {
    private IOrderDataService dataService;

    public OrderController() {
        this.dataService = new OrderDataService(new OrderRepository());
    }

    @GET
    @Path("/get/all")
    public Response getAllOrders(){
        List<Order> orders = dataService.getAllOrders();
        if(orders.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(orders).build();
    }

    @GET
    @Path("/get/{id}")
    public Response getOrderById(@PathParam("id") int id){
        Order order = dataService.getOrderById(id);
        if(order == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(order).build();
    }

    @GET
    @Path("/get/user")
    public Response getOrdersByUser(@QueryParam("id") int id){
        List<Order> orders = dataService.getOrdersByUser(id);
        if(orders.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else if(!orders.isEmpty()){
            return Response.ok(orders).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/create")
    public Response createOrder(Order order){
        if(dataService.createOrder(order)){
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update")
    public Response updateOrder(Order order){
        if(dataService.getOrderById(order.getId()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(dataService.updateOrder(order)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteOrder(Order order){
        if(dataService.getOrderById(order.getId()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(dataService.deleteOrder(order)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteOrderById(@PathParam("id") int id){
        if(dataService.getOrderById(id) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(dataService.deleteOrderById(id)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}