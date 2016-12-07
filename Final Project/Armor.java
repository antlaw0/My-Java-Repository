public class Armor extends Item {
private String name;
private int armorType;
private int armorValue;

public Armor(String name, String description, int armorType, int armorValue) {
	this.name=name;
	this.description=description;
	this.armorValue=armorValue;
	Game.itemMasterList.add(this.name);
}//end of constructor
}//end of consumable class