package com.pizzashop.data.services;

import com.pizzashop.data.models.Pizza;

import java.io.Serializable;
import java.util.List;

public interface IPizzaDataService extends Serializable {
    public List<Pizza> getAllPizzas();

    public Pizza getPizzaByName(String pizzaName);

    public List<Pizza> getPizzasBy(String parameter, String value);

    public boolean addNewPizza(Pizza pizza);

    public boolean updateExistingPizza(Pizza pizza);

    public boolean deletePizza(Pizza pizza);

    public boolean deletePizzaByName(String name);
}
