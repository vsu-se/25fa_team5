package auction_system;

public class ItemTest {

	public static void main(String[] args) {
		// Test 1: toString 
		Item item1 = new Item(12345, "Comically Large Spoon", 30.75);
		System.out.println("Expected toString output: \nItem ID: 12345 \nItem Name: Comically Large Spoon \nAuction Start Date: Local Computer Time \nEnd Date: Local Computer Time + 30 Days \nItem Buy-It-Now Price: 30.75");
		System.out.println("Actual Output:");
		System.out.println(item1.toString());
		
		// Test 2: setStartingBid
		item1.setStartingBid(5.15);
		System.out.println("Expected Output: \n5.15");
		System.out.println("Actual Output: ");
		System.out.println(item1.getCurrentBid());
		
		// Test 3: setBIN fail condition
		System.out.println("Expected Output: \nThe Buy it Now price cannot be at or lower than $0.00");
		System.out.println("Actual Output: ");
		item1.setBIN(0.0);
		
		// Test 4: addBid
		System.out.println("Expected Output: \nBid Accepted \n8.2");
		System.out.println("Actual Output: ");
		item1.addBid(12346, 8.20);
		System.out.println(item1.getCurrentBid());

	}

}
