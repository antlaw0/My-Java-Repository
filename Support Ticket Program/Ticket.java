import java.util.Date;
public class Ticket {
private int priority;
private String reporter; //Stores person or department who reported issue
private String description;
private Date dateReported;
private int ID;
private String resolveString="unresolved";
private Date resolveDate;

private static int IDCounter=1;

public Ticket(String desc, int p, String rep, Date date) {
this.description = desc;
this.priority = p;
this.reporter = rep;
this.dateReported = date;
this.ID=IDCounter; //assign the ticket's ID to the static ID counter

IDCounter+=1; //increase static ID counter because just created new ticket

}
//getID method
public int getID(){
	
	return this.ID;
	
}
//set the static ID counter
public static void setStaticTicketCounter(int s)  {
	Ticket.IDCounter = s;
}
//get the static ID counter's value
public static int getStaticTicketID() {
	return IDCounter;
}
//get the reporter for this ticket
public String getReporter() {
	return this.reporter;
}
//set this ticket's resolution
public void setResolveString(String r) {
	this.resolveString = r;
}
//set the resolve date for this ticket
public void setResolveDate(Date d) {
	this.resolveDate=d;
}
//get the resolution string for this ticket
public String getResolveString() {
	return this.resolveString;
}
//get the resolve date for this ticket
public Date getResolveDate() {
	return this.resolveDate;
}
	//get the date this ticket was created
public Date getDate() {
	return this.dateReported;
}
//get the priority level of this ticket
public int getPriority() {
	return this.priority;
}
//get the description of this ticket
public String getDescription(){
	return this.description;
}

//Called automatically if a Ticket object is an argument to System.out.println
public String toString(){
return("ID:  "+this.ID+" \n "+this.description + " Priority: " + this.priority + " Reported by: "
+ this.reporter + " Reported on: " + this.dateReported);
}
}