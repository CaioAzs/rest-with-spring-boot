package com.restspringboot.azsrest.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.restspringboot.azsrest.controllers.BookController;
import com.restspringboot.azsrest.exceptions.RequiredObjectNullException;
import com.restspringboot.azsrest.exceptions.ResourceNotFoundException;
import com.restspringboot.azsrest.mapper.DozerMapper;
import com.restspringboot.azsrest.models.Book;
import com.restspringboot.azsrest.repositories.BookRepository;
import com.restspringboot.azsrest.vo.v1.BookVO;

@Service
public class BookService {

    // Aqui serão implemetadas as lógicas de consulta da aplicação.

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository bookRepository;

    @Autowired
	PagedResourcesAssembler<BookVO> assembler;

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

		logger.info("Finding all books!");

		var booksPage = bookRepository.findAll(pageable);

		var booksVOs = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		booksVOs.map(p -> {
            try {
                return p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return p;
        });
		
		Link findAllLink = linkTo(
		          methodOn(BookController.class)
		          	.findAll(pageable.getPageNumber(),
	                         pageable.getPageSize(),
	                         "asc")).withSelfRel();
		
		return assembler.toModel(booksVOs, findAllLink);
	}

    public BookVO findById(Long id) throws Exception {

        var entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        // Map to VO
        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public BookVO postBook(BookVO book) throws Exception {

        if (book == null)
            throw new RequiredObjectNullException();

        logger.info("createBook called");
        var entity1 = DozerMapper.parseObject(book, Book.class);

        // Map to VO
        var vo = DozerMapper.parseObject(bookRepository.save(entity1), BookVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO putBook(BookVO book) throws Exception {

        if (book == null)
            throw new RequiredObjectNullException();

        logger.info("updateBook called");

        var entity = bookRepository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        // Map to VO
        var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);

        // HATEOAS self
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void deleteBook(Long bookId) {
        logger.info("deleteBook called" + bookId);

        var entity = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

        bookRepository.delete(entity);
    }

}
