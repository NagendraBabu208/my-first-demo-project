package com.shristi.tech.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.shristi.tech.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
	
	@Autowired
	private IBookRepository bookRepository;
	
	@Test
	void testFindByBookByBookId() {
		
		Book book=new Book();
		book.setBookId(1L);
		book.setAuthor("Nagendra");
		book.setTitle("Core Java");
		book.setIsbn("123");
		
		Book savedBook=bookRepository.save(book);
		
		Book foundBook=bookRepository.findById(savedBook.getBookId()).orElse(null);
		
		assertNotNull(foundBook);
		assertEquals("Nagendra", foundBook.getAuthor());
		assertEquals("Core Java", foundBook.getTitle());
	}
	
	@Test
	void testFindByAuthor() {
		
		Book book1=new Book();
		book1.setBookId(1L);
		book1.setAuthor("Nagendra");
		book1.setTitle("Core Java");
		
		Book book2= new Book();
		book2.setBookId(2L);
		book2.setAuthor("Nagendra");
		book2.setTitle("Spring");
		
		Book savedBook1=bookRepository.save(book1);
		Book savedBook2=bookRepository.save(book2);
		
		List<Book> books=bookRepository.findByAuthor("Nagendra");
		
		assertNotNull(books);
		assertEquals("Nagendra", books.get(0).getAuthor());
		assertNotEquals(books.get(0).getTitle(), books.get(1).getTitle());
		}
	
	@Test
	void testFindByTitle() {
		Book book1=new Book();
		book1.setBookId(1L);
		book1.setAuthor("Nagendra");
		book1.setTitle("Core Java");
		
		Book book2= new Book();
		book2.setBookId(2L);
		book2.setAuthor("Nagendra");
		book2.setTitle("Core Java");
		
		Book savedBook1=bookRepository.save(book1);
		Book savedBook2=bookRepository.save(book2);
		
		List<Book> books=bookRepository.findByTitle("Core Java");
		
		assertNotNull(books);
		assertEquals(books.get(0).getTitle(), savedBook1.getTitle());
		assertEquals(books.get(0).getTitle(), savedBook2.getTitle());
		

	}

}
