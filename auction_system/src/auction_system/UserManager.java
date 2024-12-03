package auction_system;

import java.util.Random;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class UserManager {
    ArrayList<User> userList = new ArrayList<>();
    final int idLength = 6;

    public void addUser(User user){
        if(!userList.contains(user)) {
            userList.add(user);
        }
    }

    public int generateUserID (){
        StringBuilder id = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < idLength; i++){
            id.append(rand.nextInt(10));
        }
        String idS = id.toString();
        int idI = Integer.parseInt(idS);
        if(containsID(idI)){
            idI = generateUserID();
        }
        return idI;
    }

    public boolean containsID (int id){
        for(int i = 0; i<userList.size(); i++){
            if(userList.get(i).getID() == id){
                return true;
            }
        }
        return false;
    }
}