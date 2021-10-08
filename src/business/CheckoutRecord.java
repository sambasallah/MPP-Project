package business;

import java.io.Serializable;

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
	
}
