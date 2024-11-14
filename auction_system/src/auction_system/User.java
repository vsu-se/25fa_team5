package auction_system;

public class User {
	private int id;
	private String name;

	public User(int id, String name) {
		this.id = id;
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

//    public int getIDGivenName(String userName) {
//    	int id = 0;
//        if(userName.equals(getName())){
//            id = this.id;
//        }
//		return id;
//    }
//
//    public String getNameGivenID(int id) {
//    	String name = "Null";
//        if(id == getID()) {
//            name = this.name;
//        }
//        return name;
//    }
}