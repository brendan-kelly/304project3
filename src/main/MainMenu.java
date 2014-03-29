package main;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MainMenu extends JFrame{

	JPanel panel;
	JButton button; 
	JLabel label; 

	public MainMenu(String name){
	super(name);
	}
	
	public void addComponentsToPane(final Container pane){
		
//		JRadioButton RtoLbutton;
//		JRadioButton LtoRbutton;
		FlowLayout flowLayout = new FlowLayout();

	//	JButton applyButton = new JButton("Apply component orientation");
        final JPanel panel = new JPanel();
        panel.setLayout(flowLayout);
        flowLayout.setAlignment(FlowLayout.TRAILING);
        JPanel welcomepanel = new JPanel();
        welcomepanel.setLayout(new FlowLayout());
        Label label = new Label("Welcome to the Library Database!");
        welcomepanel.add(label);
        
//        LtoRbutton = new JRadioButton(LtoR);
//        LtoRbutton.setActionCommand(LtoR);
//        LtoRbutton.setSelected(true);
//        RtoLbutton = new JRadioButton(RtoL);
//        RtoLbutton.setActionCommand(RtoL);
        
        //Add buttons to the experiment layout
        JButton borrowerButton = new JButton("Borrower");
        panel.add(borrowerButton);
        JButton librarianButton = new JButton("Librarian");
        panel.add(librarianButton);
        JButton clerkButton = new JButton("Clerk");
        panel.add(clerkButton);

        
        //Left to right component orientation is selected by default        
        //Add controls to set up the component orientation in the experiment layout
//        final ButtonGroup group = new ButtonGroup();
//        group.add(LtoRbutton);
//        group.add(RtoLbutton);
//        controls.add(LtoRbutton);
//        controls.add(RtoLbutton);
        
        //Process the Apply component orientation button press
        borrowerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	BorrowerMenu bmenu = new BorrowerMenu();
            	pane.removeAll();
            	//update the experiment layout
                panel.validate();
                panel.repaint();
            }
        });
        pane.add(panel, BorderLayout.CENTER);
        pane.add(welcomepanel, BorderLayout.NORTH); 
    }

		
		/*setTitle("Library Menu");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout()); //PREFERRED!		getContentPane().add(panel);
		getContentPane().add(panel);

		JButton borrowerButton = new JButton("Borrower");
		borrowerButton.setVisible(true);

		JButton clerkButton = new JButton("Clerk");
		clerkButton.setVisible(true);
		JButton librarianButton = new JButton("Librarian");
		librarianButton.setVisible(true);

		panel.add(borrowerButton);
		panel.add(clerkButton);
		panel.add(librarianButton);

		label = new JLabel();*/



	}

