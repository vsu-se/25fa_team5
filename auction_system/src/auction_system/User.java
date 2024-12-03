package auction_system;

public class User {
	private int id;
	private String name;

	public User(int id, String name) { // Constructor needs to be reworked to auto generate id to a specified format that checks for duplicates
		this.id = id;
		this.name = name;
		// After the user is constructed, it will need to be added to the userList in UserManager
	}

	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}