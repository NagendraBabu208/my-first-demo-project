package com.shristi.tech.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="BookTable")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long bookId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String author;
	
	private String summary;
	private String isbn;
	private Date publishDate;
	
	private boolean active;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(@NotEmpty String title, @NotEmpty String author, String summary, String isbn, Date publishDate,
			boolean active) {
		super();
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.isbn = isbn;
		this.publishDate = publishDate;
		this.active = active;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", summary=" + summary + ", isbn="
				+ isbn + ", publishDate=" + publishDate + ", active=" + active + "]";
	}
	
	

}
