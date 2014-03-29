package Library;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.Book;
import main.branch;


public class Clerk {

	//So we can use the connectoin "con" to access the SQL database
	branch b = new branch();
	Connection con = b.getConnection();
	java.util.Date date = new Date();
	java.sql.Date currentdate = new java.sql.Date(date.getTime());


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
	// THROWING ERRORS, but OTHERWISE WORKING
	public void checkOutItems(int id, ArrayList<Integer> callNumbers){
		//stub
		// If (
		//SELECT COUNT (bookcopy) FROM bookcopy, book WHERE book.bookcopy_status = "in", book.book_callNumber = bookcopy.book_callNumber 
		// != 0, &&
		// if ((SELECT borrower.borrower_expiryDate FROM borrower WHERE borrower.borrower_bid = id) > Current Date) {
		// INSERT INTO borrowing VALUES (?, id, i, copyNo, currentDate, null)
		// Print same values inserted
		// if (borrower.borrower_type = ___) returnDate = 
		PreparedStatement ps;
		PreparedStatement ps2;
		PreparedStatement ps3;
		PreparedStatement ps4;

		ResultSet rs1;
		ResultSet bookCopy;
		Date expiryDate = currentdate;

		//Getting # of books that are in
		int numberOfBooks =0;
		int copyNo=0;
		int borrowerDays=0;

		try{
			// Get the expiryDate of the user
			ps = con.prepareStatement("SELECT borrower.borrower_expiryDate FROM borrower WHERE borrower.borrower_bid = ?");
			ps.setInt(1, id);
			rs1 = ps.executeQuery();
			//have to call next once to get to the first row
			while(rs1.next()){
			expiryDate = rs1.getDate(1);
			}
			ps.close();

			// Compare to the current date	
			if (expiryDate.before(currentdate) == true){
				System.out.print("User is expired, cannot checkout books.");
				return;
			}

			else{
				// Do this for every book in the list
				for(int i = 0; i < callNumbers.size(); i++ ){
					//get the number of books available
					ps2 = con.prepareStatement("SELECT COUNT(bookcopy.bookcopy_copyNo), bookcopy.bookcopy_copyNo FROM bookcopy WHERE bookcopy.bookcopy_status = 'In' AND bookcopy.book_callNumber = ? GROUP BY bookcopy.bookcopy_copyNo");
					int callNumber = callNumbers.get(i);
					ps2.setInt(1, callNumber);
					bookCopy = ps2.executeQuery();

					if (bookCopy != null){
						while(bookCopy.next()){
							if (bookCopy.getInt(1) != 0){

								numberOfBooks = bookCopy.getInt(1);
								copyNo = bookCopy.getInt(2);		
							}
						}
					}

					ps2.close();

					// if there are copies of the book available...
					if (numberOfBooks > 0){

						ps3 = con.prepareStatement("INSERT INTO borrowing VALUES (seq_borrowing.nextval, ?, ?, ?, ?, null)");
						ps3.setInt(1, id);
						ps3.setInt(2, callNumber);
						ps3.setInt(3, copyNo);
						ps3.setDate(4, currentdate);
						ps3.executeUpdate();
						ps3.close();
						con.commit();

						//set status of book to "out"
						ps4 = con.prepareStatement("UPDATE bookcopy SET bookcopy.bookcopy_status = 'Out' WHERE bookcopy.bookcopy_copyNo = ?");
						ps4.setInt(1, copyNo);
						ps4.executeUpdate();
						ps4.close();
						con.commit();

						//Figure out when the user has to return the book by
						borrowerDays = findBorrowerDays(id);
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Calendar c = Calendar.getInstance();
						c.setTime(currentdate);
						c.add(Calendar.DATE, borrowerDays); // Adding 5 days
						Date tempDate = c.getTime();

						System.out.println("User " + id + " checked out book with callNo " + callNumber + "and copyNo " + copyNo +
								" until due date " + tempDate);
					}
					//Otherwise you can't checkout yo book
					else{
						System.out.print("Book with call number " + callNumber);
						System.out.print(" is not available.");
					}







				}
			}
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
		ResultSet rs;
		ResultSet rs2;
		ResultSet rs3;
		ResultSet rs4;
		PreparedStatement ps;
		PreparedStatement ps2;
		PreparedStatement ps3;
		PreparedStatement ps4;
		PreparedStatement ps5;
		PreparedStatement ps6;
		try
		{
			// Creates variable for the outDate and Bid of the user who bororwed this book
			ps = con.prepareStatement("SELECT * FROM borrowing WHERE borrowing.bookcopy_copyNo = ?");
			ps.setInt(1, copyNo);
			rs = ps.executeQuery();
			int bid = 0;
			int borid = 0;
			Date outDate = null;
			String BorrowerType = "";
			int BorrowerDays = 0;
			while (rs.next()){
				borid = rs.getInt(1);
				bid = rs.getInt(2);
				outDate = rs.getDate(5);
				System.out.print(bid);

			}


			//Sets the bookcopy to In
			ps2 = con.prepareStatement("UPDATE bookcopy SET bookcopy.bookcopy_status = 'In' WHERE bookcopy.bookcopy_copyNo = ?");
			ps2.setInt(1, copyNo);
			ps2.executeUpdate();
			con.commit();
			ps2.close();
			//Sets teh bookcopy InDate to today
			ps3 = con.prepareStatement("UPDATE borrowing SET borrowing.borrowing_inDate = ? WHERE borrowing.bookcopy_copyNo = ?");
			ps3.setDate(1,  currentdate);
			ps3.setInt(2,  copyNo);
			ps3.executeUpdate();
			con.commit();
			ps3.close();
			//		  //Figure out the BorrowerType
			ps4 = con.prepareStatement("SELECT * FROM borrower WHERE borrower.borrower_bid = ?");
			ps4.setInt(1, bid);
			rs2 = ps4.executeQuery();
			while (rs2.next()){
				BorrowerType = rs2.getString(9);
			}
			ps4.close();
			//		  //Figure out ascossiated Date with BorrowerType
			ps5 = con.prepareStatement("SELECT * FROM borrowertype WHERE borrowertype_type = ?");
			ps5.setString(1, BorrowerType);
			rs = ps5.executeQuery();
			while (rs.next()){
				BorrowerDays = rs.getInt(2);
			}
			ps5.close();

			//Creates a tempDate that is equal to the outdate + the borrowingtime
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(outDate);
			c.add(Calendar.DATE, BorrowerDays); // Adding 5 days
			Date tempDate = c.getTime();
			Date now = new Date();

			// If the user is late returning their book, a fine will be created for 20$
			if(now.before(tempDate) == false){
				ps6 = con.prepareStatement("INSERT INTO fine VALUES (seq_fine.nextval, 20, ?, null, ?)");
				ps6.setDate(1, currentdate);
				ps6.setInt(2, borid);
				ps6.executeUpdate();
				con.commit();
				ps6.close();
			}




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

	//Gives you the number of days a borrower is allowed to checkout an item for
	public int findBorrowerDays(int bid){
		//		  //Figure out the BorrowerType
		PreparedStatement ps;
		ResultSet rs;
		ResultSet rs2;
		String BorrowerType;
		int BorrowerDays = 0;

		try{

			ps = con.prepareStatement("SELECT * FROM borrower WHERE borrower.borrower_bid = ?");
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			rs.next();
			BorrowerType = rs.getString(9);

			ps.close();
			//Figure out ascossiated Date with BorrowerType
			ps = con.prepareStatement("SELECT * FROM borrowertype WHERE borrowertype_type = ?");
			ps.setString(1, BorrowerType);
			rs2 = ps.executeQuery();
			rs.next();
			BorrowerDays = rs2.getInt(2);

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
		return BorrowerDays;
	}


}
