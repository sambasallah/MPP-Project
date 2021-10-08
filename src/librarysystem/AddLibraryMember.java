package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccessFacade;


public class AddLibraryMember extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2997228611243297399L;
	public static final AddLibraryMember INSTANCE = new AddLibraryMember();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JTextField id;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField state;
	private JTextField street;
	private JTextField city;
	private JTextField zip;
	private JTextField telephone;
	
	private AddLibraryMember() {}
	
	public void init() {
		setTitle("Add Library Member");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		JLabel firstNameLabel = new JLabel("First Name");
		JLabel lastNameLabel = new JLabel("Last Name");
		JLabel stateLabel = new JLabel("State ");
		JLabel idLabel = new JLabel("ID ");
		JLabel cityLabel = new JLabel("City ");
		JLabel streetLabel = new JLabel("Street");
		JLabel zipLabel = new JLabel("Zip ");
		JLabel telephoneLabel = new JLabel("Telephone ");
		
		id = new JTextField(10);
		firstName = new JTextField(10);
		lastName = new JTextField(10);
		state = new JTextField(10);
		city = new JTextField(10);
		street = new JTextField(10);
		zip = new JTextField(10);
		telephone = new JTextField(10);
		
		JButton addMember = new JButton("Add Member");
		addMember.addActionListener(evt -> {
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
			
			JOptionPane.showMessageDialog(this, "Member added");

		});
		
		middlePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		middlePanel.add(idLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		middlePanel.add(id, c);
		c.gridx = 0;
		c.gridy = 1;
		middlePanel.add(firstNameLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		middlePanel.add(firstName, c);
		c.gridx = 0;
		c.gridy = 2;
		middlePanel.add(lastNameLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		middlePanel.add(lastName, c);
		c.gridx = 0;
		c.gridy = 3;
		middlePanel.add(streetLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		middlePanel.add(street, c);
		c.gridx = 0;
		c.gridy = 4;
		middlePanel.add(cityLabel, c);
		c.gridx = 1;
		c.gridy = 4;
		middlePanel.add(city, c);
		c.gridx = 0;
		c.gridy = 5;
		middlePanel.add(stateLabel, c);
		c.gridx = 1;
		c.gridy = 5;
		middlePanel.add(state, c);
		c.gridx = 0;
		c.gridy = 6;
		middlePanel.add(zipLabel, c);
		c.gridx = 1;
		c.gridy = 6;
		middlePanel.add(zip, c);
		c.gridx = 0;
		c.gridy = 7;
		middlePanel.add(telephoneLabel, c);
		c.gridx = 1;
		c.gridy = 7;
		middlePanel.add(telephone, c);
		c.gridx = 0;
		c.gridy = 8;
		middlePanel.add(addMember, c);
		
		
	}
	
	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}
	
	
//	public void setData(String data) {
//		textArea.setText(data);
//	}
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
	}

	@Override
	public boolean isInitialized() {
		
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
	
	
}


