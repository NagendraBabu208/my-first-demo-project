package com.shristi.tech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shristi.tech.entity.Book;
import com.shristi.tech.exception.BookNotFoundException;
import com.shristi.tech.mapper.BookMapper;
import com.shristi.tech.model.BookDTO;
import com.shristi.tech.repository.IBookRepository;



@Service
public class BookServiceImpl implements IBookService  {

	private IBookRepository bookRepository;
	private BookMapper bookMapper;
	
	@Autowired
	public BookServiceImpl(IBookRepository bookRepository, BookMapper bookMapper) {
		this.bookRepository=bookRepository;
		this.bookMapper=bookMapper;
		
	}
	
	@Override
	public Book createBook(BookDTO bookDTO) {
		
		Book book=bookMapper.bookDTOMapTOBook(bookDTO);
		Book savedBook=bookRepository.save(book);
	
		return savedBook;
		
	}

	@Override
	public List<BookDTO> getAllBooks() {
		
		List<Book> listOfBooks=bookRepository.findAll();
		List<BookDTO> listofBookDTOS= null;
		
		
		listofBookDTOS=listOfBooks.stream()
		.map(bookMapper::bookMapToBookDTO)
		.collect(Collectors.toList());
	
		return listofBookDTOS;
	}

	@Override
	public BookDTO getBookById(long bookId) {
		
		Book book =bookRepository.findById(bookId)
				.orElseThrow(()-> new BookNotFoundException("Book not found with that bookId ->"+bookId));
		
		return bookMapper.bookMapToBookDTO(book);
		
	}

	@Override
	public List<BookDTO> getByAuthor(String author) {
		List<Book> booksList=bookRepository.findByAuthor(author);
		List<BookDTO> bookDTOsList=null;
		
		if(booksList.isEmpty()) {
			throw new BookNotFoundException("Book are not available with that author -> "+author);
		}
		else {
			
			
			bookDTOsList =booksList.stream()
			   .map(book ->bookMapper.bookMapToBookDTO(book)) 
			   .collect(Collectors.toList());
			
		}
		return bookDTOsList;
	}

	@Override
	public List<BookDTO> getByTitle(String title) {
		List<Book> listOfBooks=bookRepository.findByTitle(title);
		List<BookDTO> listOfDTOs=new ArrayList<>();
		BookDTO bookDTO=null;
		if(listOfBooks.isEmpty()) {
			throw new BookNotFoundException("Books are not found with that title -> "+title);
		}
		else {
		
			  listOfDTOs=listOfBooks.stream() 
			  .map(book-> bookMapper.bookMapToBookDTO(book))
			  .collect(Collectors.toList());
			 
		}
		
		return listOfDTOs;
	}

	@Override
	public BookDTO updateBook(BookDTO bookDTO, long bookId) {
		
		Book book=bookRepository.findById(bookId)
				.orElseThrow(()->new BookNotFoundException("Book is not found with that book id -> "+bookId) );
		BookDTO updatedBookDTO=null;
		
		     book.setTitle(bookDTO.getTitle());
		     book.setAuthor(bookDTO.getAuthor());
		     book.setActive(bookDTO.isActive());
		     book.setIsbn(bookDTO.getIsbn());
		     book.setPublishDate(bookDTO.getPublishDate());
		     book.setSummary(bookDTO.getSummary());
		     Book updatedBook=bookRepository.save(book);
		     
		    updatedBookDTO = bookMapper.bookMapToBookDTO(updatedBook);
		    
	
		return updatedBookDTO;
	}
	@Override
	public long deleteBook(long bookId) {
		
		Book book=bookRepository.findById(bookId)
				.orElseThrow(()-> new BookNotFoundException("Book not found with that book id -> "+bookId) );
		
		bookRepository.deleteById(bookId);
		return bookId;
	}
	

}
 