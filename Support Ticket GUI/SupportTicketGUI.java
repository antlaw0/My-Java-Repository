import java.text.*;
import java.io.*;

import javax.swing.event.*;
 
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	

//Notice that this class extends JPanel
public class SupportTicketGUI extends JPanel{
 static LinkedList<Ticket> searchResultsList = new LinkedList<Ticket>();
	static LinkedList<Ticket> resolvedList = new LinkedList<Ticket>();
    static LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();



    //Global variables for GUI components
    private JLabel descriptionLabel;
    private JLabel reporterLabel;

    private JTextField descriptionTextField;
    private JTextField reporterTextField;
    private JButton addTicketToListButton;
    private JScrollPane listScrollPane;
    private JList<Ticket> ticketJList;
    private JButton deleteTicketButton;
    private JButton quitButton;
	private int priority;
	private JSpinner prioritySpinner;
	private JLabel priorityLabel;
//create a model for a spinner	
	SpinnerNumberModel priorityModel = new SpinnerNumberModel(1,1,5,1);
	
    static DefaultListModel<Ticket> ticketListModel;

    //Configure your GUI components in the constructor
    SupportTicketGUI() {

        //Set up GUI components here

        addComponents();
        configureListeners();
    }
//This method is called in the GUI constructor
    private void addComponents() {

        priorityLabel = new JLabel("Choose a priority rating");
		prioritySpinner = new JSpinner(priorityModel);
		descriptionLabel = new JLabel("Enter description of the issue");
        reporterLabel = new JLabel("Enter the name of who reported this issue");

        descriptionTextField = new JTextField();
        reporterTextField = new JTextField();

        
        addTicketToListButton = new JButton("Add");

        //Create a scroll pane, and add the list to it.
        //This keeps the list with	in the size of the scroll pane.
        //otherwise, the list gets larger with every element added, and the
        //GUI window gets larger too. With a scroll pane, if there are more elements
        //than fit in the scroll pane, then scroll bars appear.
        ticketJList = new JList<Ticket>();
        listScrollPane = new JScrollPane(ticketJList);

        deleteTicketButton = new JButton("Delete");

        quitButton = new JButton("Quit");

        ticketListModel = new DefaultListModel<Ticket>();

        ticketJList.setModel(ticketListModel);

        //Configure JList to only allow user to select one item at a time
        //Default is multiple selections.

        ticketJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
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
        dataEntryPanel.add(descriptionLabel);
        dataEntryPanel.add(descriptionTextField);
        dataEntryPanel.add(reporterLabel);
        dataEntryPanel.add(reporterTextField);
        dataEntryPanel.add(priorityLabel);
		dataEntryPanel.add(prioritySpinner);
		dataEntryPanel.add(addTicketToListButton);

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
        buttonPanel.add(deleteTicketButton);
        buttonPanel.add(quitButton);
        //And then add the JPanel to the South, or bottom, of the window.
        add(buttonPanel, BorderLayout.SOUTH);


    }
//This method is called in the GUI constructor
    private void configureListeners() {
        addTicketToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read data from JTextFields 
                String description = descriptionTextField.getText();

                //Validation - make sure user enters something for the name. Trim whitespace and then check length is more than 0
                if (description.trim().length() == 0) {
                    JOptionPane.showMessageDialog(SupportTicketGUI.this, "Please enter a description");
                    return;
                }

String reporter = reporterTextField.getText();

                 if (reporter.trim().length() == 0) {
                    JOptionPane.showMessageDialog(SupportTicketGUI.this, "Please enter a reporter");
                    return;
                }
				
				
				
				
				Date date = new Date();
				// Create Ticket and add to JList's model
                Ticket newTicket = new Ticket(description, priority, reporter, date);
                //add new ticket to list model
				ticketListModel.addElement(newTicket);
				//add this ticket to the ticketQueue
				ticketQueue.add(newTicket);
				//clear both text fields
                descriptionTextField.setText("");
                reporterTextField.setText("");

            }
        });

		// credit for spinner http://www.tutorialspoint.com/swing/swing_jspinner.htm
		 prioritySpinner.addChangeListener(new ChangeListener() {
			 @Override
				public void stateChanged(ChangeEvent e) {
				priority =(int) prioritySpinner.getValue();
				}
				});
				
		
		
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ask the JLIST what ticket is selected
                // Notice since we've used generic types, setSelectedValue returns a Ticket.
                // Without generic types, it would return an Object, and we'd have to cast it.
                Ticket toDelete = ticketJList.getSelectedValue();
                
				Ticket t;
				for (int i=0; i<ticketQueue.size(); i++)
				{
					t=ticketQueue.get(i);
					if (t.getID() == toDelete.getID())
					{
						ticketQueue.remove(i);
					}
				}
				
				//add this Ticket to resolve list for later writing to a file
				resolvedList.add(toDelete);
				//remove this ticket from the ticket list model
				ticketListModel.removeElement(toDelete);
            }
        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
				//before quit, write open tickets to file
				writeOpenTicketsToFile(ticketQueue);
				//and resolved tickets
				writeResolvedTicketsToFile(resolvedList);
				
				System.exit(0);
            }
        });


    }

