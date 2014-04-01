package Library;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.branch;

public class Borrower {
	java.util.Date date = new Date();
	java.sql.Date currentdate = new java.sql.Date(date.getTime());

	//Look for Wysiwyg for UI 

	public void Borrower(){
	}
	//So we can use the connectoin "con" to access the SQL database

	//	Search for books using keyword search on titles, authors and subjects. The result is a list
	//	of books that match the search together with the number of copies that are in and out.
	public void search(String keyword){

		branch b = new branch();
		Connection con = b.getConnection();
		
		PreparedStatement ps;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs;
		ResultSet bookCopy;
		
		Integer callNumber = 0;
		String title = "";

		ArrayList<Integer> bookList = new ArrayList<Integer>();
		ArrayList<String> titleList = new ArrayList<String>();
		
		int numberOfBooks = 0;
		
		try {
			ps = con.prepareStatement("SELECT book.book_callNumber, book_title FROM book, hassubject WHERE "
					+ "book.book_title LIKE ? OR book.book_mainAuthor LIKE ? OR hassubject.hassubject_subject"
					+ " LIKE ? AND book.book_callNumber = hassubject.book_callNumber");
			ps.setString(1, '%' + keyword + '%');
			ps.setString(2,'%' + keyword + '%');
			ps.setString(3,'%' + keyword + '%');
			rs = ps.executeQuery();

			while(rs.next()) {
				callNumber = rs.getInt("book_callNumber");
				title = rs.getString("book_title");
			    bookList.add(callNumber);
			    titleList.add(title);
			}
			
			con.commit();
			ps.close();
			
			System.out.println(bookList);
			System.out.println(titleList);
			
			for(int i = 0; i < bookList.size(); i++ ){ //get the number of books available 
				ps2 = con.prepareStatement("SELECT COUNT(bookcopy.bookcopy_copyNo) FROM bookcopy WHERE bookcopy.bookcopy_status = 'In'"
									     + " AND bookcopy.book_callNumber = ?"); 
				int callNumber1 = bookList.get(i); 
				ps2.setInt(1, callNumber1); 
				bookCopy = ps2.executeQuery(); 
				if (bookCopy != null){ 
					while(bookCopy.next()){ 
						if (bookCopy.getInt(1) != 0){ 
							numberOfBooks = bookCopy.getInt(1); 
						} 
					} 
				} 
				System.out.println("Book Name: " + titleList.get(i) + " has " + numberOfBooks + " copies avaliable.");
				
			}
			ps2.close();
			
			for(int i = 0; i < bookList.size(); i++ ){ //get the number of books available 
				ps3 = con.prepareStatement("SELECT COUNT(bookcopy.bookcopy_copyNo) FROM bookcopy WHERE bookcopy.bookcopy_status = 'Out'"
										 + " AND bookcopy.book_callNumber = ?"); 
				int callNumber1 = bookList.get(i); 
				ps3.setInt(1, callNumber1); 
				bookCopy = ps3.executeQuery(); 
				if (bookCopy != null){ 
					while(bookCopy.next()){ 
						if (bookCopy.getInt(1) != 0){ 
							numberOfBooks = bookCopy.getInt(1); 
						} 
					} 
				} 
				System.out.println("Book Name: " + titleList.get(i) + " has " + numberOfBooks + " copies borrowed.");
			}
			ps3.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//	Check
	//	his/her account. The system will display the items the borrower has currently
	//	borrowed and not yet returned,
	//	any outstanding fines and the hold requests that have been
	//	placed by the borrower. 
	//	NOTE: Not sure need to input id or not, because it IS in the specific borrower class, so we could create an instance of
	//	the borrower item and then call this on the specific item, or we could input the specific ID of the user. 
	public void checkAccount(int id){
		
		branch b = new branch();
		Connection con = b.getConnection();
		
		PreparedStatement ps;
		PreparedStatement ps2;
		PreparedStatement ps3;
		ResultSet rs;
		ResultSet rs2;
		ResultSet rs3;
		
		String title = "";
		String amount = "";
		String holdRequestCallNumber = "";
		
		ArrayList<String> bookList = new ArrayList<String>();
		ArrayList<String> holdRequestList = new ArrayList<String>();
		ArrayList<String> fineList = new ArrayList<String>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM book WHERE EXISTS (SELECT * FROM borrowing WHERE " +
					   "borrowing.borrowing_inDate IS NULL AND borrowing.borrower_bid = ? " +
					   "AND borrowing.book_callNumber = book.book_callNumber)");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while(rs.next()) {
				title = rs.getString("book_title");
				bookList.add(title);
			}
			
			ps2 = con.prepareStatement("SELECT * FROM Fine, borrowing WHERE fine.fine_paidDate IS NULL " +
									   "AND fine.borrowing_borid = borrowing.borrowing_borid ");
			rs2 = ps2.executeQuery();
			
			while(rs2.next()) {
				amount = rs2.getString("fine_amount");
				fineList.add(amount);
			}
			
			ps3 = con.prepareStatement("SELECT * FROM holdrequest WHERE holdrequest.borrower_bid = ?");
			ps3.setInt(1, id);
			rs3 = ps3.executeQuery();
			
			while(rs3.next()) {
				
				holdRequestCallNumber = rs3.getString("book_callNumber");
				holdRequestList.add(holdRequestCallNumber);
			}
			
			ps.executeUpdate(); 
			con.commit();
			ps.close();
			
			System.out.println(bookList);
			System.out.println("Fine amount = " + fineList);
			System.out.println(holdRequestList);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//	Place a hold request for a book that is out. When the item is r
	//	eturned, the system sends an
	//	email to the borrower and informs the library clerk to keep the book out of the shelves.
	public void placeHoldRequest(int callNo, int id){
		//stub
		// INSERT INTO holdrequest VALUES (_, id, callNo, currentDate)

		branch b = new branch();
		Connection con = b.getConnection();
		PreparedStatement  ps;

		try { 	//holdrequest_hid integer not null PRIMARY KEY,
				//borrower_bid integer not null,
				//book_callNumber integer not null,
				//holdrequest_issuedDate date,
			ps = con.prepareStatement("INSERT INTO holdrequest VALUES (seq_holdrequest.nextval, ?, ?, ?)");
			ps.setInt(1, id);
			ps.setInt(2, callNo);
			ps.setDate(3, currentdate);
			
			//gets currentDate
//			java.util.Date date = new Date();
//			java.sql.Date d = new java.sql.Date(date.getTime());
//			ps.setDate(1, d);
			//ps.setString(1, "01-01-2014");

			// commit work 
			ps.executeUpdate(); 
			con.commit();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// first value is unique generated integer, currentDate is obv
	}
	
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
