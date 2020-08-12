package com.pizzashop.data.repositories.implementations;

import com.pizzashop.data.repositories.IBaseRepository;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;


public abstract class BaseRepository<T> implements IBaseRepository<T> {

    private Class<T> model;

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        this.model = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean create(T t) {
        boolean created = false;
        List<T> currentContent = this.getAll();
        currentContent.add(t);

        created = writeCollectionToJsonFile(currentContent);

        return created;
    }


    @Override
    public boolean delete(T t) {
        boolean deleted = false;
        List<T> currentContent = this.getAll();
        if (currentContent.contains(t)) {
            currentContent.remove(t);
        }
        deleted = writeCollectionToJsonFile(currentContent);
        return deleted;
    }

    @Override
    public List<T> getAll() {
        List<T> entries = new ArrayList<T>();
        try {
            InputStream is = getClass().getClassLoader()
                    .getResourceAsStream(model.getSimpleName().toLowerCase() + ".json");
            Jsonb json = JsonbBuilder.create();
            entries = json.fromJson(is, new ArrayList<T>() {
            }.getClass().getGenericSuperclass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public boolean update(T t) {
        boolean updated = false;
        List<T> currentContent = this.getAll();

        Optional<T> entry = currentContent.stream().filter(o -> o.equals(t)).findFirst();
        if (!entry.isPresent()) {
            Field[] fields = t.getClass().getDeclaredFields();
            Optional<Field> id = Arrays.stream(fields).filter(f -> f.getName().equals("id".toLowerCase())).findFirst();
            Optional<Field> name = Arrays.stream(fields).filter(f -> f.getName().equals("name".toLowerCase())).findFirst(); //name is id for Pizza and Ingredients
            List<Field> listOfIdInEntry = Arrays.stream(fields).filter(f -> (f.getName().contains("Id") || f.getName().contains("ID"))).collect(Collectors.toList()); //for example userId

            if (id.isPresent()) {
                updated = updateCollectionByFieldName(id.get(), t, currentContent);
            } else if (!id.isPresent() && name.isPresent()) {
                updated = updateCollectionByFieldName(name.get(), t, currentContent);
            } else if (!id.isPresent() && (listOfIdInEntry.size() == 1)) {
                Field namedId = listOfIdInEntry.stream().findFirst().get();
                updated = updateCollectionByFieldName(namedId, t, currentContent);
            }
        } else {
            updated = this.create(t);

        }
        return updated;
    }

    @Override
    public List<T> findBy(String propertyName, Object value) {
        Optional<Field> searchedField = Arrays.stream(this.model.getClass().getDeclaredFields())
                .filter(f -> f.getName().toLowerCase().equals(propertyName.toLowerCase())).findFirst();

        if (searchedField.isPresent()) {
            List<T> result = new ArrayList<>();
            Field field = searchedField.get();

            List<T> models = this.getAll();
            for (T model : models) {
                try {
                    Field fieldToCheck = model.getClass().getDeclaredField(propertyName);

                    if (Collection.class.isAssignableFrom(fieldToCheck.getType())) {
                        Collection fieldsValues = (Collection) getObjectValueByField(fieldToCheck, model);

                        for (var fieldValue : fieldsValues) {
                            Field[] fields = fieldValue.getClass().getDeclaredFields();
                            var results = Arrays.stream(fields).filter(f -> getObjectValueByField(f, fieldValue).equals(value));

                            if (results.count() > 0) {
                                result.add(model);
                            }
                        }
                    } else {
                        Object fieldsValue = getObjectValueByField(fieldToCheck, model);
                        if (fieldsValue.equals(value)) {
                            result.add(model);
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

    protected Object getObjectValueByField(Field field, Object o) {
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        return null;
    }

    protected boolean writeCollectionToJsonFile(Collection collection) {
        boolean wroten = false;
        File file = new File(getClass().getClassLoader().getResource(this.model.getSimpleName().toLowerCase() + ".json").getFile());
        Jsonb jsonb = JsonbBuilder.create();
        try {
            if (file.exists()) {
                file.delete();
            }
            jsonb.toJson(collection, new FileWriter(file));
            wroten = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wroten;
    }

    protected boolean updateCollectionByFieldName(Field id, T t, List<T> listToUpdate) {
        boolean updated = false;
        Object value = getObjectValueByField(id, t);
        T entry = findBy(id.getName(), value).stream().findFirst().get();
        int index = listToUpdate.indexOf(entry);
        if (index > 0) {
            listToUpdate.set(index, t);
            updated = writeCollectionToJsonFile(listToUpdate);
        }

        return updated;
    }
}