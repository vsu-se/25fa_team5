package Test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class AuctionTest {

    private Auction auction;
    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        user = new User(12345, "Finch");
        item = new Item(11111, "Cool Hat", user);
        auction = new Auction(item, 50.0, 100.0);
    }

    @Test
    void testAddBidAccepted() {
        auction.addBid(user, 60.0);
        assertEquals(60.0, auction.getCurrentBid());
    }

    @Test
    void testAddBidRejected() {
        auction.addBid(user, 40.0);
        assertNotEquals(40.0, auction.getCurrentBid());
    }

    @Test
    void testEndAuction() {
        auction.endAuction();
        assertFalse(auction.getActive());
    }

    @Test
    void testCheckDateAuctionEnded() {
        auction.setEndDate(new Date(System.currentTimeMillis() - 1000)); // Set end date in the past
        auction.checkDate();
        assertFalse(auction.getActive());
    }

    @Test
    void testCheckDateAuctionOngoing() {
        auction.checkDate();
        assertTrue(auction.getActive());
    }
}
