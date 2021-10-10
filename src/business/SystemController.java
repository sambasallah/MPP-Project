package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.Checkout;
import librarysystem.LibrarySystem;
import business.BookStatusException;
import ui.BookStatus;
import ui.CheckoutHistory;
import librarysystem.Util;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccess data;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		
		currentAuth = map.get(id).getAuthorization();
		
	}
	
	
	public void checkout(String memberID, String bookID) throws CheckoutException {
		data = new DataAccessFacade();
		HashMap<String, LibraryMember> member = data.readMemberMap();
		HashMap<String, Book> books = data.readBooksMap();
		
		System.out.println(bookID);
		
		if(!books.containsKey(bookID)) {
			throw new CheckoutException("Book ID " + bookID + " not found");
		}else if(!member.containsKey(memberID)) {
			throw new CheckoutException("Member ID " + memberID + " not found");
		}
		
		
		for(Book book : books.values()) {
			if(book.getIsbn().equals(bookID)) {
				if(!book.isAvailable()) throw new CheckoutException("Book ID " + bookID + " is not available");
				else {
						DataAccessFacade db = new DataAccessFacade();
						LocalDate checkoutDate = LocalDate.now();
						LocalDate dueDate = checkoutDate.plusDays(book.getMaxCheckoutLength());
						BookCopy copy = book.getNextAvailableCopy();
						copy.changeAvailability();
						LibraryMember currentMember = member.get(memberID);
						CheckoutRecordEntry recordEntry = new CheckoutRecordEntry(checkoutDate, dueDate,copy);
						CheckoutRecord record = new CheckoutRecord(recordEntry);
						book.updateCopies(copy);
						db.saveNewBook(book);
						currentMember.addToRecord(record);
						db.saveNewMember(currentMember);
					    
						member = db.readMemberMap();
						currentMember = member.get(memberID);
						List<CheckoutRecord> rec = currentMember.getRecords();
						
						List<String[]> arr = new ArrayList<>();
						
						for(CheckoutRecord r : rec) {
							arr.add(r.getEntry());
						}
						
						(new CheckoutHistory()).init(arr);
//						Util.centerFrameOnDesktop(new CheckoutHistory());
//						(new CheckoutHistory()).setVisible(true);
						
				}
				
			} 
		}
		
	}
	
	public void checkBookStatus(String bookID) throws BookStatusException {
		data = new DataAccessFacade();
		HashMap<String, LibraryMember> members = data.readMemberMap();
		List<String[]> arr = new ArrayList<>();
		for(LibraryMember m : members.values()) {
			for(CheckoutRecord r : m.getRecords()) {
				if(r.getBookISBN().equals(bookID) && LocalDate.now().isBefore(r.dueDate())) {
					arr.add(r.getCheckoutRecord(m.getMemberId()));
				}
			}
		}
		
		if(arr.size() == 0) {
			throw new BookStatusException("No Record Found");
		}
		
		System.out.println(Arrays.deepToString(arr.get(0)));
		
		(new BookStatus()).init(arr);
	}
	
	public void printCheckoutRecord(String memberID) {
		data = new DataAccessFacade();
		HashMap<String, LibraryMember> member = data.readMemberMap();
		
		LibraryMember currentMember = member.get(memberID);
		List<CheckoutRecord> rec = currentMember.getRecords();
		
		StringBuilder sb = new StringBuilder();
		
		System.out.println("");
		System.out.println("");
		System.out.println("Book Name\t\tBook ISBN\t\tCheckout Date\t\tDue Date");
		for(CheckoutRecord r : rec) {
//			arr.add(r.getEntry());
			for(String s : r.getEntry()) {
				sb.append(s + "\t\t");
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		 data = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(data.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public void addBook(Book book) {
		data = new DataAccessFacade();
		data.saveNewBook(book);
		
	}

	@Override
	public void addBookCopy(String isbn) {
		data = new DataAccessFacade();
		HashMap<String, Book> books = data.readBooksMap();
		for(Book book : books.values()) {
			  if(book.getIsbn().equals(isbn))
			  {
				 book.addCopy();
				 data.saveNewBook(book);
			  }
		}
		
	}

	@Override
	public Book getBook(String isbn) {
		Book b = null;
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		for(Book book : books.values()) {
			  if(book.getIsbn().equals(isbn))
			  {
				  b= book;
				  break;
			  }
		}
		return b;
	}
	
	
}
