package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

	@DisplayName("Test Category Constructor and getName")
	@Test
	void testConstructorAndGetName() {
		Category category = new Category("Cards");
		String expectedOutput = "Cards";
		assertEquals(expectedOutput, category.getName());
	}
	
    @DisplayName("Test setName")
	@Test
	void testSetName() {
		Category category = new Category("Cards");
		category.setName("Books");
		String expectedOutput = "Books";
		assertEquals(expectedOutput, category.getName());
	}

}
