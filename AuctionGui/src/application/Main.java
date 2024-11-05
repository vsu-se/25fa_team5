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
	private PremiumController premiumController;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Auction System");
			Label statusLbl  = new Label("Select User Type: ");
			RadioButton SystemAdminCheckBox = new RadioButton("System Admin");
		    RadioButton UserCheckBox = new RadioButton("User");
		    RadioButton RegisteredUserCheckBox = new RadioButton("Registered User");
		    SystemAdminCheckBox.setOnAction(e -> systemAdminUser(primaryStage));
		    UserCheckBox.setOnAction(e -> User(primaryStage));
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
			premiumController = new PremiumController();
			// US-1
			Button returnButton = new Button("<-- User Selection");
			TextField categoryField = new TextField("Enter category name");
	        Button addButton = new Button("Add Category");
	        
	        returnButton.setOnAction(e -> start(primaryStage));
	        
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
			
			setPremiumButton.setOnAction(e -> {
				String premiumText = premiumField.getText().trim();
				try {
					double premiumValue = Double.parseDouble(premiumText);
					premiumController.setBuyerPremium(premiumValue);
					currentPremiumLbl.setText("Current Premium: " + premiumController.getBuyerPremium() + "%");
					premiumField.clear();
				} catch (NumberFormatException ex) {
					showAlert("Invalid Input", "Please enter a valid number for premium percentage.");
				} catch (IllegalArgumentException ex) {
					showAlert("Invalid Input", ex.getMessage());
				}
			});
			
			VBox systemBox1 = new VBox(returnButton, categoryField, addButton, categoryListView);
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
	
	public void User(Stage primaryStage) {
		try {
			primaryStage.setTitle("Bidder");
			Button returnButton = new Button("<-- User Selection");
			returnButton.setOnAction(e -> start(primaryStage));
			HBox returnBox = new HBox(returnButton);
			returnBox.setSpacing(10);
			
			// US-6
			// All search results should be active auctions ending soonest to latest.
			Label searchLbl = new Label("Search for Auctions: ");
			TextField searchField = new TextField();
			Button searchButton = new Button("Search");

			
			HBox searchBox = new HBox(searchLbl, searchField);
			searchBox.setSpacing(10);

			HBox searchButtonBox = new HBox(searchButton);
			searchButtonBox.setSpacing(10);

			TextArea searchResultsArea = new TextArea();
			searchResultsArea.setEditable(false);

			searchButton.setOnAction(e -> {
				String search = searchField.getText();
				searchResultsArea.clear();
				searchResultsArea.appendText("Search results for: " + search + "\n");
				searchField.clear();
			});
			
			// US-7 & 8
			// Bid function & Show my bids
			Button bidButton = new Button("Place Bid");
			TextField bidField = new TextField("Enter Bid Amount");
			Button showBidsButton = new Button("Show My Bids");
			TextArea currentBidsArea = new TextArea();
			
			HBox bidBox = new HBox(bidButton, bidField);
			bidBox.setSpacing(10);
			
			currentBidsArea.setEditable(false);
			showBidsButton.setOnAction(e -> {
				String bid = bidField.getText();
				currentBidsArea.appendText("Bid placed: $" + bid + "\n");
				bidField.clear();
			});

			VBox userBox1 = new VBox(returnButton, searchBox, searchButtonBox, searchResultsArea, bidField, bidBox, showBidsButton, currentBidsArea);
			userBox1.setSpacing(10);
			
			Scene scene = new Scene(userBox1, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	        
	public void sellerListItem(Stage primaryStage) {
		try {
			primaryStage.setTitle("Seller");
			Button returnButton = new Button("<-- User Selection");
			Label ListItemLbl = new Label("List Item to Auction: ");
			HBox listItemBox = new HBox(ListItemLbl);
			listItemBox.setSpacing(10);
			
			returnButton.setOnAction(e -> start(primaryStage));
			HBox returnBox = new HBox(returnButton);
			returnBox.setSpacing(10);
			
			Label idLbl = new Label("Enter Item ID: ");
			TextField idField = new TextField();
			HBox idBox = new HBox(idLbl, idField);
			idBox.setSpacing(10);
			
			Label nameLbl = new Label("Enter Item Name: ");
			TextField nameField = new TextField();
			HBox nameBox = new HBox(nameLbl, nameField);
			nameBox.setSpacing(10);
			
			Label startDateLbl = new Label("Enter Start Date: ");
			TextField startDateField = new TextField();
			HBox startDateBox = new HBox(startDateLbl, startDateField);
			startDateBox.setSpacing(10);
			
			Label endDateLbl = new Label("Enter End Date: ");
			TextField endDateField = new TextField();
			HBox endDateBox = new HBox(endDateLbl, endDateField);
			endDateBox.setSpacing(10);
			
			Label binLbl = new Label("Enter BIN (Buy-It-Now) Price: ");
			TextField binField = new TextField();
			HBox binBox = new HBox(binLbl, binField);
			
			Button addItemButton = new Button("Add Item");
			HBox addItemBox = new HBox(addItemButton);
			
			TextArea itemListArea = new TextArea();
			addItemButton.setOnAction(e -> {
				String id = idField.getText();
	            String name = nameField.getText();
	            String startDate = startDateField.getText();
	            String endDate = endDateField.getText();
	            String bin = binField.getText();
	            
	            String itemDetails = String.format("ID: %s, Name: %s, Start: %s, End: %s, BIN: $%s\n", id, name, startDate, endDate, bin);
	            itemListArea.appendText(itemDetails);
	            
		        idField.clear();
		        nameField.clear();
		        startDateField.clear();
		        endDateField.clear();
		        binField.clear();
			});
			
			Button showMyAuctionsBtn = new Button("Show My Auctions");
			
			VBox myAuctionsBox = new VBox(showMyAuctionsBtn);
			VBox itemBox = new VBox(returnButton, listItemBox, idBox, nameBox, startDateBox, endDateBox, binBox, addItemBox, itemListArea, myAuctionsBox);
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
