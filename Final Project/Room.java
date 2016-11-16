import java.util.*;

public class Room
{
private String name;
private int x;
private int y;
private String description;
private boolean block;
private int id;
public Room(String name, int x, int y, String description, boolean block)
{
	this.name=name;
	this.description=description;
	this.block=block;
	this.x = x;
	this.y=y;
}//end of room constructor
public String getRoomName() {
	return this.name;
}//end of getName method

public String getRoomDescription() {
	return this.description;
	
}//end of getRoomDescription
public int getY() {
	return this.y;
}

public int getX() {
	return this.x;
}
public int getRoomID() {
	return this.id;
}//end of get room id method

}//end of room class