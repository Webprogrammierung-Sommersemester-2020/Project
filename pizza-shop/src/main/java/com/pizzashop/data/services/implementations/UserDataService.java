package com.pizzashop.data.services.implementations;

import com.pizzashop.data.models.User;
import com.pizzashop.data.repositories.IUserRepository;
import com.pizzashop.data.services.IUserDataService;

import java.util.List;
import java.util.Optional;

public class UserDataService implements IUserDataService {
    private final IUserRepository repository;

    public UserDataService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = repository.getAll();
        return users.isEmpty() ? null : users;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optionalUser = repository.findBy("userId", id).stream().findFirst();
        return  optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @Override
    public boolean createUser(User user) {
        // TODO: later -> Password hashing and security implementation...
        Optional<Integer> lastGeneratedId = repository.getAll().stream().map(o->o.getUserId()).max(Integer::compareTo);
        if(lastGeneratedId.isPresent()){
            user.setUserId(lastGeneratedId.get()+1);
        }
        else{
            user.setUserId(1);
        }
        return repository.create(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return repository.delete(user);
    }

    @Override
    public boolean updateUser(User user) {
        return repository.update(user);
    }
}
