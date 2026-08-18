package com.shristi.tech.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
		List<BookDTO> listofBookDTOS= new ArrayList<>();
		
		/*
		 * List<BookDTO> bookDTOList=listOfBooks.stream() 
		 * .map(book->Mapper.bookMapToBookDTO(book)) 
		 * .collect(Collectors.toList());
		 */
		
		for(Book book:listOfBooks) {
			BookDTO bookDTO=bookMapper.bookMapToBookDTO(book);
			listofBookDTOS.add(bookDTO);
		}
		return listofBookDTOS;
	}

	@Override
	public BookDTO getBookById(long bookId) {
		
		Optional<Book> optionalBook=bookRepository.findById(bookId);
		BookDTO bookDTO=null;
		if(!optionalBook.isPresent()) {
			throw new BookNotFoundException("Book not found with that bookId ->"+bookId);
		}
		else {
			Book book=optionalBook.get();
			bookDTO=bookMapper.bookMapToBookDTO(book);
		}
		
		return bookDTO;
	}

	@Override
	public List<BookDTO> getByAuthor(String author) {
		List<Book> booksList=bookRepository.findByAuthor(author);
		List<BookDTO> bookDTOsList= new ArrayList<>();
		
		if(booksList.isEmpty()) {
			throw new BookNotFoundException("Book are not available with that author -> "+author);
		}
		else {
			
			/*
			 * List<BookDTO> listOfBookDTOs=booksList.stream()
			 *  .map(book ->Mapper.bookMapToBookDTO(book)) 
			 *  .collect(Collectors.toList());
			 */
			
			for(Book book:booksList) {
				BookDTO bookDTO=bookMapper.bookMapToBookDTO(book);
				bookDTOsList.add(bookDTO);
			}
			
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
			
			for(Book book:listOfBooks) {
				bookDTO=bookMapper.bookMapToBookDTO(book);
				listOfDTOs.add(bookDTO);
			}
			
			/*
			 * listOfDTOs=listOfBooks.stream() 
			 * .map(book-> Mapper.bookMapToBookDTO(book))
			 * .collect(Collectors.toList());
			 */
		}
		
		return listOfDTOs;
	}

	@Override
	public BookDTO updateBook(BookDTO bookDTO, long bookId) {
		
		Optional<Book> optionalBook=bookRepository.findById(bookId);
		BookDTO updatedBookDTO=null;
		if(!optionalBook.isPresent()) {
			throw new BookNotFoundException("Book is not found with that book id -> "+bookId);
		}
		else {
			
			Book book=optionalBook.get();
		     book.setTitle(bookDTO.getTitle());
		     book.setAuthor(bookDTO.getAuthor());
		     book.setActive(bookDTO.isActive());
		     book.setIsbn(bookDTO.getIsbn());
		     book.setPublishDate(new Date());
		     book.setSummary(bookDTO.getSummary());
		     
		    updatedBookDTO = bookMapper.bookMapToBookDTO(book);
		    }
	
		return updatedBookDTO;
	}

	@Override
	public long deleteBook(long bookId) {
		
		Optional<Book> optionalBook=bookRepository.findById(bookId);
		
		if(!optionalBook.isPresent()) {
			throw new BookNotFoundException("Book not found with that book id -> "+bookId);
		}
		else {
			bookRepository.deleteById(bookId);
		}
		
		return bookId;
	}
	

}
 