//Java Text Adventure RPG
//created by Anthony Lawlor
//main file to launch game from
package com.company;
import java.sql.*;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
 

import java.util.*;
//main class
public class Game
{
		
		//initialize all static stuff
		//Create two scanners, one for Strings, and one for numbers - int and float values.

		//Use this scanner to read text data that will be stored in String variables
		static Scanner stringScanner = new Scanner(System.in);
		//Use this scanner to read in numerical data that will be stored in int or double variables
		static Scanner numberScanner = new Scanner(System.in);
static Random randomint = new Random();
//static SQL components
static Connection conn;		
		static ResultSet rs;
		static PreparedStatement psUpdatePlayerInfo;
		static PreparedStatement psInsert;
		static Statement statement;
		//init player object used throughout the class
static Player player;
		public static int x; //used throughout project as player's x position in game world
public static int y;// same as above, except y position
public static LinkedList<String> itemMasterList= new LinkedList();//used to keep track of all items in game
		public static LinkedList<String> enemyMasterList=new LinkedList<String>();//master list of enemies to keep track of all enemy names
		public static ArrayList<Room> roomList = new ArrayList<Room>();//used to keep track of all Room objects
public static Room currentRoom;//the current room the player is in
static boolean displayedRoomInfo=false;//used to not display room info over and over again when a new command is input
//starting class stats
static int startingWarriorHP=200;
static int startingWarriorSP=200;
static int startingWarriorMP=50;
static int startingWarriorStrength=20;
static int startingWarriorAgility=15;
static int startingWarriorDextarity=15;
static int startingWarriorIntelligence=5;

static int startingRogueHP=150;
static int startingRogueSP=200;
static int startingRogueMP=100;
static int startingRogueStrength=15;
static int startingRogueDextarity=20;
static int startingRogueAgility=20;
static int startingRogueIntelligence=10;

static int startingMageHP=100;
static int startingMageSP=100;
static int startingMageMP=200;
static int startingMageStrength=5;
static int startingMageDextarity=10;
static int startingMageAgility=15;
static int startingMageIntelligence=20;

//main method
public static void main(String[] args)
{
//set connection to SQL strings
final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/GameDB";     //Connection string – where's the database?
    final String USER = "Test";   
    final String PASSWORD = "password";   
rs =null;
statement=null;
			try {

            
			Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

		try{
	//create connection object conn
			conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            //create statement object from connection object
			statement = conn.createStatement();
			//create insert statement from connection object 
			psInsert = conn.prepareStatement("INSERT INTO PlayerInfo VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//create prepared statement from connection object
			 psUpdatePlayerInfo=conn.prepareStatement("UPDATE PlayerInfo SET Description=?, Gender=?, Xpos=?, Ypos=?, ClassID=?, Level=?, Experience=?, HP=?, MaxHP=?, SP=?, MaxSP=?, MP=?, MaxMP=?, Strength=?, Dextarity=?, Agility=?, Intelligence=?  WHERE Name=?");
			 //if table does not exist, create it
			 String createTableSQL = "CREATE TABLE IF NOT EXISTS PlayerInfo (Name varchar(90), Description varchar(255), Gender VARCHAR(7), Xpos int, Ypos int, ClassID int, Level int, Experience int, HP int, MaxHP int, SP int, MaxSP int, MP int, MaxMP int, Strength int, Dextarity int, Agility int, Intelligence int)";
            statement.executeUpdate(createTableSQL);//send this command to sql
            
			 
		} catch (SQLException se) {
			se.printStackTrace();
		}
	//create all objects inside the game
Consumable test = new Consumable("Test Potion", "A test potion is lying here", "This is a test potion. It serves no real purpose. It is just for testing. That is about it.", 4,3,2);
Consumable apple = new Consumable("Apple","A shiny, red apple is lying on the ground here", "It is a shiny, red apple. It looks to be safe to eat, not poisoned, no worms. It should heal some health if you eat it. Or not, you will have to eat it and see...", 1,1,1);
Weapon stick = new Weapon("Stick", "A wooden stick is lying on the ground here", "It is a simple, wooden stick approximately 2 feet long. It could be used as a weapon. It is better than nothing.", 1, 50);
Weapon sword = new Weapon("Sword", "A shiny new sword is lying on the ground here", "It is an iron sword. It appears to be of good quality and well-crafted. Good for fending off all sorts of ill-tempered beasties.", 5, 75);

Armor testHelmet = new Armor("Helmet","A test helmet is lying on the ground here", "It appears to be a low-quality helmet made of rough leather. It doesn't look like it will provide much protection but beggers cannot be choosers. Just wear it.", 1,5);
Armor testTorsoArmor = new Armor("Test Armor","A test torso armor is lying on the ground here", "It is a low-quality piece of armor that is worn on the body protecting the chest and vital organs from harm. It does not look like much but is better than nothing.", 2,5);
Armor startingTorsoArmor = new Armor("Leather Armor","A leather torso armor is lying on the ground here", "This is a lower quality light armor made of thick leather. It should provide minimal protection without sacrificing mobility. Good for light infantry, archers, and rogues.", 2,5);
Armor startingHelmet = new Armor("Leather Helmet","A leather helmet is lying on the ground here", "This looks like a basic leather helmet used by light infantry. It is light-weight and provides minimal protection. It is better than nothing.", 1,5);
Armor startingLegArmor = new Armor("Leather Leggings","A pair of leather leggings is lying on the ground here", "This is a pair of thick leather leggings worn by light infantry and archers. It provides minimal protection without sacrificing mobility. It is better than nothing.", 3,2);
Armor startingFeetArmor = new Armor("Leather Boots","A pair of leather boots is lying on the ground here", "These are a pair of low-quality leather boots. They provide minimal protection and do not look too comfortable. They are better than nothing though.", 4,1);


String command;
Room startingRoom = new Room("Small Camp", 0,0,"This is a small camp for travellers of all sorts. There a few tents scattered about with a fire crackling softly in the center. There are not many people currently staying here. ",true);
NPC Bob = new NPC("Bob","An old man in a travelling cloak stands here.", "He looks very old and well travelled. He has numerous bags that presumably  contain his travelling gear. He seems friendly enough. He is a human male approximately 5 ft tall.", false);
Bob.inventory.add(apple);
Furniture bed = new Furniture("bed","A nice, comfy bed.", "It is a small cott with a quilt and a pillow. Perfect for sleeping.");
startingRoom.add(bed);
startingRoom.add(Bob);
startingRoom.add(testTorsoArmor);
startingRoom.add(testHelmet);
startingRoom.add(apple);
currentRoom=startingRoom;
roomList.add(startingRoom);
Room eastRoom = new Room("A dark forest",1,0,"This is a dark forest.",false);

roomList.add(eastRoom);
Room stoneRoom = new Room("A circle of stones",2,0,"This is a clearing in the forest. large stones surround a lone stone with a sword stuck in it. How odd a sword could be stuck in a stone. Oh well, best leave it alone.",false);
stoneRoom.add(sword);
roomList.add(stoneRoom);
//create an enemy
NPC skeleton =new NPC("Skeletal Warrior", "An undead skeletal warrior stands here menacingly.", "It is a reanimated skeleton made to do its master's bidding. It creeks grotesquely as it moves clumbsily around. It seems to be wielding some sort of crude weapon. It does not look too tough. It is a pile of bones after all.", true);
//set attributes of this enemy
skeleton.experience=10;
skeleton.weaponSlot=stick;
skeleton.HP=50;
skeleton.maxHP=50;
skeleton.SP=50;
skeleton.maxSP=50;
skeleton.MP=10;
skeleton.maxMP=10;
skeleton.level=1;
skeleton.strength=5;
skeleton.dextarity=10;
skeleton.agility=10;
skeleton.intelligence=5;
//add this enemy to the room
eastRoom.add(skeleton);
Room southRoom = new Room("A grassy field",0,1,"A grassy field stretches out before you. You can see the camp to the north and a tall tower shrouded in mist to the southeast.",false);
NPC Mary = new NPC("Mary","A woman in a long flowing blue robe stands here.", "She appears to be meditating. She senses your presence and opens her eyes. \n 'Go away, can't you see I'm busy divining the location of my keys? I seem to have misplaced them again.'\n She is a human approximately 5 ft tall.", false);
southRoom.add(Mary);
southRoom.add(stick);
roomList.add(southRoom);
Room testRoom = new Room("A test room",5,5,"This is a testing room.",false);
roomList.add(testRoom);
//Initial greeting message
System.out.println("Welcome to the game!");

//find character in DB while loop
boolean hasCharacter=false;
String charName;
while (hasCharacter==false)
{
	//ask to start new character or resume a previous character in database
	System.out.println("Enter the name of your character or type 'new' to start a new character:  ");
	charName=stringScanner.nextLine();
	if (charName.equals ("new")) {//if you want to start a new character
		if (searchForPlayerInDB(charName)==false) { //character was not found
			createNewCharacter();//call createNewCharacter method
			hasCharacter=true;
		}//end of search for player in DB if statement
		
	}//end of if new character
	else
	{
		if (searchForPlayerInDB(charName)) {//call method that returns if that character exists in DB
		loadPlayerFromDB(charName);	//call method since that character exists
		System.out.println("Loading "+charName);
		hasCharacter=true;
		}
		else //no character with entered name exists
		{
			System.out.println("No character with name "+charName+" found.");
		}
	}//end of if statement searchfor char in DB
}//end of finding character loop
//set player starting equipment 
player.torsoSlot=startingTorsoArmor;
player.headSlot=startingHelmet;
player.legSlot=startingLegArmor;
player.feetSlot=startingFeetArmor;
player.weaponSlot=stick;
player.inventory.add(test);//give player an item to start with


GUI gameGUI = new GUI();

//game while loop
while (true)
{
	if (displayedRoomInfo==false)// so don't keep displaying this info every time a new command is entered
	{
	System.out.println("===================="+currentRoom.getRoomName()+"==================== \n ");//display name of current room
	System.out.println(currentRoom.getRoomDescription()+"\n");//print description of this room
	showRoomObjects(currentRoom);//call method to display descriptions of all objects added to the room
	displayedRoomInfo=true;
	}//end of displayedRoomInfo if statement
	command = stringScanner.nextLine();//get the command the user has just typed in
	processCommand(command);//call processCommand with this typed in argument and go through all the processing to determine what the command should do
	
}//end of game while loop

}//end of main method
//This method displays all the object descriptions in the current room
public static void showRoomObjects(Room r) {
	WorldObject obj;
	LinkedList<WorldObject> list = r.getList();//get the room's object list and assign to 'list'
	//loop through all objects in list and print each's description
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);//get current iteration's object
		//print its description
		System.out.println(obj.getDescription());
	}//end of for loop
}//end of show all objects method
//This method is to check if the x and y position the user entered actually contain a room in that direction
public static boolean checkRoom(int checkX, int checkY)
{
	Room R;
	boolean exists=false;
	//loop through all rooms to determine if that move to that location is possible
	for (int i=0; i<roomList.size(); i++)
	{
		R=roomList.get(i);
		//if there is a room at that x and y
		if (R.getX() == checkX && R.getY() == checkY)
		{
			exists=true;//yes, there is a room in that direction
		}//end if if statement
	
	}//end of for loop
	return exists;
}//end of check room method
//Get the room that is currently at the x and y position of player
public static Room getRoom()
{
	Room R;
	Room result=roomList.get(0);;
	//loop through all rooms to find which one is the correct room at this position
	for (int i=0; i<roomList.size(); i++)
	{
		R=roomList.get(i);
		//if this iteration's x and y match the player's x and y
		if (R.getX() == x && R.getY() == y)
		{
			result=R;
		}//end of if statement
	}//end of for loop
return result;//return the room object with matching x and y
	}//end of getroom method
//This method is the main command processing in the main game loop
public static void processCommand(String command)
{
	command = command.toLowerCase();//convert entered command to lower case for easier matching
	String[] parts = command.split("\\s+");//split the command string at the spaces
	
	
	//if first word is for going north
	if ((parts[0]).equals ("north") || parts[0].equals ("n"))
	{
		if (parts.length == 1) {
		if (checkRoom(x,y-1)) {
			player.SP-=1;//fatigue from travelling
			y-=1;
			currentRoom=getRoom();//get room at new x and y
			displayedRoomInfo=false;
		}//end of if statement
	else//there is no room in that direction
	{
		System.out.println("Cannot go that way.");
	}//end of go north else statement
		}//end of check parts array length
		else{//command not entered correctly
			System.out.println("Enter direction:  north, south, east, west.");//help message tip
		}//end of else parts length
	}
	else if ((parts[0]).equals ("east") || parts[0].equals ("e"))
	{
		if (parts.length == 1) {
		if (checkRoom(x+1,y))
		{
			player.SP-=1;
			x+=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		
			
		}//end of if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go east else statement
		} else {
			System.out.println("Enter direction:  north, south, east, west.");
		
		}//end of else check length of parts
	}//end of go east statement
	else if ((parts[0]).equals ("west") || parts[0].equals ("w"))
	{
		if (parts.length == 1) {
		if (checkRoom(x-1,y))
		{
			player.SP-=1;
			x-=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		}//end of go west if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go west else statement
		} else {
			System.out.println("Enter direction:  north, south, east, west.");
		
		}//end of check parts length statement
	}//end of go west statement
	else if ((parts[0]).equals ("south") || parts[0].equals ("s"))
	{
		if (parts.length == 1) {
		if (checkRoom(x,y+1))
		{
			player.SP-=1;
			y+=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		}//end of check room statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go south else statement
		} else {
			System.out.println("Enter direction:  north, south, east, west.");
		
		}//end of check parts length
	}//end of go south 
	//used for testing new commands, need to remove in final release
	else if ((parts[0]).equals ("test"))
	{
		LinkedList<WorldObject> list = currentRoom.getList();
		NPC o=(NPC) list.get(0);
		o.showInventory();
		
	}
	else if ((parts[0]).equals ("inventory") || (parts[0]).equals ("i"))
	{
if (parts.length == 1) {
System.out.println("You are carrying:  \n");
player.showInventory();
}//end of check for length of array
else{
	System.out.println("Command takes 1 argument <inventory> or <i>.");
}
	}//end of show inventory command
	//show player's equipment
	else if ((parts[0]).equals ("equipment")) {
		if (parts.length == 1) {
		player.showEquipment();
		}//end of show equipment
		else {
			System.out.println("Command takes 1 argument <equipment>.");
		}
	}//end of show equipment method
	// equip, then name of equipment, example, 'equip helmet'
	else if ((parts[0]).equals ("equip") || (parts[0]).equals ("wear"))
	{
		if (parts.length == 2) {
		equipItem(parts[1]);
	}//end of check length of array
	else {
		System.out.println("Command takes 2 arguments <equip> or <wear> <item_name>.");
	}
	}//end of equip
	
	//used to get object's longDescription
	else if ((parts[0]).equals ("look") || (parts[0]).equals ("examine"))
	{
		if (parts.length == 2) {
		lookAtObject(parts[1]);
		}//end of check array length
		else {
			System.out.println("Command takes 2 arguments, <look> or <examine> <object_name>.");
		}
	}//end of looking
	
	//take <item_name>
	else if ((parts[0]).equals ("take"))
	{
		if (parts.length == 2) {
		takeItem((parts[1]));
		}//end of check if array length is 2
		else{
			System.out.println("Command 'take' takes 2 arguments (take <name_of_item>).");
		}
	}//end of if take
	//used to save game and refill hp, sp, and mp
	else if ((parts[0]).equals ("sleep") || (parts[0]).equals ("rest"))
	{
		if (parts.length == 1) {
		//can only sleep in safe rooms, adds strategy because cannot sleep and refill all stats in hostile areas or travelling
		if (currentRoom.isSafe() == true) {
			System.out.println("You lay down and fall asleep.");
			//fully heal player
			player.HP=player.maxHP;
			player.SP=player.maxSP;
			player.MP=player.maxMP;
			
			savePlayerInfoToDB(player.getName());
		} else {
			System.out.println("You cannot "+parts[0]+" here.");
		}
		}//end of check length of array
		else {
			System.out.println("Command takes 1 argument <sleep> or <rest>.");
		}
	}//end of if sleep or rest
	//attack <name_of_target>
	else if ((parts[0]).equals ("attack"))
	{
		if (parts.length == 2) {
		attackEnemy((parts[1]));
		}//end of check if array length is 2
		else{
			System.out.println("Command 'attack' takes 2 arguments (attack <name_of_enemy>).");
		}
	}//end of if attackEnemy
	
	//used to show all player's statistics
	else if ((parts[0]).equals ("stats") || (parts[0]).equals ("score"))
	{
		if (parts.length == 1) {
player.showStats();
		}//end of check length of array
		else {
			System.out.println("Command takes 1 argument <stats> or <score>.");
		}
	}//end of if check stats
	
	
	
	
	//used to quit game
	else if ((parts[0]).equals ("exit") || (parts[0]).equals ("quit"))
	{
		if (parts.length == 1) {
		quitGame();
	}//end of check for length of array
	else {
		System.out.println("Command takes 1 argument <exit> or <quit>.");
	}
	}
	
	else { //command is not recognized
	System.out.println("Command not recognized");
	}//end of command not recognized else
}//end of command processing method
//This method is called in command processing
public static void quitGame() {
	String answer;
	System.out.println("Do you want to quit? Enter y for yes and n for no.");
	answer=stringScanner.next();
	if (answer.equals ("y"))
	{
		savePlayerInfoToDB(player.getName());//save the character's current stats
		System.exit(0);
	}
}
	
