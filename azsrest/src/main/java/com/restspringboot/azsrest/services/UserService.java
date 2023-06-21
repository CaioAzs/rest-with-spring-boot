package com.restspringboot.azsrest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.models.User;

@Service
public class UserService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private final AtomicLong userId = new AtomicLong();
    private Logger logger = Logger.getLogger(UserService.class.getName());

    public List<User> findAll() {

        logger.info("findAll called");

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 8; i++) {
            User user = mockPerson(i);
            users.add(user);
        }

        return users;
    }

    public User findById(String id) {

        User user = new User();
        user.setId(userId.incrementAndGet());
        user.setFirstName("Caio");
        user.setLastName("Souza");
        user.setAddress("SBC");
        user.setGender("Male");

        return user;
    }

    public User postUser(User user) {
        logger.info("createUser called");

        return user;
    }

    public User putUser(User user) {
        logger.info("updateUser called");

        return user;
    }

    public void deleteUser (String userId) {
        logger.info("deleteUser called"  + userId);

    }

    private User mockPerson(int i) {

        logger.info("findById called!");
        User user = new User();
        user.setId(userId.incrementAndGet());
        user.setFirstName("Person name " + i);
        user.setLastName("Last name " + i);
        user.setAddress("Address " + i);
        user.setGender("Gender " + i);
        return user;
    }
}
