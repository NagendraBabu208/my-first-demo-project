package com.shristi.tech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shristi.tech.entity.Book;
import com.shristi.tech.exception.BookNotFoundException;
import com.shristi.tech.mapper.BookMapper;
import com.shristi.tech.model.BookDTO;
import com.shristi.tech.repository.IBookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
	
	@Mock
	private IBookRepository bookRepository;
	@Mock
	private BookMapper bookMapper; 
	@InjectMocks
	private BookServiceImpl bookServiceImpl;
	
	private Book book;
	private Book book2;
	private BookDTO bookDTO;
	private BookDTO bookDTO2;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
		book=new Book();
		book.setBookId(1L);
		book.setAuthor("Sudha");
		book.setTitle("Core Java");
		
		book2=new Book();
		book2.setBookId(2L);
		book2.setAuthor("Sudha");
		book2.setTitle("Core Java");
		
		bookDTO=new BookDTO();
		bookDTO.setBookId(1L);
		bookDTO.setAuthor("Sudha");
		bookDTO.setTitle("Core Java");
		
		bookDTO2=new BookDTO();
		bookDTO2.setBookId(2L);
		bookDTO2.setAuthor("Sudha");
		bookDTO2.setTitle("Core Java");
	}
	
	@Test
	void testCreateBookShouldBeSuccessfullyCreated() {
		
		when(bookMapper.bookDTOMapTOBook(bookDTO)).thenReturn(book);
		when(bookRepository.save(book)).thenReturn(book);
		
	     Book savedBook=bookServiceImpl.createBook(bookDTO);
	     
	     assertNotNull(savedBook);
	     assertEquals("Sudha", savedBook.getAuthor());
	     verify(bookRepository, times(1)).save(book);
		
	}
	@Test
	void testGetAllBooksShouldSuccessfullyRetrived() {
		
		when(bookRepository.findAll()).thenReturn(Arrays.asList(book,book2));
		when(bookMapper.bookMapToBookDTO(book)).thenReturn(bookDTO);
		
		List<BookDTO> bookDTOs=bookServiceImpl.getAllBooks();
		
		assertNotNull(bookDTOs);
		assertEquals(2, bookDTOs.size());
		assertEquals("Sudha", bookDTOs.get(0).getAuthor());
	}
	
	@Test
	void testGetBookSuccessfullyWithBookId() {
		
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(bookMapper.bookMapToBookDTO(book)).thenReturn(bookDTO);
		
		BookDTO bookDTO=bookServiceImpl.getBookById(1L);
		
		assertNotNull(bookDTO);
		assertEquals("Sudha", bookDTO.getAuthor());
		verify(bookRepository, times(1)).findById(anyLong());
	}
	@Test
	void testGetBookFailedWithBookId() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThrows(BookNotFoundException.class,()-> bookServiceImpl.getBookById(1L));
		
	}
	
	@Test
	void testGetByAuthorShouldReturnListOfRecords() {
		
	when(bookRepository.findByAuthor(anyString())).thenReturn(Arrays.asList(book,book2));
	when(bookMapper.bookMapToBookDTO(book)).thenReturn(bookDTO);
	
	List<BookDTO> listOfDTO=bookServiceImpl.getByAuthor("Sudha");
	
	assertNotNull(listOfDTO);
	assertEquals(2, listOfDTO.size());
	assertEquals("Sudha", listOfDTO.get(0).getAuthor());
	
		
	}
	@Test
	void testGetByAuthor_ShouldReturnBookNotFoundException() {
		
		when(bookRepository.findByAuthor(anyString())).thenReturn(Arrays.asList());
		
		BookNotFoundException bookNotFoundException
		=assertThrows(BookNotFoundException.class, ()-> bookServiceImpl.getByAuthor("Nagendra"));
		
		assertEquals("Book are not available with that author -> Nagendra", bookNotFoundException.getMessage());
		verify(bookRepository, times(1)).findByAuthor(anyString());
	}
	@Test
	void testGetByTitle_ShouldReturnListOfRecords() {
		
		when(bookRepository.findByTitle(anyString())).thenReturn(Arrays.asList(book,book2));
		when(bookMapper.bookMapToBookDTO(book)).thenReturn(bookDTO);
		
		List<BookDTO> listOfDTOs=bookServiceImpl.getByTitle("Core Java");
		
		assertNotNull(listOfDTOs);
		assertEquals(2, listOfDTOs.size());
		assertEquals("Core Java", listOfDTOs.get(0).getTitle());
		verify(bookRepository, times(1)).findByTitle(anyString());
		
	}
	@Test
	void testGetByTitle_ShouldReturnBookNotFoundException() {
		
		when(bookRepository.findByTitle(anyString())).thenReturn(Arrays.asList());
		
		BookNotFoundException bookNotFoundException=
				assertThrows(BookNotFoundException.class, ()-> bookServiceImpl.getByTitle("Spring"));
		
		assertEquals("Books are not found with that title -> Spring", bookNotFoundException.getMessage());
		verify(bookRepository, times(1)).findByTitle(anyString());
	}
	
	@Test
	void testDeleteBook_shouldDeleteBookBasedOnBookId() {
		
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		
		long bookId=bookServiceImpl.deleteBook(1L);
		
		assertNotNull(bookId);
		assertNotEquals(2, bookId);
		assertEquals(1L, bookId);
		verify(bookRepository, times(1)).deleteById(anyLong());
		
	}
	@Test
	void testDeleteBook_ShouldThrowBookNotFoundException() {
		
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		BookNotFoundException bookNotFoundException
		=assertThrows(BookNotFoundException.class, ()->bookServiceImpl.deleteBook(1L));
		
		assertEquals("Book not found with that book id -> 1", bookNotFoundException.getMessage());
		verify(bookRepository, times(0)).deleteById(anyLong());
		
	}
	@Test
	void TestUpdateBook_ShouldBeUpdateSuccessfully() {
		
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(bookRepository.save(book)).thenReturn(book);
		when(bookMapper.bookMapToBookDTO(book)).thenReturn(bookDTO);
		
		BookDTO updateBookDTO=bookServiceImpl.updateBook(bookDTO, 1L);
		
		assertNotNull(updateBookDTO);
		assertEquals("Sudha", updateBookDTO.getAuthor());
		verify(bookRepository, times(1)).save(book);
		
		
		}
	
	@Test
	void TestUpdateBook_ShouldBeThrowBookNotFoundException() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		BookNotFoundException bookNotFoundException
		=assertThrows(BookNotFoundException.class, ()-> bookServiceImpl.updateBook(bookDTO, 1L));
		
		assertEquals("Book is not found with that book id -> 1", bookNotFoundException.getMessage());
		verify(bookRepository, times(1)).findById(anyLong());
		verify(bookRepository, times(0)).save(book);
		
	}
	
	
	
 
}
