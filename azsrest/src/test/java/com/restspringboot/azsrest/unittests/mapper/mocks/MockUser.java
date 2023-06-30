package com.restspringboot.azsrest.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.restspringboot.azsrest.models.User;
import com.restspringboot.azsrest.vo.v1.UserVO;

public class MockUser {


    public User mockEntity() {
        return mockEntity(0);
    }
    
    public UserVO mockVO() {
        return mockVO(0);
    }
    
    public List<User> mockEntityList() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 14; i++) {
            users.add(mockEntity(i));
        }
        return users;
    }

    public List<UserVO> mockVOList() {
        List<UserVO> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockVO(i));
        }
        return users;
    }
    
    public User mockEntity(Integer number) {
        User user = new User();
        user.setAddress("Addres Test" + number);
        user.setFirstName("First Name Test" + number);
        user.setGender(((number % 2)==0) ? "Male" : "Female");
        user.setId(number.longValue());
        user.setLastName("Last Name Test" + number);
        return user;
    }

    public UserVO mockVO(Integer number) {
        UserVO user = new UserVO();
        user.setAddress("Addres Test" + number);
        user.setFirstName("First Name Test" + number);
        user.setGender(((number % 2)==0) ? "Male" : "Female");
        user.setKey(number.longValue());
        user.setLastName("Last Name Test" + number);
        return user;
    }

}
