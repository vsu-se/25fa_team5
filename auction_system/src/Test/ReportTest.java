package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReportTest {
    Report report;
    AuctionManager auctionManager;
    double premium;
    double commission;
    Item itemOne;
    Auction auctionOne;
    Item itemTwo;
    Auction auctionTwo;
    Item itemThree;
    Auction auctionThree;
    Bid bidOne;
    Bid bidTwo;
    Bid bidThree;
    Bid bidFour;
    User user;
    User otherUser;

    @BeforeEach
    void setUp() {
        report = new Report();
        auctionManager = new AuctionManager();
        itemOne = new Item(1, "one");
        auctionOne = new Auction(itemOne, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        user = new User("Jared");
        otherUser = new User("Reese");
        bidOne = new Bid(1, 20, LocalDateTime.now(), user);
        itemTwo = new Item(2, "two");
        auctionTwo = new Auction(itemTwo, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        bidTwo = new Bid(2, 20, LocalDateTime.now(), user);
        itemThree = new Item(3, "three");
        auctionThree = new Auction(itemThree, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 50);
        bidThree = new Bid(3, 20, LocalDateTime.now(), user);
        bidFour = new Bid(3, 20, LocalDateTime.now(), otherUser);
    }

    @Test
    @DisplayName("testGenerateBuyersReport")
    void testGenerateBuyersReport() {
        auctionOne.addBid(bidOne);
        auctionManager.addAuction(auctionOne);
        premium = 15;
        String result = report.generateBuyerReport(auctionManager, user.getName(), premium);
        assertTrue(result.contains("Total amount bought: $32.19"));
        assertTrue(result.contains("Total buyer's premiums paid: $4.20"));
    }

    @Test
    @DisplayName("testGenerateBuyersReportWithMultipleItems")
    void testGenerateBuyersReportWithMultipleItems() {
        auctionOne.addBid(bidOne);
        auctionTwo.addBid(bidTwo);
        auctionThree.addBid(bidThree);
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        premium = 15;
        String result = report.generateBuyerReport(auctionManager, user.getName(), premium);
        assertTrue(result.contains("Total amount bought: $96.57"));
        assertTrue(result.contains("Total buyer's premiums paid: $12.60"));
        assertTrue(result.contains("Total shipping cost paid: $23.97"));
    }

    @Test
    @DisplayName("testGenerateBuyersReportWithZeroPremium")
    void testGenerateBuyersReportWithZeroPremium() {
        auctionOne.addBid(bidOne);
        auctionManager.addAuction(auctionOne);
        premium = 0;
        String result = report.generateBuyerReport(auctionManager, user.getName(), premium);
        assertTrue(result.contains("Total amount bought: $27.99"));
        assertTrue(result.contains("Total buyer's premiums paid: $0.00"));
    }

    @Test
    @DisplayName("testGenerateSellerReport")
    void testGenerateSellerReport() {
        auctionThree.setUser(user.getName());
        auctionThree.addBid(bidFour);
        auctionManager.addAuction(auctionThree);
        commission = 15;
        String result = report.generateSellerReport(auctionManager, user.getName(), commission);
        assertTrue(result.contains("Total winning bids: $20.00"));
        assertTrue(result.contains("Total profits: $15.80"));
    }

    @Test
    @DisplayName("testGenerateSellerReportWithZeroCommission")
    void testGenerateSellerReportWithZeroCommission() {
        auctionThree.setUser(user.getName());
        auctionThree.addBid(bidFour);
        auctionManager.addAuction(auctionThree);
        commission = 0;
        String result = report.generateSellerReport(auctionManager, user.getName(), commission);
        assertTrue(result.contains("Total winning bids: $20.00"));
        assertTrue(result.contains("Total profits: $20.00"));
    }

    @Test
    @DisplayName("testGenerateSellerReportWithMultipleItems")
    void testGenerateSellerReportWithMultipleItems() {
        auctionOne.setUser(otherUser.getName());
        auctionTwo.setUser(otherUser.getName());
        auctionThree.setUser(otherUser.getName());
        auctionOne.addBid(bidOne);
        auctionTwo.addBid(bidTwo);
        auctionThree.addBid(bidThree);
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionTwo);
        auctionManager.addAuction(auctionThree);
        commission = 15;
        String result = report.generateSellerReport(auctionManager, otherUser.getName(), commission);
        assertTrue(result.contains("Total winning bids: $60.00"));
        assertTrue(result.contains("Total profits: $47.40"));
    }
}
