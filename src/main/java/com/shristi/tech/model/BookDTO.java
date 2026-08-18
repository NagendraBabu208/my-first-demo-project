package com.shristi.tech.model;

import java.util.Date;

public class BookDTO {
	
	private long bookId;
	private String title;
	private String  author;

	private String summary;
	private String isbn;
	private Date publishDate;
	
	private boolean active;
	
	public BookDTO() {
		
	}

	public BookDTO(long bookId,String title, String author, String summary, String isbn, Date publishDate, boolean active) {
		this.bookId=bookId;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.isbn = isbn;
		this.publishDate = publishDate;
		this.active = active;
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
	
	public void setBookId(long bookId) {
		this.bookId=bookId;
	}

	public long getBookId() {
		return bookId;
	}

	@Override
	public String toString() {
		return "BookDTO [bookId=" + bookId + ", title=" + title + ", author=" + author + ", summary=" + summary
				+ ", isbn=" + isbn + ", publishDate=" + publishDate + ", active=" + active + "]";
	}

	
	
	
}
