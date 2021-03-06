/*
Support Ticket Program
By Anthony Lawlor
This program handles the adding and resolving of support tickets submitted to an IT support department. 
*/
import java.util.*;
import java.text.*;
import java.io.*;
public class TicketManager {

    //Revised to use separate scanners for Strings and numbers
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

	static LinkedList<Ticket> searchResultsList = new LinkedList<Ticket>();
	static LinkedList<Ticket> resolvedList = new LinkedList<Ticket>();
    static LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
		
        
	
    public static void main(String[] args) throws IOException
	{
        
		//call method to read open tickets previously written to a file
		readTicketsFromFile();
		
		
		//main menu loop
        while(true){
            // display numbered options
			System.out.println("1. Enter Ticket\n2. Search for Ticket\n3. Display All Tickets\n4. Delete Ticket by Issue \n5. Delete by ID \n6. Quit");
            //assign variable to user's input
			int task = getPositiveIntInput();
            
			//if 1, call add tickets method
			if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } 
			else if (task == 2) { 
                //delete a ticket
                searchTickets(ticketQueue);
            } 
			else if ( task == 4 ) {
				//call delete by issue method
				deleteByIssue(ticketQueue);
			}
				else if (task == 5 ) {
				//call delete ticket method
				deleteTicket(ticketQueue);
			}
			else if (task == 6) {
				//quit
				//call two methods to write open and then resolved tickets to text files
				writeOpenTicketsToFile(ticketQueue);
				writeResolvedTicketsToFile(resolvedList);
				break;
				
			
				
			
				
				
				
				}
            else { // if number entered is greater than the list of options
                System.out.println("Please enter a valid option.");
            }
        }
        numberScanner.close();
        stringScanner.close();
    }
