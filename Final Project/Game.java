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
static Player player = new Player("Guy","Brave and noble adventurer");
		public static int x=0;
public static int y=0;
public static LinkedList<String> itemMasterList= new LinkedList();
		public static ArrayList<Room> roomList = new ArrayList<Room>();
public static Room currentRoom;
static boolean displayedRoomInfo=false;

public static void main(String[] args)
{
GUI gameGUI = new GUI();
Consumable test = new Consumable("Test Potion", "A test potion", 4,3,2);
Consumable apple = new Consumable("Apple","A shiny, red apple",1,1,1);
player.inventory.add(test);
String command;
Room startingRoom = new Room("Starting Room", 0,0,"This is a large blank room... for now",false);
NPC Bob = new NPC("Bob","An old man in a travelling cloak stands here.");
Bob.inventory.add(apple);
startingRoom.add(Bob);
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
		if (n.equals (searchName))
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
	
}//end of game class