//Main method
    public static void main(String[] args) {

			
			
        //Create a frame - the GUI window
        JFrame ticketFrame = new JFrame("Support Ticket");
        //configure the JFrame / GUI window
        ticketFrame.setVisible(true);
        ticketFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ticketFrame.setPreferredSize(new Dimension(400, 300));

        //Create an object from this class - it's a JPanel,  which is a GUI component
        SupportTicketGUI panel = new SupportTicketGUI();

        //And add it to the JFrame
        ticketFrame.add(panel);
//call readticketsFromFile method to start where program last exited
		readTicketsFromFile();

		
        ticketFrame.pack();

    }
//This method takes all open tickets and writes them to OpenTickets.txt
	public static void writeOpenTicketsToFile(LinkedList<Ticket> ticketQueue) {
	
	try{
		FileWriter writer = new FileWriter("OpenTickets.txt");
	
		BufferedWriter bufWriter = new BufferedWriter(writer);


		
		
        Ticket T;
		for (int i=0; i<ticketQueue.size(); i++)
		{
		T=ticketQueue.get(i);
		bufWriter.write(T.getID()+";");
		bufWriter.write(T.getDescription()+";");
		bufWriter.write(T.getReporter()+";");
		bufWriter.write(T.getDate()+"\r\n");
		
		
	
	
		}
		bufWriter.write("Counter;"+Ticket.getStaticTicketID());
		
		
        
        bufWriter.close();
	}
	catch (Exception e){
		System.out.println("Error");
	}
	
	
	}
		//This method takes all tickets in resolved list and writes them to a text file
	public static void writeResolvedTicketsToFile(LinkedList<Ticket> ticketQueue) {
	
	try{
		DateFormat dateFormatter = null;

		//create date object with today's date
		Date d = new Date();
		//create date formatter object
		dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			
		//use date formatter on date object
		String dateString = dateFormatter.format(d);
		//have to replace : with another char because can't use : in Windows paths
		dateString = dateString.replace(":",";");
		//take the date string and make part of FileWriter file name
		FileWriter writer = new FileWriter("Resolved_Tickets_As_of_"+dateString+".txt");
	
		BufferedWriter bufWriter = new BufferedWriter(writer);


		
		//create ticket object
        Ticket T;
		//each loop, assign ticket object T to every ticket in resolved list and write its info to the file
		for (int i=0; i<ticketQueue.size(); i++)
		{
		T=ticketQueue.get(i);
		bufWriter.write(T.getID()+";");
		bufWriter.write(T.getDescription()+";");
		bufWriter.write(T.getReporter()+";");
		bufWriter.write(T.getDate()+"\r\n");
		bufWriter.write(T.getResolveString()+"\r\n");
		bufWriter.write(T.getResolveDate()+"\r\n");
		
		
	
	
		}
		
		
        
        bufWriter.close();
	}
	catch (IOException e){
		System.out.println("IO Error detected");
	}
	
	
	}
		
	
	
			//This method takes all written ticket information in OpenTickets.txt and makes ticket objects out of it and places them in a LinkedList
	public static void readTicketsFromFile()
	{
		 
		 
		 //credit for date formatting:  https://www.mkyong.com/java/how-to-convert-string-to-date-java/
		 
		 
		 try{
		 FileReader reader = new FileReader("OpenTickets.txt");
        //BufferedReader combines the characters read into whole lines.
        BufferedReader bufReader = new BufferedReader(reader);
//create date format object
		 DateFormat dateFormatter = null;

//read in first line
		String line = bufReader.readLine();

        //while line is not blank
        while (line != null) {
            //take the current line and split it into elements at each ;
			String[] elements = line.split(";+");
			//if the current line is not the static counter line
			if (elements[0].equals ("Counter"))
			{
			int s = Integer.parseInt(elements[1]);
			Ticket.setStaticTicketCounter(s); //pick up where left off ticket counting
	
			}
			else
			{
			//take all elements and use them to create a ticket object
			int newID = Integer.parseInt(elements[0]);
			
			
			String stringDate = elements[3];

			//Have to convert date string into a Date object
 dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			

Date date = (Date) dateFormatter.parse(stringDate);

//create the ticket from this line
			Ticket T = new Ticket(elements[1], newID, elements[2], date);
			
			//add this ticket to the listModel so it shows up in the GUI on open
			ticketListModel.addElement(T);
			
			
			//ticketQueue.add(T);	
			
			
			}
			// And then read in the next line ...
            line = bufReader.readLine();
			
        }

        	
		 }//end of try block
			catch (ParseException e)  {
				System.out.println("There was a ParseException detected.");
			}
			
			catch (IOException e){
					System.out.println("There was an IOException detected.");
			}
			

	}//end of read in from file method

	
	
	
	}