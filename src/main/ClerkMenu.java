package main;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Library.Borrower;
import Library.Clerk;

public class ClerkMenu {
	JTextField bname;
	JPasswordField bpassword;
	JTextField baddress;
	JTextField bphone;
	JTextField bemail;
	JTextField bsinorstno;
	Date bexpirydate; 
	JRadioButton std;
	JRadioButton fac;
	JRadioButton stf;


	public ClerkMenu(){
		FlowLayout flowLayout = new FlowLayout();
		
		final JPanel panel = new JPanel();
        
		panel.setLayout(flowLayout);
		flowLayout.setAlignment(FlowLayout.TRAILING);
        
		JPanel borrowerpanel = new JPanel();
        borrowerpanel.setLayout(new FlowLayout());
        Label label = new Label("Borrower Menu");
        borrowerpanel.add(label);	
        
	//TODO: Insert borrower (int string string string int string int int string)
        
        JButton addbor = new JButton("Add Borrower");
        addbor.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	addBorrowerInformation(panel);
            	
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
            
	//TODO: Check out items (int id, arraylist<integer> callnumbers)
	
	//TODO: Process Return (integer)
	
	//TODO:	Check out overdue items ()
	
	}


	private void addBorrowerInformation(final JPanel panel) {
		
		//Add borrower fields
        bname = new JTextField(50);
        bpassword = new JPasswordField(50);
        baddress = new JTextField(50);
        bphone = new JTextField(50);
        bemail = new JTextField(50);
        bsinorstno = new JTextField(50);
        std = new JRadioButton();
        fac = new JRadioButton();
        stf = new JRadioButton();
        
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Clerk clerk = new Clerk();
                clerk.insertBorrower(0, bname.getText(), bpassword.getText(), baddress.getText(), 
                		Integer.getInteger(bphone.getText()), bemail.getText(), 
                		Integer.getInteger(bsinorstno.getText()), Integer.getInteger(bphone.getText()), null);
            	
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
	}
}
