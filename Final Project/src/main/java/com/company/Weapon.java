package com.company;

public class Weapon extends Item {
public int weaponDamage;
public int weaponAccuracy;

public Weapon(String name, String description, String longDescription, int dmg, int acc) {
this.name=name;
this.description=description;
this.longDescription=longDescription;
this.weaponDamage=dmg;
this.weaponAccuracy=acc;
Game.itemMasterList.add(this.name);

}//end of constructor


}//end of class