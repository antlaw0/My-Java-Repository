public class Record {

private int ID;
private String name;
private double time;
private static int IDCounter=1;

public Record(int ID, String name, double time) {
this.name=name;
this.time=time;
this.ID=IDCounter;

IDCounter+=1;

}

public int getID(){
	
	return this.ID;
	
}
public static void setStaticRecordCounter(int s)  {
	Record.IDCounter = s;
}
public static int getStaticRecordID() {
	return IDCounter;
}

public double getTime() {
	return this.time;
}

public void setTime(double time) {
	this.time=time;
}


	
public void setName(String name) {
	this.name=name;
}

public String getName(){
	return this.name;
}

public String toString() {
	return ("ID:  "+this.ID+""+this.name+"   Time:  " +this.time);
	
}
	

}