package com.restspringboot.azsrest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restspringboot.azsrest.models.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
    //This class has a lot of methods that implement a simple CRUD

     @Query("SELECT p FROM Book p WHERE p.title LIKE LOWER(CONCAT ('%',:title,'%'))")
	Page<Book> findBookByName(@Param("title") String title, Pageable pageable);
}
