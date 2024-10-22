package auction;
public class Auction {
    public String placeBid (user user, item item, double bid) {
        if (bid > item.getCurrentBid()){
            item.addBid(item.getID(), bid);
        }
    }
}
