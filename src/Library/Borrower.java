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
		//stub
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
	}
	//	Place a hold request for a book that is out. When the item is r
	//	eturned, the system sends an
	//	email to the borrower and informs the library clerk to keep the book out of the shelves.

	public void placeHoldRequest(int callNo){
		//stub
	}
	//Pay a fine - not sure what to do here. 
	public void payFine(){
		//stub
	}
}
