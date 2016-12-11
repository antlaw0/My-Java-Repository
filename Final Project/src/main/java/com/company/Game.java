package com.company;
import java.sql.*;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
 

import java.util.*;

public class Game
{
		//Create two scanners, one for Strings, and one for numbers - int and float values.

		//Use this scanner to read text data that will be stored in String variables
		static Scanner stringScanner = new Scanner(System.in);
		//Use this scanner to read in numerical data that will be stored in int or double variables
		static Scanner numberScanner = new Scanner(System.in);

static Connection conn;		
		static ResultSet rs;
		static PreparedStatement psUpdatePlayerInfo;
		static PreparedStatement psInsert;
		static Statement statement;
		
static Player player;
		public static int x;
public static int y;
public static LinkedList<String> itemMasterList= new LinkedList();
		public static ArrayList<Room> roomList = new ArrayList<Room>();
public static Room currentRoom;
static boolean displayedRoomInfo=false;
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
			 String createTableSQL = "CREATE TABLE IF NOT EXISTS PlayerInfo (Name varchar(90), Description varchar(255), Gender VARCHAR(7), Xpos int, Ypos int, ClassID int, Level int, Experience int, HP int, MaxHP int, SP int, MaxSP int, MP int, MaxMP int, Strength int, Dextarity int, Agility int, Intelligence int)";
            statement.executeUpdate(createTableSQL);
            
			 
		} catch (SQLException se) {
			se.printStackTrace();
		}
	
Consumable test = new Consumable("Test Potion", "A test potion", 4,3,2);
Consumable apple = new Consumable("Apple","A shiny, red apple",1,1,1);
Armor testHelmet = new Armor("Helmet","A test helmet",1,5);
Armor testTorsoArmor = new Armor("Test Armor","A test torso armor",2,5);
Armor startingTorsoArmor = new Armor("Leather Armor","A leather torso armor",2,5);
Armor startingHelmet = new Armor("Leather Helmet","A test helmet",1,5);
Armor startingLegArmor = new Armor("Leather Leggings","A pair of leather leggings",3,2);
Armor startingFeetArmor = new Armor("Leather Boots","A pair of leather boots",4,1);


String command;
Room startingRoom = new Room("Starting Room", 0,0,"This is a large blank room... for now",false);
NPC Bob = new NPC("Bob","An old man in a travelling cloak stands here.");
Bob.inventory.add(apple);
startingRoom.add(Bob);
startingRoom.add(testTorsoArmor);
startingRoom.add(testHelmet);
startingRoom.add(apple);
currentRoom=startingRoom;
roomList.add(startingRoom);
Room eastRoom = new Room("A dark forest",1,0,"This is a dark forest.",false);
roomList.add(eastRoom);
Room southRoom = new Room("A grassy field",0,1,"A grassy field stretches out before you.",false);
roomList.add(southRoom);
Room testRoom = new Room("A test room",5,5,"This is a testing room.",false);
roomList.add(testRoom);

System.out.println("Welcome to the game!");

//find character in DB while loop
boolean hasCharacter=false;
String charName;
while (hasCharacter==false)
{
	System.out.println("Enter the name of your character or type 'new' to start a new character:  ");
	charName=stringScanner.nextLine();
	if (charName.equals ("new")) {
		if (searchForPlayerInDB(charName)==false) {
			createNewCharacter();
			hasCharacter=true;
		}//end of search for player in DB if statement
		
	}//end of if new character
	else
	{
		if (searchForPlayerInDB(charName)) {
		loadPlayerFromDB(charName);	
		System.out.println("Loading "+charName);
		hasCharacter=true;
		}
		else //no character with entered name exists
		{
			System.out.println("No character with name "+charName+" found.");
		}
	}//end of if statement searchfor char in DB
}//end of finding character loop
player.torsoSlot=startingTorsoArmor;
player.headSlot=startingHelmet;
player.legSlot=startingLegArmor;
player.feetSlot=startingFeetArmor;
player.inventory.add(test);


GUI gameGUI = new GUI();

//game while loop
while (true)
{
	if (displayedRoomInfo==false)
	{
	System.out.println("===================="+currentRoom.getRoomName()+"==================== \n ");
	System.out.println(currentRoom.getRoomDescription()+"\n");
	showRoomObjects(currentRoom);
	displayedRoomInfo=true;
	}//end of displayedRoomInfo if statement
	command = stringScanner.nextLine();
	processCommand(command);
	
}//end of game while loop

}//end of main method

public static void showRoomObjects(Room r) {
	WorldObject obj;
	LinkedList<WorldObject> list = r.getList();
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);
		System.out.println(obj.getDescription());
	}//end of for loop
}//end of show all objects method

public static boolean checkRoom(int checkX, int checkY)
{
	Room R;
	boolean exists=false;
	for (int i=0; i<roomList.size(); i++)
	{
		R=roomList.get(i);
		if (R.getX() == checkX && R.getY() == checkY)
		{
			exists=true;
		}//end if if statement
	
	}//end of for loop
	return exists;
}//end of check room method

public static Room getRoom()
{
	Room R;
	Room result=roomList.get(0);;
	for (int i=0; i<roomList.size(); i++)
	{
		R=roomList.get(i);
		
		if (R.getX() == x && R.getY() == y)
		{
			result=R;
		}//end of if statement
	}//end of for loop
return result;
	}//end of getroom method

