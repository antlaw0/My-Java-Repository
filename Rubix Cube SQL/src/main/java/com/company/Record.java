public class Record {

private String name;
private double time;

public Record(String name, double time) {
this.name=name;
this.time=time;


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
	return (this.name+"   Time:  " +this.time);
	
}
	

}