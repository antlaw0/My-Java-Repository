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
 static Statement statement = null;
 static PreparedStatement psInsert = null;
        
        
 static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cube";     //Connection string – where's the database?
    static final String USER = "Test";   //TODO replace with your username
    static final String PASSWORD = "password";   //TODO replace with your password

 
 static LinkedList<Record> searchResultsList = new LinkedList<Record>();
	static LinkedList<Record> recordList = new LinkedList<Record>();



    //Global variables for GUI components
    private JLabel nameLabel;
    private JLabel timeLabel;

    private JTextField nameTextField;
    private JTextField timeTextField;
    private JButton addRecordToListButton;
    private JScrollPane listScrollPane;
    private JList<Record> RecordJList;
    private JButton deleteRecordButton;
    private JButton quitButton;
	
	
	
//create

    static DefaultListModel<Record> RecordListModel;

    //Configure your GUI components in the constructor
    CubeDBGUI() {

        //Set up GUI components here

        addComponents();
        configureListeners();
    }
//This method is called in the GUI constructor
    private void addComponents() {

        
		
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
				
				
				
				
	
				// Create Record and add to JList's model
                Record newRecord = new Record(name, Double.parseDouble(time));
                //add new Record to list model
				RecordListModel.addElement(newRecord);
				//add this Record to the recordList
				recordList.add(newRecord);
				
				//clear both text fields
                nameTextField.setText("");
                timeTextField.setText("");
				
            }
        });
/*
        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
				// Ask the JLIST what Record is selected
                // Notice since we've used generic types, setSelectedValue returns a Record.
                // Without generic types, it would return an Object, and we'd have to cast it.
                Record toDelete = RecordJList.getSelectedValue();
                
				Record r;
				for (int i=0; i<recordList.size(); i++)
				{
					r=recordList.get(i);
					if (r.getID() == toDelete.getID())
					{
						recordList.remove(i);
					}
				}
				
				//remove this Record from the Record list model
				RecordListModel.removeElement(toDelete);
            }
        });

*/
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
				//before quit, update Cubes DB
				updateDB(psInsert);
				statement.executeUpdate("DROP TABLE Cubes"); //This is to delete the Cubes table everytime the program is run. Would not use in real program. Have to or else same inserts are added every time.
				JOptionPane.showMessageDialog(CubeDBGUI.this, "Update successful!");
                    
				//System.exit(0);
				}catch (SQLException e){
					System.out.println(e.printStackTrace());
				}
				
				
            }
			
        });


    }

//Main method
    public static void main(String[] args) {

//initialize sql components
		Connection conn = null;
        ResultSet rs = null;
        
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
            //System.out.println("Rubix Cube Database \n ");//Title of program
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
	
	public static void updateDB(PreparedStatement psInsert) {
try {	
	
	Record r;
	String n;
	double t;
	for (int i=0; i<recordList.size(); i++)
	{
		r=recordList.get(i);
		n=r.getName();
		t=r.getTime();
		psInsert.setString(1, n);
		psInsert.setDouble(2, t);
		psInsert.executeUpdate();			
			
		
	}
	
} catch (SQLException se) {
	System.out.println("SQL Exception detected");
}
	
	}//end of updateDB method
	
	//This method retrieves all records from Cubes table in cube database and puts them in the recordList
	public static void getRecordsFromDB(ResultSet rs, Statement statement)
	{
try {	
	
	String fetchAllDataSQL = "SELECT * FROM Cubes";
            rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()) {
                String name = rs.getString("name");
                double time = rs.getDouble("Time");
				
				Record newRecord = new Record(name, time);
				//add this new record to recordList for later updating to DB
				recordList.add(newRecord);
				RecordListModel.addElement(newRecord);
			}//end of while loop
            
            
		
	
	
} catch (SQLException se) {
System.out.println("SQL Exception detected.");
}	
	
	
	}//end of read in from DB method

	
	
	
	}//end of CubeDBGUI class

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
