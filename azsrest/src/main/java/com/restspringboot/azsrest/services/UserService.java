package com.restspringboot.azsrest.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.controllers.UserController;
import com.restspringboot.azsrest.exceptions.ResourceNotFoundException;
import com.restspringboot.azsrest.mapper.DozerMapper;
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

        var users = DozerMapper.parseObjectList(userRepository.findAll(), UserVO.class);

        // HATEOAS self
        users.stream().forEach(u -> {
            try {
                u.add(linkTo(methodOn(UserController.class).findById(u.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    public UserVO findById(Long id) throws Exception {

        var entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        // Map to VO
        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());

        return vo;
    }

    public UserVO postUser(UserVO user) throws Exception {
        logger.info("createUser called");
        var entity1 = DozerMapper.parseObject(user, User.class);

        // Map to VO
        var vo = DozerMapper.parseObject(userRepository.save(entity1), UserVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public UserVO putUser(UserVO user) throws Exception {
        logger.info("updateUser called");

        var entity = userRepository.findById(user.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setGender(user.getGender());
        entity.setAddress(user.getAddress());

        //Map to VO
        var vo = DozerMapper.parseObject(userRepository.save(entity), UserVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void deleteUser(Long userId) {
        logger.info("deleteUser called" + userId);

        var entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        userRepository.delete(entity);
    }

}
