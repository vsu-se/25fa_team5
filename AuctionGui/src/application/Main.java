package application;

	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;

import java.util.List;
import java.util.Objects;


import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextInputDialog;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	private ObservableList<String> categories;
	private CategoryController categoryController;
	private CommissionController commissionController;
	private PremiumController premiumController;

	private FileManager fileManager = new FileManager();




	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Auction System");
			Label statusLbl  = new Label("Select User Type: ");
			RadioButton SystemAdminCheckBox = new RadioButton("System Admin");


		    RadioButton UserCheckBox = new RadioButton("User");
		    RadioButton RegisteredUserCheckBox = new RadioButton("Registered User");
		    SystemAdminCheckBox.setOnAction(e -> login(primaryStage, "System Admin"));
		    RegisteredUserCheckBox.setOnAction(e -> login(primaryStage, "Registered User"));
		    UserCheckBox.setOnAction(e -> login(primaryStage, "User"));
		    
		    Button createAccountButton = new Button("Create Account");
      createAccountButton.setOnAction(e -> createAccount());
			
		    VBox UserBox = new VBox(statusLbl, SystemAdminCheckBox, UserCheckBox, RegisteredUserCheckBox, createAccountButton);
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

			primaryStage.setTitle("System Admin");
			CategoryManager categoryManager = new CategoryManager();
			categoryController = new CategoryController(categoryManager);
			commissionController = new CommissionController();
			premiumController = new PremiumController();


//			categories = FXCollections.observableArrayList();
//			ListView<String> categoryListView = new ListView<>(categories);
//			loadData();


			// US-1
			Button returnButton = new Button("<-- User Selection");
			TextField categoryField = new TextField("Enter category name");

	        Button addButton = new Button("Add Category");
	        
	        returnButton.setOnAction(e -> start(primaryStage));
	        
	        ListView<String> categoryListView = new ListView<>();
            categories = FXCollections.observableArrayList();
            categoryListView.setItems(categories);
            
            loadAdminData();


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
			Label currentCommissionLbl = new Label("Current Commission: " + commissionController.getSellerCommission() + "%");


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
			Label currentPremiumLbl = new Label("Current Premium: " + premiumController.getBuyerPremium() + "%");


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


			Button saveDataButton = new Button("Save Data");
			saveDataButton.setOnAction(e -> saveAdminData());

			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			
			VBox systemBox1 = new VBox(categoryField, addButton, categoryListView);
			systemBox1.setSpacing(10);
			

			VBox systemBox2 = new VBox(commissionField, setCommissionButton, currentCommissionLbl, premiumField, setPremiumButton, currentPremiumLbl, saveDataButton, signOutButton);
			systemBox2.setSpacing(10);

			HBox systemBox = new HBox(systemBox1, systemBox2);
			systemBox.setSpacing(20);

			Scene scene = new Scene(systemBox,600,400);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());

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

			loadRegisteredUserData(itemListArea);


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

			Button saveDataButton = new Button("Save Data");
			saveDataButton.setOnAction(e -> saveRegisteredUserData(itemListArea));

			Button showMyAuctionsBtn = new Button("Show My Auctions");


			
			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));
                
			

			VBox myAuctionsBox = new VBox(showMyAuctionsBtn);
			VBox itemBox = new VBox(listItemBox, idBox, nameBox, startDateBox, endDateBox, binBox, addItemBox, saveDataButton, itemListArea, myAuctionsBox, signOutButton);
			itemBox.setSpacing(10);
			Scene scene = new Scene(itemBox,600,400);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	

	// User Page / Not completed
	public void User(Stage primaryStage) {
		try {
			primaryStage.setTitle("Bidder");

//			Scene scene = new Scene(     , 600, 400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);


			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	private void saveAdminData(){
		List<String> categories = categoryController.getCategories();
		double sellerCommission = commissionController.getSellerCommission();
		double buyerPremium = premiumController.getBuyerPremium();
		fileManager.saveAdminData(categories, sellerCommission, buyerPremium);
	}

	private void saveRegisteredUserData(TextArea itemListArea){
		fileManager.saveRegisteredUserData(itemListArea);
	}

	private void loadRegisteredUserData(TextArea itemListArea){
		fileManager.loadRegisteredUserData(itemListArea);
	}

	private void loadAdminData(){
		fileManager.loadAdminData(categoryController, commissionController, premiumController);
		updateCategoryListView(categories);
	}


	private void updateCategoryListView(ObservableList<String> categories) {
		categories.clear();
		List<String> categoryNames = categoryController.getCategories();
		categories.addAll(categoryNames);
	}


	private void createAccount(){
		Stage accountStage = new Stage();
		accountStage.setTitle("Create Account");

		Label usernameLbl = new Label("Enter Username: ");
		TextField usernameField = new TextField();

		Label passwordLbl = new Label("Enter Password: ");
		PasswordField passwordField = new PasswordField();

		Label userTypeLbl = new Label("Select User Type: ");
		RadioButton systemAdminCheckBox = new RadioButton("System Admin");
		RadioButton registeredUserCheckBox = new RadioButton("Registered User");

		systemAdminCheckBox.setOnAction(e -> {
			registeredUserCheckBox.setSelected(false);
		});
		registeredUserCheckBox.setOnAction(e -> {
			systemAdminCheckBox.setSelected(false);
		});

		Button createAccountButton = new Button("Create Account");
		createAccountButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			String userTyp = "";

			if (systemAdminCheckBox.isSelected()) {
				userTyp = "System Admin";
			} else if (registeredUserCheckBox.isSelected()) {
				userTyp = "Registered User";
			}

			System.out.println("Account Created: Username: " + username + ", Password: " + password + ", User Type: " + userTyp);
			saveCredentials(username, password, userTyp);
			accountStage.close();
		});

		VBox layout = new VBox(10, usernameLbl, usernameField, passwordLbl, passwordField, userTypeLbl, systemAdminCheckBox, registeredUserCheckBox, createAccountButton);
		Scene scene = new Scene(layout, 300, 300);
		accountStage.setScene(scene);
		accountStage.show();
	}


	private void saveCredentials(String username, String password, String userType){
		fileManager.saveCredentials(username,password, userType);
	}


	private void login(Stage primaryStage, String userType){
		TextInputDialog userNameDialog = new TextInputDialog();
		userNameDialog.setTitle("Login Required");
		userNameDialog.setHeaderText("Enter Username");
		userNameDialog.setContentText("Username:");
		String username = userNameDialog.showAndWait().orElse("").trim();

		TextInputDialog passwordDialog = new TextInputDialog();
		passwordDialog.setTitle("Password Required");
		passwordDialog.setHeaderText("Enter Password");
		PasswordField passwordField = new PasswordField();
		passwordDialog.getDialogPane().setContent(passwordField);
		passwordDialog.showAndWait();

		String password = passwordField.getText().trim();
		boolean isValid = fileManager.loadCredentials(username, password, userType);


		if (isValid){
			if ("System Admin".equals(userType)){
				systemAdminUser(primaryStage);
			}
			else if ("Registered User".equals(userType)){
				sellerListItem(primaryStage);
			}
		}
		else {
			showAlert("Access Denied", "Invalid username or password. Please try again.");
		}
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
