package auction_system;

public class Item {
	private String name;
	private int id;

	public Item(int id, String name) { 
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String line1 = "Item Name: " + name + "\n";
		String line2 = "Item ID: " + id + "\n";
		return line1 + line2;
	}
}
