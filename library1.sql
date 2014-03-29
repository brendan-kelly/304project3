drop table book;
drop table bookcopy;
drop table borrower;
drop table borrowertype;
drop table borrowing;
drop table fine;
drop table hasauthor;
drop table hassubject;
drop table holdrequest;

create table borrowertype
(borrowertype_type varchar(20) not null PRIMARY KEY,
borrowertype_bookTimeLimit integer not null);

create table borrower
( borrower_bid integer not null PRIMARY KEY,
borrower_password varchar(20) not null,
borrower_name varchar(20) not null,
borrower_addr varchar(50),
borrower_phone integer,
borrower_email varchar(50),
borrower_sinOrStNo integer,
borrower_expiryDate date,
borrowertype_type varchar(20),
foreign key (borrowertype_type) references borrowertype);
	
create table book
( book_callNumber integer not null PRIMARY KEY,
book_isbn integer not null,
book_title varchar(50),
book_mainAuthor varchar(50),
book_publisher varchar(50),
book_year date, 
foreign key (borrower_bid) references borrower );

create table hasauthor
(book_callNumber integer not null,
hasauthor_name varchar(50) not null,
PRIMARY KEY (book_callNumber, hasauthor_name),
foreign key (book_callNumber) references book);

create table hassubject
(book_callNumber integer not null,
hassubject_subject varchar(20) not null,
PRIMARY KEY (book_callNumber, hassubject_subject),
foreign key (book_callNumber) references book);

create table bookcopy
(book_callNumber integer not null,
bookcopy_copyNo integer not null,
bookcopy_status varchar(20), 
PRIMARY KEY (book_callNumber, bookcopy_copyNo),
foreign key (book_callNumber) references book);

create table holdrequest
(holdrequest_hid integer not null PRIMARY KEY,
borrower_bid integer not null,
book_callNumber integer not null,
holdrequest_issuedDate date,
foreign key (borrower_bid) references borrower,
foreign key (book_callNumber) references book);

create table borrowing
(borrowing_borid integer not null PRIMARY KEY,
borrower_bid integer not null,
book_callNumber integer not null,
bookcopy_copyNo integer not null,
borrowing_outDate date,
borrowing_inDate date,
foreign key (borrower_bid) references borrower,
foreign key (book_callNumber) references book,
foreign key (bookcopy_copyNo) references bookcopy);

create table fine
(fine_fid integer not null PRIMARY KEY,
fine_amount integer,
holdrequest_issuedDate date,
fine_paidDate date,
borrowing_borid integer,
foreign key (holdrequest_issuedDate) references holdrequest,
foreign key (borrowing_borid) references borrowing);

insert into borrower values
( 1, 'password', 'Harlan', 'Vancouver', 5551234, 'asdf@asdf', 12345, TO_DATE('10-JUN-1996','DD-MM-YYYY'), 'Student' );

insert into borrower values
( 2, 'passwordx', 'Harlanx', 'Vancouverx', 55512345, 'asdf@asdfx', 123456, TO_DATE('10-JUN-1997','DD-MM-YYYY'), 'Faculty' );

insert into borrower values
( 3, 'passwordxy', 'Harlanxy', 'Vancouverxy', 555123456, 'asdf@asdfxy', 1234567, TO_DATE('10-JUN-1998','DD-MM-YYYY'), 'Staff' );

insert into borrowertype values
( 'Staff', 213);

insert into book values
( 12345, 54321, 'Carl Sagan UltraGod', 'Niel DeGrass Tyson',  'NWA', TO_DATE('02-FEB-1976','DD-MM-YYYY'));