package com.shristi.tech.mapper;



import org.springframework.stereotype.Component;

import com.shristi.tech.entity.Book;
import com.shristi.tech.model.BookDTO;

@Component
public class BookMapper {

    public  Book bookDTOMapTOBook(BookDTO bookDTO) {
		
		Book book= new Book();
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setSummary(bookDTO.getSummary());
		book.setIsbn(bookDTO.getIsbn());
		book.setActive(bookDTO.isActive());
		book.setPublishDate(bookDTO.getPublishDate());
		
		return book;	
	}
	
	
	  public  BookDTO bookMapToBookDTO(Book book) {
	  
	  BookDTO bookDTO= new BookDTO();
	  bookDTO.setBookId(book.getBookId());
	  bookDTO.setTitle(book.getTitle());
	  bookDTO.setAuthor(book.getAuthor());
	  bookDTO.setActive(book.isActive());
	  bookDTO.setSummary(book.getSummary());
	  bookDTO.setIsbn(book.getIsbn());
	  bookDTO.setPublishDate(book.getPublishDate());
	  return bookDTO;
	  }
	 

}
