package com.restspringboot.azsrest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.models.Person;

@Service
public class PersonService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private final AtomicLong userId = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {

        logger.info("findAll requested");

        List<Person> users = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            users.add(person);
        }

        return users;
    }

    private Person mockPerson(int i) {

        logger.info("findById requested!");
        Person person = new Person();
        person.setId(userId.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Address " + i);
        person.setGender("Gender " + i);
        return person;
    }

    public Person findById(String id) {

        Person person = new Person();
        person.setId(userId.incrementAndGet());
        person.setFirstName("Caio");
        person.setLastName("Souza");
        person.setAddress("SBC");
        person.setGender("Male");

        return person;
    }
}
