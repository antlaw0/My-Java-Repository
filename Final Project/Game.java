import java.util.*;

public class Game
{
		//Create two scanners, one for Strings, and one for numbers - int and float values.

		//Use this scanner to read text data that will be stored in String variables
		static Scanner stringScanner = new Scanner(System.in);
		//Use this scanner to read in numerical data that will be stored in int or double variables
		static Scanner numberScanner = new Scanner(System.in);
static ArrayList<Room> roomList = new ArrayList<Room>();
static int x=0;
static int y=0;
static Room currentRoom;
public static void main(String[] args)
{

String command;
Room startingRoom = new Room("Starting Room", 0,0,"This is a large blank room... for now",false);
NPC Bob = new NPC("Bob","An old man in a travelling cloak stands here.");
startingRoom.add(Bob);
currentRoom=startingRoom;
Room eastRoom = new Room("A dark forest",1,0,"This is a dark forest.",false);
roomList.add(eastRoom);
roomList.add(startingRoom);

System.out.println("Welcome to the game!");
//game while loop
while (true)
{
	System.out.println("===================="+currentRoom.getRoomName()+"==================== \n ");
	System.out.println(currentRoom.getRoomDescription()+"\n");
	showRoomObjects(currentRoom);
	
	command = stringScanner.next();
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
		}//end of go west if statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go west else statement
	}//end of go west statement
	else if ((parts[0]).equals ("south") || parts[0].equals ("e"))
	{
		if (checkRoom(x,y+1))
		{
			y+=1;
			currentRoom=getRoom();
		}//end of check room statement
	else
	{
		System.out.println("Cannot go that way.");
	}//end of go south else statement
	}//end of go south 
	else { //command is not recognized
	System.out.println("Command not recognized");
	}//end of command not recognized else
}//end of command processing method

}//end of game class