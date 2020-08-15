package com.pizzashop.data.repositories.implementations;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.repositories.IOrderRepository;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class OrderRepository extends BaseRepository<Order> implements IOrderRepository {
    //Methoden wurden ueberschrieben, da in BaseRepository by deserialisierung von JsonArray zu Collection
    //folgendes Problemm auftritt. JsonArray deserialisiert waehrend der RunTime nicht zu z.B List<Pizza>, sondern
    // zu HashMap, dadurch kommt es auch zum Problem mit Reflection in findBy - Methode, beim direkten Einsatz in jsonb.toJson(...)
    // mit new ArrayList<Pizza> anstatt ... new ArrayList<T> laeft deserialisierung problemloes....
    @Override
    public List<Order> getAll() {
        var entries = new ArrayList<Order>();
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(Order.class.getSimpleName().toLowerCase() + ".json");
            Jsonb json = JsonbBuilder.create();
            entries = json.fromJson(inputStream, new ArrayList<Order>() {
            }.getClass().getGenericSuperclass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public List<Order> findBy(String propertyName, Object value) {

        Optional<Field> searchedField = Arrays.stream(Order.class.getDeclaredFields())
                .filter(f -> f.getName().toLowerCase().equals(propertyName.toLowerCase())).findFirst();

        if (searchedField.isPresent()) {
            List<Order> result = new ArrayList<>();
            Field field = searchedField.get();

            List<Order> orders = this.getAll();
            for (Order order : orders) {
                try {
                    Field fieldToCheck = order.getClass().getDeclaredField(propertyName);

                    if (Collection.class.isAssignableFrom(fieldToCheck.getType())) {

                        Collection fieldsValues = (Collection) getObjectValueByField(fieldToCheck, order);

                        for (Object fieldValue : fieldsValues) {
                            Field[] fields = fieldValue.getClass().getDeclaredFields();
                            var results = Arrays.stream(fields).filter(f -> getObjectValueByField(f, fieldValue).equals(value));

                            if (results.count() > 0) {
                                result.add(order);
                            }
                        }
                    } else {
                        Object fieldsValue = getObjectValueByField(fieldToCheck, order);

                        if (fieldsValue.equals(value)) {
                            result.add(order);
                        }
                    }

                } catch (NoSuchFieldException e) {

                    e.printStackTrace();
                } catch (SecurityException e) {

                    e.printStackTrace();
                }
            }
            return result;
        }
        return null;
    }
}
