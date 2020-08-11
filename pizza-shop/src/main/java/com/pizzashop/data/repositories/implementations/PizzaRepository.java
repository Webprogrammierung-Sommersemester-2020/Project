package com.pizzashop.data.repositories.implementations;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.pizzashop.data.models.Pizza;
import com.pizzashop.data.repositories.IPizzaRepository;

public class PizzaRepository extends BaseRepository<Pizza> implements IPizzaRepository {
    //Methoden wurden ueberschrieben, da in BaseRepository by deserialisierung von JsonArray zu Collection
    //folgendes Problemm auftritt. JsonArray deserialisiert waehrend der RunTime nicht zu z.B List<Pizza>, sondern
    // zu HashMap, dadurch kommt es auch zum Problem mit Reflection in findBy - Methode, beim direkten Einsatz in jsonb.toJson(...)
    // mit new ArrayList<Pizza> anstatt ... new ArrayList<T> laeft deserialisierung problemloes....
    @Override
    public List<Pizza> getAll() {
        var entries = new ArrayList<Pizza>();
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(Pizza.class.getSimpleName().toLowerCase() + ".json");
            Jsonb json = JsonbBuilder.create();
            entries = json.fromJson(inputStream, new ArrayList<Pizza>() {
            }.getClass().getGenericSuperclass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public List<Pizza> findBy(String propertyName, Object value) {
                
        Optional<Field> searchedField = Arrays.stream(Pizza.class.getDeclaredFields())
                .filter(f -> f.getName().toLowerCase().equals(propertyName.toLowerCase())).findFirst();

        if (searchedField.isPresent()) {
            List<Pizza> result = new ArrayList<>();
            Field field = searchedField.get();
            
            if (!Collection.class.isAssignableFrom(field.getType())) {
                List<Pizza> pizzas = this.getAll();
                for (Pizza pizza : pizzas) {
                    try {
                        Field fieldToCheck = pizza.getClass().getDeclaredField(propertyName);                        

                        fieldToCheck.setAccessible(true);
                        Object fieldsValue = getObjectValueByField(fieldToCheck, pizza);

                        if(fieldsValue.equals(value)){
                            result.add(pizza);
                        }                        

                    } catch (NoSuchFieldException   e) {
                        
                        e.printStackTrace();
                    } catch (SecurityException e) {
                       
                        e.printStackTrace();
                    }
                }
                return result;
            }
            
        }
        return null;
    }
}