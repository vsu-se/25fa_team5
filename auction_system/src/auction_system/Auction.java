package auction_system;

import java.util.Calendar;
import java.util.Date;

public class Auction {
	
//    public String placeBid (User user, Item item, double bid) {
//        if (bid > item.getCurrentBid()){
//            item.addBid(user.getID(), bid);
//        }
//    }
	
//	private boolean isActive (Item item) { Gonna need a comparator
//		if(item.getEndDate() > c.getTime()) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	public Date endAuction(Item item) {
    	Calendar d = Calendar.getInstance();
    	item.setStartDate(item.getEndDate());
    	return d.getTime();
    }
}
