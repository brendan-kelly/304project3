package Library;

import java.sql.*;
import java.util.ArrayList;

import Library.Main;

public class Librarian {
	//So we can use the connectoin "con" to access the SQL database
	Main m = new Main();
	Connection con = m.getConnection();

	//		Adds a new book or new copy of an existing book to the library. The librarian provides
	//		the information for the new book, and the system adds it to the library
	public void addBook(int callNumber, int isbn, String title, String mainAuthor, String publisher, Date Year){
		//stub, think Brendan already wrote this
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
	public void generateBookReport(){
		//stub
	}
//	Generate a report with the most popular items in a given year.
//	The librarian provides a
//	year and a number n. The system lists out the top n books that where borrowed the most
//	times during that year. The books are ordered by the number of times they were
//	borrowed.
	
	public void generateYearlyReport(){
		//stub
	}

}
