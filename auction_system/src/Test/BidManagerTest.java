package Test;

import auction_system.BidManager;
import auction_system.Bid;
import auction_system.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BidManagerTest {
private BidManager bidManager;
    private Bid bid1, bid2, bid3;
    private User user1, user2, user3;
    private BidManager auction;

    @BeforeEach
    void setUp() {
        bidManager = new BidManager();
        user1 = new User("User1");
        user2 = new User("User2");
        user3 = new User("User3");
        bid1 = new Bid(100, user1);
        bid2 = new Bid(200, user2);
        bid3 = new Bid(150, user3);
    }

    @Test
    void testAddBid() {
        bidManager.addBid(bid1);
        assertTrue(bidManager.containsBid(bid1), "Bid1 should be added to the bid list");
        bidManager.addBid(bid2);
        bidManager.addBid(bid3);
        assertTrue(bidManager.containsBid(bid2), "Bid2 should be added to the bid list");
        assertTrue(bidManager.containsBid(bid3), "Bid3 should be added to the bid list");
    }

    @Test
    void testContainsBid() {
        bidManager.addBid(bid1);
        assertTrue(bidManager.containsBid(bid1), "ContainsBid should return true for added bid");
        assertFalse(bidManager.containsBid(bid2), "ContainsBid should return false for non-added bid");
    }

    @Test
    void testSortBidsByBidAmount() {
        bidManager.addBid(bid1);
        bidManager.addBid(bid2);
        bidManager.addBid(bid3);

        bidManager.sortBidsByBidAmount();
        ArrayList<Bid> sortedBids = new ArrayList<>(bidManager.bidList);

        assertEquals(bid2, sortedBids.get(0), "Bid2 should be the highest bid");
        assertEquals(bid3, sortedBids.get(1), "Bid3 should be the second highest bid");
        assertEquals(bid1, sortedBids.get(2), "Bid1 should be the lowest bid");
    }

    @Test
    void testGetWinningBid() {
        bidManager.addBid(bid1);
        bidManager.addBid(bid2);
        bidManager.addBid(bid3);

        assertEquals(200, bidManager.getWinningBid(), "Winning bid value should be 200");
    }

    @Test
    void testCheckIfEmpty() {
        assertTrue(bidManager.checkIfEmpty(), "Bid list should be empty initially");
        bidManager.addBid(bid1);
        assertFalse(bidManager.checkIfEmpty(), "Bid list should not be empty after adding a bid");
    }

    @Test
    void testCheckIfUserHasBid() {
        bidManager.addBid(bid1);
        assertTrue(bidManager.checkIfUserHasBid(user1), "User1 should have a bid");
        assertFalse(bidManager.checkIfUserHasBid(user2), "User2 should not have a bid");
    }

    @Test
    void testGetUserBid() {
        bidManager.addBid(bid1);
        assertEquals(bid1, bidManager.getUserBid("User1"), "Should return the correct bid for User1");
        assertNull(bidManager.getUserBid("User2"), "Should return null for a user with no bid");
    }

    @Test
    void testGetIndexOfUserOldBid() {
        bidManager.addBid(bid1);
        assertEquals(0, bidManager.getIndexOfUserOldBid(bid1), "Should return the correct index for User1's bid");
        assertEquals(-1, bidManager.getIndexOfUserOldBid(bid2), "Should return -1 for a non-existent bid");
    }

    @Test
    void testToString() {
        bidManager.addBid(bid1);
        bidManager.addBid(bid2);

        String expected = bid2.toString() + bid1.toString();
        assertEquals(expected, bidManager.toString(), "ToString should return the concatenated string of all bids");
    }
}
