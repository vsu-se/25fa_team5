package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.CommissionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommissionControllerTest {
    private CommissionController controller;

    @BeforeEach
    public void setUp() {
        controller = new CommissionController();
    }

    @Test
    public void testInitialBuyerPremiumIsZero() {
        assertEquals(0.0, controller.getSellerCommission(), "Initial commission should be 0.0");
    }

    @Test
    public void testSetValidBuyerPremium() {
        controller.setSellerCommission("25.5");
        assertEquals(25.5, controller.getSellerCommission(), "Commission should be set to 25.5");
    }

    @Test
    public void testSetBuyerPremiumAtLowerBoundary() {
        controller.setSellerCommission("0.0");
        assertEquals(0.0, controller.getSellerCommission(), "Commission should be set to 0.0");
    }

    @Test
    public void testSetBuyerPremiumAtUpperBoundary() {
        controller.setSellerCommission("100.0");
        assertEquals(100.0, controller.getSellerCommission(), "Commission should be set to 100.0");
    }

    @Test
    public void testSetBuyerPremiumBelowLowerBoundary() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setSellerCommission("-1.0");
        });
        assertEquals("Commission must be between 0 and 100", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumAboveUpperBoundary() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setSellerCommission("101.0");
        });
        assertEquals("Commission must be between 0 and 100", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumWithInvalidNumberFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setSellerCommission("invalid");
        });
        assertEquals("Please enter a valid numeric value for the commission", exception.getMessage());
    }

    @Test
    public void testSetBuyerPremiumWithEmptyString() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setSellerCommission("");
        });
        assertEquals("Please enter a valid numeric value for the commission", exception.getMessage());
    }
}
