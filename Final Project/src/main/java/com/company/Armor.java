package com.company;
public class Armor extends Item {
private int armorType;
public int armorValue;

public Armor(String name, String description, String longDescription, int armorType, int armorValue) {
	this.name=name;
	this.description=description;
	this.longDescription=longDescription;
	this.armorValue=armorValue;
	this.itemType=armorType;
	Game.itemMasterList.add(this.name);
}//end of constructor
}//end of Armor  class