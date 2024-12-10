package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.AuctionManager;
import auction_system.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    User userOne;
    User userTwo;
    User userThree;

    @BeforeEach
    void setUp() {
        userOne = new User("Avery");
        userTwo = new User("Bob");
        userThree = new User("Avery");
    }

    @Test
    @DisplayName("testTwoUsersAreEqual")
    void testTwoUsersAreEqual() {
        assertEquals(userOne, userThree);
    }

    @Test
    @DisplayName("testTwoUsersAreNotEqual")
    void testTwoUsersAreNotEqual() {
        assertNotEquals(userOne, userTwo);
    }

    @Test
    @DisplayName("testUserNotEqualToNonUserObject")
    void testUserNotEqualToNonUserObject() {
        AuctionManager auctionManager = new AuctionManager();
        assertNotEquals(userOne, auctionManager);
    }
}