package com.shristi.tech.service;

import java.util.List;

import com.shristi.tech.entity.Book;
import com.shristi.tech.model.BookDTO;

public interface IBookService {
	
	public Book createBook(BookDTO bookDTO);
	public List<BookDTO> getAllBooks();
	public BookDTO getBookById(long bookId);
	public List<BookDTO> getByAuthor(String author);
	public List<BookDTO> getByTitle(String title);
	public BookDTO updateBook(BookDTO bookDTO, long bookId);
	public long deleteBook(long bookId);
	

}
