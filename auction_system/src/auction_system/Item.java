package auction_system;

public class Item {
	private int id;
	private String name;
	private User user;
	final int idLength = 5;

	public Item(int id, String name, User user) { 
		this.id = id;
		this.name = name;
		this.user = user;
		// After the constructor generates the item, it will need to be added to the itemList in itemManager
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
