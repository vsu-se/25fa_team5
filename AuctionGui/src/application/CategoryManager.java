package application;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
	private List<Category> categories;
	
	public CategoryManager() {
        categories = new ArrayList<>();
    }
	
	public void addCategory(Category category) {
		categories.add(category);
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
/*	public void removeCategory(Category category) {
		categories.remove(category);
	}
	*/
}
