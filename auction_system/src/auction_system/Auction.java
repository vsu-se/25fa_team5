package auction_system;
public class Auction {
    public String placeBid (User user, Item item, double bid) {
        if (bid > item.getCurrentBid()){
            item.addBid(user.getID(), bid);
        }
    }
}
