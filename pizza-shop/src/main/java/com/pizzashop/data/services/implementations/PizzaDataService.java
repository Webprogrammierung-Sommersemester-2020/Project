package com.pizzashop.data.services.implementations;

import com.pizzashop.data.models.Pizza;
import com.pizzashop.data.repositories.IPizzaRepository;
import com.pizzashop.data.services.IPizzaDataService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class PizzaDataService implements IPizzaDataService {

    private IPizzaRepository repository;

    public PizzaDataService(IPizzaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = repository.getAll();
        return (pizzas != null) ? pizzas : null;
    }

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        Optional<Pizza> pizza = repository.findBy("name", pizzaName).stream().findFirst();

        return pizza.isPresent() ? pizza.get() : null;
    }
    public List<Pizza> getPizzasBy(String parameter, String value){
        List<Pizza> result = repository.findBy(parameter, value );
        return result.isEmpty()? Collections.emptyList() : result;
    }

    @Override
    public boolean addNewPizza(Pizza pizza) {
        return repository.create(pizza);
    }

    @Override
    public boolean updateExistingPizza(Pizza pizza) {
        return repository.update(pizza);
    }

    @Override
    public boolean deletePizza(Pizza pizza) {
        return repository.delete(pizza);
    }

    @Override
    public boolean deletePizzaByName(String name) {
        Optional<Pizza> optionalPizza= repository.findBy("name", name).stream().findFirst();
        return optionalPizza.isPresent() ? deletePizza(optionalPizza.get()) : false;
    }
}
