package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private ObservableList<String> categories;
	private CategoryController categoryController;
	private CommissionController commissionController;
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			CategoryManager categoryManager = new CategoryManager();
			categoryController = new CategoryController(categoryManager);
			commissionController = new CommissionController();
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
	        
			
			// US-2
			TextField commissionField = new TextField("Enter commission");
			Button setCommissionButton = new Button("Set Seller Commission");
			Label currentCommissionLbl = new Label("Current Commission: 0%");
			
			setCommissionButton.setOnAction(e -> {
                String commissionText = commissionField.getText().trim();
                try {
                    double commissionValue = Double.parseDouble(commissionText);
                    commissionController.setSellerCommission(commissionValue);
                    currentCommissionLbl.setText("Current Commission: " + commissionController.getSellerCommission() + "%");
                    commissionField.clear();
                } 
                catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid number for commission percentage.");
                } 
                catch (IllegalArgumentException ex) {
                    showAlert("Invalid Input", ex.getMessage());
                }
             });
			
			// US-3
			TextField premiumField = new TextField("Enter premium");
			Button setPremiumButton = new Button("Set Buyer Premium ");
			Label currentPremiumLbl = new Label("Current Premium: 0%");
			
			VBox categoryBox = new VBox(categoryField, addButton, categoryListView);
			categoryBox.setSpacing(10);
			
			VBox commissionBox = new VBox(commissionField, setCommissionButton, currentCommissionLbl, premiumField, setPremiumButton, currentPremiumLbl);
			commissionBox.setSpacing(10);
			
			HBox hBox = new HBox(categoryBox, commissionBox);
			hBox.setSpacing(20);
			
//			VBox vBox = new VBox(categoryField, addButton, categoryListView, commissionField, setCommissionButton, currentCommissionLbl, premiumField, setPremiumButton, currentPremiumLbl);
//			vBox.setSpacing(10);
	        
			Scene scene = new Scene(hBox,400,400);
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
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
