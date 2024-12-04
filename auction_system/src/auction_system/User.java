package auction_system;

public class User {
	private String name;
	private int id;

	public User(int id, String name) {
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
		return id;
	}

	public void setID() {
		this.id = id;
	}

	@Override
	public String toString(){
		String line1 = "Username: " + name;
	}
}