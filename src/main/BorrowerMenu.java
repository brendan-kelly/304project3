package main;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Library.Borrower;

public class BorrowerMenu extends JFrame {
	JTextField searchfield;
	JTextField bidfield;
	JTextField bidfield2;
	JTextField callnumfield;

	
	public BorrowerMenu() {
		FlowLayout flowLayout = new FlowLayout();
		
		final JPanel panel = new JPanel();
        
		panel.setLayout(flowLayout);
		flowLayout.setAlignment(FlowLayout.TRAILING);
        
		JPanel borrowerpanel = new JPanel();
        borrowerpanel.setLayout(new FlowLayout());
        Label label = new Label("Borrower Menu");
        borrowerpanel.add(label);	

        //TODO:Search(string)
        searchfield = new JTextField(50);
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String input = searchfield.getText();	
                Borrower bor = new Borrower();
                bor.search(input);
            	
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
        
        //TODO:Check account(int)
        bidfield = new JTextField(10);
        JButton checkacc = new JButton("Check Account");
        checkacc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	Integer input = Integer.getInteger(bidfield.getText());	
                Borrower bor = new Borrower();
                bor.checkAccount(input);
            	
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
        
        //TODO:Place hold request(int int)
       
        bidfield2 = new JTextField(10);
        callnumfield = new JTextField(10);
        JButton holdreq = new JButton("Place hold request");
        holdreq.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	Integer bid = Integer.getInteger(bidfield2.getText());
            	Integer callno = Integer.getInteger(callnumfield.getText());	
                Borrower bor = new Borrower();
                bor.placeHoldRequest(bid, callno);
            	
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
        
        //TODO:Pay fine (int)
        
	}
	
}
