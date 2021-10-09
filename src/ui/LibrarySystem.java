package ui;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class LibrarySystem extends JFrame implements MessageableWindow {
   String[] links;
   JList<ListItem> linkList;
   JPanel cards;
   public static JTextArea statusBar = new JTextArea("Welcome to Library System");
   LoginPanel lp;
   AllMemberIds abip;
   AllBooksID abid;
   ListItem login = new ListItem("Login", true);
   ListItem allBooks = new ListItem("All Books ID", false);
   ListItem allMembers = new ListItem("All Members ID", false);
   ListItem addLibraryMember = new ListItem("Add Library Member", false);
   ListItem checkoutBook = new ListItem("Checkout Book", false);
   ListItem checkoutRecords = new ListItem("Print Checkout Records", false);
   ListItem bookStatus = new ListItem("Book Status", false);
   ListItem addBookCopy = new ListItem("Add Book Copy", false);
   ListItem addBook = new ListItem("Add Book", false);
   ListItem[] adminItems;
   ListItem[] librarianItems;

   public ListItem[] getAdminItems() {
      return this.adminItems;
   }

   public ListItem[] getLibrarianItems() {
      return this.librarianItems;
   }

   public JList<ListItem> getLinkList() {
      return this.linkList;
   }

   public LibrarySystem() {
      this.adminItems = new ListItem[]{this.login,this.allBooks, this.allMembers,
    		  this.addLibraryMember,this.checkoutBook, this.checkoutRecords, this.bookStatus, this.addBook, this.addBookCopy};
      this.librarianItems = new ListItem[]{this.login, this.allBooks, 
    		  this.allMembers, this.checkoutBook, this.checkoutRecords,this.bookStatus, this.addBook, this.addBookCopy};
      Util.adjustLabelFont(statusBar, Util.DARK_BLUE, true);
      this.setSize(650, 450);
      this.createLinkLabels();
      this.createMainPanels();
      CardLayout cl = (CardLayout)this.cards.getLayout();
      this.linkList.addListSelectionListener((event) -> {
         String value = ((ListItem)this.linkList.getSelectedValue()).getItemName();
         boolean allowed = ((ListItem)this.linkList.getSelectedValue()).highlight();
         System.out.println(value + " " + allowed);
         statusBar.setText("");
         if (!allowed) {
            value = this.allBooks.getItemName();
            this.linkList.setSelectedIndex(0);
         }

         if (value.equals("All Members ID")) {
            this.abip.updateData();
         }
         
         if (value.equals("All Books ID")) {
             this.abid.updateData();
          }

         cl.show(this.cards, value);
      });
      JSplitPane innerPane = new JSplitPane(1, this.linkList, this.cards);
      innerPane.setDividerLocation(180);
      JSplitPane outerPane = new JSplitPane(0, innerPane, statusBar);
      outerPane.setDividerLocation(350);
      this.add(outerPane, "Center");
      this.lp.setBookClub(this);
   }

   public JTextArea getStatusBar() {
      return statusBar;
   }

   public void createLinkLabels() {
      DefaultListModel<ListItem> model = new DefaultListModel();
      model.addElement(login);
      model.addElement(this.allBooks);
      model.addElement(this.allMembers);
      model.addElement(this.addLibraryMember);
      model.addElement(this.checkoutBook);
      model.addElement(this.checkoutRecords);
      model.addElement(this.bookStatus);
      model.addElement(this.addBook);
      model.addElement(this.addBookCopy);
      this.linkList = new JList(model);
      this.linkList.setCellRenderer(new DefaultListCellRenderer() {
         public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ListItem) {
               ListItem nextItem = (ListItem)value;
               this.setText(nextItem.getItemName());
               if (nextItem.highlight()) {
                  this.setForeground(Util.LINK_AVAILABLE);
               } else {
                  this.setForeground(Util.LINK_NOT_AVAILABLE);
               }

               if (isSelected) {
                  this.setForeground(Color.BLACK);
                  this.setBackground(new Color(236, 243, 245));
               }
            }

            return c;
         }
      });
   }

   public void createMainPanels() {
      this.lp = new LoginPanel();
      JPanel loginPanel = this.lp.getMainPanel();
      AddLibraryMember abp = new AddLibraryMember();
      JPanel addLibraryMember = abp.getMainPanel();
      Checkout checkout = new Checkout();
      JPanel addCheckout = checkout.getMainPanel();
      this.abip = new AllMemberIds();
      JPanel allMemberIds = this.abip.getMainPanel();
      this.abid = new AllBooksID();
      JPanel allBooksId = this.abid.getMainPanel();
      CheckoutRecord record = new CheckoutRecord();
      JPanel recordInstance = record.getMainPanel();
      CheckoutCopyDueDate copyStatus = new CheckoutCopyDueDate();
      JPanel copyInstance = copyStatus.getMainPanel();
      AddBookCopy bookCopy = new AddBookCopy();
      JPanel bookCopyInstance = bookCopy.getMainPanel();
      AddBookPanel addBookPanel = new AddBookPanel();
      JPanel bookPanelInstance = addBookPanel.getMainPanel();
      this.cards = new JPanel(new CardLayout());
      this.cards.add(loginPanel, this.login.getItemName());
      this.cards.add(allBooksId, this.allBooks.getItemName());
      this.cards.add(allMemberIds, this.allMembers.getItemName());
      this.cards.add(addLibraryMember, this.addLibraryMember.getItemName());
      this.cards.add(addCheckout, this.checkoutBook.getItemName());
      this.cards.add(recordInstance, this.checkoutRecords.getItemName());
      this.cards.add(copyInstance, this.bookStatus.getItemName());
      this.cards.add(bookCopyInstance, this.addBookCopy.getItemName());
      this.cards.add(bookPanelInstance, this.addBook.getItemName());
   }

   public void updateData() {
   }
}
