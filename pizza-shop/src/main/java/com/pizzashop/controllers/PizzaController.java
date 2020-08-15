package com.pizzashop.controllers;

import com.pizzashop.data.models.Pizza;
import com.pizzashop.data.repositories.implementations.PizzaRepository;
import com.pizzashop.data.services.IPizzaDataService;
import com.pizzashop.data.services.implementations.PizzaDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pizza")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class PizzaController {

    private final IPizzaDataService dataService;

    public PizzaController() {
        this.dataService = new PizzaDataService(new PizzaRepository());
    }

    @GET
    @Path("/get/all")
    public Response getAllPizzas(){
        List<Pizza>  pizzas = dataService.getAllPizzas();
        if(pizzas.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pizzas).build();
    }

    @GET
    @Path("/get/name")
    public Response getPizzaByName(@QueryParam("name") final String name){
        Pizza pizza = dataService.getPizzaByName(name);
        if (pizza != null){
            return Response.ok(pizza).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/get/{parameter}")
    public Response getPizzasByIngredient(@PathParam("parameter") final String parameter,@QueryParam("value") String value){
        List<Pizza> pizzas = dataService.getPizzasBy(parameter, value);
        if (!pizzas.isEmpty()){
            return Response.ok(pizzas).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Path("/add")
    public Response addNewPizza(Pizza pizza){

        if(dataService.addNewPizza(pizza)){
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update")
    public Response updatePizza(Pizza pizza){

        if(dataService.getPizzaByName(pizza.getName()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(dataService.updateExistingPizza(pizza)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete")
    public Response deletePizzaByName(@QueryParam("name") final String name){

        if(dataService.getPizzaByName(name) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(dataService.deletePizzaByName(name)){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
