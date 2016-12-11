package com.company;

import java.util.*;

public class Room
{
private String name;
private int x;
private int y;
private String description;
private boolean safe;
private int id;
private LinkedList<WorldObject> roomList = new LinkedList<WorldObject>();
public Room(String name, int x, int y, String description, boolean safe)
{
	this.name=name;
	this.description=description;
	this.safe=safe;
	this.x = x;
	this.y=y;
}//end of room constructor
public void add(WorldObject obj) {
	roomList.add(obj);
}
public boolean isSafe() {
	return this.safe;
}//end of if safe method
public void setSafe(boolean safe) {
	this.safe=safe;
}//end of set safe method
public LinkedList getList() {
	return this.roomList;
}//end of get  object list method
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