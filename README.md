#Simple CRUD Operation
This project demonstrates the basic CRUD (Create, Read, Update, Delete) operations using Java, Servlet, JDBC, and HTML.

To execute the project on your computer, follow these steps:

1. Unzip the folder and open it in Eclipse IDE.
2. Ensure that Tomcat server is installed in your Eclipse IDE.
3. Create a database named "new" in your preferred database management system.
4. Create the following table in the "new" database using the provided query:

CREATE TABLE registration (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    DateOfBirth DATE
);

This table will store registration information with fields for ID, Name, Email, and Date of Birth.

Once you have set up the environment and database, you can run the project in Eclipse to see how CRUD operations work in a web application context.
