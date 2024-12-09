package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.Auction;
import auction_system.AuctionManager;
import auction_system.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class AuctionManagerTest {
    AuctionManager auctionManager;
    Item itemOne;
    Auction auctionOne;
    Item itemTwo;
    Auction auctionTwo;
    Item itemThree;
    Auction auctionThree;
    Item itemFour;
    Auction auctionFour;

    @BeforeEach
    void setUp() {
        auctionManager = new AuctionManager();
        itemOne = new Item(1, "itemOne");
        auctionOne = new Auction(itemOne, LocalDate.now(), LocalDate.parse("2028-01-01"), LocalTime.now(), LocalTime.now(), 50);
        itemTwo = new Item(2, "itemTwo");
        auctionTwo = new Auction(itemTwo, LocalDate.now(), LocalDate.parse("2025-01-01"), LocalTime.now(), LocalTime.now(), 50);
        itemThree = new Item(3, "itemThree");
        auctionThree = new Auction(itemThree, LocalDate.now(), LocalDate.parse("2027-01-01"), LocalTime.now(), LocalTime.now(), 50);
        itemFour = new Item(1, "itemFour");
        auctionFour = new Auction(itemFour, LocalDate.now(), LocalDate.parse("2028-01-01"), LocalTime.now(), LocalTime.now(), 50);
    }

    @Test
    @DisplayName("testAddAuctionSuccess")
    void testAddAuctionSuccess() {
        auctionManager.addAuction(auctionOne);
        assertTrue(auctionManager.containsAuction(auctionOne));
    }

    @Test
    @DisplayName("testDuplicateAuctionNotAdded")
    void testDuplicateAuctionNotAdded() {
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionFour);
        assertEquals(1, auctionManager.getAuctionListLength());
    }

//    @Test
//    @DisplayName("testSortAuctionsSuccess")
//    void testSortAuctionsSuccess() {
//        auctionManager.addAuction(auctionOne);
//        auctionManager.addAuction(auctionTwo);
//        auctionManager.addAuction(auctionThree);
//        auctionManager.sortBySoonestEndingActiveAuctions();
//        Auction soonestAuction = auctionManager.getAuctionFromAuctionList(0);
//        Auction nextSoonestAuction = auctionManager.getAuctionFromAuctionList(1);
//        Auction lastAuction = auctionManager.getAuctionFromAuctionList(2);
//        boolean auctionTwoIsSoonest = soonestAuction.equals(auctionTwo);
//        boolean auctionThreeIsNextSoonest = nextSoonestAuction.equals(auctionThree);
//        boolean auctionOneIsLast = lastAuction.equals(auctionOne);
//        assertTrue(auctionTwoIsSoonest && auctionThreeIsNextSoonest && auctionOneIsLast);
//    }

}