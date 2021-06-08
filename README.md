Matthew Paul Bird-s3482450- Further Programming Assignment 2

# GITHUB LINK:
https://github.com/FurtherProgramming/assignment-2-birdman769

# Readme
This project is a table booking system intended for the Arub company. It has both user and admin functionality and is designed to allow for modern day
covid restriction flexibility. It is written in Java, using javaFX + FXML for GUI implementation. 


## Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
 - main.model
 - main.ui
Packaging for test:
 - test.model

## Login info:

##### "test" user is used as the test user in our Junit Tests- do not edit test user account details!

DEFAULT USER TEST: 

Username: test

Password: test

DEFAULT ADMIN USER:

Username: admin

Password: admin

do not delete either of these users, as they are used for demonstration purposes. 

## TO RUN:
ensure Javac compiler is chosen in IntelliJ.

Ensure Java 8 is selected as compiler language (file>settings>Java compiler)

Ensure project structure is set to JAVA 1.8 SDK (file> projectStructure)

Ensure project Language Level is set to 8 - Lambdas, Type annotations etc.  (file> projectStructure)

Once application is cloned and built, it should run as a standalone application. 

to run program, right click main method and select run

if there is a database error> (File> ProjectStructure>Libraries) > ensure sqlite-jdbc-3.34.02 is chosen as library

## TO TEST:

to run JUNIT tests, right click test.model and choose "Run tests in test.model"


## Functionality implemented(see user landing page):

### User/admin- login page:
users or admins can log in and be directed to the appropriate login page. a new user can also register from the login page.
a user or admin can also reset their password via secret question if they wish. 


### User- book a Table:

Users can use the booking page to a table on a given date. 
an orange colour means the table is covid locked. a user cannot book on this date. However a covid lock made
After a booking will not delete a booking, but it will override the booking, until the covid lock is removed. 
A user is informed if a booking is covid locked under their manage bookings page. A user also cannot make two bookings on the same day, nor can they book a table if they sat at it yesterday. 

### User- manage bookings:

A user can cycle through their bookings to view multiple bookings. They can edit the booking which will allow them to choose a new booking
that will override the existing booking. They can also cancel a booking if they wish to not continue with it. A confirmed/unconfirmed status
displays that shows if an admin has approved this. This confirmation status will be overridden and replaced with "COVID LOCK" if the admin 
implements a covid lock on this table for the day of the booking, to inform the user that they may wish to move their booking. However a covid lock
does not automatically delete a booking, with the reason being that a covid lock could also be lifted, and I didn't want to automatically whipe these bookings, but
rather give the user an update that they may wish to change their booking. 

### User- manage Account:

user can edit some basic account information about themselves if they wish under this functionality. 


### Admin- Booking Manager:

from this page an admin can choose a date and alter bookings appropriately. an admin can place or remove a covid lock on a table.
They can edit a booking by choosing it and then selecting edit booking button. This allows then to cancel or confirm a booking.
The admin can also select an existing booking and choose the whitelist button. This will create an exception that will allow the user
who has booked this table to also book the table again two days in a row if they wish. 

A whitelist is made by choosing the day of the **existing** booking, not by choosing the day **intended** to allow the user to book(so you whitelist the day AFTER a currently SELECTED booking). 

A tables name will be replaced with the name of a user who sat at it the previous day. 

### Admin- User Manager:

To use this page, the admin must first type th e users name into the account tab and select search button. This will load the user details if an account is found. 
The user can then be deleted using the delete button, toggle admin will give/remove admin capability from the searched account. The account details can also be edited
 in the appropriate textfield and sent to the database with the submit button.

### Admin- Generate CSV:

Admin can generate a list of Employee details into a CSV using the Employee CSV button. 

Admin can select a date using the date picker and generate a CSV output of bookings on that date.

both export into the main project directroy under "Booking-export.csv" or "Employee-export.csv"



## bugs:

-sometimes the ADMIN- user manager page displays the admins details upon load. I have not figured out why it does this. Usually does not occur.

-Console outputs an integer during testing. Doesnt affect testing but unable to locate the source of it. Always an interger of 7 to date. 





