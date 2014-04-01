package main;


// We need to import the java.sql package to use JDBC
import java.sql.*;

// for the login window
import Library.Borrower;
import Library.Librarian;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class branch
{
	// command line reader 
	//    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private Connection con;

	// user is allowed 3 login attempts
	//    private int loginAttempts = 0;
	//
	//    // components of the login window
	//    private JTextField usernameField;
	//    private JPasswordField passwordField;
	//    private JFrame mainFrame;


	/*
	 * constructs login window and loads JDBC driver
	 */ 
	public branch()
	{
		//      mainFrame = new JFrame("User Login");
		//
		//      JLabel usernameLabel = new JLabel("Enter username: ");
		//      JLabel passwordLabel = new JLabel("Enter password: ");
		//
		//      usernameField = new JTextField(10);
		//      passwordField = new JPasswordField(10);
		//      passwordField.setEchoChar('*');
		//
		//      JButton loginButton = new JButton("Log In");
		//
		//      JPanel contentPane = new JPanel();
		//      mainFrame.setContentPane(contentPane);
		//
		//
		//      // layout components using the GridBag layout manager
		//
		//      GridBagLayout gb = new GridBagLayout();
		//      GridBagConstraints c = new GridBagConstraints();
		//
		//      contentPane.setLayout(gb);
		//      contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//
		//      // place the username label 
		//      c.gridwidth = GridBagConstraints.RELATIVE;
		//      c.insets = new Insets(10, 10, 5, 0);
		//      gb.setConstraints(usernameLabel, c);
		//      contentPane.add(usernameLabel);
		//
		//      // place the text field for the username 
		//      c.gridwidth = GridBagConstraints.REMAINDER;
		//      c.insets = new Insets(10, 0, 5, 10);
		//      gb.setConstraints(usernameField, c);
		//      contentPane.add(usernameField);
		//
		//      // place password label
		//      c.gridwidth = GridBagConstraints.RELATIVE;
		//      c.insets = new Insets(0, 10, 10, 0);
		//      gb.setConstraints(passwordLabel, c);
		//      contentPane.add(passwordLabel);
		//
		//      // place the password field 
		//      c.gridwidth = GridBagConstraints.REMAINDER;
		//      c.insets = new Insets(0, 0, 10, 10);
		//      gb.setConstraints(passwordField, c);
		//      contentPane.add(passwordField);
		//
		//      // place the login button
		//      c.gridwidth = GridBagConstraints.REMAINDER;
		//      c.insets = new Insets(5, 10, 10, 10);
		//      c.anchor = GridBagConstraints.CENTER;
		//      gb.setConstraints(loginButton, c);
		//      contentPane.add(loginButton);
		//
		//      // register password field and OK button with action event handler
		//      passwordField.addActionListener(this);
		//      loginButton.addActionListener(this);
		//
		//      // anonymous inner class for closing the window
		//      mainFrame.addWindowListener(new WindowAdapter() 
		//      {
		//	public void windowClosing(WindowEvent e) 
		//	{ 
		//	  System.exit(0); 
		//	}
		//      });
		//
		//      // size the window to obtain a best fit for the components
		//      mainFrame.pack();
		//
		//      // center the frame
		//      Dimension d = mainFrame.getToolkit().getScreenSize();
		//      Rectangle r = mainFrame.getBounds();
		//      mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		//
		//      // make the window visible
		//      mainFrame.setVisible(true);
		//
		//      // place the cursor in the text field for the username
		//      usernameField.requestFocus();

		try 
		{
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.print("Driver Registered!");
			
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}
		
//		if(connect("These values do", "not matter")){
//			insertHasAuthor();
//			System.out.print("CONNECTED");
//		}
//		else System.out.print("NOT CONNECTED!");
	}


	/*
	 * connects to Oracle database named ug using user supplied username and password
	 */ 
/*	private boolean connect(String username, String password)
	{
		// String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

		try 
		{
			//con = DriverManager.getConnection(connectURL,username,password);
			con = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug","ora_z7z7","a29420114");
			System.out.println("\nConnected to Oracle!");
			return true;
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}*/

	public static void main(String args[]) throws SQLException
	{
		//MainMenu m = new MainMenu();
        
		 /* Use an appropriate Look and Feel */
//        try {
//            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//        //Schedule a job for the event dispatchi thread:
//        //creating and showing this application's GUI.
//        
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });


		
		branch b = new branch();
		Librarian Conor = new Librarian();
		Conor.generateYearlyReport(2013, 10);
		Conor.generateYearlyReport(2012, 10);
		Conor.generateYearlyReport(2011, 10);

	
	}


/*	private void insertHasAuthor() {
		PreparedStatement  ps;
		try {
			ps = con.prepareStatement("INSERT into hasauthor values (?,?)");
					ps.setInt(1, 1);
					ps.setString(2, "HARLAN IS OVERLORD; BOW DOWN PLS");
					ps.executeUpdate(); 
					con.commit();
					ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        MainMenu frame = new MainMenu("Library");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
	public Connection getConnection() {
		try 
		{
			//con = DriverManager.getConnection(connectURL,username,password);
			con = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug","ora_z7z7","a29420114");
			System.out.println("\nConnected to Oracle!");

		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
		return con;
	}
}
