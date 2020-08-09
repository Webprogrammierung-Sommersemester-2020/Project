package com.pizzashop.data.models;

import java.util.HashSet;
import java.util.Set;

public class Pizza {
    private String name;
    private Set<PizzaSize> sizes = new HashSet<>();
    private Set <Ingredient> ingredients = new HashSet<>();
    private double price;

    public Pizza() {
    }

    public Pizza(String name, Set<PizzaSize> sizes, Set<Ingredient> ingredients, double price) {
        this.name = name;
        this.sizes = sizes;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PizzaSize> getSizes() {
        return sizes;
    }

    public void setSizes(Set<PizzaSize> sizes) {
        this.sizes = sizes;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((sizes == null) ? 0 : sizes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pizza other = (Pizza) obj;
        if (ingredients == null) {
            if (other.ingredients != null)
                return false;
        } else if (!ingredients.equals(other.ingredients))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (sizes == null) {
            if (other.sizes != null)
                return false;
        } else if (!sizes.equals(other.sizes))
            return false;
        return true;
    }
    
    

}