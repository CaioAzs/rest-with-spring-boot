package com.restspringboot.azsrest.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.exceptions.ResourceNotFoundException;
import com.restspringboot.azsrest.models.User;
import com.restspringboot.azsrest.repositories.UserRepository;

@Service
public class UserService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {

        logger.info("findAll called");

        return userRepository.findAll();
    }

    public User findById(Long id) throws Exception {

        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));
    }

    public User postUser(User user) {
        logger.info("createUser called");

        return userRepository.save(user);
    }

    public User putUser(User user) {
        logger.info("updateUser called");

        var entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setGender(user.getGender());
        entity.setAddress(user.getAddress());

        return userRepository.save(entity);
    }

    public void deleteUser(Long userId) {
        logger.info("deleteUser called" + userId);

        var entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        userRepository.delete(entity);
    }

}
