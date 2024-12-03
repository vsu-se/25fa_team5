package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import auction_system.PremiumController;

class PremiumControllerTest {
    private PremiumController controller;

    @BeforeEach
    public void setUp() {
        controller = new PremiumController();
    }

    @Test
    public void testInitialBuyerPremiumIsZero() {
        assertEquals(0.0, controller.getBuyerPremium(), "Initial premium should be 0.0");
    }

    @Test
    public void testSetValidBuyerPremium() {
        controller.setBuyerPremium("25.5");
        assertEquals(25.5, controller.getBuyerPremium(), "Premium should be set to 25.5");
    }

    @Test
    public void testSetBuyerPremiumAtLowerBoundary() {
        controller.setBuyerPremium("0.0");
        assertEquals(0.0, controller.getBuyerPremium(), "Premium should be set to 0.0");
    }

    @Test
    public void testSetBuyerPremiumAtUpperBoundary() {
        controller.setBuyerPremium("100.0");
        assertEquals(100.0, controller.getBuyerPremium(), "Premium should be set to 100.0");
    }

    @Test
    public void testSetBuyerPremiumBelowLowerBoundary() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setBuyerPremium("-1.0");
        });
        assertEquals("Premium must be between 0 and 100", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumAboveUpperBoundary() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setBuyerPremium("101.0");
        });
        assertEquals("Premium must be between 0 and 100", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumWithInvalidNumberFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setBuyerPremium("invalid");
        });
        assertEquals("Please enter a valid numeric value for the premium", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumWithEmptyString() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setBuyerPremium("");
        });
        assertEquals("Please enter a valid numeric value for the premium", exception.getMessage());
    }

}
