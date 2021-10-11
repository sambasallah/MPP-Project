package ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;

public class AddBookCopy  implements MessageableWindow
{
	 private JPanel mainPanel;
	    private JPanel topPanel;
	    private JPanel outerMiddle;
	    private JTextField isbn;
	    private JTextField numberofcopies;

	    LibrarySystem system;
	    public void setLibrarySystem(final LibrarySystem system) {
	        this.system = system;
	    }
	    public JPanel getMainPanel() {
	        return this.mainPanel;
	    }
	    
	    public AddBookCopy() {
	        (this.mainPanel = new JPanel()).setLayout(new BorderLayout());
	        this.defineTopPanel();
	        this.defineOuterMiddle();
	        this.mainPanel.add(this.topPanel, "North");
	        this.mainPanel.add(this.outerMiddle, "Center");
	    }
	    
	    public void defineTopPanel() {
	        this.topPanel = new JPanel();
	        final JLabel AddBookLabel = new JLabel("Add Book Copy");
	        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
	        this.topPanel.setLayout(new FlowLayout(0));
	        this.topPanel.add(AddBookLabel);
	    }
	    
	    public void defineOuterMiddle() {
	        (this.outerMiddle = new JPanel()).setLayout(new BorderLayout());
	        final JPanel middlePanel = new JPanel();
	        final FlowLayout fl = new FlowLayout(1, 25, 25);
	        middlePanel.setLayout(fl);
	        final JPanel leftPanel = new JPanel();
	        final JPanel rightPanel = new JPanel();
	        leftPanel.setLayout(new BoxLayout(leftPanel, 1));
	        rightPanel.setLayout(new BoxLayout(rightPanel, 1));
	        final JLabel authFirstNameLabel = new JLabel("ISBN");
	        final JLabel numcopiesLabel = new JLabel("Num of Copies");
	        this.isbn = new JTextField(10);
	        this.numberofcopies = new JTextField(10);
	        
	        leftPanel.add(authFirstNameLabel);
	        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
	      
	        rightPanel.add(this.isbn);
	        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
	        
	        leftPanel.add(numcopiesLabel);
	        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
	      
	        rightPanel.add(this.numberofcopies);
	        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
	        
	        middlePanel.add(leftPanel);
	        middlePanel.add(rightPanel);
	        this.outerMiddle.add(middlePanel, "North");
	        final JButton addBookButton = new JButton("Add Book Copy");
	        final JButton searchBookButton = new JButton("Search");
	        this.attachAddBookButtonListener(addBookButton);
	        this.searchAddBookButtonListener(searchBookButton);
	        final JPanel addBookButtonPanel = new JPanel();
	        addBookButtonPanel.setLayout(new FlowLayout(1));
	        addBookButtonPanel.add(searchBookButton);
	        addBookButtonPanel.add(addBookButton);
	        this.outerMiddle.add(addBookButtonPanel, "Center");
	    }
	    
	    private void searchAddBookButtonListener(final JButton butn) {
	         
	        butn.addActionListener(evt -> {
	        	String isbnstr =isbn.getText();
	        	if(isbnstr.isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "Enter ISBN");
	        		return;
	        	}
	      	  final ControllerInterface ci = new SystemController();
	        	Book b = ci.getBook(isbnstr);
	        	if(b== null)
	        	{
	        		JOptionPane.showMessageDialog(null, "There is no Book By this ISBN");
	        	}else
	        	{
	        		JLabel isbn= new JLabel(b.getIsbn());
	        		JLabel title= new JLabel(b.getTitle());
	        		JLabel authers= new JLabel(b.getIsbn());
	        		JLabel noOfCopy= new JLabel(""+b.getNumCopies());
	        		String authersNames="";
	        		List<Author> authors =b.getAuthors();
	        		for(Author a:authors)
	        		{
	        			
	        			authersNames+= a.getFirstName() +" "+a.getLastName() +" and ";
	        		}
	        		JLabel authersStr= new JLabel(authersNames);
	        		JLabel maxCheckoutLength= new JLabel(""+b.getMaxCheckoutLength());
	        		
	        		 final JComponent[] inputs = new JComponent[] {
	        		         new JLabel("Isbn"),
	        		         isbn,
	        		         new JLabel("Title"),
	        		         title,
	        		         new JLabel("Authers"),
	        		         authersStr,
	        		         new JLabel("maxCheckoutLength"),
	        		         maxCheckoutLength,
	        		       
	        		         new JLabel("Numbeer OfCopy"),
	        		         noOfCopy
	        		       
	        		 };
	        		 int result = JOptionPane.showConfirmDialog(null, inputs, "Add Book Copy", JOptionPane.PLAIN_MESSAGE);
	        		 
	        	}
	        	
	        	
	        	
	        });
	    }
	    private void attachAddBookButtonListener(final JButton butn) {
	         
	        butn.addActionListener(evt -> {
	        	String isbnstr =isbn.getText();
	        	String numcopy =numberofcopies.getText();
	        	if(numcopy.isEmpty() || isbnstr.isEmpty())
	        	{
	        		JOptionPane.showMessageDialog(null, "Enter Number of Copies and  ISBN");
	        		return;
	        	}
	        	
	        	
	      	  final ControllerInterface ci = new SystemController();
	        	Book b = ci.getBook(isbnstr);
	        	
	        	 if(b== null)
	        	{
	        		JOptionPane.showMessageDialog(null, "There is no Book By this ISBN");
	        	}else
	        	{
	        		int  numcopy1 =Integer.parseInt(numcopy);
	        			 System.out.println(b.toString());
	 		        
	 		        	
	 		        	for(int i=0; i<numcopy1;i++)
	 	               {
	 		        		ci.addBookCopy(isbnstr);
	 	               }
	 		        	
	 		        	JOptionPane.showMessageDialog(null, "New Book Copy Added");
	        	}
	        	
	        	
	        	
	        });
	    }
	    @Override
	    public void updateData() {
	    }
}
