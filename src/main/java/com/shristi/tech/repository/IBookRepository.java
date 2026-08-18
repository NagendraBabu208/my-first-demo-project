package com.shristi.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shristi.tech.entity.Book;
@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
	
	
public List<Book> findByAuthor(String author);
public List<Book> findByTitle(String title);
}
