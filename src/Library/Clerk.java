package Library;

import java.sql.*;
import java.util.ArrayList;

import main.Book;
import main.branch;


public class Clerk {

	//So we can use the connectoin "con" to access the SQL database
	branch b = new branch();
	Connection con = b.getConnection();


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
	//	determines if the borrower's account is valid and if the library items are available for
	//	borrowing. Then it creates one or more borrowing records and prints a note with the
	//	items and their due day (which is giver to the borrower)
	
	public void checkOutItems(int id, ArrayList<Integer> callNumbers){
		//stub
		// If (
		//SELECT COUNT (bookcopy) FROM bookcopy, book WHERE book.bookcopy_status = "in", book.book_callNumber = bookcopy.book_callNumber 
		// != 0, &&
		// if ((SELECT borrower.borrower_expiryDate FROM borrower WHERE borrower.borrower_bid = id) > Current Date) {
		// INSERT INTO borrowing VALUES (?, id, i, copyNo, currentDate, null)
		// Print same values inserted
		// if (borrower.borrower_type = ___) returnDate = 
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
		// Find user that borrowed:
		// SELECT borrowing FROM  borrowing WHERE borrowing.book_copyNo = copyNo AS Temp
		// This changes it to in: 
		// UPDATE bookcopy SET bookcopy.bookcopy_status = "in" WHERE bookcopy.bookcopy_copyNo = copyNo
		// if (borrowing.borrowing_outDate > borrowing.borrowing_inDate + userTypeTime) then
		// INSERT INTO fine VALUES (generated, amount??, currentDate, null, id)
		// Send "messages" to all the following users: 
		// SELECT holdrequest.borrowing_bid FROM holdrequest, Temp WHERE Temp.book_callNumber = holdrequest.book_callNumber
		
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
		//																									borrower.borrowertype_type = borrowertype.borrowertype_type
		// SELECT bookcopy, borrower FROM borrowing, borrowertype WHERE borrowing.borrowing_inDate IS NULL, ((int [borrowertype.borrowertype_bookTimeLimit) + 
		//												  borrowing.borrowing_outDate) < CurrentDate 
	}

}
