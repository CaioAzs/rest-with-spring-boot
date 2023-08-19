package com.restspringboot.azsrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restspringboot.azsrest.models.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
    //This class has a lot of methods that implement a simple CRUD
}
