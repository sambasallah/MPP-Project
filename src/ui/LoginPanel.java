package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import business.SystemController;
import dataaccess.Auth;

public class LoginPanel implements MessageableWindow {
	SystemController control;
   LibrarySystem bookClub;
   private JPanel mainPanel = new JPanel();
   private JPanel upperHalf;
   private JPanel middleHalf;
   private JPanel container;
   private JPanel topPanel;
   private JPanel middlePanel;
   private JPanel lowerPanel;
   private JPanel leftTextPanel;
   private JPanel rightTextPanel;
   private JTextField username;
   private JTextField password;
   private JLabel label;
   private JButton loginButton;
   private JButton logoutButton;
   private Auth auth;
   private static final long serialVersionUID = 3618976789175941432L;

   public void setBookClub(LibrarySystem club) {
      this.bookClub = club;
   }

   public JPanel getMainPanel() {
      return this.mainPanel;
   }

   public LoginPanel() {
      this.defineUpperHalf();
      this.defineMiddleHalf();
      BorderLayout bl = new BorderLayout();
      bl.setVgap(30);
      this.mainPanel.setLayout(bl);
      this.mainPanel.add(this.upperHalf, "North");
      this.mainPanel.add(this.middleHalf, "Center");
   }

   private void defineUpperHalf() {
      this.upperHalf = new JPanel();
      this.upperHalf.setLayout(new BorderLayout());
      this.defineTopPanel();
      this.defineMiddlePanel();
      this.defineLowerPanel();
      this.upperHalf.add(this.topPanel, "North");
      this.upperHalf.add(this.middlePanel, "Center");
      this.upperHalf.add(this.lowerPanel, "South");
   }

   private void defineMiddleHalf() {
      this.middleHalf = new JPanel();
      this.middleHalf.setLayout(new BorderLayout());
      JSeparator s = new JSeparator();
      s.setOrientation(0);
      this.middleHalf.add(s, "South");
   }

   private void defineTopPanel() {
      this.topPanel = new JPanel();
      JLabel loginLabel = new JLabel("Login");
      Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
      this.topPanel.setLayout(new FlowLayout(0));
      this.topPanel.add(loginLabel);
   }

   private void defineMiddlePanel() {
      this.middlePanel = new JPanel();
      this.middlePanel.setLayout(new FlowLayout(0));
      this.defineLeftTextPanel();
      this.defineRightTextPanel();
      this.middlePanel.add(this.leftTextPanel);
      this.middlePanel.add(this.rightTextPanel);
   }

   private void defineLowerPanel() {
      this.lowerPanel = new JPanel();
      this.loginButton = new JButton("Login");
      this.addLoginButtonListener(this.loginButton);
      this.lowerPanel.add(this.loginButton);
   }

   private void defineLeftTextPanel() {
      JPanel topText = new JPanel();
      JPanel bottomText = new JPanel();
      topText.setLayout(new FlowLayout(0, 5, 0));
      bottomText.setLayout(new FlowLayout(0, 5, 0));
      this.username = new JTextField(10);
      this.label = new JLabel("Username");
      this.label.setFont(Util.makeSmallFont(this.label.getFont()));
      topText.add(this.username);
      bottomText.add(this.label);
      this.leftTextPanel = new JPanel();
      this.leftTextPanel.setLayout(new BorderLayout());
      this.leftTextPanel.add(topText, "North");
      this.leftTextPanel.add(bottomText, "Center");
   }

   private void defineRightTextPanel() {
      JPanel topText = new JPanel();
      JPanel bottomText = new JPanel();
      topText.setLayout(new FlowLayout(0, 5, 0));
      bottomText.setLayout(new FlowLayout(0, 5, 0));
      this.password = new JPasswordField(10);
      this.label = new JLabel("Password");
      this.label.setFont(Util.makeSmallFont(this.label.getFont()));
      topText.add(this.password);
      bottomText.add(this.label);
      this.rightTextPanel = new JPanel();
      this.rightTextPanel.setLayout(new BorderLayout());
      this.rightTextPanel.add(topText, "North");
      this.rightTextPanel.add(bottomText, "Center");
   }

   private void addLoginButtonListener(JButton butn) {
      butn.addActionListener((evt) -> {
         String user = this.username.getText().trim();
         String pwd = this.password.getText().trim();
         if (user.length() != 0 && pwd.length() != 0) {
//            User login = new User(user, pwd, (Auth)null);
//            List<User> list = Data.logins;
//            User u = Util.findUser(list, login);
        	 
        	 String id = username.getText();
			 String pass = password.getText();
			 control = new SystemController();
			 try {
				control.login(id, pass);
				
			} catch (business.LoginException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			if(SystemController.currentAuth != null) {
	               this.displayInfo("Login successful");
	               this.updateLeftPanel(SystemController.currentAuth);
	               this.bookClub.repaint();
			}
         } else {
            this.displayError("Id and Password fields must be nonempty");
         }

      });
   }

   private void updateLeftPanel(Auth auth) {
      if (auth == Auth.ADMIN) {
         this.adminItems();
      } else if (auth == Auth.LIBRARIAN) {
         this.librarianItems();
      } else if (auth == Auth.BOTH) {
         this.bothItems();
      }

   }

   private void adminItems() {
      ListItem[] adminItems = this.bookClub.getAdminItems();
      this.updateList(adminItems);
   }

   private void librarianItems() {
      ListItem[] librarianItems = this.bookClub.getLibrarianItems();
      this.updateList(librarianItems);
   }

   private void bothItems() {
      this.updateList((ListItem[])null);
   }

   private void updateList(ListItem[] items) {
      JList<ListItem> linkList = this.bookClub.getLinkList();
      DefaultListModel<ListItem> model = (DefaultListModel)linkList.getModel();
      int size = model.getSize();
      ListItem item;
      if (items != null) {
         List<Integer> indices = new ArrayList();
         ListItem[] var9 = items;
         int var8 = items.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            item = var9[var7];
            int index = model.indexOf(item);
            indices.add(index);
            ListItem next = (ListItem)model.get(index);
            next.setHighlight(true);
         }

         for(int i = 0; i < size; ++i) {
            if (!indices.contains(i)) {
               ListItem next = (ListItem)model.get(i);
               next.setHighlight(false);
            }
         }
      } else {
         for(int i = 0; i < size; ++i) {
            item = (ListItem)model.get(i);
            item.setHighlight(true);
         }
      }

   }

   public void updateData() {
   }
}
