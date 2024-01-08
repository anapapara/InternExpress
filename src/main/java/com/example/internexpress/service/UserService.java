package com.example.internexpress.service;

import com.example.internexpress.domain.User;
import com.example.internexpress.repository.Repository;
import com.example.internexpress.validator.ValidationException;
import com.example.internexpress.validator.Validator;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final Repository<Long, User> repositoryUser;
    private Long freeId;
    private final Validator<User> validator;

    public UserService(Repository<Long, User> repositoryUser, Validator<User> validator) {
        this.repositoryUser = repositoryUser;
        freeId = 0L;
        this.validator = validator;
    }

    public User login(String email, String password) {
        if (repositoryUser.findloggedUser(email, password).isPresent()) {
            return repositoryUser.findloggedUser(email, password).orElse(null);
        } else {
            throw new ValidationException("Incorrect email or password.");
        }
    }

    public User signup(String firstName, String lastName, String date, String gender, String email, String password, String userType) {
        if (repositoryUser.findUser(email).isPresent()) {
            throw new ValidationException("User exist already. Try to login.");
        } else {
            User newUser = new User(firstName, lastName, date, gender, email, password, userType);
            Long id = 0L;
            int nr = 0;
            for (User user : repositoryUser.findAll()) {
                id++;
                nr++;
                if (!id.equals(user.getId())) {
                    break;
                }
            }
            if (nr == repositoryUser.findAll().size())
                id++;
            newUser.setId(id);
            repositoryUser.save(newUser);
            return newUser;
        }
    }

    private void checkId() {
        freeId = 0L;
        int nr = 0;
        for (User user : repositoryUser.findAll()) {
            freeId++;
            nr++;
            if (!freeId.equals(user.getId())) {
                break;
            }

        }
        if (nr == repositoryUser.findAll().size())
            freeId++;
    }


    public void addUser(String firstName, String lastName, String date, String gender, String email, String password, String userType) {
        User user = new User(firstName, lastName, date, gender, email, password, userType);
        validator.validate(user);
        checkId();
        user.setId(freeId);
        repositoryUser.save(user);
    }

    public List<User> getAll() {
        return repositoryUser.findAll();
    }

    public void deleteUser(String id1) {
        repositoryUser.delete(Long.valueOf(id1));
        freeId = 0L;
    }

    public User getUser(Long id) {
        List<User> list = repositoryUser.findAll();
        for (User u : list) {
            if (Objects.equals(u.getId(), id)) {
                return u;
            }
        }
        return null;
    }
}
