package com.pizzashop.data.services;

import com.pizzashop.data.models.User;

import java.util.List;

public interface IUserDataService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public boolean createUser(User user);
    public boolean deleteUser(User user);
    public boolean updateUser(User user);
}
