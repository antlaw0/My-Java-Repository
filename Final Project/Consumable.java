public class Consumable extends Item {
private int restoreHPAmount;
private int restoreMPAmount;
private int restoreSPAmount;

public Consumable(String name, String description, int h, int m, int s) {
	this.name=name;
	this.description=description;
	this.restoreHPAmount=h;
	this.restoreMPAmount=m;
	this.restoreSPAmount=s;
	this.itemType=0;
	Game.itemMasterList.add(this.name);
}//end of constructor
}//end of consumable class