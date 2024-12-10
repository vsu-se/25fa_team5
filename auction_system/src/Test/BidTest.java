package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.Bid;
import auction_system.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BidTest {
    Bid bid1;
    Bid bid2;
    Bid bid3;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        user1 = new User("User1");
        user2 = new User("User2");
        user3 = new User("User3");
        bid1 = new Bid(1, 20, LocalDateTime.now(), user1);
        bid2 = new Bid(1, 20, LocalDateTime.now(), user2);
        bid3 = new Bid(1, 40, LocalDateTime.now(), user3);
    }

    @Test
    @DisplayName("testTwoBidsAreEqual")
    void testTwoBidsAreEqual() {
        assertEquals(bid1, bid2);
    }

    @Test
    @DisplayName("testTwoBidsAreNotEqual")
    void testTwoBidsAreNotEqual() {
        assertNotEquals(bid1, bid3);
    }

    @Test
    @DisplayName("testBidNotEqualToNonBidObject")
    void testBidNotEqualToNonBidObject() {
        assertNotEquals(user1, bid1);
    }
}
