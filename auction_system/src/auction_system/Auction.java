package auction_system;
public class Auction {
    public String placeBid (user user, Item item, double bid) {
        if (bid > item.getCurrentBid()){
            item.addBid(item.getID(), bid);
        }
    }
}