	//This method takes a string from the second element of the command string and searches through the object list for the current room to see if the player can take that item and add it to their inventory
	public static void takeItem(String searchName) {
	WorldObject obj;
	Room r=currentRoom;
	String n;
	LinkedList<WorldObject> list = r.getList();
	boolean foundItem=false;
	searchName = searchName.toLowerCase();
	//loop through all objects in current room's list of objects
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);
		n=obj.getName();
		
		
		n=n.toLowerCase();
		//if this object's name contains part of the search name
		if (n.contains(searchName))
		{
			//check if this object is an Item
			if (isItem(n) == true)
			{
				//add this Item to the player's inventory
				player.inventory.add((Item) obj);
				list.remove(i);//remove this Item from the room
				System.out.println(obj.getName()+" added to your inventory.");
				foundItem=true;
				break;
				
				
			}//end of inner if statement
			else{ // obj has searchName but is not an inventory item
				System.out.println("You cannot take that.");
				foundItem=true;
				break;
				
			}//end of inner else statement
		}	//end of outer if statement
		
		}//end of for loop

		
		if (foundItem == false)
		{
			System.out.println("You see nothing like that here.");
		}//end of if foundItem is true
		
	
	}//end of takeItem method
	//This method is called in command processing
	public static void attackEnemy(String searchName) {
	WorldObject obj;
	Room r=currentRoom;
	String n;
	LinkedList<WorldObject> list = r.getList();
	boolean foundEnemy=false;
	boolean enemyDefeated=false;
	searchName = searchName.toLowerCase();
	//loop through all objects in this room until match is found or not
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);
		n=obj.getName();//get name of iteration's object
		
		
		n=n.toLowerCase();
		//if object's name is match in whole or in part of object's name
		if (n.contains(searchName))
		{
			if (isEnemy(n) == true)//if method called returns true
			{
				foundEnemy=true;
				NPC e = (NPC) obj;//create object 'e' from casting obj to an NPC
				//this is a typical RPG attack formula to see if the player's attack hit based on their dextarity and how accurate their weapon is. It is easier to hit things with a dagger than it is with a huge battleaxe for example. There is randomness so to keep things interesting
				
				if ((player.weaponSlot.weaponAccuracy+randomint.nextInt(player.dextarity)) >= e.agility)
				{
					//damage dealt should also be a combination of multiple stats run through a formula with some randomness into the equation. Eventually resistence from armor will midigate some of this damage
					int totalDmg=(player.weaponSlot.weaponDamage+randomint.nextInt(player.strength));
					player.SP-=5;//fatigue from attacking eventually will be different for each weapon or attack type
					System.out.println("You attack "+e.getName()+" for "+totalDmg+" points of damage.");
					e.HP-=totalDmg;//subtract the damage dealt from player to this enemy
				if (e.HP<=0) {// if this enemy is dead
					System.out.println(e.getName()+" has been defeated! You receive "+e.experience+" experience for your victory.");
					player.experience+=e.experience;//player gets experience used to level up from defeating this enemy. Tougher enemies yield more experience.
					list.remove(i);//remove enemy from room
					enemyDefeated=true;
					break;
				}//end of check of enemy hp is less than or zero
				}//end of check if hit
				else{//your attack missed based on formula, perhaps enemy was too nimble
					System.out.println("You missed!");
				}//end of if attack missed
				if (enemyDefeated==false) {//have to check in case enemy was defeated, dead enemies cannot still attack
					System.out.println(e.getName()+" attacks...");
					//now do the same formula from above except reverse the roles
					if ((e.weaponSlot.weaponAccuracy+randomint.nextInt(e.dextarity)) >= player.agility) {
						int totalDmg= (e.weaponSlot.weaponDamage+randomint.nextInt(e.strength));//same as above, except enemy is dealing damage to player
						player.HP-=totalDmg;//subtract total damage from player's hp
						//print the result of the attack
						System.out.println(e.getName()+" hits you for "+totalDmg+" points of damage leaving you with "+player.HP+" health.");
						if (player.HP <=0) {//if player is now dead
							System.out.println("You have been defeated... game over");
							System.exit(0);//exit game, don't want player to be able to save after being dead as a penalty
						}//end of check if player hp less than or equal to zero
					}//end of check of enemy attack hit
					else {//enemy missed
						System.out.println(e.getName()+"'s attack missed.");
					}
				}//end of if enemy not defeated
				
				
				
			}//end of inner if statement
			else{ // obj has searchName but is not an enemy
				System.out.println("You cannot attack that.");
				foundEnemy=true;
				break;
				
			}//end of inner else statement
		}	//end of outer if statement
		
		}//end of for loop

		//no such name was found
		if (foundEnemy == false)
		{
			System.out.println("You see nothing like that here.");
		}//end of if foundEnemy is true
		
	
	}//end of attackEnemy method
	
	
	//This method is to check if the given string is an item used for item varification
	public static boolean isItem(String name) {
		boolean foundMatch=false;
		String nameInList;
		for (int i=0; i<itemMasterList.size(); i++)
		{
			
			nameInList=itemMasterList.get(i);
			nameInList=nameInList.toLowerCase();
			if (name.equals (nameInList))
			{
				foundMatch=true;
			}//end of if statement
		}//end of for loop
		
		return foundMatch;
	}//end of isItem method
	public static boolean isEnemy(String name) {
		boolean foundMatch=false;
		String nameInList;
		for (int i=0; i<enemyMasterList.size(); i++)
		{
			
			nameInList=enemyMasterList.get(i);
			nameInList=nameInList.toLowerCase();
			if (name.equals (nameInList))
			{
				foundMatch=true;
			}//end of if statement
		}//end of for loop
		
		return foundMatch;
	}//end of isEnemy method
	
	//This method is for calling in command processing
	public static void equipItem(String name)
	{
			int index=-1;
			Armor A;
			Weapon W;
			Item I;
			//check if player has this item in their inventory
			if (player.getIndexOf(name) != -1)
			{
				index=player.getIndexOf(name);//get the index of this item in the inventory
				I=player.inventory.get(index);//assign I to this item
				if (I.itemType >=1 && I.itemType <= 5)//if itemType of this Item is within itemType ranges for equippable items
				{
				if (I.itemType==5){//if weapon
				W=(Weapon) I;//cast Item to an Weapon object
				player.inventory.add(player.weaponSlot);//remove held weapon and put in inventory
				player.weaponSlot=W;//equip new weapon
				System.out.println(player.weaponSlot.getName()+" equipped.");
					player.inventory.remove(index);//remove item from inventory
				}//end of if weapon
				if (I.itemType == 1) {//if helmet type
					A=(Armor) I;//cast Item to an Armor object
				
					player.inventory.add(player.headSlot);//take off headwear and put in inventory
					player.headSlot=A;//this item is equipped to player's head
					System.out.println(player.headSlot.getName()+" equipped.");
					player.inventory.remove(index);//remove from inventory because player is wearing it on their head now
				}//end of if itemType is 1
				if (I.itemType == 2) {//if Item is torso armor
					A=(Armor) I;//cast Item to an Armor object
				
					player.inventory.add(player.torsoSlot);//remove torso armor and put in player's inventory
					player.torsoSlot=A;//assign Item to player's torsoSlot
					System.out.println(player.torsoSlot.getName()+" equipped.");
					player.inventory.remove(index);//remove from inventory
					
				}// end of if itemType is 2
				if (I.itemType == 3) {//if Item is torso armor
					A=(Armor) I;//cast Item to an Armor object
				
					player.inventory.add(player.legSlot);//remove leg armor and put in player's inventory
					player.legSlot=A;//assign Item to player's legSlot
					System.out.println(player.legSlot.getName()+" equipped.");
					player.inventory.remove(index);//remove from inventory
					
				}// end of if itemType is 3
				if (I.itemType == 4) {//if Item is torso armor
					A=(Armor) I;//cast Item to an Armor object
				
					player.inventory.add(player.feetSlot);//remove feet armor and put in player's inventory
					player.feetSlot=A;//assign Item to player's feetSlot
					System.out.println(player.feetSlot.getName()+" equipped.");
					player.inventory.remove(index);//remove from inventory
					
				}// end of if itemType is 4
			
				
				
			}//end of if statement
		
		//eventually, add other equippable items like leggings, boots, weapons, shields, rings, etc.
	else{
			System.out.println("You cannot equip that.");
		}//end of else statement
		
}//end of check if have item 
			else{System.out.println("You do not have that item.");}
		
	}//end of equip item method
		
		//This method is for finding if an object with a given name is in the room
		public static int searchRoom(String name) {
		int result=-1;
		LinkedList list=currentRoom.getList();
for (int i=0; i<list.size(); i++) {
if ((list.get(i)).equals (name)) {
	result=i;
}//end of if statement
}//end of for loop	
return result;
		}//end of searchRoom method
		//This method searches the database for a player with a given name and returns boolean if player exists or not
		public static boolean searchForPlayerInDB(String searchName)
	{
		    boolean foundMatch=false;
			//sql try...
try {	
	//make string containing the query
	String fetchAllDataSQL = "SELECT * FROM PlayerInfo";
            //create resultset with this query
			rs = statement.executeQuery(fetchAllDataSQL);
			//while there are results in the results set
			while (rs.next()) {
                //get string name from this result
				String name = rs.getString("name");
                if (name.equals (searchName))
				{
					foundMatch=true;
				}//end of if name exists
			}//end of while loop
            
	
	
} catch (SQLException se) {
System.out.println("SQL Exception detected.");
}	
	
	return foundMatch;
		
	}//end of read in from DB method

	//This method loads the name of the given player from the database
	public static void loadPlayerFromDB(String searchName)
	{
		    
try {	
	//make string containing the query
	String fetchAllDataSQL = "SELECT * FROM PlayerInfo";
            //create resultset with this query
			rs = statement.executeQuery(fetchAllDataSQL);
			//while there are results in the results set
			while (rs.next()) {
                //get string name from this result
				String name = rs.getString("name");
                if (name.equals (searchName))//this player exists
				{
					//get all the stats from this player in the database
					String description=rs.getString("Description");
					//set global x position to read-in value
					Game.x=rs.getInt("Xpos");
					String gender=rs.getString("Gender");
					int classID=rs.getInt("ClassID");
					int level=rs.getInt("Level");
					int xp=rs.getInt("Experience");
					int strength=rs.getInt("Strength");
					int agility = rs.getInt("Agility");
					int dextarity=rs.getInt("Dextarity");
					int intelligence=rs.getInt("Intelligence");
					//set global y position to read-in value
					Game.y=rs.getInt("Ypos");
					int hp=rs.getInt("HP");
					int maxhp=rs.getInt("MaxHP");
					int sp=rs.getInt("SP");
					int maxsp=rs.getInt("MaxSP");
					int mp=rs.getInt("MP");
					int maxmp=rs.getInt("MaxMP");
					//create player object from this info from database
					player= new Player(name,description);
					player.level=level;
					player.experience=xp;
					player.gender=gender;
					player.classID=classID;
					player.strength=strength;
					player.dextarity=dextarity;
					player.agility=agility;
					player.intelligence=intelligence;
					player.HP=hp;
					player.maxHP=maxhp;
					player.SP=sp;
					player.maxSP=maxsp;
					player.MP=mp;
					player.maxMP=maxmp;
				
					
				}//end of if name exists
			}//end of while loop
            
	
	
} catch (SQLException se) {
System.out.println("SQL Exception detected.");
}	
	
	
		
	}//end of read in from DB method
