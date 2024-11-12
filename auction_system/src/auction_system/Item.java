package auction_system;
//import java.util.*;
//import java.lang.Double;

public class Item {
    private int id;
    private String name;
      
    public Item (int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getID () {
    	return this.id;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setName (String name) {
    	this.name = name;
    }
    
    public void setID (int ID) {
    	this.id = ID;
    }

    @Override
    public String toString () {
    	String line1 = "Item ID: " +  id + "\n";
    	String line2 = "Item Name: "+ name + "\n";
    	return line1+line2;
    }

}
