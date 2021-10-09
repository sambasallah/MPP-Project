package ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.CheckoutException;
import business.SystemController;

public class Checkout {
	SystemController control;
   private JPanel mainPanel = new JPanel();
   private JPanel topPanel;
   private JPanel outerMiddle;
	private JTextField memberID;
	private JTextField bookID;

   public JPanel getMainPanel() {
      return this.mainPanel;
   }

   public void clearData() {
    this.memberID.setText("");
    this.bookID.setText("");
   }

   public Checkout() {
      this.mainPanel.setLayout(new BorderLayout());
      this.defineTopPanel();
      this.defineOuterMiddle();
      this.mainPanel.add(this.topPanel, "North");
      this.mainPanel.add(this.outerMiddle, "Center");
   }

   public void defineTopPanel() {
      this.topPanel = new JPanel();
      JLabel AddBookLabel = new JLabel("Book Checkout");
      Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
      this.topPanel.setLayout(new FlowLayout(0));
      this.topPanel.add(AddBookLabel);
   }

   public void defineOuterMiddle() {
      this.outerMiddle = new JPanel();
      this.outerMiddle.setLayout(new BorderLayout());
      JPanel middlePanel = new JPanel();
      FlowLayout fl = new FlowLayout(1, 25, 25);
      middlePanel.setLayout(fl);
      JPanel leftPanel = new JPanel();
      JPanel rightPanel = new JPanel();
      leftPanel.setLayout(new BoxLayout(leftPanel, 1));
      rightPanel.setLayout(new BoxLayout(rightPanel, 1));
      JLabel memberIDLabel = new JLabel("Member ID");
      JLabel isbnLabel = new JLabel("ISBN Number");
    
      this.memberID = new JTextField(10);
      this.bookID = new JTextField(10);
      
      leftPanel.add(memberIDLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(isbnLabel);
      rightPanel.add(this.memberID);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.bookID);
     
      middlePanel.add(leftPanel);
      middlePanel.add(rightPanel);
      this.outerMiddle.add(middlePanel, "North");
      JButton checkoutBook = new JButton("Checkout");
      this.attachCheckoutBookButtonListener(checkoutBook);
      JPanel addBookButtonPanel = new JPanel();
      addBookButtonPanel.setLayout(new FlowLayout(1));
      addBookButtonPanel.add(checkoutBook);
      this.outerMiddle.add(addBookButtonPanel, "Center");
   }

   private void attachCheckoutBookButtonListener(JButton butn) {
      butn.addActionListener((evt) -> {
    	  String memberid = memberID.getText();
			 String bookid = bookID.getText();
			 control = new SystemController();
			 try {
				 clearData();
				control.checkout(memberid, bookid);
			} catch (CheckoutException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
      });
   }

   public void updateData() {
   }
}
