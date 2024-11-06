package auction_system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class AuctionTest {
	Item item;
	Auction auction;
	Date defEndDate;
	Date defStartDate;
	double bIN;
	
	@BeforeEach
	void init() {
		item = new Item(12345, "Hat");
		bIN = 25.99;
		auction = new Auction(item, 10.25, bIN);
		defEndDate = auction.getEndDate();
		defStartDate = auction.getStartDate();
	}

	@AfterEach
	void reset() {
		item.setID(12345);
		item.setName("Hat");
		auction.setbIN(25.99);
		auction.bids.clear();
		auction.addBid(item.getID(), 10.25);
		auction.setEndDate(defEndDate);
	}
	
	@Test
	void testGetItem() {
		assertEquals(item, auction.getItem(), "getItem() did not return the expected item");
	}

	@Test
	void testGetStartDate() {
		assertEquals(defStartDate, auction.getStartDate(), "getStartDate() did not return the expected date");
	}

	@Test
	void testGetEndDate() {
		assertEquals(defEndDate, auction.getEndDate(), "getEndDate() did not return the expected date");
	}

	@Test
	void testGetbIN() {
		assertEquals(bIN, auction.getbIN(), "getbIN() did not return the expected Buy-it-Now price");
	}

	@Test
	void testSetEndDate() {
		Date newEndDate = new Date(defEndDate.getTime() + 86400000); //Adds one day to the end date
		auction.setEndDate(newEndDate);
		assertEquals(newEndDate, auction.getEndDate(), "setEndDate() did not update the end date properly");
	}

	@Test
	void testGetCurrentBid() {
		assertEquals(10.25, auction.getCurrentBid(), "getCurrentBid() did not return the expected bid value");
	}

	@Test
	void testGetAllBids() {
		auction.addBid(12346, 15.75);
		auction.getAllBids();
	}
	@Test
	void testEndAuction() {
		auction.endAuction(item);
		assertFalse(auction.getIsActive(), "endAuction() did not set the auction as inactive");
	}

	@Test
	void testCheckDate() {
		auction.checkDate();
	}

	@Test
	void testAddBidSuccess() {
		auction.addBid(12346, 15.75);
		assertEquals(15.75, auction.getCurrentBid(), "addBid() did not accept a valid bid");
	}
	
	@Test
	void testAddBidFailure() {
		auction.addBid(12346, 5.00);
		assertEquals(10.25, auction.getCurrentBid(), "addBid() accepted a bid lower than the current bid");
	}

	@Test
	void testToString() {
		String output = auction.toString();
		assertTrue(output.contains("Current and Previous bids:"), "toString() output is not as expected");
	}

}
