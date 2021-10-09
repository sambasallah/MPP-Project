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

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

public class AddLibraryMember implements MessageableWindow {
   private JPanel mainPanel = new JPanel();
   private JPanel topPanel;
   private JPanel outerMiddle;
   private JTextField authFirstNameField;
   private JTextField authLastNameField;
   private JTextField titleField;
   
   private JTextField id;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField state;
	private JTextField street;
	private JTextField city;
	private JTextField zip;
	private JTextField telephone;

   public JPanel getMainPanel() {
      return this.mainPanel;
   }

   public void clearData() {
      this.authFirstNameField.setText("");
      this.authLastNameField.setText("");
      this.titleField.setText("");
   }

   public AddLibraryMember() {
      this.mainPanel.setLayout(new BorderLayout());
      this.defineTopPanel();
      this.defineOuterMiddle();
      this.mainPanel.add(this.topPanel, "North");
      this.mainPanel.add(this.outerMiddle, "Center");
   }

   public void defineTopPanel() {
      this.topPanel = new JPanel();
      JLabel AddBookLabel = new JLabel("Add Book Title");
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
      	
      JLabel firstNameLabel = new JLabel("First Name");
		JLabel lastNameLabel = new JLabel("Last Name");
		JLabel stateLabel = new JLabel("State ");
		JLabel idLabel = new JLabel("ID ");
		JLabel cityLabel = new JLabel("City ");
		JLabel streetLabel = new JLabel("Street");
		JLabel zipLabel = new JLabel("Zip ");
		JLabel telephoneLabel = new JLabel("Telephone");
		
		
		id = new JTextField(10);
		firstName = new JTextField(10);
		lastName = new JTextField(10);
		state = new JTextField(10);
		city = new JTextField(10);
		street = new JTextField(10);
		zip = new JTextField(10);
		telephone = new JTextField(10);
		
		
      leftPanel.add(idLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(firstNameLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(lastNameLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(stateLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(cityLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(streetLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(zipLabel);
      leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
      leftPanel.add(telephoneLabel);
      
      
      rightPanel.add(this.id);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.firstName);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.lastName);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.state);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.city);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.street);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.zip);
      rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
      rightPanel.add(this.telephone);
      middlePanel.add(leftPanel);
      middlePanel.add(rightPanel);
      this.outerMiddle.add(middlePanel, "North");
      JButton addBookButton = new JButton("Add Member");
      this.attachAddBookButtonListener(addBookButton, this);
      JPanel addBookButtonPanel = new JPanel();
      addBookButtonPanel.setLayout(new FlowLayout(1));
      addBookButtonPanel.add(addBookButton);
      this.outerMiddle.add(addBookButtonPanel, "Center");
   }
   
   private void clear() {
	   	id.setText("");
		firstName.setText("");
		lastName.setText("");
		state.setText("");
		city.setText("");
		street.setText("");
		zip.setText("");
		telephone.setText("");
   }
   
   private void attachAddBookButtonListener(JButton butn, AddLibraryMember m) {
      butn.addActionListener((evt) -> {
    	  String memberID = id.getText();
			String memberFirstName = firstName.getText();
			String memberLastName = lastName.getText();
			String memberState = state.getText();
			String memberCity = city.getText();
			String memberStreet = street.getText();
			String memberZip = zip.getText();
			String memberTelephone = telephone.getText();
			
			Address address = new Address(memberStreet, memberCity, memberState, memberZip);
			LibraryMember library = new LibraryMember(memberID,memberFirstName, memberLastName,
					memberTelephone,address);

			DataAccessFacade dataAccess = new DataAccessFacade();
			dataAccess.saveNewMember(library);
			clear();
			JOptionPane.showMessageDialog(null, "Member Added");
      });
   }

   public void updateData() {
   }
}
