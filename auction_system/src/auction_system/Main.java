package auction_system;

	
import java.util.List;
import java.util.Objects;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.scene.layout.VBox;


public class Main extends Application {
	private ObservableList<String> categories;
	private CategoryController categoryController;
	private CommissionController commissionController;
	private PremiumController premiumController;
	private AuctionManager auctionManager = new AuctionManager();
	private AuctionController auctionController = new AuctionController(auctionManager);
	private User currentUser;

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
			// US - 13
			Button bidHistoryBtn = new Button("Show Bid History");
			bidHistoryBtn.setOnAction(e -> {
				// not yet implemented
			});
			// US - 6
			Button activeAuctionsBtn = new Button("Show Active Auctions");
			activeAuctionsBtn.setOnAction(e -> {
				// not yet implemented
			});
			// US - 10
			Button concludedAuctionsBtn = new Button("Show Concluded Auctions");
			concludedAuctionsBtn.setOnAction(e -> {
				// not yet implemented
			});

			HBox systemAdminBtns = new HBox(bidHistoryBtn, activeAuctionsBtn, concludedAuctionsBtn);
			systemAdminBtns.setSpacing(10);

			TextArea systemAdminData = new TextArea();
			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			
			VBox systemBox1 = new VBox(categoryField, addButton, categoryListView);
			systemBox1.setSpacing(10);
			

			VBox systemBox2 = new VBox(commissionField, setCommissionButton, currentCommissionLbl, premiumField, setPremiumButton, currentPremiumLbl, saveDataButton, systemAdminBtns, systemAdminData, signOutButton);
			systemBox2.setSpacing(10);

			HBox systemBox = new HBox(systemBox1, systemBox2);
			systemBox.setSpacing(20);

			Scene scene = new Scene(systemBox,700,400);
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
	//		TextField startDateField = new TextField();
			DatePicker startDatePicker = new DatePicker();
			Label startTimeLbl = new Label("Enter start time (HH:MM:ss): ");
			TextField startTimeField = new TextField();
			HBox startDateBox = new HBox(startDateLbl, startDatePicker, startTimeLbl, startTimeField);
			startDateBox.setSpacing(10);

			startDatePicker.setOnAction(e -> {
//				LocalDate date = startDatePicker.getValue();
//				DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//				String stringDate = format.format(date);
//				startDateField.setText(stringDate);
				startDatePicker.setEditable(false);
			});

			Label endDateLbl = new Label("Enter End Date: ");
	//		TextField endDateField = new TextField();
			DatePicker endDatePicker = new DatePicker();
			Label endTimeLbl = new Label("Enter end time (HH:MM:ss): ");
			TextField endTimeField = new TextField();
			HBox endDateBox = new HBox(endDateLbl, endDatePicker, endTimeLbl, endTimeField);
			endDateBox.setSpacing(10);

			endDatePicker.setOnAction(e -> {
				endDatePicker.setEditable(false);
			});

			Label binLbl = new Label("Enter BIN (Buy-It-Now) Price: ");
			TextField binField = new TextField();
			HBox binBox = new HBox(binLbl, binField);

			Button addItemButton = new Button("Add Item");
			HBox addItemBox = new HBox(addItemButton);

			TextArea itemListArea = new TextArea();

			loadRegisteredUserData(itemListArea);

			TextArea registeredUserData = new TextArea();

			addItemButton.setOnAction(e -> {
//				String id = idField.getText();
//				String name = nameField.getText();
//				String startDate = startDateField.getText();
//				String endDate = endDateField.getText();
//				String bin = binField.getText();

//				String itemDetails = String.format("ID: %s, Name: %s, Start: %s, End: %s, BIN: $%s, User: %d\n", id, name, startDate, endDate, bin, currentUser.getID());
//				itemListArea.appendText(itemDetails);

//				idField.clear();
//				nameField.clear();
//				startDateField.clear();
//				endDateField.clear();
//				binField.clear();
				try {
					auctionController.addAuction(idField, nameField, startDatePicker, startTimeField, endDatePicker, endTimeField, binField, itemListArea);
				}
				catch (IDException exID) {
					showAlert("Auction ID error", exID.getMessage());
				}
				catch (NameException exName) {
					showAlert("Auction name error", exName.getMessage());
				}
				catch (TimeException exTime) {
					showAlert("Auction time error", exTime.getMessage());
				}
//				catch (NullPointerException exNullDates) {
//					showAlert("Auction date error", exNullDates.getMessage());
//				}
				catch (BinException exBin) {
					showAlert("Auction BIN error", exBin.getMessage());
				}
				catch (IllegalArgumentException exDates) {
					showAlert("Auction date and time error", exDates.getMessage());
				}
				catch (RuntimeException ex) {
					showAlert("Auction date error", "Please enter date information.");
				}
			});

