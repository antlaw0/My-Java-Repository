package com.company;

import java.util.*;

public abstract class Character extends WorldObject
{
public int maxHP=100;
public int HP=100;
public int maxSP=100;
public int SP=100;
public int maxMP=100;
public int MP=100;
public int strength=10;
public int intelligence=10;
public int dextarity=10;
public int agility=10;
public Armor headSlot;
public Armor torsoSlot;
public Armor legSlot;
public Armor feetSlot;
public int totalArmorValue=0;

public LinkedList<Item> inventory = new LinkedList<Item>();


public void showInventory() {
	Item I;
	boolean is;
	String isString;
	for (int i=0; i<this.inventory.size(); i++)
	{
		I=this.inventory.get(i);
			
		System.out.println("     "+I.getName());
	}
	
}//end of showInventory method

public int getIndexOf(String name) {
	int index=-1;
	name=name.toLowerCase();
	String resultName;
	Item I;
	for (int i=0; i<this.inventory.size(); i++)
	{
		I=this.inventory.get(i);
		resultName=I.getName();
		resultName=resultName.toLowerCase();
		if (resultName.contains(name)) {
			index=i;
		}//end of if statement
	}//end of for loop
	
	return index;
}//end of getIndexOf method

public int getTotalArmorValue() {
	int total = this.headSlot.armorValue+
	this.torsoSlot.armorValue+
	this.legSlot.armorValue+
	this.feetSlot.armorValue;
	
	return total;
	
}//end of getTotalArmorValue

public void showEquipment() {
	System.out.println(this.name+"'s equipment \n Slot     Armor Value");
	
	System.out.println("Head:  "+this.headSlot.getName()+"     "+headSlot.armorValue);
	System.out.println("Torso:  "+this.torsoSlot.getName()+"     "+torsoSlot.armorValue);

	
	
}//end of show equipment method

}//end of Character class
