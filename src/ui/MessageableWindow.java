package ui;

public interface MessageableWindow {
   default void displayError(String msg) {
      LibrarySystem.statusBar.setForeground(Util.ERROR_MESSAGE_COLOR);
      LibrarySystem.statusBar.setText(msg);
   }

   default void displayInfo(String msg) {
      LibrarySystem.statusBar.setForeground(Util.INFO_MESSAGE_COLOR);
      LibrarySystem.statusBar.setText(msg);
   }

   void updateData();
}
