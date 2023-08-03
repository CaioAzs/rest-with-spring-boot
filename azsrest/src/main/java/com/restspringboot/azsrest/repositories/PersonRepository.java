package com.restspringboot.azsrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restspringboot.azsrest.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // This class has a lot of methods that implement a simple CRUD

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);
}
