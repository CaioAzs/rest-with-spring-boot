package com.restspringboot.azsrest.unittests.mocks;

import java.util.ArrayList;
import java.util.List;

import com.restspringboot.azsrest.models.Person;
import com.restspringboot.azsrest.vo.v1.PersonVO;

public class MockPerson {


    public Person mockEntity() {
        return mockEntity(0);
    }
    
    public PersonVO mockVO() {
        return mockVO(0);
    }
    
    public List<Person> mockEntityList() {
        List<Person> users = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            users.add(mockEntity(i));
        }
        return users;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockVO(i));
        }
        return users;
    }
    
    public Person mockEntity(Integer number) {
        Person user = new Person();
        user.setAddress("Address Test" + number);
        user.setFirstName("First Name Test" + number);
        user.setGender(((number % 2)==0) ? "Male" : "Female");
        user.setId(number.longValue());
        user.setLastName("Last Name Test" + number);
        return user;
    }

    public PersonVO mockVO(Integer number) {
        PersonVO user = new PersonVO();
        user.setAddress("Address Test" + number);
        user.setFirstName("First Name Test" + number);
        user.setGender(((number % 2)==0) ? "Male" : "Female");
        user.setKey(number.longValue());
        user.setLastName("Last Name Test" + number);
        return user;
    }

}
