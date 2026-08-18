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

import org.springframework.web.bind.annotation.RestController;

import com.shristi.tech.entity.Book;
import com.shristi.tech.mapper.BookMapper;
import com.shristi.tech.model.BookDTO;
import com.shristi.tech.service.IBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BookController {
	
	
	private IBookService bookServiceInterf;
	private BookMapper booMapper;
	
	@Autowired
	public BookController(IBookService bookServiceInterf, BookMapper booMapper) {
		this.bookServiceInterf = bookServiceInterf;
		this.booMapper = booMapper;
	}
	
	
	@PostMapping("/create-book")
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
		BookDTO createdBook=null;
		Book book=bookServiceInterf.createBook(bookDTO);
		createdBook=booMapper.bookMapToBookDTO(book);
		
		return new ResponseEntity<BookDTO>(createdBook, HttpStatus.CREATED);
		
	}

	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>>  getAllBooks(){
	return ResponseEntity.ok(bookServiceInterf.getAllBooks());
		
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable("bookId") long bookId) {
		return ResponseEntity.ok(bookServiceInterf.getBookById(bookId));
		
	}
	@PatchMapping("/books/{id}")
	public ResponseEntity<BookDTO> update(@Valid @RequestBody BookDTO bookDTO,@PathVariable("id")long id) {
		return ResponseEntity.ok(bookServiceInterf.updateBook(bookDTO, id));
		
	}
	@GetMapping("/books/author")
	public ResponseEntity<List<BookDTO>> getByAuthor(@RequestParam(required = true) String author){
		return ResponseEntity.ok(bookServiceInterf.getByAuthor(author));
		
	}
	@GetMapping("/books/title")
	public ResponseEntity<List<BookDTO>> getByTitle(@RequestParam(required = true) String title){
		return ResponseEntity.ok(bookServiceInterf.getByTitle(title)); 
		
	}
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") long id) {
		long bookId=bookServiceInterf.deleteBook(id);
		return new ResponseEntity<Long>(bookId, HttpStatus.OK);
		
	}


}
