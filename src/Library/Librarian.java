package Library;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.branch;

public class Librarian {
	//So we can use the connectoin "con" to access the SQL database
	branch b = new branch();
	Connection con = b.getConnection();
	java.util.Date date = new Date();
	java.sql.Date currentdate = new java.sql.Date(date.getTime());

	//		Adds a new book or new copy of an existing book to the library. The librarian provides
	//		the information for the new book, and the system adds it to the library
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, java.sql.Date Year){
		//stub, think Brendan already wrote this


		PreparedStatement  ps;

		try {
			ps = con.prepareStatement("INSERT INTO book VALUES (seq_book.nextval, ?, ?, ?, ?, ?)");
			ps.setInt(1,  isbn);
			ps.setString(2,  title);
			ps.setString(3,  mainAuthor);
			ps.setString(4,  publisher);
			ps.setDate(5,  Year);
			ps.executeUpdate(); 
			con.commit();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//		
	//		Generate a report with all the books that have been
	//		checked out. For each book the report
	//		shows the date it was checked out and the due date. The system flags the items that are
	//		overd
	//		ue. The items are ordered by the book call number.
	//		If a subject is provided the
	//		report lists only books related to that subject, otherwise all the books that are out are
	//		listed by the report.
	//		NOTE: potentailly we will do all the UI stuff with one class, in which case we would 
	//		 change the return type of this method from void to some sort of ArrayList<> - and then return that
	//		 so our UI function can call/print it
	public void generateBookReport(String subject){
		//stub

		//We first want to find all the books that have been checked out
		// Get all teh bookcopies that are out (if (subject == null)): 
		// SELECT bookcopy.bookcopy_copyNo FROM bookcopy WHEERE bookcopy.bookcopy_status = 'Out' ORDER BY bookcopy.book_callNumber
		// Get all the bookcopies that are out with a certain subject: (else {)
		// SELECT bookcopy.bookcopy_copyNo FROM bookcopy, hassubject WHERE bookcopy.bookcopy_status = 'Out' AND hassubject.hassubject_subject = ? AND hassubject.hassubject_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber
		// have a list with all the ArrayList<bookcopy.copyNo>
		// For each bookcopy we need to know the c/o date and due date (requires bid)
		// for each entry in arraylist:
		// SELECT borrowing.borrower_bid, borrowing.borrowing_outDate FROM borrowing WHERE borrowing.bookcopy_copyNo = ?
		// plug in id to Borrower.FindBorrowerDays

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs;
		ResultSet rs2;
		ResultSet rs3;
		Statement st;
		ArrayList<Integer> bookCopyNos = new ArrayList<Integer>();
		ArrayList<Date> bookOutDates = new ArrayList<Date>();
		ArrayList<Date> bookDueDates = new ArrayList<Date>();
		int bid = 0;
		int days = 0;
		Date outDate = null;

		try{
			// if there is no subject, put it all in a list of bookcopynos
			if (subject == null){
				st = con.createStatement();
				rs = st.executeQuery("SELECT bookcopy.bookcopy_copyNo FROM bookcopy WHERE bookcopy.bookcopy_status = 'Out' ORDER BY bookcopy.book_callNumber");
				while (rs.next()){
					bookCopyNos.add(rs.getInt(1));
				}
				System.out.println(bookCopyNos);	
			}
			// if there is a subject, do the same thing but only for books with that subject
			else{
				ps = con.prepareStatement("SELECT bookcopy.bookcopy_copyNo FROM bookcopy, hassubject WHERE bookcopy.bookcopy_status = 'Out' AND hassubject.hassubject_subject = ? AND hassubject.book_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber");
				ps.setString(1, subject);
				rs2 = ps.executeQuery();
				while (rs2.next()){
					bookCopyNos.add(rs2.getInt(1));
				}
				System.out.println(bookCopyNos);	
				ps.close();
			}
			con.commit();
		
			//Now there is an ArrayList with all the bookcopies that match the given subject and are out
			// for everything in the list
			for (int i=0; i<bookCopyNos.size(); i++){
				ps2 = con.prepareStatement("SELECT borrowing.borrower_bid, borrowing.borrowing_outDate FROM borrowing WHERE borrowing.bookcopy_copyNo = ?");
				ps2.setInt(1, bookCopyNos.get(i));
				rs3 = ps2.executeQuery();
				
					while(rs3.next()){
						bid = rs3.getInt(1);
						outDate = rs3.getDate(2);
						bookOutDates.add(outDate);
					}
				
				//Get a Clerk to use function in Clerk class
				Clerk cl = new Clerk();
				days = cl.findBorrowerDays(bid);
				Calendar c = Calendar.getInstance();
				c.setTime(outDate);
				c.add(Calendar.DATE, days); // Adding days
				Date tempDate = c.getTime();

				//Check if book is overdue
				if (tempDate.before(currentdate) == true){
					System.out.println("The book with copyNo " + bookCopyNos.get(i) + " is overdue and its outDate is " + outDate);
				}
				else{
					System.out.println("The book with copyNo " + bookCopyNos.get(i) + " is NOT overdue and its outDate is " + outDate);
				}
				ps2.close();

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
	//	Generate a report with the most popular items in a given year.
	//	The librarian provides a
	//	year and a number n. The system lists out the top n books that where borrowed the most
	//	times during that year. The books are ordered by the number of times they were
	//	borrowed.

	public void generateYearlyReport(Integer year, Integer n){
		//SELECT book.book_callNumber, count(book.book_callNumber) FROM borrowing, book WHERE (select to_char(borrowing.borrowing_outDate, 'YYYY') from dual) = '2013' AND book.book_callNumber = borrowing.book_callNumber GROUP BY book.book_callNumber;
		//A29420114
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Integer> callNumbers = new ArrayList<Integer>();
		ArrayList<Integer> timesCheckedOut = new ArrayList<Integer>();
		try{
			//Select all the callNumbers and how many times each were checked out and store them in a resultset
			ps = con.prepareStatement("SELECT book.book_callNumber, count(book.book_callNumber) FROM borrowing, book WHERE (select to_char(borrowing.borrowing_outDate, 'YYYY') from dual) = ? AND book.book_callNumber = borrowing.book_callNumber GROUP BY book.book_callNumber");
			ps.setInt(1, year);
			rs = ps.executeQuery();
			while(rs.next()){
				callNumbers.add(rs.getInt(1));
				timesCheckedOut.add(rs.getInt(2));
			}
			
			if (callNumbers.size() == 0){
				System.out.println("No books were checked-out in " + year);
			}
			
			for(int i =0; i < Math.min(callNumbers.size(), n-1); i++){
				System.out.println("The book with call number " + callNumbers.get(i) + " was checked out " + timesCheckedOut.get(i) + " times in " + year);
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

	
}




