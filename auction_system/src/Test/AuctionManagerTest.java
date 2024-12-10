package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
    User userOne;
    User userTwo;
    User userThree;
    Bid bidOne;
    Bid bidTwo;
    Bid bidThree;

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
        userOne = new User("Greg");
        userTwo = new User("May");
        userThree = new User("Kristen");
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



    @Test
    @DisplayName("testSortAuctionsSuccess")
    void testSortAuctionsSuccess() {
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        auctionManager.sortBySoonestEndingActiveAuctions();
        Auction soonestAuction = auctionManager.getAuctionFromActiveList(0);
        Auction nextSoonestAuction = auctionManager.getAuctionFromActiveList(1);
        Auction lastAuction = auctionManager.getAuctionFromActiveList(2);
        boolean auctionTwoIsSoonest = soonestAuction.equals(auctionTwo);
        boolean auctionThreeIsNextSoonest = nextSoonestAuction.equals(auctionThree);
        boolean auctionOneIsLast = lastAuction.equals(auctionOne);
        assertTrue(auctionTwoIsSoonest && auctionThreeIsNextSoonest && auctionOneIsLast);
    }

    @Test
    @DisplayName("testGetUserListedAuctionsSuccess")
    void testGetUserListedAuctionsSuccess() {
        auctionOne.setUser(userOne.getName());
        auctionTwo.setUser(userOne.getName());
        auctionThree.setUser(userOne.getName());
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        assertEquals(auctionManager.getAuctionList(), auctionManager.getUserListedAuctions(userOne.getName()));
    }

    @Test
    @DisplayName("testGetUsersBidOnAuctionsSuccess")
    void testGetUsersBidOnAuctionsSuccess() {
        bidOne = new Bid(1, 20, LocalDateTime.now(), userOne);
        auctionOne.addBid2(bidOne);
        bidTwo = new Bid(2, 20, LocalDateTime.now(), userOne);
        auctionTwo.addBid2(bidTwo);
        bidThree = new Bid(3, 20, LocalDateTime.now(), userOne);
        auctionThree.addBid2(bidThree);
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        assertEquals(auctionManager.getAuctionList(), auctionManager.getUserBidOnAuctions(userOne.getName()));
    }

    @Test
    @DisplayName("testGetUsersBidOnAuctionsWithMultipleUsers")
    void testGetUsersBidOnAuctionsWithMultipleUsers() {
        bidOne = new Bid(1, 20, LocalDateTime.now(), userOne);
        auctionOne.addBid2(bidOne);
        bidTwo = new Bid(2, 20, LocalDateTime.now(), userOne);
        auctionTwo.addBid2(bidTwo);
        bidThree = new Bid(3, 20, LocalDateTime.now(), userTwo);
        auctionThree.addBid2(bidThree);
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        ArrayList<Auction> userOneBidOnAuctions = auctionManager.getUserBidOnAuctions(userOne.getName());
        ArrayList<Auction> userTwoBidOnAuctions = auctionManager.getUserBidOnAuctions(userTwo.getName());
        boolean auctionOneIsEqual = userOneBidOnAuctions.get(0).toString().equals(auctionOne.toString());
        boolean auctionTwoIsEqual = userOneBidOnAuctions.get(1).toString().equals(auctionTwo.toString());
        boolean auctionThreeIsEqual = userTwoBidOnAuctions.get(0).toString().equals(auctionThree.toString());
        assertTrue(auctionOneIsEqual && auctionTwoIsEqual && auctionThreeIsEqual);
    }

}