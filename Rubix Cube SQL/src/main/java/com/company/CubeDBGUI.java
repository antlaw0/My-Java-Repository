/*
Rubix Cube SQL Program with GUI
By Anthony Lawlor

This program creates a GUI where the user enters names and times (unit unspecified) for solving of a Rubix cube puzzle. The entries are stored in a mysql database called 'Cubes' where user 'Test' with password 'password' has access. Records are added to the database when the add button is clicked. Highlight a record in the selection window and click 'delete' to remove the record from the GUI and the database. Quit exits the program and closes the GUI.
*Now supports updating database records. 
*/

package com.company;
import java.sql.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	

//Notice that this class extends JPanel
public class CubeDBGUI extends JPanel{
 //create static prepared statements so can be used anywhere in program
 static Statement statement = null;
 static PreparedStatement psInsert = null;
 static PreparedStatement psDelete=null;
static PreparedStatement psUpdateSolverTime=null;        
static JList<Record> RecordJList;
    
        //static string setup for making DB connection
 static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cube";     //Connection string – where's the database?
    static final String USER = "Test";   //TODO replace with your username
    static final String PASSWORD = "password";   //TODO replace with your password

 //list for storing records for searching
 static LinkedList<Record> searchResultsList = new LinkedList<Record>();
	static LinkedList<Record> recordList = new LinkedList<Record>();



    //Global variables for GUI components
    private JLabel nameLabel;
    private JLabel timeLabel;

    private JTextField nameTextField;
    private JTextField timeTextField;
    private JButton addRecordToListButton;
    private JScrollPane listScrollPane;
    private JButton deleteRecordButton;
    private JButton quitButton;
	
	
	
//create

    static DefaultListModel<Record> RecordListModel;

    //Configure GUI components in the constructor
    CubeDBGUI() {

        //call two methods to setup GUI
        addComponents();
        configureListeners();
    }
//This method is called in the GUI constructor
    private void addComponents() {

        
		//create components
		nameLabel = new JLabel("Enter name of the solver");
        timeLabel = new JLabel("Enter the time for this solver");

        nameTextField = new JTextField();
        timeTextField = new JTextField();

        
        addRecordToListButton = new JButton("Add");

        //Create a scroll pane, and add the list to it.
        //This keeps the list with	in the size of the scroll pane.
        //otherwise, the list gets larger with every element added, and the
        //GUI window gets larger too. With a scroll pane, if there are more elements
        //than fit in the scroll pane, then scroll bars appear.
        RecordJList = new JList<Record>();
        listScrollPane = new JScrollPane(RecordJList);

        deleteRecordButton = new JButton("Delete");

        quitButton = new JButton("Quit");

        RecordListModel = new DefaultListModel<Record>();

        RecordJList.setModel(RecordListModel);

        //Configure JList to only allow user to select one item at a time
        //Default is multiple selections.

        RecordJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        //BorderLayout arranges components in the north, south, east, west, or center.
        //BoxLayout can arrange components in vertical or horizontal stack(s).

        //To get the desired layout,
        //Create a BorderLayout for the whole JPanel.
        //Create a JPanel for the data entry fields - the JTextFields, JLabels, JCheckBox and use BoxLayout to arrange these components
        //The BoxLayout.Y_AXIS parameter specifies a vertical stack. See docs for more on
        //using BoxLayout.
        //And create another JPanel for the Quit and Delete buttons, Use another BoxLayout to arrange these buttons in a horizontal line.


        //The window as a whole uses a BorderLayout.
        BorderLayout layoutManager = new BorderLayout();
        setLayout(layoutManager);

        //Create a new panel for the data entry components. Use a BoxLayout to manage
        //the layout of these components
        JPanel dataEntryPanel = new JPanel();
        dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));

        //Add the components to dataEntryPanel...
        dataEntryPanel.add(nameLabel);
        dataEntryPanel.add(nameTextField);
        dataEntryPanel.add(timeLabel);
        dataEntryPanel.add(timeTextField);
        
		
		dataEntryPanel.add(addRecordToListButton);

        //And then add dataEntryPanel to the main JPanel window, in the north (at the top)
        add(dataEntryPanel, BorderLayout.NORTH);

        //Add the scroll pane in the middle. By default, center components will take up all of the remaining
        //space in the GUI window, so we don't need to set the size.
        add(listScrollPane, BorderLayout.CENTER);   //Which contains the dogJList

        //And make another panel to contain the quit and delete buttons
        //this uses a horizontal BoxLayout
        //Add components to this JPanel...
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(deleteRecordButton);
        buttonPanel.add(quitButton);
        //And then add the JPanel to the South, or bottom, of the window.
        add(buttonPanel, BorderLayout.SOUTH);


    }
