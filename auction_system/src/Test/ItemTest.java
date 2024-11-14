package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemTest {
	private Item item;
	
	@BeforeEach
	public void init() {
	item = new Item(12345, "Hat");
	}
	
	@AfterEach
	public void reset() {
		item.setName("Hat");
		item.setID(12345);
	}
	
//	@Test
//	void testItem() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetID() {
		assertEquals(12345, item.getID(), "getID() returns an incorrect value\n");
	}

	@Test
	void testGetName() {
		assertEquals("Hat", item.getName(), "getName() returns an incorrect value\n");
	}

	@Test
	void testSetName() {
		item.setName("Trilby");
		assertEquals("Trilby", item.getName(), "setName() does not change the name properly\n");
	}

	@Test
	void testSetID() {
		item.setID(99999);
		assertEquals(99999, item.getID(), "setID() does not change the ID properly\n");
	}

	@Test
	void testToString() {
		String expected = "Item ID: 12345\nItem Name: Hat\n";
		assertEquals(expected, item.toString(), "toString() does not return the proper string");
		
	}

}
