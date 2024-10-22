package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private ObservableList<String> categories;
	private CategoryController categoryController;
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			CategoryManager categoryManager = new CategoryManager();
			categoryController = new CategoryController(categoryManager);
			// US-1
			TextField categoryField = new TextField("Enter category name");
	        Button addButton = new Button("Add Category");
	        
	        ListView<String> categoryListView = new ListView<>();
            categories = FXCollections.observableArrayList();
            categoryListView.setItems(categories);

			addButton.setOnAction(e -> {
				String categoryName = categoryField.getText().trim();
				categoryController.addCategory(categoryName);
				updateCategoryListView(categories);
				categoryField.clear();
				
			});		
	        
			VBox vBox = new VBox(categoryField, addButton, categoryListView);
			vBox.setSpacing(10);
	        
			Scene scene = new Scene(vBox,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateCategoryListView(ObservableList<String> categories) {
        categories.clear();
        List<String> categoryNames = categoryController.getCategories();
        categories.addAll(categoryNames);
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
