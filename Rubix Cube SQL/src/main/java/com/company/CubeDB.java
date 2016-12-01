package com.company;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CubeDB {
			//Create two scanners, one for Strings, and one for numbers - int and float values.

		//Use this scanner to read text data that will be stored in String variables
		static Scanner stringScanner = new Scanner(System.in);
		//Use this scanner to read in numerical data that will be stored in int or double variables
		static Scanner numberScanner = new Scanner(System.in);


	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cube";     //Connection string – where's the database?
    static final String USER = "Test";   //TODO replace with your username
    static final String PASSWORD = "password";   //TODO replace with your password

public static void main(String[] args) {
//initialize sql components
		Statement statement = null;
        Connection conn = null;
        ResultSet rs = null;
        
		PreparedStatement psInsert = null;
        PreparedStatement psFindSolver = null;
		PreparedStatement psUpdateSolverTime = null;
		
		
		try {

            
			Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

		try{
	//create connection object conn
			conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            //create statement object from connection object
			statement = conn.createStatement();
			//create insert statement from connection object 
			psInsert = conn.prepareStatement("INSERT INTO Cubes VALUES (?,?)");
			//create prepared statement from connection object
			psFindSolver = conn.prepareStatement("SELECT * FROM Cubes where UPPER(name) = UPPER(?)");
			 psUpdateSolverTime=conn.prepareStatement("UPDATE Cubes SET Time=? WHERE Name=?");
            System.out.println("Rubix Cube Database \n ");//Title of program
statement.executeUpdate("DROP TABLE Cubes"); //This is to delete the Cubes table everytime the program is run. Would not use in real program. Have to or else same inserts are added every time.
            	//if the table does not exist, create it. Table will never exist because of previous drop statement
			String createTableSQL = "CREATE TABLE IF NOT EXISTS Cubes (Name varchar(90), Time double)";
            statement.executeUpdate(createTableSQL);
            //populate the table with data
			//run prepared statements inserting names and times for each parameter and execute the update
			psInsert.setString(1,"Cubestormer II");
			psInsert.setDouble(2, 5.270);
			psInsert.executeUpdate();			
			
			psInsert.setString(1,"Fakhri Raihaan (using his feet)");
			psInsert.setDouble(2, 27.93);
			psInsert.executeUpdate();			
			
			psInsert.setString(1,"Ruxin Liu (age 3)");
			psInsert.setDouble(2, 99.33);
			psInsert.executeUpdate();			
			
			psInsert.setString(1,"Mats Valk (human record holder)");
			psInsert.setDouble(2, 6.27);
			
			
			psInsert.executeUpdate();			
//call showTable method which displays all data in table
			showTable(rs, statement);						
						//initialize some variables
			String s;
			String answer;
			double newTime;
			//program's main loop
			while (true)
			{
				//prompt for user
				System.out.println("Enter name of solver or press enter to exit:  ");
//assign s to user's input
				s = stringScanner.nextLine();
//if s is blank, exit program
				if (s.equals (""))
{
	//close all resources before close program
	statement.close();
	stringScanner.close();
	numberScanner.close();
	conn.close();
	psInsert.close();
	psFindSolver.close();
	psUpdateSolverTime.close();
	break;
}
				//insert user input into SQL statement
				psFindSolver.setString(1, s);
				//create results set from running this query
				rs = psFindSolver.executeQuery();
				boolean found=false;
			//while there are results in the results set
			while (rs.next()) 
			{
found = true;
		//assign n to 's name value
		String n = rs.getString("Name");
        //do the same for time
		double t = rs.getDouble("Time");
		//display this record
		System.out.println(n+" has best time of "+t);        
		 //ask user if want to update this record's time
		 System.out.println("Would you like to update "+n+"'s time? Enter y for yes and n for no: ");
		//assign answer to user input
		answer = stringScanner.nextLine();
		//if answer is yes
		if (answer.equals ("y"))
		{
			//get new time value from user
			System.out.println("Enter new time for "+s+":");
			newTime = numberScanner.nextDouble();
//insert this value into the first ? in query
			psUpdateSolverTime.setDouble(1, newTime);
			//and the second
			psUpdateSolverTime.setString(2, s);
			//execute the statement
			psUpdateSolverTime.executeUpdate();			
			//inform user that the time for s was updated
			System.out.println("New time updated.");
		}//end of answered yes if statement
				
			}//end of inner while loop			
        if (found == false)
		{
//The solver's name was not found. ask user if want to add this user to database
	System.out.println("Solver not found. Would you like to add "+s+" to the database? Enter y for yes and n for no.");
answer=stringScanner.nextLine();
//if answer is yes
if (answer.equals ("y"))
{
	//insert first parameter
	psInsert.setString(1, s);
	System.out.println("Please enter time for "+s);
	newTime = numberScanner.nextDouble();
	//insert second parameter from user's input
			psInsert.setDouble(2, newTime);
			//execute this update
			psInsert.executeUpdate();			
			//inform user that the new record was added to database
			System.out.println(s+" with time of "+newTime+" added to database. \n");
			//call showTable method which displays all records in Cubes table
			showTable(rs, statement);						

			
}//end of entered yes for adding new solver
		}//end of if statement	
			}//end of while loop
						
						
						
		}catch(SQLException se) {
			System.out.println("SQL Exception detected");
			se.printStackTrace();
			}//end of sqlException catch block
						
						
			
		
}//end of main method	
	//This method shows all records for Cubes table
	public static void showTable(ResultSet rs, Statement statement) {
		try{
		
		String fetchAllDataSQL = "SELECT * FROM Cubes";
            rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()) {
                String name = rs.getString("name");
                double time = rs.getDouble("Time");
                System.out.println("Solver's name = " + name  + " Time = " + time);
                
            }
            
		}//end of try block
		catch (SQLException se)
		{
			System.out.println("SQL exception detected in showTable method");
			se.printStackTrace();
		}//end of sqlException
			
	}//end of showTable method
	
}//end of CubeDB class

