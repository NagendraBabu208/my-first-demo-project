package com.shristi.tech.service;

import java.util.List;

import com.shristi.tech.entity.Book;

public interface BookServiceInterf {
	
	public Book createBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(long bookId);
	public List<Book> getByAuthor(String author);
	public List<Book> getByTitle(String title);
	public Book updateBook(Book book, long bookId);
	public long deleteBook(long bookId);
	

}
