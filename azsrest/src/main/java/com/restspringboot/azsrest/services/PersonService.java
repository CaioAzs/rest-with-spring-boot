package com.restspringboot.azsrest.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.controllers.PersonController;
import com.restspringboot.azsrest.exceptions.RequiredObjectNullException;
import com.restspringboot.azsrest.exceptions.ResourceNotFoundException;
import com.restspringboot.azsrest.mapper.DozerMapper;
import com.restspringboot.azsrest.models.Person;
import com.restspringboot.azsrest.repositories.PersonRepository;
import com.restspringboot.azsrest.vo.v1.PersonVO;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository userRepository;

    public List<PersonVO> findAll() {

        logger.info("findAll called");

        var users = DozerMapper.parseObjectList(userRepository.findAll(), PersonVO.class);

        // HATEOAS self
        users.stream().forEach(u -> {
            try {
                u.add(linkTo(methodOn(PersonController.class).findById(u.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    public PersonVO findById(Long id) throws Exception {

        var entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        // Map to VO
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public PersonVO postPerson(PersonVO user) throws Exception {

        if (user == null)
            throw new RequiredObjectNullException();

        logger.info("createUser called");
        var entity1 = DozerMapper.parseObject(user, Person.class);

        // Map to VO
        var vo = DozerMapper.parseObject(userRepository.save(entity1), PersonVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public PersonVO putPerson(PersonVO user) throws Exception {

        if (user == null)
            throw new RequiredObjectNullException();

        logger.info("updateUser called");

        var entity = userRepository.findById(user.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setGender(user.getGender());
        entity.setAddress(user.getAddress());

        // Map to VO
        var vo = DozerMapper.parseObject(userRepository.save(entity), PersonVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void deletePerson(Long userId) {
        logger.info("deleteUser called" + userId);
        
        var entity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));
        
        
        userRepository.delete(entity);
    }
    
    @Transactional
    public PersonVO disablePerson(Long id) throws Exception {
        
        logger.info("disabling one person" + id);

        userRepository.disablePerson(id);

        var entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));
        // Map to VO
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

}
