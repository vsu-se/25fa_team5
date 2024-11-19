package auction_system;
//import java.util.*;

//import java.lang.Double;

public class Item {
	private int id;
	private String name;
	private User user;

	public Item(int id, String name, User user) { // Constructor needs to be reworked to generate the id on its own, the id should be a random integer of a predetermined format that checks for duplication prevention
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setID(int ID) {
		this.id = ID;
	}

	@Override
	public String toString() {
		String line1 = "Item ID: " + id + "\n";
		String line2 = "Item Name: " + name + "\n";
		String line3 = "Seller ID: " + user.getID() + "\n";
		String line4 = "Seller Name: " + user.getName() + "\n";
		return line1 + line2 + line3 + line4;
	}

}
