package com.shristi.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shristi.tech.entity.Book;
import com.shristi.tech.service.BookServiceInterf;

@RestController
@RequestMapping("/api")
public class BooKController {
	
	@Autowired
	private BookServiceInterf bookServiceInterf;
	
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		
		Book createdBook=bookServiceInterf.createBook(book);
		return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
		
	}

}
