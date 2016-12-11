package com.company;

import java.util.*;

abstract class WorldObject
{
String name;
String description;
public String longDescription="";
public void setDescription(String d) {
	this.description = d;
}

public String getDescription() {
	return this.description;
}
public String getName()
{
	return this.name;
}
public void setName(String n)
{
this.name=n;
}
	
}