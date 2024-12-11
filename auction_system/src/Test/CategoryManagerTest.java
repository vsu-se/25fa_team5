package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.Category;
import auction_system.CategoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryManagerTest {
    CategoryManager categoryManager;

    @BeforeEach
    void setUp() {
        categoryManager = new CategoryManager();
    }

    @Test
    void testAddCategories() {
        categoryManager.addCategory(new Category("Sports"));
        categoryManager.addCategory(new Category("Shoes"));
        categoryManager.addCategory(new Category("Coats"));
        String result = categoryManager.getCategories().toString();
        assertTrue(result.contains("Category: Sports"));
        assertTrue(result.contains("Category: Shoes"));
        assertTrue(result.contains("Category: Coats"));
    }

}
