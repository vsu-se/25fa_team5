package Test;

import org.junit.jupiter.api.*;

import auction_system.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class AuctionTest {

    // Auction auction;
    // User user;
    // Item item;

    // @BeforeEach
    // void setUp() {

    //     ItemManager itemManager = new ItemManager();
    //     UserManager userManager = new UserManager();
    //     AuctionManager auctionManager = new AuctionManager();
    //     ItemController itemController = new ItemController(itemManager);
    //     UserController userController = new UserController(userManager);
    //     user = userController.createUser("Finch");
    //     item = itemController.createItem("Cool Hat", user);
    //     auction = new Auction(item, 50.0, 100.0);
    //     auctionManager.addAuction(auction);
        
    // }

    // @Test
    // void testAddBidAccepted() {
    // 	System.out.println("Testing AddBid for accept condition");
    //     auction.addBid(user, 60.0);
    //     assertEquals(60.0, auction.getCurrentBid());
    // }

    // @Test
    // void testAddBidRejected() {
    // 	System.out.println("Testing AddBid for reject condition");
    //     auction.addBid(user, 40.0);
    //     assertNotEquals(40.0, auction.getCurrentBid());
    // }

    // @Test
    // void testEndAuction() {
    // 	System.out.println("Testing endAuction");
    //     auction.endAuction();
    //     assertFalse(auction.getActive());
    // }

    // @Test
    // void testCheckDateAuctionEnded() {
    // 	System.out.println("Testing checkDate on an auction that has ended");
    //     auction.setEndDate(new Date(System.currentTimeMillis() - 1000)); // Set end date in the past
    //     auction.checkDate();
    //     assertFalse(auction.getActive());
    // }

    // @Test
    // void testCheckDateAuctionOngoing() {
    // 	System.out.println("Testing checkDate on an auction that is ongoing");
    //     auction.checkDate();
    //     assertTrue(auction.getActive());
    // }
}