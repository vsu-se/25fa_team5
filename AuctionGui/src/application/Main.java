package application;

import java.io.*;
import java.nio.Buffer;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
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

			SystemAdminCheckBox.setOnAction(e -> password(primaryStage, "admin"));
			RegisteredUserCheckBox.setOnAction(e -> password(primaryStage, "seller"));

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

//			categories = FXCollections.observableArrayList();
//			ListView<String> categoryListView = new ListView<>(categories);
//			loadData();

			// US-1
			TextField categoryField = new TextField("Enter category name");
			Button addButton = new Button("Add Category");

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
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void saveAdminData(){
		try(FileWriter writer = new FileWriter("admin_data.txt")){
			writer.write("Categories\n");
			for(String category: categoryController.getCategories()){
				writer.write("- " + category + "\n");
			}
			writer.write("\nSeller Commission: ");
			writer.write(commissionController.getSellerCommission() + "\n");
			writer.write("Buyer Premium: ");
			writer.write(premiumController.getBuyerPremium() + "\n");

			showAlert("Data Saved Successfully", "Data saved successfully to admin_data.txt");
		}
		catch(Exception e){
			showAlert("File Error", "Error saving data to file.");
			e.printStackTrace();
		}
	}

	private void saveRegisteredUserData(TextArea itemListArea){
		try(FileWriter writer = new FileWriter("registered_user_data.txt", true)) {
			writer.write(itemListArea.getText());
			showAlert("Data Saved Successfully", "Data saved successfully to registered_user_data.txt");
		}
		catch(Exception e){
			showAlert("File Error", "Error saving data to file.");
			e.printStackTrace();
		}
	}

	private void loadRegisteredUserData(TextArea itemListArea){
		File file = new File("registered_user_data.txt");
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader("registered_user_data.txt"))){
				String line;
				while ((line = reader.readLine()) != null) {
					itemListArea.appendText(line + "\n");
				}
			}
			catch (IOException e){
				showAlert("File Error", "Error loading data from file.");
				e.printStackTrace();
			}
		}
	}

	private void loadAdminData(){
		File file = new File("admin_data.txt");
		if (file.exists()){
			try (BufferedReader reader = new BufferedReader(new FileReader(file))){
				String line;
				boolean inCategories = false;

				while ((line = reader.readLine()) != null){
					if (line.equals("Categories")){
						inCategories = true;
						continue;
					}
					if (inCategories){
						if(line.startsWith("- ")) {
							String category = line.substring(2).trim();
							categoryController.addCategory(category);
						}
						else {
							inCategories = false;
						}
					}

					if (line.startsWith("Seller Commission:")){
						double commission = Double.parseDouble(line.split(":")[1].replace("%", "").trim());
						commissionController.setSellerCommission(commission);
					}
					else if(line.startsWith("Buyer Premium:")){
						double premium = Double.parseDouble(line.split(":")[1].replace("%", "").trim());
						premiumController.setBuyerPremium(premium);
					}
				}
				updateCategoryListView(categories);
			}
			catch (IOException | NumberFormatException e){
				showAlert("File Error", "Error loading data from file.");
				e.printStackTrace();
			}
		}
	}

	private void updateCategoryListView(ObservableList<String> categories) {
		categories.clear();
		List<String> categoryNames = categoryController.getCategories();
		categories.addAll(categoryNames);
	}

	private void password(Stage primaryStage, String userType){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Password Required");
		dialog.setHeaderText("Enter Password");
		PasswordField passwordField = new PasswordField();
		dialog.getDialogPane().setContent(passwordField);
		dialog.showAndWait();

		if (passwordField.getText().equals("hi")) {
			if ("admin".equals(userType)) {
				systemAdminUser(primaryStage);
			}
		} else if (passwordField.getText().equals("hello")) {
			if ("seller".equals(userType)) {
				sellerListItem(primaryStage);
			}
		} else {
			showAlert("Access Denied", "Invalid password. Please try again.");
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