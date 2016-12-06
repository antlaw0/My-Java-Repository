import java.util.*;

abstract class WorldObject
{
public boolean isInventoryItem;
String name;
String description;
public void setDescription(String d) {
	this.description = d;
}

public String getDescription() {
	return this.description;
}
public String getName()
{
	return this.name;
}
public void setName(String n)
{
this.name=n;
}
	

}