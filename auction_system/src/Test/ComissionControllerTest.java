package Test;

import static org.junit.jupiter.api.Assertions.*;
import auction_system.CommissionController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComissionControllerTest {

	@DisplayName("Test CommissionController Constructor and getSellerCommission")
	@Test
	void testConstructorAndGetCommission() {
		CommissionController controller = new CommissionController();
		double expectedOutput = 0.0;
		assertEquals(expectedOutput, controller.getSellerCommission());
	}
	
	@DisplayName("Test setSellerCommission")
	@Test
	void testSetCommission() {
		CommissionController controller = new CommissionController();
		controller.setSellerCommission(0.1);
		double expectedOutput = 0.1;
		assertEquals(expectedOutput, controller.getSellerCommission());
	}

}
