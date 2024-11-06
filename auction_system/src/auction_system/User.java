package auction_system;

public class User{
    private int id;

    public User(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }
    
    public void setID(int id) {
    	this.id = id;
    }
}