package com.pizzashop.data.repositories.implementations;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.pizzashop.data.repositories.IBaseRepository;


public abstract class BaseRepository<T> implements IBaseRepository<T> {
    
    private Class<T> model;
    
    @SuppressWarnings("unchecked")
    public BaseRepository() {
		this.model = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean create(T t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(T t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<T> getAll() {
        var entries = new ArrayList<T>();
        try {            
            InputStream is = getClass().getClassLoader().getResourceAsStream(model.getSimpleName().toLowerCase()+".json");
            Jsonb json = JsonbBuilder.create();
            entries = json.fromJson(is, new ArrayList<T>() {}.getClass().getGenericSuperclass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public List<T> findBy(String propertyName, Object value) {        
        return null;
    }

    @Override
    public boolean update(T t) {        
        return false;
    }
}