package com.pizzashop.data.repositories.implementations;

import com.pizzashop.data.models.Order;
import com.pizzashop.data.models.User;
import com.pizzashop.data.repositories.IUserRepository;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class UserRepository extends BaseRepository<User> implements IUserRepository {
    //Methoden wurden ueberschrieben, da in BaseRepository by deserialisierung von JsonArray zu Collection
    //folgendes Problemm auftritt. JsonArray deserialisiert waehrend der RunTime nicht zu z.B List<Pizza>, sondern
    // zu HashMap, dadurch kommt es auch zum Problem mit Reflection in findBy - Methode, beim direkten Einsatz in jsonb.toJson(...)
    // mit new ArrayList<Pizza> anstatt ... new ArrayList<T> laeuft deserialisierung problemloes....
    @Override
    public List<User> getAll() {
        var entries = new ArrayList<User>();
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(User.class.getSimpleName().toLowerCase() + ".json");
            Jsonb json = JsonbBuilder.create();
            entries = json.fromJson(inputStream, new ArrayList<Order>() {
            }.getClass().getGenericSuperclass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public List<User> findBy(String propertyName, Object value) {

        Optional<Field> searchedField = Arrays.stream(Order.class.getDeclaredFields())
                .filter(f -> f.getName().toLowerCase().equals(propertyName.toLowerCase())).findFirst();

        if (searchedField.isPresent()) {
            List<User> result = new ArrayList<>();
            Field field = searchedField.get();

            List<User> users = this.getAll();
            for (User user : users) {
                try {
                    Field fieldToCheck = user.getClass().getDeclaredField(propertyName);

                    if (Collection.class.isAssignableFrom(fieldToCheck.getType())) {

                        Collection fieldsValues = (Collection) getObjectValueByField(fieldToCheck, user);

                        for (Object fieldValue : fieldsValues) {
                            Field[] fields = fieldValue.getClass().getDeclaredFields();
                            var results = Arrays.stream(fields).filter(f -> getObjectValueByField(f, fieldValue).equals(value));

                            if (results.count() > 0) {
                                result.add(user);
                            }
                        }
                    } else {
                        Object fieldsValue = getObjectValueByField(fieldToCheck, user);

                        if (fieldsValue.equals(value)) {
                            result.add(user);
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
