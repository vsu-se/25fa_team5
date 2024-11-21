package auction_system;

import java.util.Random;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class ItemManager {
    ArrayList<Item> itemList = new ArrayList<>();
    final int idLength = 5;

    public void addItem(Item item){
        if(!itemList.contains(item)) {
            itemList.add(item);
        }
    }

    public int generateItemID (){
        StringBuilder id = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < idLength; i++){
            id.append(rand.nextInt(10));
        }
        String idS = id.toString();
        int idI = Integer.parseInt(idS);
        if(containsID(idI)){
            idI = generateItemID();
        }
        return idI;
    }

    public boolean containsID (int id){
        for(int i = 0; i<itemList.size(); i++){
            if(itemList.get(i).getID() == id){
                return true;
            }
        }
        return false;
    }
}