package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;

public class AllMemberIds implements MessageableWindow {
	ControllerInterface ci = new SystemController();
   private JPanel mainPanel = new JPanel();
   private JPanel topPanel;
   private JPanel middlePanel;
   private TextArea textArea;

   public JPanel getMainPanel() {
      return this.mainPanel;
   }

   public AllMemberIds() {
      this.mainPanel.setLayout(new BorderLayout());
      this.defineTopPanel();
      this.defineMiddlePanel();
      this.mainPanel.add(this.topPanel, "North");
      this.mainPanel.add(this.middlePanel, "Center");
   }

   public void defineTopPanel() {
      this.topPanel = new JPanel();
      JLabel titlesLabel = new JLabel("View Book ID");
      Util.adjustLabelFont(titlesLabel, Util.DARK_BLUE, true);
      this.topPanel.setLayout(new FlowLayout(0));
      this.topPanel.add(titlesLabel);
   }

   public void defineMiddlePanel() {
      this.middlePanel = new JPanel();
      FlowLayout fl = new FlowLayout(1, 25, 25);
      this.middlePanel.setLayout(fl);
      this.textArea = new TextArea(8, 20);
      this.updateData();
      this.middlePanel.add(this.textArea);
   }

   public void updateData() {	   
	   List<String> ids = ci.allMemberIds();
		Collections.sort(ids);
		StringBuilder sb = new StringBuilder();
		for(String s: ids) {
			sb.append(s + "\n");
		}

      this.textArea.setText(sb.toString());
      this.mainPanel.repaint();
   }
}
