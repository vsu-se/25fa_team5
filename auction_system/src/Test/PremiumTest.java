package Test;

import static org.junit.jupiter.api.Assertions.*;
import auction_system.Premium;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PremiumTest {

	@DisplayName("Test Premium Constructor and getBuyerPremium")
	@Test
	void testConstructorAndGetPremium() {
        Premium premium = new Premium(0.1);
        double expectedOutput = 0.1;
        assertEquals(expectedOutput, premium.getBuyerPremium());
    }
		
	@DisplayName("Test setBuyerPremium")
	@Test
	void testSetPremium() {
		Premium premium = new Premium(0.1);
		premium.setBuyerPremium(0.2);
		double expectedOutput = 0.2;
		assertEquals(expectedOutput, premium.getBuyerPremium());
	}
}
