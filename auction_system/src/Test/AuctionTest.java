package Test;

import auction_system.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    private Auction auction;
    private BidManager bidManager;
    private User user1;
    private User user2;
    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item(1, "Laptop");
        auction = new Auction(item, LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(9, 0), LocalTime.of(18, 0), 1000.0);
        bidManager = new BidManager();
        auction.setBidManager(bidManager);
        user1 = new User(1, "Alice");
        user2 = new User(2, "Bob");
    }

    @Test
    void testAddBid() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	LocalDateTime dateTime2 = dateTime.plusSeconds(10);
        Bid bid1 = new Bid(1, 500.0, dateTime, user1);
        Bid bid2 = new Bid(1, 700.0, dateTime2, user2);

        assertTrue(auction.addBid2(bid1), "First bid should be added successfully");
        assertTrue(auction.addBid2(bid2), "Second bid should be added successfully");
        assertFalse(auction.addBid2(bid1), "Duplicate bid should not be added");
    }

    @Test
    void testEquals() {
        Auction otherAuction = new Auction(new Item(1, "Laptop"), LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(9, 0), LocalTime.of(18, 0), 1000.0);
        assertTrue(auction.equals(otherAuction), "Auctions with the same item ID should be equal");

        Auction differentAuction = new Auction(new Item(2, "Phone"), LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(9, 0), LocalTime.of(18, 0), 500.0);
        assertFalse(auction.equals(differentAuction), "Auctions with different item IDs should not be equal");
    }

    @Test
    void testFindWinningBid() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	LocalDateTime dateTime2 = dateTime.plusSeconds(10);
        Bid bid1 = new Bid(1, 500.0, dateTime, user1);
        Bid bid2 = new Bid(1, 700.0, dateTime2, user2);

        auction.addBid2(bid1);
        auction.addBid2(bid2);

        BidManager manager = auction.getBidManager();

        manager.sortBidsByBidAmount();

        assertTrue(auction.findWinningBid(), "Highest bid should be the winning bid");
    }

    @Test
    void testGetWinningBid() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	LocalDateTime dateTime2 = dateTime.plusSeconds(10);
        Bid bid1 = new Bid(1, 500.0, dateTime, user1);
        Bid bid2 = new Bid(1, 700.0, dateTime2, user2);

        auction.addBid2(bid1);
        auction.addBid2(bid2);

        assertEquals(700.0, bidManager.getWinningBid(), "The current bid should be the highest bid");
    }
}