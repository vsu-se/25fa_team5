package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	
	User user;
	
	@BeforeEach
	public void init() {
		user = new User(12345);
	}
	
	@AfterEach
	public void reset() {
		user.setID(12345);
	}

	@Test
	void testGetID() {
		assertEquals(12345, user.getID(), "getID() does not return the expected value");
	}
	
	@Test
	void testSetID() {
		user.setID(12346);
		assertEquals(12346, user.getID(), "setID() does not set ID to the expected value");
	}

}
