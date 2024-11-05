package auction_system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuctionTest {

	@Test
	void testAuctionItemDouble() {
		Item item = new Item(12345, "Hat");
		Auction auction = new Auction(item, 10.00);	
		assertEquals(12345, auction.getItem().getID(), "Failed to get itemID");
		System.out.println("Got itemID from Auction");
		assertEquals("Hat", auction.getItem().getName(), "Failed to get itemName");
		System.out.println("Got itemName from Auction");
		fail("Not yet implemented");
	}

	@Test
	void testAuctionItemDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	void testGetItem() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStartDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEndDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetbIN() {
		fail("Not yet implemented");
	}

	@Test
	void testSetEndDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentBid() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllBids() {
		fail("Not yet implemented");
	}
	@Test
	void testEndAuction() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckDate() {
		fail("Not yet implemented");
	}

	@Test
	void testAddBid() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
