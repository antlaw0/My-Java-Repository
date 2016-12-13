package com.company;
//type of Item, food potions etc.
public class Consumable extends Item {
private int restoreHPAmount;//how much health this item restores when consumed
private int restoreMPAmount;//amount of mp restored when item is consumed
private int restoreSPAmount;//amount of sp restored when consumed
//constructor
public Consumable(String name, String description, String longDescription, int h, int m, int s) {
	this.name=name;
	this.description=description;
	this.longDescription=longDescription;
	this.restoreHPAmount=h;
	this.restoreMPAmount=m;
	this.restoreSPAmount=s;
	this.itemType=0;
	Game.itemMasterList.add(this.name);//add this item to the master list
}//end of constructor
}//end of consumable class