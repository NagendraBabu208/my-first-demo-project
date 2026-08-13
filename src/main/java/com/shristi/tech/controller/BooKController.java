package com.shristi.tech.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/books")
	public ResponseEntity<List<Book>>  getAllBooks(){
		
		List<Book> listBooks=bookServiceInterf.getAllBooks();
		return new ResponseEntity<List<Book>>(listBooks,HttpStatus.OK);
		
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable("bookId") long bookId) {
		Book retrivedBook =bookServiceInterf.getBookById(bookId);
		return new ResponseEntity<Book>(retrivedBook,HttpStatus.OK);
		
	}
	@PatchMapping("/books/{id}")
	public ResponseEntity<Book> update(@RequestBody Book book,@PathVariable("id")long id) {
		
		Book updatedBook=bookServiceInterf.updateBook(book, id);
		return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
		
	}
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getByAuthor(@RequestParam(required = true) String author){
		List<Book> books=bookServiceInterf.getByAuthor(author);
		return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
		
	}
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getByTitle(@RequestParam(required = true) String title){
		 List<Book> books=bookServiceInterf.getByTitle(title);
		 return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
		
	}
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Long> delete(@PathVariable long id) {
		long bookId=bookServiceInterf.deleteBook(id);
		return new ResponseEntity<Long>(bookId, HttpStatus.OK);
		
	}


}
