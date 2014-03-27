package Library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Borrower {

	//So we can use the connectoin "con" to access the SQL database
	Main m = new Main();
	Connection con = m.getConnection();

	//	Search for books using keyword search on titles, authors and subjects. The result is a list
	//	of books that match the search together with the number of copies that are in and out.
	public void search(String keyword){
		Statement  stmt;
		ResultSet  rs;

		// SELECT * FROM book, hassubject WHERE book.book_title = keyword OR book.book_mainAuthor = keyword OR hassubject.hassubject_subject = keyword, book.book_callNumber = hassubject.book_callNumber ORDER BY book.book_callNumber AS Temp
		// SELECT COUNT (bookcopy) FROM bookcopy, Temp WHERE book.bookcopy_status = "in", Temp.book_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber
		// SELECT COUNT (bookcopy) FROM bookcopy, Temp WHERE book.bookcopy_status = "out", Temp.book_callNumber = bookcopy.book_callNumber ORDER BY bookcopy.book_callNumber
		// 
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
		// SELECT borrowing FROM borrowing WHERE borrowing.borrowing_inDate IS NULL AND borrowing.borrowing_outDate IS NOT NULL AND borrowing.borrower_bid = id AS Temp
		// this should return the books they borrowed
		// SELECT * FROM Fine, borrowing, Temp WHERE fine.fine_paidDate IS NULL AND borrowing.borrowing_borid = fine.borrowing_borid AND borrowing.borrower_bid = id
		// This shoudl return all the fines that have not been paid from the list of borrowed books?
		// SELECT * FROM holdrequest WHERE holdrequest.borrower_bid = id
	}
	//	Place a hold request for a book that is out. When the item is r
	//	eturned, the system sends an
	//	email to the borrower and informs the library clerk to keep the book out of the shelves.

	public void placeHoldRequest(int callNo, int id){
		//stub
		// INSERT INTO holdrequest VALUES (?, id, callNo, currentDate)
		// first value is unique generated integer, currentDate is obv
	}
	//Pay a fine - not sure what to do here. 
	public void payFine(int fid){
		//stub
		// UPDATE fine SET fine.fine_paidDate = currentDate WHERE fine.fine_fid = fid
	}
}
