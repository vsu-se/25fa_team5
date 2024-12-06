package auction_system;

import java.util.ArrayList;

public class User {
	private String name;
	private int id;
	private ArrayList<Auction> wonAuctions = new ArrayList<>();
	private ArrayList<Auction> listedAuctions = new ArrayList<>();
	private ArrayList<Auction> bidOnAuctions = new ArrayList<>();

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(String name) {
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
		return line1;

	}
}