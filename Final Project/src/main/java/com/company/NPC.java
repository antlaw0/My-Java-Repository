package com.company;

import java.util.*;

public class NPC extends Character
{
public boolean isInventoryItem=false;	
private String dialogue;
public NPC(String name, String description) {
	this.name=name;
	this.description=description;
}

public void setDialogue(String text) {
	this.dialogue = text;
}//end of set dialogue method
public String getDialogue() {
	return this.dialogue;
}//end of get dialogue method
}