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
        user1 = new User("Alice");
        user2 = new User("Bob");
    }

    @Test
    void testAddBid() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	LocalDateTime dateTime2 = dateTime.plusSeconds(10);
        Bid bid1 = new Bid(1, 500.0, dateTime, user1);
        Bid bid2 = new Bid(1, 700.0, dateTime2, user2);

        assertTrue(auction.addBid(bid1), "First bid should be added successfully");
        assertTrue(auction.addBid(bid2), "Second bid should be added successfully");
        assertFalse(auction.addBid(bid1), "Duplicate bid should not be added");
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

        auction.addBid(bid1);
        auction.addBid(bid2);

        assertTrue(auction.findWinningBid(), "Highest bid should be the winning bid");
    }

    @Test
    void testGetWinningBid() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	LocalDateTime dateTime2 = dateTime.plusSeconds(10);
        Bid bid1 = new Bid(1, 500.0, dateTime, user1);
        Bid bid2 = new Bid(1, 700.0, dateTime2, user2);

        auction.addBid(bid1);
        auction.addBid(bid2);
        auction.findWinningBid();

        assertEquals(700.0, auction.getWinningBid().getBidValue(), "The current bid should be the highest bid");
    }

    @Test
    void testCheckEndDateTimeIsBeforeNowIfNoTimeAndHasBid() {
        auction = new Auction(item, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 30);
        Bid bid = new Bid(1, 200, LocalDateTime.now(), user1);
        auction.addBid(bid);
        auction.checkEndDateTimeIsBeforeNow();
        assertTrue(auction.isBought());
        assertFalse(auction.getActive());
    }

    @Test
    void testCheckAuctionIsActiveAndNotBoughtIfStillHasTime() {
        Bid bid = new Bid(1, 200, LocalDateTime.now(), user1);
        auction.addBid(bid);
        auction.checkEndDateTimeIsBeforeNow();
        assertFalse(auction.isBought());
        assertTrue(auction.getActive());
    }

    @Test
    void testCheckAuctionIsInactiveAndNotBoughtIfNoTimeAndNoBids() {
        auction = new Auction(item, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        auction.checkEndDateTimeIsBeforeNow();
        assertFalse(auction.isBought());
        assertFalse(auction.getActive());
    }

    @Test
    void testCalculateTimeRemainingWithActiveAuction() {
        String result = auction.calculateTimeRemaining();
        assertTrue(result.contains("7 days"));
    }

    @Test
    void testCalculateTimeRemainingWithEndedAuction() {
        auction = new Auction(item, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-02"), LocalTime.now(), LocalTime.now(), 40);
        String result = auction.calculateTimeRemaining();
        assertTrue(result.contains("none"));
    }

    @Test
    void testGetUserBidOnAuction() {
        Bid bid = new Bid(1, 100, LocalDateTime.now(), user1);
        auction.addBid(bid);
        assertEquals(bid, auction.getUserBid(user1.getName()));
    }

    @Test
    void testShowMyBidsData() {
        Bid bid = new Bid(1, 100, LocalDateTime.now(), user1);
        auction.addBid(bid);
        String result = auction.showMyBidsData(user1.getName());
        assertTrue(result.contains("7 days"));
        assertTrue(result.contains("Winning bid: $100.00"));
        assertTrue(result.contains(bid.toString()));
        assertTrue(result.contains("BIN: $1000"));
    }
}