//this method asks for an integer then deletes the ticket with the corresponding ID if it exists
    private static void deleteTicket(LinkedList<Ticket> ticketQueue) {
        int idToDelete;
        boolean found=false;
        System.out.println("Enter the ID of the ticket you wish to delete:  \n ");
        idToDelete = numberScanner.nextInt();
        Ticket T;
        //loop through all ticket objects
		for (int i=0; i<ticketQueue.size(); i++)
        {
            //make a ticket object variable
			T = ticketQueue.get(i);
            //check this iteration's ticket object against the integer ID that the user entered
			if (T.getID() == idToDelete)
            {
                //ask for, then assign resolution string to this ticket object
				System.out.println("Enter text for the resolution of this issue:  ");
				String rString = stringScanner.next();
				ticketQueue.remove(i);
				Date dateResolved = new Date(); //Default constructor creates date with current date/time
				T.setResolveDate(dateResolved);
				T.setResolveString(rString);
				resolvedList.add(T); //add this ticket to resolved list
                found = true;
            }





        }

        if (found == true){
            System.out.println("Ticket with ID "+idToDelete+" has been deleted."); }
        else{
            System.out.println("No ticket found."); }

    }
	//This method adds a new ticket to the ticketqueue
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {

        boolean moreProblems = true;
        String description;
        String reporter;
        //let's assume all tickets are created today, for testing. We can change this later if needed
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;
        while (moreProblems){
            System.out.println("Enter problem");
            description = stringScanner.nextLine();
            System.out.println("Who reported this issue?");
            reporter = stringScanner.nextLine();
            System.out.println("Enter priority of " + description);
            priority = numberScanner.nextInt();
            Ticket t = new Ticket(description, priority, reporter, dateReported);
            ticketQueue.add(t);
            //To test, let's print out all of the currently stored tickets
            printAllTickets(ticketQueue);
            System.out.println("More tickets to add?");
            String more = stringScanner.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }
    //This method takes a string entered by the user and searches through all open tickets to find a match
private static void searchTickets(LinkedList<Ticket> ticketQueue) {
	System.out.println("Enter text to search all open tickets for:  ");
	String searchString = stringScanner.next();
	String tempDescription;
	Ticket T;
	//loop through all ticket objects
	for (int i=0; i<ticketQueue.size(); i++)
	{
		T=ticketQueue.get(i);
		//have to convert all strings involved to lowercase temporarily in order to search more effectively
		tempDescription=T.getDescription();
		tempDescription.toLowerCase();
		searchString.toLowerCase();
		//if the search string, now made lowercase, matches the lowercase description of this ticket...
		if (tempDescription.contains(searchString))
		{
		//add this ticket to the search results list for displaying
		searchResultsList.add(T);
		}
	}
		printAllTickets(searchResultsList); // print out the searchresults list
		searchResultsList.clear(); //after displaying, must clear the list for next time
		
}
//This method calls the search for ticket method followed by the delete ticket method to delete a specific ticket
private static void deleteByIssue(LinkedList<Ticket> ticketQueue)
{
	searchTickets(ticketQueue);
	deleteTicket(ticketQueue);
}

	//This method is a loop that only breaks once a positive integer is input
private static int getPositiveIntInput() 
	{
		//start loop
		while (true) {
		try{ //start try catch block
			int n = numberScanner.nextInt(); // ask for a number
		if (n >=0){ // check if the entered number is positive
				return n;// it is positive, return the positive integer
				
			}
			else{
				System.out.println("Error, please enter a positive value."); //entered number was not positive
			}
		}//end of try block
		catch (NumberFormatException  ime){ //catch if the entered information was not an integer
			System.out.println("Invalid input. Please enter a positive integer.");
			numberScanner.next(); // clear the scanner or else entered value will repeat in the loop
				
		}
		
		}//end of while loop
}//end of getpositiveInt method
	//This method prints all tickets in the LinkedList that is the argument that is passed to it
	protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");
        for (Ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
            //println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");
		
		
    }

	
	
	//This method writes all the tickets in the open tickets list and writes their information to a text file
	public static void writeOpenTicketsToFile(LinkedList<Ticket> ticketQueue) {
	
	try{
		//create file writer with the file name "OpenTickets.txt"
		FileWriter writer = new FileWriter("OpenTickets.txt");
	//wrap FileWriter in BufferedWriter
		BufferedWriter bufWriter = new BufferedWriter(writer);


		
		//declare a ticket object variable
        Ticket T;
		//loop through all open tickets
		for (int i=0; i<ticketQueue.size(); i++)
		{
		//assign T to this iteration of the loop's ticket
		T=ticketQueue.get(i);
		
		//Write this ticket's info to the file using ; as separaters for parsing later with the read tickets method
		bufWriter.write(T.getID()+";"); //write ticket's ID to a file
		bufWriter.write(T.getDescription()+";"); //write ticket's description to this file
		bufWriter.write(T.getReporter()+";"); //write this ticket's reporter to the file
		bufWriter.write(T.getDate()+"\r\n"); //write the date information to the file
		
		
	
	
		}
		bufWriter.write("Counter;"+Ticket.getStaticTicketID()); //write the static ticket counter to this file to keep track of ticket counting when the program is closed and opened again
		
		
        
        bufWriter.close(); //save the written info to the text file
	}
	catch (Exception e){ //catch if any exceptions were thrown. Needs to be here or else compiler complains
		System.out.println("Error");
	}
	
	
	}
		//This method is similar to the writeOpenTickets to file method except for resolved tickets
	public static void writeResolvedTicketsToFile(LinkedList<Ticket> ticketQueue) {
	
	try{
		//create a date formatter object
		DateFormat dateFormatter = null;

		//create a date object with today's date
		Date d = new Date();
		//create dateformatter object with proper format
		dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			
		//make a string containing the date using the dateformatter on the date object
		String dateString = dateFormatter.format(d);
		//PCs do not allow : in path names so must replace all : with ; instead
		dateString = dateString.replace(":",";");
		
		//creater filewriter with the dateString being used as part of the file name
		FileWriter writer = new FileWriter("Resolved_Tickets_As_of_"+dateString+".txt");
	//wrap filewriter in bufferedwriter
		BufferedWriter bufWriter = new BufferedWriter(writer);


		
		//create ticket object variable
        Ticket T;
		//loop through all resolved tickets
		for (int i=0; i<ticketQueue.size(); i++)
		{
		T=ticketQueue.get(i);
		
		//write all parts of ticket info to file. Uses ; as separaters for easier parsing later if required
		bufWriter.write(T.getID()+";"); //write the ticket's ID to file
		bufWriter.write(T.getDescription()+";"); //write ticket's description to file
		bufWriter.write(T.getReporter()+";"); //write name of reporter for this ticket to file
		bufWriter.write(T.getDate()+"\r\n"); //writethe date info for this ticket to file
		bufWriter.write(T.getResolveString()+"\r\n"); //write ticket's resolve string to file
		bufWriter.write(T.getResolveDate()+"\r\n"); //write the resolve date for this ticket to file
		
		
	
	
		}
		
		
        
        bufWriter.close(); //close the bufWriter saving the written text to the file
	}
	catch (IOException e){ //catch any IOExceptions. Needs to be here or else the compiler complains
		System.out.println("IO Error detected");
	}
	
	
	}
		
	
	
			//This method creates ticket objects based on the information written to the OpenTickets file
	public static void readTicketsFromFile()
	{
		 
		 
		 //credit for date formatting:  https://www.mkyong.com/java/how-to-convert-string-to-date-java/
		 
		 
		 try{
		 //create file reader with OpenTickets.txt file
		 FileReader reader = new FileReader("OpenTickets.txt");
        //BufferedReader combines the characters read into whole lines.
        BufferedReader bufReader = new BufferedReader(reader);

		 //create dateFormatter object
		 DateFormat dateFormatter = null;


		String line = bufReader.readLine();

        //while line is not blank...
        while (line != null) {
            //split this line into elements separated at each ;
			String[] elements = line.split(";+");
			//if the first element is the string "Counter", means has reached the last line
			if (elements[0].equals ("Counter"))
			{
			//if on reading Counter line, take the next element containing the statick ID counter and make its string into an integer
			int s = Integer.parseInt(elements[1]);
			Ticket.setStaticTicketCounter(s); //take this value and assign it to the static ticket ID counter
	
			}
			else //otherwise, it is a normal ticket line
			{
			int newID = Integer.parseInt(elements[0]); //get the first element and make it an integer
			
			
			String stringDate = elements[3]; //take element 3 as the date 

			//create dateFormatter object
 dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			
//make a date object from the formatted date string
Date date = (Date) dateFormatter.parse(stringDate);

//take all these elements and make a new ticket object
			Ticket T = new Ticket(elements[1], newID, elements[2], date);
			//add this ticket into the ticketQueue
			ticketQueue.add(T);
            }
			// And then read in the next line ...
            line = bufReader.readLine();
			
        }

        	
		 }//end of try block
			catch (ParseException e)  { // compiler complains if no catch block
				System.out.println("There was a ParseException detected.");
			}
			
			catch (IOException e){ // catch any IOExceptions and inform user
					System.out.println("There was an IOException detected.");
			}
			

	}//end of read in from file method

	
	}