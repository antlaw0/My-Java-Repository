package com.company;

import java.util.*;

public class NPC extends Character
{
private String dialogue;
public boolean enemy;

public NPC(String name, String description, String longDescription, boolean enemy) {
	this.name=name;
	this.description=description;
	this.longDescription=longDescription;
	this.enemy=enemy;
	
	if (enemy==true) {
		Game.enemyMasterList.add(name);
	}
	
}
public void setDialogue(String text) {
	this.dialogue = text;
}//end of set dialogue method
public String getDialogue() {
	return this.dialogue;
}//end of get dialogue method
}