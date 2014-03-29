package main;

import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BorrowerMenu extends JFrame {
	
	public BorrowerMenu() {
		FlowLayout flowLayout = new FlowLayout();
		
		final JPanel panel = new JPanel();
        
		panel.setLayout(flowLayout);
		flowLayout.setAlignment(FlowLayout.TRAILING);
        
		JPanel borrowerpanel = new JPanel();
        borrowerpanel.setLayout(new FlowLayout());
        Label label = new Label("Borrower Menu");
        borrowerpanel.add(label);	
	}
}
