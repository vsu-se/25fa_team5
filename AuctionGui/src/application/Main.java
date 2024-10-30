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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
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
			primaryStage.setTitle("Auction System");
			Label statusLbl  = new Label("Select User Type: ");
			RadioButton SystemAdminCheckBox = new RadioButton("System Admin");
		    RadioButton UserCheckBox = new RadioButton("User");
		    RadioButton RegisteredUserCheckBox = new RadioButton("Registered User");
		    SystemAdminCheckBox.setOnAction(e -> systemAdminUser(primaryStage));
		    RegisteredUserCheckBox.setOnAction(e -> sellerListItem(primaryStage));
		    
		    VBox UserBox = new VBox(statusLbl, SystemAdminCheckBox, UserCheckBox, RegisteredUserCheckBox);
		    Scene scene = new Scene(UserBox,250,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void systemAdminUser(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			primaryStage.setTitle("System Admin");
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
				if (categoryName.isEmpty()) {
					showAlert("Invalid Input", "Please enter a category name.");
					return;
				}
				if (categoryController.getCategories().contains(categoryName)) {
					showAlert("Invalid Input", "Category already exists.");
					return;
				}
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
			
			VBox systemBox1 = new VBox(categoryField, addButton, categoryListView);
			systemBox1.setSpacing(10);
			
			VBox systemBox2 = new VBox(commissionField, setCommissionButton, currentCommissionLbl, premiumField, setPremiumButton, currentPremiumLbl);
			systemBox2.setSpacing(10);
			
			HBox systemBox = new HBox(systemBox1, systemBox2);
			systemBox.setSpacing(20);
			
			Scene scene = new Scene(systemBox,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	        
	public void sellerListItem(Stage primaryStage) {
		try {
			primaryStage.setTitle("Seller");
			Label ListItemLbl = new Label("List Item to Auction: ");
			HBox listItemBox = new HBox(ListItemLbl);
			listItemBox.setSpacing(10);
			
			Label idLbl = new Label("Enter Item ID: ");
			TextField idField = new TextField();
			HBox idBox = new HBox(idLbl, idField);
			idBox.setSpacing(10);
			
			Label nameLbl = new Label("Enter Item Name: ");
			TextField nameField = new TextField();
			HBox nameBox = new HBox(nameLbl, nameField);
			nameBox.setSpacing(10);
			
			Label startTimeLbl = new Label("Enter Start Time: ");
			TextField startTimeField = new TextField();
			HBox startTimeBox = new HBox(startTimeLbl, startTimeField);
			startTimeBox.setSpacing(10);
			
			Label endDateLbl = new Label("Enter End Time: ");
			TextField endDateField = new TextField();
			HBox endDateBox = new HBox(endDateLbl, endDateField);
			endDateBox.setSpacing(10);
			
			Label binLbl = new Label("Enter Buy-It-Now Price: ");
			TextField binField = new TextField();
			HBox binBox = new HBox(binLbl, binField);
			
			Button addItemButton = new Button("Add Item");
			HBox addItemBox = new HBox(addItemButton);
			
			TextArea itemListArea = new TextArea();
			addItemButton.setOnAction(e -> {
				// add toString() method from Item class to print the details of the item

		        idField.clear();
		        nameField.clear();
		        startTimeField.clear();
		        endDateField.clear();
		        binField.clear();
			});
			
			VBox itemBox = new VBox(listItemBox, idBox, nameBox, startTimeBox, endDateBox, binBox, addItemBox, itemListArea);
			itemBox.setSpacing(10);
			Scene scene = new Scene(itemBox,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
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
