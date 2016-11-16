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
        
		readTicketsFromFile();
		
		
		
        while(true){
            System.out.println("1. Enter Ticket\n2. Search for Ticket\n3. Display All Tickets\n4. Delete Ticket by Issue \n5. Delete by ID \n6. Quit");
            int task = getPositiveIntInput();
            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } 
			else if (task == 2) {
                //delete a ticket
                searchTickets(ticketQueue);
            } 
			else if ( task == 4 ) {
				deleteByIssue(ticketQueue);
			}
				else if (task == 5 ) {
				
				deleteTicket(ticketQueue);
			}
			else if (task == 6) {
				//quit
				writeOpenTicketsToFile(ticketQueue);
				writeResolvedTicketsToFile(resolvedList);
				break;
				
			
				
			
				
				
				
				}
            else {
                //this will happen for 3 or any other selection that is a valid int
                //TODO Program crashes if you enter anything else - please fix
                //Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }
        numberScanner.close();
        stringScanner.close();
    }

    private static void deleteTicket(LinkedList<Ticket> ticketQueue) {
        int idToDelete;
        boolean found=false;
        System.out.println("Enter the ID of the ticket you wish to delete:  \n ");
        idToDelete = numberScanner.nextInt();
        Ticket T;
        for (int i=0; i<ticketQueue.size(); i++)
        {
            T = ticketQueue.get(i);
            if (T.getID() == idToDelete)
            {
                System.out.println("Enter text for the resolution of this issue:  ");
				String rString = stringScanner.next();
				ticketQueue.remove(i);
				Date dateResolved = new Date(); //Default constructor creates date with current date/time
				T.setResolveDate(dateResolved);
				T.setResolveString(rString);
				resolvedList.add(T);
                found = true;
            }





        }

        if (found == true){
            System.out.println("Ticket with ID "+idToDelete+" has been deleted."); }
        else{
            System.out.println("No ticket found."); }

    }
    //Move the adding ticket code to a method
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
    
private static void searchTickets(LinkedList<Ticket> ticketQueue) {
	System.out.println("Enter text to search all open tickets for:  ");
	String searchString = stringScanner.next();
	String tempDescription;
	Ticket T;
	for (int i=0; i<ticketQueue.size(); i++)
	{
		T=ticketQueue.get(i);
		tempDescription=T.getDescription();
		tempDescription.toLowerCase();
		searchString.toLowerCase();
		if (tempDescription.contains(searchString))
		{
		searchResultsList.add(T);
		}
	}
		printAllTickets(searchResultsList);
		searchResultsList.clear();
		
}

private static void deleteByIssue(LinkedList<Ticket> ticketQueue)
{
	searchTickets(ticketQueue);
	deleteTicket(ticketQueue);
}

	
private static int getPositiveIntInput() 
	{
		while (true) {
		try{
			int n = numberScanner.nextInt();
		if (n >=0){
				return n;
				
			}
			else{
				System.out.println("Error, please enter a positive value.");
			}
		}//end of try block
		catch (NumberFormatException  ime){
			System.out.println("Invalid input. Please enter a positive integer.");
			numberScanner.next();
				
		}
		
		}//end of while loop
}//end of getpositiveInt method
	
	protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");
        for (Ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
            //println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");
		
		
    }

	
	
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
		
	public static void writeResolvedTicketsToFile(LinkedList<Ticket> ticketQueue) {
	
	try{
		DateFormat dateFormatter = null;

		
		Date d = new Date();
		dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			
		String dateString = dateFormatter.format(d);
		//print out date, just for testing
		System.out.println(dateString);
		
		FileWriter writer = new FileWriter("Resolved_Tickets_As_of_"+dateString+".txt");
	
		BufferedWriter bufWriter = new BufferedWriter(writer);


		
		
        Ticket T;
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
		
	
	
			
	public static void readTicketsFromFile()
	{
		 
		 
		 //credit for date formatting:  https://www.mkyong.com/java/how-to-convert-string-to-date-java/
		 
		 
		 try{
		 FileReader reader = new FileReader("OpenTickets.txt");
        //BufferedReader combines the characters read into whole lines.
        BufferedReader bufReader = new BufferedReader(reader);

		 DateFormat dateFormatter = null;


		String line = bufReader.readLine();

        
        while (line != null) {
            String[] elements = line.split(";+");
			
			if (elements[0].equals ("Counter"))
			{
			int s = Integer.parseInt(elements[1]);
			Ticket.setStaticTicketCounter(s);
	
			}
			else
			{
			int newID = Integer.parseInt(elements[0]);
			
			
			String stringDate = elements[3];

			
 dateFormatter = new SimpleDateFormat("E MMM dd HH:mm:ssZ yyyy");			

Date date = (Date) dateFormatter.parse(stringDate);


			//print message, just for testing, will not be in final version
			System.out.println(newID+", "+elements[1]+", "+date);
			Ticket T = new Ticket(elements[1], newID, elements[2], date);
			
			ticketQueue.add(T);
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