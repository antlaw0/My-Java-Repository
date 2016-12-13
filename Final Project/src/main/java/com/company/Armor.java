package com.company;
//Armor is a type of Item that Characters wear in the game
public class Armor extends Item {
private int armorType;
public int armorValue;
//constructor
public Armor(String name, String description, String longDescription, int armorType, int armorValue) {
	this.name=name;
	this.description=description;
	this.longDescription=longDescription;
	this.armorValue=armorValue;
	this.itemType=armorType;
	Game.itemMasterList.add(this.name);//add this newly instantiated Armor's name to the item master list
}//end of constructor
}//end of Armor  class