package com.company;

import java.util.*;
//All entities in the game are Characters
public abstract class Character extends WorldObject
{
//all characters have same attributes
public int maxHP=100;
public int HP=100;//health
public int maxSP=100;
public int SP=100;//stamina
public int maxMP=100;
public int MP=100;//magic points
public int classID;
public String gender;
public int level=1;
public int experience=0;
public int strength=10;
public int intelligence=10;
public int dextarity=10;
public int agility=10;
public Armor headSlot;
public Armor torsoSlot;
public Armor legSlot;
public Armor feetSlot;
public Weapon weaponSlot;
public int totalArmorValue=0;
//all have inventories of items
public LinkedList<Item> inventory = new LinkedList<Item>();

//This method shows a character's inventory
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
//This is used mainly to check if a character has a given item and get the index of that item if needed
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
//This is used in combat calculations to get the amount of damage reduction of a given character
public int getTotalArmorValue() {
	int total = this.headSlot.armorValue+
	this.torsoSlot.armorValue+
	this.legSlot.armorValue+
	this.feetSlot.armorValue;
	
	return total;
	
}//end of getTotalArmorValue
//show all equipment this character is currently wearing
public void showEquipment() {
	System.out.println(this.name+" Equipment");
	System.out.println("Weapon Name:  "+this.weaponSlot.getName()+"  Damage:  "+this.weaponSlot.weaponDamage+"   Accuracy:  "+this.weaponSlot.weaponAccuracy);
	
	System.out.println("Armor Slot     Armor Value");
	
	System.out.println("Head:  "+this.headSlot.getName()+"     "+headSlot.armorValue);
	System.out.println("Torso:  "+this.torsoSlot.getName()+"     "+torsoSlot.armorValue);
	System.out.println("Legs:  "+this.legSlot.getName()+"     "+legSlot.armorValue);
	System.out.println("Feet:  "+this.feetSlot.getName()+"     "+feetSlot.armorValue);

	
	
}//end of show equipment method
//display all stats of this character
public void showStats() {
	
	String className;
	if (this.classID==1){className="Warrior";}
	else if (this.classID==2){className="Rogue";}
	else if (this.classID==3){className="Mage";}
	else{className="Unknown";}
	
	
	System.out.println("Name:  "+this.name);
	System.out.println(this.description);
	System.out.println("Gender:  "+this.gender);
	System.out.println("Level:  "+this.level);
	System.out.println("Class:  "+className);
	System.out.println("Experience:  "+this.experience);
	System.out.println("HP:  "+this.HP+" / "+this.maxHP);
	System.out.println("SP:  "+this.SP+" / "+this.maxSP);
	System.out.println("MP:  "+this.MP+" / "+this.maxMP);
	System.out.println("Strength:  "+this.strength);
	System.out.println("Dextarity:  "+this.dextarity);
	System.out.println("Agility:  "+this.agility);
	System.out.println("Intelligence:  "+this.intelligence);
	
	
	
}//end of show character stats method

}//end of Character class
