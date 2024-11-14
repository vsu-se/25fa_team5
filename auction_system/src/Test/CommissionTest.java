package Test;

import static org.junit.jupiter.api.Assertions.*;
import auction_system.Commission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommissionTest {

	@DisplayName("Test Commission Constructor and getSellerCommission")
	@Test
	void testConstructorAndGetCommission() {
        Commission commission = new Commission(0.1);
        double expectedOutput = 0.1;
        assertEquals(expectedOutput, commission.getSellerCommission());
    }
    
	@DisplayName("Test setSellerCommission")
	@Test
	void testSetCommission() {
		Commission commission = new Commission(0.1);
		commission.setSellerCommission(0.2);
		double expectedOutput = 0.2;
		assertEquals(expectedOutput, commission.getSellerCommission());
	}
}
