package Library;

import java.sql.*;
import java.util.ArrayList;

import Library.Main;


public class Clerk {


	Main m = new Main();
	Connection con = m.getConnection();


	// Creates a borrower with the given input
	public void insertBorrower(int id, String name, String password, String address, int phone, String email, int sinOrStNo, int expiryDate, String type){


		PreparedStatement  ps;


		try
		{
			ps = con.prepareStatement("INSERT INTO borrower VALUES (?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, password);
			ps.setString(3, name);

			if (address.length() == 0)
			{
				ps.setString(4, null);
			}
			else
			{
				ps.setString(4, address);
			}

			ps.setInt(5, phone);

			ps.setString(6, email);
			ps.setInt(sinOrStNo, 7);
			ps.setInt(expiryDate, 8);	 
			ps.setString(9, type);

			// commit work 
			ps.executeUpdate(); 
			con.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	//	Check-out items borrowed by a borrower. To borrow items, borrowers provide their card
	//	number and a list with the call numbers of the items they want to check out. The system
	//	determines if the borrower's account is valid and if the library items are availab
	//	le for
	//	borrowing. Then it creates one or more borrowing records and prints a note with the
	//	items and their due day (which is giver to the borrower)
	public void checkOutItems(int id, ArrayList<Integer> callNumbers){
		//stub
	}

	//	Processes a return. When
	//			an item is returned, the clerk records the return by providing the
	//			item's catalo
	//			gue number. The system determines the borrower who had borrowed the
	//			item and records that the item is "in".
	//			If the item is overdue, a fine is assessed for the
	//			borrower.
	//			If there is a hold request for this item by another borrower, the item is
	//			registered
	//			as "on hold" and a message is send to the borrower who made the hold request

	public void processReturn(int copyNo){
		//stub
	}

	//	Checks overdue items. The system displays a list of the items that are overdue and the
	//	borrowers who have checked them out.
	//	The clerk may decide to se
	//	nd an email messages
	//	to any
	//	of
	//	them (or to all of them)
	public void checkOverdueItems(){
		//stub
	}

}