//This method is called in the GUI constructor
    private void configureListeners() {
        addRecordToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read data from JTextFields 
                String name = nameTextField.getText();

                //Validation - make sure user enters something for the name. Trim whitespace and then check length is more than 0
                if (name.trim().length() == 0) {
                    JOptionPane.showMessageDialog(CubeDBGUI.this, "Please enter a name");
                    return;
                }

String time = timeTextField.getText();

                 if (time.trim().length() == 0) {
                    JOptionPane.showMessageDialog(CubeDBGUI.this, "Please enter a time");
                    return;
                }
				
				Record checkRecord;
				boolean foundMatch=false;
				//search through records to find if record already exists
				for (int i=0; i<recordList.size(); i++)
				{
					checkRecord=recordList.get(i);
					//if a record matches the name of the entered name
					if (name.equals (checkRecord.getName())) {
						foundMatch=true;//need for processing logic below
					}
				}
				
//if there is a match, bring up a yes/no dialogue to ask if user wants to update the new record
if (foundMatch) {
int dialogResult = JOptionPane.showConfirmDialog (null, "A record with name "+name+" already exists. Would you like to update this time?","Warning", JOptionPane.YES_NO_OPTION);
if(dialogResult == JOptionPane.YES_OPTION){				

try{
 Record element;
 //find the record in the record list
 for (int i = 0; i < RecordJList.getModel().getSize(); i++) {
             //assign element to this loop's record
			 element = RecordJList.getModel().getElementAt(i);
			 //if this iteration's record has the same name as the entered text
			 if (element.getName().equals (name)) {
			RecordListModel.removeElement(element);//delete this element from the GUI's list
			Record newRecord=new Record(name, Double.parseDouble(time));//create a new record from the entered text fields
			RecordListModel.addElement(newRecord);//add this record to the list model
				
			 }//end of if statement
							 
             }					
				//put the elements into the prepared sql statement
	psUpdateSolverTime.setDouble(1, Double.parseDouble(time));
	psUpdateSolverTime.setString(2, name);
	//execute this statement
	psUpdateSolverTime.executeUpdate();			
		
	
} catch (SQLException se) {
	se.printStackTrace();
}
	
	

}//end of if dialogue yes
				
}//end of if foundMatch
else { //no record with that entered name in database, add this new record
	
				// Create Record and add to JList's model
                Record newRecord = new Record(name, Double.parseDouble(time));
                //add new Record to list model
				RecordListModel.addElement(newRecord);
				//add this Record to the recordList
				recordList.add(newRecord);
String n=newRecord.getName();
double t= newRecord.getTime();
				//start try add record to database
				try{
				//put string into first element in statement
				psInsert.setString(1, n);
		//put double into second statement element
		psInsert.setDouble(2, t);
		//submit/run the update to the database
		psInsert.executeUpdate();			
				}catch (SQLException se){
//print errors if sql excpetion detected
				se.printStackTrace();
				}//end of sql exception				
				//clear both text fields
                nameTextField.setText("");
                timeTextField.setText("");
				
            }
        
			}//end of else foundMatch was false
		});

        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
				//get the object selected in scroll list
				Record toDelete = RecordJList.getSelectedValue();
                
				Record r;
				//loop through all records in list until a match with the same name occurs
				for (int i=0; i<recordList.size(); i++)
				{
					r=recordList.get(i);
					//if the selected record's name matches name in recordlist
					if ((r.getName()).equals (toDelete.getName()))
					{
						//put this record's name into the prepared statement
						psDelete.setString(1, r.getName());
						//execute the statement
						psDelete.executeUpdate();			
								
						recordList.remove(i);
					}
				}
				
				//remove this Record from the Record list model
				RecordListModel.removeElement(toDelete);
				
				} catch (SQLException se) {se.printStackTrace();}
            }
        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
				    //exit the program
				System.exit(0);
				
				
				
            }
			
        });


    }

//Main method
    public static void main(String[] args) {

//initialize sql components
		Connection conn = null;
        ResultSet rs = null;
        
		
		//try to instantiate the driver
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
			psDelete=conn.prepareStatement("DELETE FROM Cubes WHERE Name = ?");
			psUpdateSolverTime=conn.prepareStatement("UPDATE Cubes SET Time=? WHERE Name=?");
			 	//if the table does not exist, create it. Table will never exist because of previous drop statement
			String createTableSQL = "CREATE TABLE IF NOT EXISTS Cubes (Name varchar(90), Time double)";
            statement.executeUpdate(createTableSQL);
			
			
        //Create a frame - the GUI window
        JFrame RecordFrame = new JFrame("Rubix Cube DB GUI");
        //configure the JFrame / GUI window
        RecordFrame.setVisible(true);
        RecordFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RecordFrame.setPreferredSize(new Dimension(400, 300));

        //Create an object from this class - it's a JPanel,  which is a GUI component
        CubeDBGUI panel = new CubeDBGUI();

        //And add it to the JFrame
        RecordFrame.add(panel);

		
		
		//call getRecordsFromDB method to start where program last exited
		getRecordsFromDB(rs, statement);
				
		
        RecordFrame.pack();

		
		
		}catch(SQLException se) {
			System.out.println("SQL Exception detected");
			se.printStackTrace();
			}//end of sqlException catch block
		
		
		
    }//end of main method
	
	
	//This method retrieves all records from Cubes table in cube database and puts them in the recordList
	public static void getRecordsFromDB(ResultSet rs, Statement statement)
	{
try {	
	//make string containing the query
	String fetchAllDataSQL = "SELECT * FROM Cubes";
            //create resultset with this query
			rs = statement.executeQuery(fetchAllDataSQL);
            //while there are results in the results set
			while (rs.next()) {
                //get string name from this result
				String name = rs.getString("name");
                //get double time from this result
				double time = rs.getDouble("Time");
				//create a record object from this data
				Record newRecord = new Record(name, time);
				//add this new record to recordList for easier searching
				recordList.add(newRecord);
				//add this record to the scroll pane in the GUI
				RecordListModel.addElement(newRecord);
			}//end of while loop
            
		
	
	
} catch (SQLException se) {
System.out.println("SQL Exception detected.");
}	
	
	
	}//end of read in from DB method

	
	
	
	}//end of CubeDBGUI class

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
