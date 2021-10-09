package ui;

import java.util.List;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.TestData;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AddBookPanel implements MessageableWindow, Serializable
{
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel outerMiddle;
    private JTextField isbn;

    private JTextField titleField;
    private JTextField maximumcheckoutlength;
    private JTextField numberofcopies;
    private JList jList;
    private JButton addAuthorButton;
    private JButton clearAuthorButton;
    private JLabel authorNum;
    List<Author> allAuthors = new ArrayList<Author>() ;
    public JPanel getMainPanel() {
        return this.mainPanel;
    }
    
    public void clearData() {
        this.isbn.setText("");
        this.titleField.setText("");
        this.maximumcheckoutlength.setText("");
        this.numberofcopies.setText("");
    }
    
    public AddBookPanel() {
        (this.mainPanel = new JPanel()).setLayout(new BorderLayout());
        this.defineTopPanel();
        this.defineOuterMiddle();
        this.mainPanel.add(this.topPanel, "North");
        this.mainPanel.add(this.outerMiddle, "Center");
    }
    
    public void defineTopPanel() {
        this.topPanel = new JPanel();
        final JLabel AddBookLabel = new JLabel("Add Book Title");
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
        
        addAuthorButton = new JButton("Add Author");
        clearAuthorButton = new JButton("Clear Author");
        clearAuthorButton.setVisible(false);
        final JLabel author = new JLabel("Author");
         authorNum = new JLabel("0 Author");
        authorNum.setText(""+allAuthors.size()+" Author");
        final JLabel isbn = new JLabel("ISBN");
        final JLabel titleLabel = new JLabel("Book Title");
        final JLabel maxcheclen = new JLabel("Maximum Checkout Length");
        final JLabel numofcopy = new JLabel("Number Of Copy");

        this.isbn = new JTextField(10);
        this.maximumcheckoutlength = new JTextField(10);
        this.isbn = new JTextField(10);
        this.numberofcopies = new JTextField(10);
       this.titleField = new JTextField(10);
     
       
        leftPanel.add(isbn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(author);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(author);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(maxcheclen);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(numofcopy);
        
        rightPanel.add(this.isbn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(this.titleField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authorNum);
      
        rightPanel.add(this.addAuthorButton);
        rightPanel.add(this.clearAuthorButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(this.maximumcheckoutlength);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(this.numberofcopies);
        
        
        
        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        this.outerMiddle.add(middlePanel, "North");
        final JButton addBookButton = new JButton("Add Book");
        
       
        this.attachAddBookButtonListener(addBookButton);
        this.clearAddBookButtonListener(clearAuthorButton);
        this.attachAddauthorButtonListener(addAuthorButton);
        final JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(1));
        addBookButtonPanel.add(addBookButton);
        this.outerMiddle.add(addBookButtonPanel, "Center");
    }
    
    private void attachAddBookButtonListener(final JButton butn) {
         
        butn.addActionListener(evt -> {
        	 final ControllerInterface ci = new SystemController();
        
        	String isbn =this.isbn.getText();
        	String title =this.titleField.getText();
            String max=this.maximumcheckoutlength.getText();
          
            String numcopy =this.numberofcopies.getText();
            
            if(allAuthors.size() > 0 && 	
					 !isbn.isEmpty()	&&
					 !title.isEmpty()	&&
					 !isbn.isEmpty()	&&
					 !numcopy.isEmpty()	&&
					 !max.isEmpty()		
            		
            		)
            {
            	  int maxCheckoutLength = Integer.parseInt(max);
            	  int numcopyInt = Integer.parseInt(numcopy);
            	Book newBook = new  Book(isbn,title, maxCheckoutLength,allAuthors);
               for(int i=0; i<numcopyInt-1;i++)
               {
            	   newBook.addCopy();
               }
            	
          	  ci.addBook(newBook);
          	 clearData() ;
          	allAuthors.clear();
   		 clearAuthorButton.setVisible(false);
   		 authorNum.setText(""+allAuthors.size()+" Author");
   		JOptionPane.showMessageDialog(null, "Book Added Succesfully");
            }else{
            	JOptionPane.showMessageDialog(null, "Fill the Fileds");
            }
            
            
        });
    }
    public void checkAuthorButton ()
    {
    	 
        if(allAuthors.size() >0)
        {
        	 clearAuthorButton.setVisible(true);
             	
        }
        else
        {
        	 clearAuthorButton.setVisible(false);
        }
    	
    }
    private void clearAddBookButtonListener(JButton butn)
    {
    	butn.addActionListener(evt -> {
    		allAuthors.clear();
    		 clearAuthorButton.setVisible(false);
    		 authorNum.setText(""+allAuthors.size()+" Author");
    		
    	});
    }
    private void attachAddauthorButtonListener(JButton butn)
    {
    	 butn.addActionListener(evt -> {
    		 JTextField firstName = new JTextField();
    		 JTextField lastName = new JTextField();
    		 JTextField telephone = new JTextField();
    		 JTextField street = new JTextField();
    		 JTextField city = new JTextField();
    		 JTextField state = new JTextField();
    		 JTextField zip = new JTextField();
    		 JTextArea bio = new JTextArea(10,20);
    		 JScrollPane sp = new JScrollPane(bio); 
    		 final JComponent[] inputs = new JComponent[] {
    		         new JLabel("FirstName"),
    		         firstName,
    		         new JLabel("Lastname"),
    		         lastName,
    		         new JLabel("telephone"),
    		         telephone,
    		         new JLabel("street"),
    		         street,
    		         new JLabel("city"),
    		         city,
    		         new JLabel("state"),
    		         state,
    		         new JLabel("zip"),
    		         zip,
    		         new JLabel("Bio"),
    		         sp
    		 };
    		 int result = JOptionPane.showConfirmDialog(null, inputs, "Add Author", JOptionPane.PLAIN_MESSAGE);
    		 if (result == JOptionPane.OK_OPTION) {
    		     
    			 
    			 if(
    					 !firstName.getText().isEmpty()	&&
    					 !lastName.getText().isEmpty()	&&
    					 !telephone.getText().isEmpty()	&&
    					 !street.getText().isEmpty()	&&
    					 !city.getText().isEmpty()	&&
    					 !state.getText().isEmpty()	&&
    					 !zip.getText().isEmpty()	&&
    					 !bio.getText().isEmpty()	
    					
    			)
    			 {
    				 
    				 allAuthors.add(new Author(
    						 firstName.getText().trim(),
    						 lastName.getText().trim(), 
    						 telephone.getText().trim(), 
    						 new Address(
    								 street.getText().trim(),
    								 city.getText().trim(), 
    								 state.getText().trim(),
    								 zip.getText().trim())
    						 ,bio.getText().trim()));
    				 authorNum.setText(""+allAuthors.size()+" Author");
    				 checkAuthorButton ();
    				 
    				 
    			 }
    			 else {
    				 JOptionPane.showMessageDialog(null, "Fill the Fileds");
    				 System.out.println(bio.getText());
    			 }
    		 } else {
    		     
    		 }
    	 });
    	 
    }
    @Override
    public void updateData() {
    }
}
