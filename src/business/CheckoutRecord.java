package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CheckoutRecordEntry recordEntry;
	public CheckoutRecord(CheckoutRecordEntry entry) {
		this.recordEntry = entry;
	}
	
	public String[] getEntry() {
		return new String[]{recordEntry.getBookName(),recordEntry.getISBN(),recordEntry.getCheckoutDate(),
			 recordEntry.getdueDate()}
		;
		}
	

	public String[] getCheckoutRecord(String memberID) {
		return new String[]{recordEntry.getBookName(),recordEntry.getISBN(),recordEntry.getCopyNum(),
			 recordEntry.getdueDate(), memberID}
		;
		}
	
	public String getBookISBN() {
		return recordEntry.getISBN();
	}
	
	public LocalDate dueDate() {
		return recordEntry.getDueDate();
	}
}
