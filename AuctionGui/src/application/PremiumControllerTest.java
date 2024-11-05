package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PremiumControllerTest {

	@DisplayName("Test PremiumController Constructor and getBuyerPremium")
	@Test
	void testConstructorAndGetPremium() {
		PremiumController premiumController = new PremiumController();
		double expectedOutput = 0.0;
		assertEquals(expectedOutput, premiumController.getBuyerPremium());
	}
	
	@DisplayName("Test setBuyerPremium")
	@Test
	void testSetPremium() {
		PremiumController premiumController = new PremiumController();
		premiumController.setBuyerPremium(0.1);
		double expectedOutput = 0.1;
		assertEquals(expectedOutput, premiumController.getBuyerPremium());
	}

}
