package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BookCopy book;
	public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, BookCopy book) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.book = book;
	}
	
	
	public String getCheckoutDate() {
		return checkoutDate.toString();
	}
	
	public String getdueDate() {
		return dueDate.toString();
	}
	
	public String getBookName() {
		return book.getBook().getTitle();
	}
	
	
	public String getISBN() {
		return book.getBook().getIsbn();
	}
	
	public String getCopyNum() {
		return book.getCopyNum() + "";
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
}
