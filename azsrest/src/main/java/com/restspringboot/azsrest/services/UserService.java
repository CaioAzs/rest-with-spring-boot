package com.restspringboot.azsrest.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.exceptions.ResourceNotFoundException;
import com.restspringboot.azsrest.mapper.Mapper;
import com.restspringboot.azsrest.models.User;
import com.restspringboot.azsrest.repositories.UserRepository;
import com.restspringboot.azsrest.vo.v1.UserVO;

@Service
public class UserService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository userRepository;

    public List<UserVO> findAll() {

        logger.info("findAll called");

        return Mapper.parseObjectList(userRepository.findAll(), UserVO.class);
    }

    public UserVO findById(Long id) throws Exception {

        var entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        return Mapper.parseObject(entity, UserVO.class);
    }

    public UserVO postUser(UserVO user) {
        logger.info("createUser called");
        var entity1 = Mapper.parseObject(user, User.class);
        var vo = Mapper.parseObject(userRepository.save(entity1), UserVO.class);
        return vo;
    }

    public UserVO putUser(UserVO user) {
        logger.info("updateUser called");

        var entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setGender(user.getGender());
        entity.setAddress(user.getAddress());

        var vo = Mapper.parseObject(userRepository.save(entity), UserVO.class);

        return vo;
    }

    public void deleteUser(Long userId) {
        logger.info("deleteUser called" + userId);

        var entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        userRepository.delete(entity);
    }

}
