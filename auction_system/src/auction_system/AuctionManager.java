package auction_system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuctionManager {
    private ArrayList<Auction> auctionList = new ArrayList<>();
    private ArrayList<Auction> inactiveAuctionList = new ArrayList<>();
    private ArrayList<Auction> activeAuctionList = new ArrayList<>();
    private ScheduledExecutorService scheduler;

    public AuctionManager() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(this::checkAuctionStatus, 0, 1, TimeUnit.MINUTES);
    }

    public void addAuction(Auction auction) {
        // auctionList.add(auction);
        if((!auctionList.contains(auction)) && auction.getActive()) {
            auctionList.add(auction);
            activeAuctionList.add(auction);
        }
    }
    
    public void endAuction(Auction auction) {
    	activeAuctionList.remove(auction);
    	inactiveAuctionList.add(auction);
    	auction.endAuction();
    }

    private void checkAuctionStatus() {
        Date now = new Date();
        List<Auction> endedAuctions = new ArrayList<>();
        for(Auction auction : activeAuctionList){
            if(auction.getEndDate().before(now)){
                endedAuctions.add(auction);
            }
        }
        for(Auction auction : endedAuctions) {
            endAuction(auction);
        }
    }

    public void shutDownScheduler() {
        if(scheduler != null && !scheduler.isShutdown()){
            scheduler.shutdown();
        }
    }

    public boolean containsAuction(Auction auction) {
        return auctionList.contains(auction);
    }

    @Override
    public String toString() {
        String str = "";
        for(Auction a : auctionList) {
            str += a.toString() + "\n";
        }
        return str;
    }
}
