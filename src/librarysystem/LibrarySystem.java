package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;


public class LibrarySystem extends JFrame implements LibWindow {

	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addLibraryMember, checkout, logout; 
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		Checkout.INSTANCE,
		CheckoutHistory.INSTANCE
	};
        	
    	
	public static void hideAllWindows() {
		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
		}
		
	}
    
    
    private LibrarySystem() {}
    
    public void init() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"//src//librarysystem//library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   login = new JMenuItem("Login");
 	   login.addActionListener(new LoginListener());
 	
 	   checkout = new JMenuItem("Checkout book");
	    checkout.addActionListener(new CheckoutListener());
	    logout = new JMenuItem("Logout");
	    logout.addActionListener(new LogoutListener());
 	  if(SystemController.currentAuth == null)  options.add(login);
 	   options.add(allBookIds);
 	   options.add(allMemberIds);
 	   options.add(addLibraryMember);
 	   options.add(checkout);
 	   if(SystemController.currentAuth != null) options.add(logout);

    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
    class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SystemController.currentAuth = null;
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
 
    
  
   
    
    
    class CheckoutListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
    		
    		if(SystemController.currentAuth == null) {
    			JOptionPane.showMessageDialog(LibrarySystem.this, "You have to login first");
    			return;
    		};
    		
			LibrarySystem.hideAllWindows();
			Checkout.INSTANCE.init();
			Util.centerFrameOnDesktop(Checkout.INSTANCE);
			Checkout.INSTANCE.setVisible(true);
			
		}
      	
      }


	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
