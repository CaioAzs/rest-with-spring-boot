package com.restspringboot.azsrest.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.restspringboot.azsrest.mapper.DozerMapper;
import com.restspringboot.azsrest.models.User;
import com.restspringboot.azsrest.unittests.mocks.MockUser;
import com.restspringboot.azsrest.vo.v1.UserVO;


public class MapperConverterTest {
    
    MockUser inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
    }

    @Test
    public void parseEntityToVOTest() {
        UserVO output = DozerMapper.parseObject(inputObject.mockEntity(), UserVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<UserVO> outputList = DozerMapper.parseObjectList(inputObject.mockEntityList(), UserVO.class);
        UserVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        UserVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        
        UserVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        User output = DozerMapper.parseObject(inputObject.mockVO(), User.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<User> outputList = DozerMapper.parseObjectList(inputObject.mockVOList(), User.class);
        User outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        User outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        
        User outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }
}
