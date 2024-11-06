package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
	private CategoryManager categoryManager;
	
	public CategoryController(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
	
	public void addCategory(String categoryName) {
		Category category = new Category(categoryName);
		categoryManager.addCategory(category);
	}

	public List<String> getCategories() {
		List<String> categoryNames = new ArrayList<>();
		for (Category category : categoryManager.getCategories()) {
			categoryNames.add(category.getName());
		}
		return categoryNames;
	}

/*	public void removeCategory(String categoryName) {
		for (Category category : categoryManager.getCategories()) {
			if (category.getName().equals(categoryName)) {
				categoryManager.removeCategory(category);
				break;
			}
		}
	}
	*/
}