public static void processCommand(String command)
{
	command = command.toLowerCase();
	String[] parts = command.split("\\s+");
	
	
	
	if ((parts[0]).equals ("north") || parts[0].equals ("n"))
	{
		if (checkRoom(x,y-1)) {
			y-=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		}//end of if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go north else statement
	}
	else if ((parts[0]).equals ("east") || parts[0].equals ("e"))
	{
		if (checkRoom(x+1,y))
		{
			x+=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		
			
		}//end of if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go east else statement
	}//end of go east statement
	else if ((parts[0]).equals ("west") || parts[0].equals ("w"))
	{
		if (checkRoom(x-1,y))
		{
			x-=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		}//end of go west if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go west else statement
	}//end of go west statement
	else if ((parts[0]).equals ("south") || parts[0].equals ("s"))
	{
		if (checkRoom(x,y+1))
		{
			y+=1;
			currentRoom=getRoom();
			displayedRoomInfo=false;
		}//end of check room statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go south else statement
	}//end of go south 
	else if ((parts[0]).equals ("test"))
	{
		LinkedList<WorldObject> list = currentRoom.getList();
		NPC o=(NPC) list.get(0);
		o.showInventory();
		
	}
	else if ((parts[0]).equals ("inventory") || (parts[0]).equals ("i"))
	{
System.out.println("You are carrying:  \n");
player.showInventory();
	}//end of show inventory command
	else if ((parts[0]).equals ("equipment")) {
		player.showEquipment();
	}//end of show equipment method
	else if ((parts[0]).equals ("equip") || (parts[0]).equals ("wear"))
	{
		equipItem(parts[1]);
	}//end of equip
	else if ((parts[0]).equals ("take"))
	{
		takeItem((parts[1]));
	}//end of if take
	else if ((parts[0]).equals ("exit") || (parts[0]).equals ("quit"))
	{
		quitGame();
	}
	
	
	else { //command is not recognized
	System.out.println("Command not recognized");
	}//end of command not recognized else
}//end of command processing method

public static void quitGame() {
	String answer;
	System.out.println("Do you want to quit? Enter y for yes and n for no.");
	answer=stringScanner.next();
	if (answer.equals ("y"))
	{
		System.exit(0);
	}
}
	
	public static void takeItem(String searchName) {
	WorldObject obj;
	Room r=currentRoom;
	String n;
	LinkedList<WorldObject> list = r.getList();
	boolean foundItem=false;
	searchName = searchName.toLowerCase();
	for (int i=0; i<list.size(); i++)
	{
		obj = list.get(i);
		n=obj.getName();
		
		
		n=n.toLowerCase();
		if (n.contains(searchName))
		{
			if (isItem(n) == true)
			{
				player.inventory.add((Item) obj);
				list.remove(i);
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
	
	
	public static void equipItem(String name)
	{
			int index=-1;
			Armor A;
			Item I;
			if (player.getIndexOf(name) != -1)
			{
				index=player.getIndexOf(name);
				I=player.inventory.get(index);
				if (I.itemType >=1 && I.itemType <= 4)
				{
				A=(Armor) I;
				if (A.itemType == 1) {
					player.headSlot=A;
					System.out.println(player.headSlot.getName()+" equipped.");
					player.inventory.remove(index);
				}//end of if itemType is 1
				else if (A.itemType == 2) {
					player.torsoSlot=A;
					System.out.println(player.torsoSlot.getName()+" equipped.");
					player.inventory.remove(index);
					
				}// end of if itemType is 2
			}//end of if statement
		
		
	else{
			System.out.println("You cannot equip that.");
		}//end of else statement
		
}//end of check if have item 
			else{System.out.println("You do not have that item.");}
		
	}//end of equip item method
		public static boolean searchForPlayerInDB(String searchName)
	{
		    boolean foundMatch=false;
			
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
                if (name.equals (searchName))
				{
					String description=rs.getString("Description");
					//set global x position to read-in value
					int x=rs.getInt("Xpos");
					//set global y position to read-in value
					String gender=rs.getString("Gender");
					int classID=rs.getInt("ClassID");
					int level=rs.getInt("Level");
					int xp=rs.getInt("Experience");
					int strength=rs.getInt("Strength");
					int agility = rs.getInt("Agility");
					int dextarity=rs.getInt("Dextarity");
					int intelligence=rs.getInt("Intelligence");
					int y=rs.getInt("Ypos");
					int hp=rs.getInt("HP");
					int maxhp=rs.getInt("MaxHP");
					int sp=rs.getInt("SP");
					int maxsp=rs.getInt("MaxSP");
					int mp=rs.getInt("MP");
					int maxmp=rs.getInt("MaxMP");
					
					player= new Player(name,description);
					player.level=level;
					player.experience=xp;
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
		
			if (className.equals ("Warrior")) { classChosen=true; startingHP=startingWarriorHP; startingSP=startingWarriorSP; startingMP=startingWarriorMP; startingStrength=startingWarriorStrength; startingDextarity=startingWarriorDextarity; startingAgility=startingWarriorAgility; startingIntelligence=startingWarriorIntelligence;}
			else if (className.equals ("Rogue")) { classChosen=true; startingHP=startingRogueHP; startingSP=startingRogueSP; startingMP=startingRogueMP; startingStrength=startingRogueStrength; startingDextarity=startingRogueDextarity; startingAgility=startingRogueAgility; startingIntelligence=startingRogueIntelligence;}
			
			else if (className.equals ("Mage")) {classChosen=true;  startingHP=startingMageHP; startingSP=startingMageSP; startingMP=startingMageMP; startingStrength=startingMageStrength; startingAgility=startingMageAgility; startingDextarity=startingMageDextarity; startingIntelligence=startingMageIntelligence; }
			else {System.out.println("Invalid input. Please enter 'Warrior', 'Rogue', or 'Mage'."); }
		}//end of classChosen while loop
			
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
			
			
			psInsert.executeUpdate();
			
			System.out.println("Character created. Starting adventure...");
			
			
			loadPlayerFromDB(name);
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
			
			
		
	}//end of createNewCharacter method
		
	
}//end of game class