package Library;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.branch;
import main.Book;

public class Borrower {
	java.util.Date date = new Date();
	java.sql.Date currentdate = new java.sql.Date(date.getTime());

	//Look for Wysiwyg for UI 

	public void Borrower(){
	//	payFine(1);
	}
	//So we can use the connectoin "con" to access the SQL database

	//	Search for books using keyword search on titles, authors and subjects. The result is a list
	//	of books that match the search together with the number of copies that are in and out.
	public void search(String keyword){
		Statement  stmt;
		ResultSet  rs;

		// SELECT * FROM book, hassubject WHERE book.book_title = keyword OR book.book_mainAuthor = keyword OR hassubject.hassubject_subject = keyword AND book.book_callNumber = hassubject.book_callNumber ORDER BY book.book_callNumber AS Temp
		// SELECT COUNT (bookcopy) FROM bookcopy, Temp WHERE book.bookcopy_status = "in", Temp.book_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber
		// SELECT COUNT (bookcopy) FROM bookcopy, Temp WHERE book.bookcopy_status = "out", Temp.book_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber
		// works! SELECT * FROM book, hassubject WHERE book.book_title = 'Kavinsky' OR book.book_mainAuthor = 'Kavinsky' OR hassubject.hassubject_subject = 'Deep and Passionate Teen Angst' AND book.book_callNumber = hassubject.book_callNumber
	}

	//	Check
	//	his/her account. The system will display the items the borrower has currently
	//	borrowed and not yet returned,
	//	any outstanding fines and the hold requests that have been
	//	placed by the borrower. 
	//	NOTE: Not sure need to input id or not, because it IS in the specific borrower class, so we could create an instance of
	//	the borrower item and then call this on the specific item, or we could input the specific ID of the user. 

	public void checkAccount(int id){
		//stub
		// SELECT * FROM book
		// works!! SELECT * FROM borrowing WHERE borrowing.borrowing_inDate IS NULL AND
		// borrowing.borrowing_outDate IS NOT NULL AND borrowing.borrower_bid = id
		// this should return the books they borrowed
		// SELECT * FROM Fine, borrowing WHERE fine.fine_paidDate IS NULL AND borrowing.borrowing_borid = fine.borrowing_borid
		// AND borrowing.borrower_bid = id
		// This shoudl return all the fines that have not been paid from the list of borrowed books?
		// SELECT * FROM holdrequest WHERE holdrequest.borrower_bid = id
		
		String callNumber = "";
		String isbn = "";
		String title = "";
		String mainAuthor = "";
		String publisher = "";
		String year = "";
		
		String fid = "";
		String amount = "";
		String fineIssuedDate = "";
		String paidDate = "";
		String borrowing = "";
		
		String hid = "";
		String holdRequestCallNumber = "";
		String holdRequestIssuedDate = "";
		
		branch b = new branch();
		Connection con = b.getConnection();
		
		PreparedStatement ps;
		PreparedStatement ps2;
		PreparedStatement ps3;
		ResultSet rs;
		ResultSet rs2;
		ResultSet rs3;
		
		try {
			ps = con.prepareStatement("SELECT * FROM book WHERE EXISTS (SELECT * FROM borrowing WHERE " +
					   "borrowing.borrowing_inDate IS NULL AND borrowing.borrower_bid = ? " +
					   "AND borrowing.book_callNumber = book.book_callNumber)");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while(rs.next()) {
				callNumber = rs.getString("book_callNumber");
				isbn = rs.getString("book_isbn");
				title = rs.getString("book_title");
				mainAuthor = rs.getString("book_mainAuthor");
				publisher = rs.getString("book_publisher");
				year = rs.getString("book_year");
			}
			
			ps2 = con.prepareStatement("SELECT * FROM Fine, borrowing WHERE fine.fine_paidDate IS NULL " +
									   "AND fine.borrowing_borid = borrowing.borrowing_borid ");
			//ps2.setInt(1, id);
			rs2 = ps2.executeQuery();
			
			while(rs2.next()) {
				fid = rs2.getString("fine_fid");
				amount = rs2.getString("fine_amount");
				fineIssuedDate = rs2.getString("fine_issuedDate");
				paidDate = rs2.getString("fine_paidDate");
				borrowing = rs2.getString("borrowing_borid");
			}
			
			ps3 = con.prepareStatement("SELECT * FROM holdrequest WHERE holdrequest.borrower_bid = ?");
			ps3.setInt(1, id);
			rs3 = ps3.executeQuery();
			
			while(rs3.next()) {
				hid = rs3.getString("holdrequest_hid");
				holdRequestCallNumber = rs3.getString("book_callNumber");
				holdRequestIssuedDate = rs3.getString("holdrequest_issuedDate");
			}
			
			ps.executeUpdate(); 
			con.commit();
			ps.close();
			
			System.out.println(callNumber);
			System.out.println(isbn);
			System.out.println(title);
			System.out.println(mainAuthor);
			System.out.println(publisher);
			System.out.println(year);
					
			System.out.println(fid);
			System.out.println(amount);
			System.out.println(fineIssuedDate);
			System.out.println(paidDate);
			System.out.println(borrowing);
					
			System.out.println(hid);
			System.out.println(holdRequestCallNumber);
			System.out.println(holdRequestIssuedDate);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//	Place a hold request for a book that is out. When the item is r
	//	eturned, the system sends an
	//	email to the borrower and informs the library clerk to keep the book out of the shelves.

	
//	public void placeHoldRequest(int callNo, int id){
//		//stub
//		// INSERT INTO holdrequest VALUES (_, id, callNo, currentDate)
//
//		branch b = new branch();
//		Connection con = b.getConnection();
//		PreparedStatement  ps;
//
//		try {
//			ps = con.prepareStatement("INSERT INTO holdrequest(bid, callNumber, issuedDate) VALUES (?, ?, ?)");
//			ps.setInt(1, id);
//			ps.setInt(2, callNo);
//			ps.setDate(3, currentdate);
//			
//			//gets currentDate
//			java.util.Date date = new Date();
//			java.sql.Date d = new java.sql.Date(date.getTime());
//			ps.setDate(1, d);
//			//ps.setString(1, "01-01-2014");
//
//			// commit work 
//			ps.executeUpdate(); 
//			con.commit();
//			ps.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		// first value is unique generated integer, currentDate is obv
//	}
	
	/*
	 * COMPLETED!
	 */ 
	public void payFine(int fid) {
		
		//stub
		// UPDATE fine SET fine.fine_paidDate = currentDate WHERE fine.fine_fid = fid

		branch b = new branch();
		Connection con = b.getConnection();


		//Connection con = b.getConnection();

		PreparedStatement  ps;


		try
		{
			ps = con.prepareStatement("UPDATE fine SET fine.fine_paidDate = ? WHERE fine.fine_fid = ?");

			//sets the WHERE to fid
			ps.setInt(2, fid);
			ps.setDate(1, currentdate);
			
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
				System.out.print("Rolled back");
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
}
