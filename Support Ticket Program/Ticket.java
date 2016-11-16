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
this.ID=IDCounter;

IDCounter+=1;

}

public int getID(){
	
	return this.ID;
	
}
public static void setStaticTicketCounter(int s)  {
	Ticket.IDCounter = s;
}
public static int getStaticTicketID() {
	return IDCounter;
}

public String getReporter() {
	return this.reporter;
}

public void setResolveString(String r) {
	this.resolveString = r;
}

public void setResolveDate(Date d) {
	this.resolveDate=d;
}

public String getResolveString() {
	return this.resolveString;
}

public Date getResolveDate() {
	return this.resolveDate;
}
	
public Date getDate() {
	return this.dateReported;
}

public int getPriority() {
	return this.priority;
}

public String getDescription(){
	return this.description;
}

//Called automatically if a Ticket object is an argument to System.out.println
public String toString(){
return("ID:  "+this.ID+" \n "+this.description + " Priority: " + this.priority + " Reported by: "
+ this.reporter + " Reported on: " + this.dateReported);
}
}