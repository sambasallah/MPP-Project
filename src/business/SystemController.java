package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.Checkout;
import librarysystem.LibrarySystem;
import librarysystem.CheckoutHistory;
import librarysystem.Util;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
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
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> member = da.readMemberMap();
		HashMap<String, Book> books = da.readBooksMap();
		
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
						Checkout.INSTANCE.setVisible(false);
						LibrarySystem.hideAllWindows();
						CheckoutHistory.INSTANCE.init(arr);
						Util.centerFrameOnDesktop(CheckoutHistory.INSTANCE);
						CheckoutHistory.INSTANCE.setVisible(true);
						
				}
				
			} 
		}
		
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
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	
}