//This method walks through the character creation process and makes a new player entry in the database
	public static void createNewCharacter() {
		String name;
		String desc;
		String className="";
		int startingHP=0;
		int startingSP=0;
		int startingMP=0;
		int startingStrength=0;
		int startingDextarity=0;
		int startingAgility=0;
		int startingIntelligence=0;
		int classID=0;
		//enter a name for your new character
		System.out.println("Enter a name for your character:  ");
		name = stringScanner.nextLine();
		boolean genderInputCheck=false;
		String gender="";
		while (genderInputCheck==false) {
		System.out.println("Will your character be male or female? Enter 'male' for male and 'female' for female:  ");
		gender=stringScanner.nextLine();
		if (gender.equals ("male")) {
			System.out.println(name+" is male.");
			genderInputCheck=true;
			
		}// end of if male
		else if (gender.equals ("female")) {
				System.out.println(name+" is female.");
				genderInputCheck=true;
		}//end of if female
		else{
			System.out.println("Invalid input. Please enter 'male' or 'female' ");
			
		}//end of else
		}//end of input validation loop
		
		
		
		boolean classChosen=false;
		
		while (classChosen==false) {
		System.out.println("Choose a class for your character. Enter the name of the desired class: \n ");
		System.out.println("Warrior- skilled in close-quarters combat, high health and stamina. ");
		System.out.println("Rogue- Swift, agile, and cunning, rogues are adept at deception and entrapping, low health, high stamina ");
		System.out.println("Mage- followers of the ways of the arcane, mages are wielders of magic in all its power and diversity, low health, high magic");
		
		className=stringScanner.nextLine();
		//set class info based on chosen class
			if (className.equals ("Warrior")) { classID=1; classChosen=true; startingHP=startingWarriorHP; startingSP=startingWarriorSP; startingMP=startingWarriorMP; startingStrength=startingWarriorStrength; startingDextarity=startingWarriorDextarity; startingAgility=startingWarriorAgility; startingIntelligence=startingWarriorIntelligence;}
			else if (className.equals ("Rogue")) { classID=2; classChosen=true; startingHP=startingRogueHP; startingSP=startingRogueSP; startingMP=startingRogueMP; startingStrength=startingRogueStrength; startingDextarity=startingRogueDextarity; startingAgility=startingRogueAgility; startingIntelligence=startingRogueIntelligence;}
			
			else if (className.equals ("Mage")) {classID=3; classChosen=true;  startingHP=startingMageHP; startingSP=startingMageSP; startingMP=startingMageMP; startingStrength=startingMageStrength; startingAgility=startingMageAgility; startingDextarity=startingMageDextarity; startingIntelligence=startingMageIntelligence; }
			else {System.out.println("Invalid input. Please enter 'Warrior', 'Rogue', or 'Mage'."); }
		}//end of classChosen while loop
			//display this choice
			System.out.println(name+" is a "+className+".");
		
		
		System.out.println("Enter a description for your character (255 character limit):  ");
		desc = stringScanner.nextLine();
		while (desc.length() > 255) {
			System.out.println("Description length greater than 255. Please re-enter a description less than 255 characters in length:  ");
			desc=stringScanner.nextLine();
		}//end of description length validation
		
		System.out.println("\n "+name+" \n ");
		System.out.println("Description:  "+desc);
		System.out.println("Gender:  "+gender+" \n ");
		System.out.println("Class:  "+className);
		
		try{
			//put all this entered info into prepared insert statement
			psInsert.setString(1, name);
			psInsert.setString(2, desc);
			psInsert.setString(3, gender);
			psInsert.setInt(4, 0); // x position
			psInsert.setInt(5, 0);//y position
			psInsert.setInt(6, classID);
			psInsert.setInt(7, 1);// level
			psInsert.setInt(8, 0); //experience
			psInsert.setInt(9, startingHP);
			psInsert.setInt(10, startingHP);
			psInsert.setInt(11, startingSP);
			psInsert.setInt(12, startingSP);
			psInsert.setInt(13, startingMP);
			psInsert.setInt(14, startingMP);
			psInsert.setInt(15, startingStrength);
			psInsert.setInt(16, startingDextarity);
			psInsert.setInt(17, startingAgility);
			psInsert.setInt(18, startingIntelligence);
			
			//execute sql in prepared statement
			psInsert.executeUpdate();
			
			System.out.println("Character created. Starting adventure...");
			
			//call method to start game
			loadPlayerFromDB(name);
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
			
			
		
	}//end of createNewCharacter method
		
		//This method saves current stats of the player to the database
		public static void savePlayerInfoToDB(String name) {
			try{
			psUpdatePlayerInfo.setString(1, player.getDescription());
			psUpdatePlayerInfo.setString(2, player.gender);
			psUpdatePlayerInfo.setInt(3, Game.x); // x position
			psUpdatePlayerInfo.setInt(4, Game.y);//y position
			psUpdatePlayerInfo.setInt(5, player.classID);
			psUpdatePlayerInfo.setInt(6, player.level);// level
			psUpdatePlayerInfo.setInt(7, player.experience); //experience
			psUpdatePlayerInfo.setInt(8, player.HP);
			psUpdatePlayerInfo.setInt(9, player.maxHP);
			psUpdatePlayerInfo.setInt(10, player.SP);
			psUpdatePlayerInfo.setInt(11, player.maxSP);
			psUpdatePlayerInfo.setInt(12, player.MP);
			psUpdatePlayerInfo.setInt(13, player.maxMP);
			psUpdatePlayerInfo.setInt(14, player.strength);
			psUpdatePlayerInfo.setInt(15, player.dextarity);
			psUpdatePlayerInfo.setInt(16, player.agility);
			psUpdatePlayerInfo.setInt(17, player.intelligence);
			psUpdatePlayerInfo.setString(18, player.getName());
			//run the sql statement based on player's current stats at time of saving
			psUpdatePlayerInfo.executeUpdate();
			

			
			System.out.println(name+" info saved.");

			
			
			
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}//end of savePlayerInfoToDB method
	//This method is called in command processing  it is used to get lonDescriptions of any object in the world
	public static void lookAtObject(String searchName) {
		
		WorldObject obj;
	Room r=currentRoom;
	String n;
	LinkedList<WorldObject> list = r.getList();
	boolean foundObject=false;
	searchName = searchName.toLowerCase();
	//loop through all objects in room until find match
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);
		n=obj.getName();
		
		//compare object's name to entered name to search for
		n=n.toLowerCase();
		if (n.contains(searchName))
		{
			foundObject=true;
			System.out.println(obj.getName()+":  "+obj.longDescription);//print this object's longDescription string variable 
			break;
		}//end of  if statement
		
		}//end of for loop

		//if no match found in room
		if (foundObject == false)
		{
			System.out.println("You see nothing like that here.");
		}//end of if foundItem is true
		
	
	
	}//end of lookAtObject method
}//end of game class