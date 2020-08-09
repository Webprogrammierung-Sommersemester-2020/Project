package com.pizzashop.data.repositories;

import java.util.List;

public interface IBaseRepository<T> {
    public List<T> getAll();
    public T findBy(String propertyName,  Object value);
    public boolean create(T t);
    public boolean update(T t);
    public boolean delete(T t);
}