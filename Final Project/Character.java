import java.util.*;

public abstract class Character extends WorldObject
{
public boolean isInventoryItem=false;
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
public int headArmorValue=0;
public String headString="None";
public int torsoArmorValue=0;
public String torsoString="None";
public int legArmorValue=0;
public String legString="None";
public int feetArmorValue=0;
public String feetString="None";
public int weaponDamageValue=0;
public String weaponString="None";
public int weaponAccuracy=0;
public int totalArmorValue=0;

public LinkedList<Item> inventory = new LinkedList<Item>();


public void showInventory() {
	Item I;
	boolean is;
	String isString;
	for (int i=0; i<this.inventory.size(); i++)
	{
		I=this.inventory.get(i);
		if (I.isInventoryItem == true) {
			isString="Yes";
		}else{
			isString="No";
		}
		System.out.println(I.getName()+"     "+isString);
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
		if (name.equals (resultName)) {
			index=i;
		}//end of if statement
	}//end of for loop
	
	return index;
}//end of getIndexOf method

}//end of Character class
