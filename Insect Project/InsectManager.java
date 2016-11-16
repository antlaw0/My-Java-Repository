import java.util.*;

public class InsectManager 
{

		//Create two scanners, one for Strings, and one for numbers - int and float values.

		//Use this scanner to read text data that will be stored in String variables
		static Scanner stringScanner = new Scanner(System.in);
		//Use this scanner to read in numerical data that will be stored in int or double variables
		static Scanner numberScanner = new Scanner(System.in);

	public static void main(String[] args) 
	{
			
			Butterfly Monarch = new Butterfly();
			
			Monarch.setname("Monarch");
			Monarch.setwings(4);
			Monarch.setwingcolor("Orange and black");
			Monarch.setflower("Milkweed");
			
			Monarch.printSpeciesInfo();
			
			Bee HoneyBee = new Bee("Honey Bee",true, "Jasmine", "yellow and black", 4);
			HoneyBee.printSpeciesInfo();
			
			LinkedList<insect> insects = new LinkedList<insect>();
			insects.add(HoneyBee);
			insects.add(Monarch);
			
	//Create and add some Butterfly and Bee objects to the list
	for (insect i : insects) {
		i.printSpeciesInfo();
	}

			
			
			
			
	// Close scanners. Good practice to clean up resources you use.
	// Don't try to use scanners after this point. All code that uses scanners goes above here.
	stringScanner.close();
	numberScanner.close();
	}
}
	abstract class insect
	{
		
		String name;
		 final int legs=6;
		int wings;
		
		public abstract void printSpeciesInfo();
		
		public void setname(String name)
		{
			this.name = name;
			
		}
		
		public void setwings(int wings)
		{
			this.wings = wings;
			
		}
		
	}
	

	class Bee extends insect
	{
		String color;
		boolean honey;
		String flower;
		
		public Bee(String n, boolean h, String f, String color, int wings)
		{
			this.name=n;
			this.honey = h;
			this.flower=f;
			this.color=color;
			this.wings=wings;
		}
		
		public void printSpeciesInfo()
		{
			System.out.println(this.name);
			System.out.println("Color:  "+this.color);
			System.out.println("Number of wings:  "+this.wings);
			System.out.println("Favorite flower:  "+this.flower);
			if (this.honey == true){
			System.out.println("This species of bee does make honey."); }
			else{
			System.out.println("This species of bee does not make honey."); }
			
		}
		
	}
	
	
	class Butterfly extends insect
	{
		String wingcolor;
		String flower;
		
		public void setwingcolor(String wingcolor)
		{
			this.wingcolor = wingcolor;
			
		}
		
		public void setflower(String flower)
		{
			this.flower = flower;
			
		}
		
		public void printSpeciesInfo()
		{
			System.out.println(this.name+"\n"+this.legs+"\n"+this.wings+"\n"+this.wingcolor+"\n"+this.flower);
			
		}
		
		
	}
	

