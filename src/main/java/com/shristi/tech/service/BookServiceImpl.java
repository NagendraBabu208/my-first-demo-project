package com.shristi.tech.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shristi.tech.entity.Book;
import com.shristi.tech.exception.BookNotFoundException;
import com.shristi.tech.repository.BookRepository;

@Service
public class BookServiceImpl implements BookServiceInterf  {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book createBook(Book book) {
		
	Book createdBook =bookRepository.save(book);
		
		return createdBook ;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> listOfBooks=bookRepository.findAll();
		return listOfBooks;
		
	}

	@Override
	public Book getBookById(long bookId) {
		Optional<Book> book=bookRepository.findById(bookId);
		Book retrivedBook=null;
		if(!book.isPresent()) {
			throw new BookNotFoundException("Book is not available with this:: "+bookId);
		}else {
			retrivedBook=book.get();
		}
		return retrivedBook;
	}

	@Override
	public List<Book> getByAuthor(String author) {
		
		List<Book> listOfBooks =bookRepository.findByAuthor(author);
		if(listOfBooks.isEmpty()) {
			throw new BookNotFoundException("Books are not available with that:: "+author);
		}
		
		
		return listOfBooks;
	}

	@Override
	public List<Book> getByTitle(String title) {
		List<Book> listBooks=bookRepository.findByTitle(title);
		if(listBooks.isEmpty()) {
			throw new BookNotFoundException("Books are not available with that ::"+title);
		}
		return listBooks;
	}


	@Override
	public long deleteBook(long bookId) {
		bookRepository.deleteById(bookId);
		return bookId;
	}

	@Override
	public Book updateBook(Book book, long bookId) {
		
		Optional<Book> optionalBook=bookRepository.findById(bookId);
		Book updatedBook=null;
		
		if(!optionalBook.isPresent()) {
			throw new BookNotFoundException("Book not with that bookId:: "+bookId);
		}
		else {
			updatedBook= new Book();
			Book optBook=optionalBook.get();
			updatedBook.setTitle(optBook.getTitle());
			updatedBook.setAuthor(optBook.getAuthor());
			updatedBook.setSummary(optBook.getSummary());
			updatedBook.setPublishDate(new Date());
			updatedBook.setIsbn(optBook.getIsbn());
			updatedBook.setActive(optBook.isActive());
		}
			
		
		return updatedBook;
	}

}
