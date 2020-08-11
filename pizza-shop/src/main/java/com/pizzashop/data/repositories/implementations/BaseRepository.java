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
        // TODO Auto-generated method stub
        return false;
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
    public List<T> findBy(String propertyName, Object value) {
        Optional<Field> searchedField = Arrays.stream(this.model.getClass().getDeclaredFields())
                .filter(f -> f.getName().toLowerCase().equals(propertyName.toLowerCase())).findFirst();

        if (searchedField.isPresent()) {
            List<T> result = new ArrayList<>();
            Field field = searchedField.get();

            if (!Collection.class.isAssignableFrom(field.getType())) {
                List<T> models = this.getAll();
                for (T model : models) {
                    try {
                        Field fieldToCheck = model.getClass().getDeclaredField(propertyName);

                        fieldToCheck.setAccessible(true);
                        Object fieldsValue = getObjectValueByField(fieldToCheck, model);

                        if (fieldsValue.equals(value)) {
                            result.add(model);
                        }

                    } catch (NoSuchFieldException e) {

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

    @Override
    public boolean update(T t) {
        return false;
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
    protected boolean writeCollectionToJsonFile(Collection collection){
        boolean wroten = false;
        File file = new File(getClass().getClassLoader().getResource(this.model.getSimpleName().toLowerCase() + ".json").getFile());
        Jsonb jsonb = JsonbBuilder.create();
        try {
            if(file.exists()){
                file.delete();
            }
            jsonb.toJson(collection, new FileWriter(file));
            wroten = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wroten;
    }
}