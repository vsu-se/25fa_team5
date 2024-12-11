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
        ArrayList<Auction> expected = new ArrayList<>();
        expected.add(auctionTwo);
        expected.add(auctionThree);
        expected.add(auctionOne);
        ArrayList<Auction> actual = auctionManager.getUserListedAuctions(userOne.getName());
        assertEquals(expected, actual);
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
        assertEquals(auctionManager.getActiveList(), auctionManager.getUserBidOnAuctions(userOne.getName()));
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
        boolean auctionOneIsEqual = userOneBidOnAuctions.get(1).toString().equals(auctionOne.toString());
        boolean auctionTwoIsEqual = userOneBidOnAuctions.get(0).toString().equals(auctionTwo.toString());
        boolean auctionThreeIsEqual = userTwoBidOnAuctions.get(0).toString().equals(auctionThree.toString());
        assertTrue(auctionOneIsEqual && auctionTwoIsEqual && auctionThreeIsEqual);
    }

    @Test
    @DisplayName("testAuctionAddedToInactiveWhenConcluded")
    void testAuctionAddedToInactiveWhenConcluded() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(),50);
        auctionManager.addAuction(auctionFive);
        assertEquals(auctionFive, auctionManager.getInactiveAuctionAtIndex(0));
    }

    @Test
    @DisplayName("testAuctionRemovedFromActiveWhenConcluded")
    void testAuctionRemovedFromActiveWhenConcluded() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        auctionManager.addAuction(auctionFive);
        assertTrue(auctionManager.activeListIsEmpty());
    }

    @Test
    @DisplayName("testGetUserWonAuctionsSuccess")
    void testGetUserWonAuctionsSuccess() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        Auction auctionSix = new Auction(itemTwo, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        Auction auctionSeven = new Auction(itemThree, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        Bid bidOne = new Bid(1, 20, LocalDateTime.now(), userOne);
        auctionFive.addBid2(bidOne);
        Bid bidTwo = new Bid(2, 20, LocalDateTime.now(), userOne);
        auctionSix.addBid2(bidTwo);
        Bid bidThree = new Bid(3, 20, LocalDateTime.now(), userOne);
        auctionSeven.addBid2(bidThree);
        auctionManager.addAuction(auctionFive);
        auctionManager.addAuction(auctionSix);
        auctionManager.addAuction(auctionSeven);
        assertEquals(auctionManager.getInactiveList(), auctionManager.getUserWonAuctions(userOne.getName()));
    }

    @Test
    @DisplayName("testGetMultipleUserWonAuctionsSuccess")
    void testGetMultipleUserWonAuctionsSuccess() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        Auction auctionSix = new Auction(itemTwo, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        Auction auctionSeven = new Auction(itemThree, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        Bid bidOne = new Bid(1, 20, LocalDateTime.now(), userOne);
        auctionFive.addBid2(bidOne);
        Bid bidTwo = new Bid(2, 20, LocalDateTime.now(), userTwo);
        auctionSix.addBid2(bidTwo);
        Bid bidThree = new Bid(3, 20, LocalDateTime.now(), userOne);
        auctionSeven.addBid2(bidThree);
        auctionManager.addAuction(auctionFive);
        auctionManager.addAuction(auctionSix);
        auctionManager.addAuction(auctionSeven);
        ArrayList<Auction> expectedUserOne = new ArrayList<>();
        expectedUserOne.add(auctionFive);
        expectedUserOne.add(auctionSeven);
        ArrayList<Auction> expectedUserTwo = new ArrayList<>();
        expectedUserTwo.add(auctionSix);
        assertEquals(expectedUserOne, auctionManager.getUserWonAuctions(userOne.getName()));
        assertEquals(expectedUserTwo, auctionManager.getUserWonAuctions(userTwo.getName()));
    }

    @Test
    @DisplayName("testGetUserWonAuctionsWithBidsFromDifferentUsers")
    void testGetUserWonAuctionsWithBidsFromDifferentUsers() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        Bid bidOne = new Bid(1, 20, LocalDateTime.now(), userOne);
        auctionFive.addBid2(bidOne);
        Bid bidTwo = new Bid(1, 50, LocalDateTime.now(), userTwo);
        auctionFive.addBid2(bidTwo);
        Bid bidThree = new Bid(1, 30, LocalDateTime.now(), userThree);
        auctionFive.addBid2(bidThree);
        auctionManager.addAuction(auctionFive);
        assertFalse(auctionManager.getUserWonAuctions(userOne.getName()).contains(auctionFive));
        assertFalse(auctionManager.getUserWonAuctions(userThree.getName()).contains(auctionFive));
        assertTrue(auctionManager.getUserWonAuctions(userTwo.getName()).contains(auctionFive));
    }

    @Test
    @DisplayName("testGetUserSoldAuctionsSuccess")
    void testGetUserSoldAuctionsSuccess() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        auctionFive.setUser(userOne.getName());
        Bid bidOne = new Bid(1, 20, LocalDateTime.now(), userTwo);
        auctionFive.addBid2(bidOne);
        auctionManager.addAuction(auctionFive);
        assertEquals(auctionManager.getInactiveList(), auctionManager.getUserSoldAuctions(userOne.getName()));
    }

    @Test
    @DisplayName("testGetUserSoldMultipleAuctions")
    void testGetUserSoldMultipleAuctions() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        auctionFive.setUser(userOne.getName());
        Auction auctionSix = new Auction(itemTwo, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        auctionSix.setUser(userOne.getName());
        Auction auctionSeven = new Auction(itemThree, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        auctionSeven.setUser(userOne.getName());
        Bid bidOne = new Bid(1, 20, LocalDateTime.now(), userTwo);
        Bid bidTwo = new Bid(2, 20, LocalDateTime.now(), userThree);
        Bid bidThree = new Bid(3, 20, LocalDateTime.now(), new User("Layton"));
        auctionFive.addBid2(bidOne);
        auctionSix.addBid2(bidTwo);
        auctionSeven.addBid2(bidThree);
        auctionManager.addAuction(auctionFive);
        auctionManager.addAuction(auctionSix);
        auctionManager.addAuction(auctionSeven);
        assertEquals(auctionManager.getInactiveList(), auctionManager.getUserSoldAuctions(userOne.getName()));
    }

    @Test
    @DisplayName("testCheckDatesActiveAndInactiveList")
    void testCheckDatesActiveAndInactiveList() {
        Auction auctionFive = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 20);
        Auction auctionSix = new Auction(itemTwo, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 20);
        auctionManager.addAuction(auctionFive);
        auctionManager.addAuction(auctionSix);
        auctionManager.checkDates();
        assertFalse(auctionManager.getActiveList().contains(auctionFive));
        assertFalse(auctionManager.getActiveList().contains(auctionSix));
        assertTrue(auctionManager.getInactiveList().contains(auctionFive));
        assertTrue(auctionManager.getInactiveList().contains(auctionSix));
    }

}