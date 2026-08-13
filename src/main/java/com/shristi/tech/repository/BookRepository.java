package com.shristi.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shristi.tech.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	
public List<Book> findByAuthor(String author);
public List<Book> findByTitle(String title);
}