			Button saveDataButton = new Button("Save Data");
			saveDataButton.setOnAction(e -> saveRegisteredUserData(itemListArea));

			// US - 5
			Button showMyAuctionsBtn = new Button("Show My Auctions");
			showMyAuctionsBtn.setOnAction(e -> {
				auctionController.showMyAuctions(registeredUserData);
			});
			// US - 13
			Button bidHistoryBtn = new Button("Show Bid History");
			bidHistoryBtn.setOnAction(e -> {
				// not yet implemented
			});
			// US - 11
			Button sellersReportBtn = new Button("Show Sellers Report");
			sellersReportBtn.setOnAction(e -> {
				// not yet implemented
			});
			// US - 12
			Button buyersReportBtn = new Button("Show Buyers Report");
			buyersReportBtn.setOnAction(e -> {
				// not yet implemented
			});

	//		TextArea registeredUserData = new TextArea();

			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			HBox myAuctionsBox = new HBox(showMyAuctionsBtn, bidHistoryBtn, sellersReportBtn, buyersReportBtn);
			myAuctionsBox.setSpacing(10);
			VBox itemBox = new VBox(listItemBox, idBox, nameBox, startDateBox, endDateBox, binBox, addItemBox, saveDataButton, itemListArea, myAuctionsBox, registeredUserData, signOutButton);
			itemBox.setSpacing(10);
			Scene scene = new Scene(itemBox,600,500);
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

			// US-6
			// Lists all active auctions
			Label listLbl = new Label("All Active Auctions: ");
			TextField listField = new TextField();

			HBox listBox = new HBox(listLbl, listField);
			listBox.setSpacing(10);


			// US-7
			// Allows user to bid on an auction
			Button bidButton = new Button("Submit Bid");
			HBox bidBox = new HBox(bidButton);
			bidBox.setSpacing(10);
			TextArea bidArea = new TextArea();

			bidButton.setOnAction(e -> {
				String bid = bidArea.getText();
				bidArea.appendText("Bid: " + bid + "\n");
				bidArea.clear();
			});

			// US-8
			// Shows all auctions user has bid on
			Button showBidsButton = new Button("Show My Bids");
			HBox showBidsBox = new HBox(showBidsButton);
			showBidsBox.setSpacing(10);
			TextArea showBidsArea = new TextArea();

			showBidsButton.setOnAction(e -> {
				showBidsArea.appendText("Bids: \n");
			});

			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			VBox userBox = new VBox(listBox, bidBox, bidArea, showBidsBox, showBidsArea, signOutButton);
			userBox.setSpacing(10);

			Scene scene = new Scene(userBox,600,400);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			primaryStage.setScene(scene);

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
		RadioButton userCheckBox = new RadioButton("User");
		RadioButton registeredUserCheckBox = new RadioButton("Registered User");

		systemAdminCheckBox.setOnAction(e -> {
			registeredUserCheckBox.setSelected(false);
		});
		userCheckBox.setOnAction(e -> {
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
			} else if (userCheckBox.isSelected()) {
				userTyp = "User";
			} else if (registeredUserCheckBox.isSelected()) {
				userTyp = "Registered User";
			}

			System.out.println("Account Created: Username: " + username + ", Password: " + password + ", User Type: " + userTyp);
			saveCredentials(username, password, userTyp);
			accountStage.close();
		});

		VBox layout = new VBox(10, usernameLbl, usernameField, passwordLbl, passwordField, userTypeLbl, systemAdminCheckBox, userCheckBox, registeredUserCheckBox, createAccountButton);
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
				currentUser = new User(1); // should be removed later in favor of code below:
	//			currentUser = new User(username);
			}
			else if ("User".equals(userType)){
				User(primaryStage);
			}
			else if ("Registered User".equals(userType)){
				sellerListItem(primaryStage);
				currentUser = new User(2); // should be removed later in favor of code below:
	//			currentUser = new User(username);
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
