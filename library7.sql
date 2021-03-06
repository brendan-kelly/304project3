drop table hasauthor;
drop table hassubject;
drop table fine;
drop table borrowing;
drop table holdrequest;
drop table bookcopy;
drop table book;
drop table borrower;
drop table borrowertype;
drop sequence seq_borrower;
drop SEQUENCE seq_book;
drop SEQUENCE seq_bookcopy;
drop SEQUENCE seq_fine;
drop sequence seq_borrowing;
drop sequence seq_holdrequest;

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
book_year date);

create table hasauthor
(book_callNumber integer not null,
hasauthor_name varchar(50) not null,
PRIMARY KEY (book_callNumber, hasauthor_name),
foreign key (book_callNumber) references book);

create table hassubject
(book_callNumber integer not null,
hassubject_subject varchar(50) not null,
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
foreign key (book_callNumber, bookcopy_copyNo) references bookcopy);

create table fine
(fine_fid integer not null PRIMARY KEY,
fine_amount integer,
fine_issuedDate date,
fine_paidDate date,
borrowing_borid integer,
foreign key (borrowing_borid) references borrowing);

create SEQUENCE seq_borrower
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_book
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_bookcopy
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_fine
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_borrowing
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;

CREATE SEQUENCE seq_holdrequest
MINVALUE 0
START WITH 0
INCREMENT BY 1
CACHE 10;


insert into borrowertype values
('Student', 14);

insert into borrowertype values
('Faculty', 84);

insert into borrowertype values
('Staff', 42);

insert into borrower values
(seq_borrower.nextval, 'password', 'Harlan', 'East Hastings', 1234567890, 'asdf@yahoo.ca', 111222333, TO_DATE('10-JUN-2016','DD-MM-YYYY'), 'Student' );

insert into borrower values
(seq_borrower.nextval, 'floofy', 'Conor', 'New Delhi', 0123456789, 'asdf@hotmail.com', 444555666, TO_DATE('10-JUN-2997','DD-MM-YYYY'), 'Faculty' );

insert into borrower values
(seq_borrower.nextval, 'macmiller4lyfe', 'Brendan', 'Whistler', 9012345678, 'asdf@bing.org', 777888999, TO_DATE('10-JUN-2014','DD-MM-YYYY'), 'Staff' );

insert into book values
(seq_book.nextval, 1, 'Carl Sagan UltraGod', 'Niel DeGrass Tyson',  'NWA', TO_DATE('02-FEB-1976','DD-MM-YYYY'));

insert into book values
(seq_book.nextval, 3, 'So Will Be Now...', 'John Talbot',  'Pional', TO_DATE('02-JUN-2006','DD-MM-YYYY'));

insert into book values
(seq_book.nextval, 6, 'Nightcall', 'Kavinsky',  'Dustin NGuyen', TO_DATE('02-FEB-1986','DD-MM-YYYY'));

insert into book values 
(seq_book.nextval, 123, 'How to get away with murder', 'OJ Simpson',  'Tom Cruise $$$ Scientology', TO_DATE('19-DEC-2006','DD-MM-YYYY'));

insert into hasauthor values
(2, 'Isaac Brock');

insert into hassubject values
(3, 'Deep and Passionate Teenage Angst');

insert into bookcopy values
(1, seq_bookcopy.nextval, 'Out');

insert into bookcopy values
(1, seq_bookcopy.nextval, 'In');

insert into bookcopy values
(2, seq_bookcopy.nextval, 'In');

insert into bookcopy values
(4, seq_bookcopy.nextval, 'Out');

insert into borrowing values
(seq_borrowing.nextval, 1, 1, 1, TO_DATE('20-JAN-2014','DD-MM-YYYY'), NULL);

insert into borrowing values
(seq_borrowing.nextval, 3, 1, 2, TO_DATE('20-MAY-2013','DD-MM-YYYY'), TO_DATE('21-MAY-2013','DD-MM-YYYY'));

insert into borrowing values
(seq_borrowing.nextval, 2, 2, 3, TO_DATE('20-JAN-2010','DD-MM-YYYY'), TO_DATE('20-JAN-2013','DD-MM-YYYY'));

insert into fine values
(seq_fine.nextval, 20, TO_DATE('21-FEB-2010','DD-MM-YYYY'),NULL,1);

insert into fine values
(seq_fine.nextval, 2400, TO_DATE('14-APR-2010', 'DD-MM-YYYY'),TO_DATE('20-JAN-2013','DD-MM-YYYY'), 3);

insert into holdrequest values 
(seq_holdrequest.nextval,3,1,TO_DATE('20-JAN-2013','DD-MM-YYYY'));
