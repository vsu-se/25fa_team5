package auction_system;

public class User {
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof User user) {
			return this.name.equals(user.getName());
		}
		else {
			return false;
		}
	}

	@Override
	public String toString(){
		String line1 = "Username: " + name;
		return line1;
	}
}