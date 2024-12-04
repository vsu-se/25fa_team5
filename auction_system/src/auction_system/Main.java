package auction_system;

	
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
	private FileManager fileManager = new FileManager();
	private ObservableList<String> categories;
	private CategoryController categoryController;
	private CommissionController commissionController;
	private PremiumController premiumController;
	private AuctionManager auctionManager = fileManager.buildAuctionManager();

//	private AuctionManager auctionManager;

	private AuctionController auctionController = new AuctionController(auctionManager);
	private User currentUser;

//	private FileManager fileManager = new FileManager();

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
			TextField categoryField = new TextField("Enter category name:");
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
			TextField commissionField = new TextField();
			commissionField.setPromptText("Enter commission");
			commissionField.setMaxWidth(130);
			Button setCommissionButton = new Button("Set Seller Commission");
			Label currentCommissionLbl = new Label("Current Commission: " + commissionController.getSellerCommission() + "%");

			setCommissionButton.setOnAction(e -> {
				try {
					commissionController.setSellerCommission(commissionField.getText());
					currentCommissionLbl.setText("Current Commission: " + commissionController.getSellerCommission() + "%");
					commissionField.clear();
				} catch (NumberFormatException ex) {
					showAlert("Invalid Input", "Please enter a valid number for commission percentage.");
				} catch (IllegalArgumentException ex) {
					showAlert("Invalid Input", ex.getMessage());
				}
			});

			// US-3
			TextField premiumField = new TextField();
			premiumField.setPromptText("Enter premium");
			premiumField.setMaxWidth(120);
			Button setPremiumButton = new Button("Set Buyer Premium ");
			Label currentPremiumLbl = new Label("Current Premium: " + premiumController.getBuyerPremium() + "%");

			setPremiumButton.setOnAction(e -> {
				try {
					premiumController.setBuyerPremium(premiumField.getText());
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

			TabPane tabPane = new TabPane();

			// List item tab
			Tab listItemTab = new Tab("List Item");
			listItemTab.setClosable(false);

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

			TextArea registeredUserData = new TextArea();

			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			// Bidding tab
			// Will be changed
			Tab biddingTab = new Tab("Bidding");
			biddingTab.setClosable(false);

			Label biddingLabel = new Label("Place Bids Here:");
			TextField itemNameField = new TextField();
			itemNameField.setPromptText("Enter Item Name");
			itemNameField.setMaxWidth(120);

			TextField bidderNameField = new TextField();
			bidderNameField.setPromptText("Bidder Name");
			bidderNameField.setMaxWidth(120);

			TextField bidAmountField = new TextField();
			bidAmountField.setPromptText("Enter Bid Amount");
			bidAmountField.setMaxWidth(120);

			Button placeBidButton = new Button("Place Bid");
			TextArea bidHistoryArea = new TextArea();
			bidHistoryArea.setEditable(false);

			placeBidButton.setOnAction(e -> {
				String itemName = itemNameField.getText();
				String bidderName = bidderNameField.getText();
				String bidAmount = bidAmountField.getText();
				if (!itemName.isEmpty() && !bidderName.isEmpty() && !bidAmount.isEmpty()) {
					bidHistoryArea.appendText("Bid Placed On Item: " + itemName + " -- $" + bidAmount + " by " + bidderName + "\n");
					itemNameField.clear();
					bidderNameField.clear();
					bidAmountField.clear();
				}
				else {
					showAlert("Error", "All fields must be filled.");
				}
			});

			// Reports tab
			Tab reportsTab = new Tab("Reports");
			reportsTab.setClosable(false);

			// US - 11
			Label sellersReportLbl = new Label ("Sellers Report:");
			TextArea sellersReportData = new TextArea();
			Button sellersReportBtn = new Button("Show Sellers Report");

			sellersReportBtn.setOnAction(e -> {
				// not yet implemented
			});

			// US - 12
			Label buyersReportLbl = new Label ("Buyers Report:");
			TextArea buyersReportData = new TextArea();
			Button buyersReportBtn = new Button("Show Buyers Report");

			buyersReportBtn.setOnAction(e -> {
				// not yet implemented
			});

	//		TextArea registeredUserData = new TextArea();

			Button signOutButton = new Button("Sign Out");
			signOutButton.setOnAction(e -> start(primaryStage));

			HBox myAuctionsBox = new HBox(showMyAuctionsBtn, bidHistoryBtn);
			myAuctionsBox.setSpacing(10);

			VBox itemBox = new VBox(listItemBox, idBox, nameBox, startDateBox, endDateBox, binBox, addItemBox, saveDataButton, itemListArea, myAuctionsBox, registeredUserData, signOutButton);
			itemBox.setSpacing(10);
			listItemTab.setContent(itemBox);

			VBox reports = new VBox(sellersReportLbl, sellersReportData, sellersReportBtn, buyersReportLbl, buyersReportData, buyersReportBtn);
			reports.setSpacing(10);
			reportsTab.setContent(reports);

			tabPane.getTabs().addAll(listItemTab, biddingTab, reportsTab);

			Scene scene = new Scene(tabPane,600,500);
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
			ListView<Auction> activeAuctionList = new ListView<>();

            if (auctionManager != null) {
                auctionManager.getSoonestEndingActiveAuctions();
				ObservableList<Auction> observableList = FXCollections.observableArrayList(auctionManager.getAuctionList());
				activeAuctionList.setItems(observableList);
				activeAuctionList.setCellFactory(e -> new ListCell<Auction>() {
					@Override
					protected void updateItem(Auction auction, boolean empty) {
						super.updateItem(auction, empty);

						if (empty || auction == null || auction.getItem() == null) {
							setText(null);
						} else {
							setText("Item #" + auction.getItem().getID() + ": " + auction.getItem().getName() + " by user: [PLACEHOLDER USERNAME]");
						}
					}
				});
            }
//            ObservableList<Auction> observableList = FXCollections.observableArrayList(auctionManager.getAuctionList());
//			activeAuctionList.setItems(observableList);
//			activeAuctionList.setCellFactory(e -> new ListCell<Auction>() {
//				@Override
//				protected void updateItem(Auction auction, boolean empty) {
//					super.updateItem(auction, empty);
//
//					if (empty || auction == null || auction.getItem() == null) {
//						setText(null);
//					} else {
//						setText("Item #" + auction.getItem().getID() + ": " + auction.getItem().getName() + " by user: [PLACEHOLDER USERNAME]");
//					}
//				}
//			});


			activeAuctionList.setPrefSize(300,300);

			VBox listBox = new VBox(listLbl, activeAuctionList);
			listBox.setSpacing(10);

			// bidding area

			Label space = new Label();
			TextArea auctionDisplayArea = new TextArea();
			auctionDisplayArea.setText("Select an auction from the list on the left to view auction information.");
			auctionDisplayArea.setPrefSize(380,300);

			TextField bidField = new TextField();
			bidField.setPrefSize(100, 50);
			Button bidButton = new Button("Submit bid");

			Button showAuctionBids = new Button("Show this auction's bids");

			HBox enterBidBox = new HBox(bidField, bidButton, showAuctionBids);
			enterBidBox.setSpacing(10);

			VBox biddingArea = new VBox(space, auctionDisplayArea, enterBidBox);
			biddingArea.setSpacing(10);



			HBox auctionListWithBidding = new HBox(listBox, biddingArea);
			auctionListWithBidding.setSpacing(50);

			// US-7
			// Allows user to bid on an auction

			Button selectButton = new Button("Select auction");
			HBox bidBox = new HBox(selectButton);
			bidBox.setSpacing(10);

			selectButton.setOnAction(e -> {
				Auction selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
				auctionDisplayArea.setText(selectedAuction.toString());
			});

			bidButton.setOnAction(e -> {
				Auction selectedAuction;
				if((auctionDisplayArea.getText().equals("Select an auction from the list on the left to view auction information."))) {
					showAlert("Select an auction", "Please select an auction from the list.");
				}
                else {
					selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
					try {
						auctionController.submitBid(selectedAuction, bidField);
					}
					catch (IllegalArgumentException ex) {
						auctionDisplayArea.setText(ex.getMessage());
					}

                }
            });
			showAuctionBids.setOnAction(e -> {
				Auction selectedAuction;
				if((auctionDisplayArea.getText().equals("Select an auction from the list on the left to view auction information."))) {
					showAlert("Select an auction", "Please select an auction from the list.");
				}
				else {
					selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
					auctionDisplayArea.setText(selectedAuction.getBidManager().toString());
				}
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

			VBox userBox = new VBox(auctionListWithBidding, bidBox, showBidsBox, showBidsArea, signOutButton);
			userBox.setSpacing(10);

			Scene scene = new Scene(userBox,800,500);
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
		// AuctionManager auctionManager = new AuctionManager();
		// Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		// 	auctionManager.shutDownScheduler();
		// 	System.out.println("Auction Scheduler Stopped");
		// }));
		launch(args);
	}
}
