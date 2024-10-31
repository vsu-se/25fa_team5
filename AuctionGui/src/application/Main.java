package application;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	private ObservableList<String> categories;
	private CategoryController categoryController;
	private CheckBox SystemAdminCheckBox;
	private CheckBox UserCheckBox;
	private CheckBox AuthUserCheckBox;
	private Label statusLabel;
	private Button CreateCategoryButton;
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			SystemAdminCheckBox = new CheckBox("System Admin");
			UserCheckBox = new CheckBox("User");
			AuthUserCheckBox = new CheckBox("Authorized User");
			statusLabel = new Label("Select User Type");

			SystemAdminCheckBox.setOnAction(e -> {SystemAdminCheckBox(primaryStage);});
//			UserCheckBox.setOnAction(e -> {UserCheckBox(primaryStage);});
//			AuthUserCheckBox.setOnAction(e -> {AuthUserCheckBox(primaryStage);});





			VBox layout = new VBox(10.0);
			layout.getChildren().addAll(new Node[]{SystemAdminCheckBox, UserCheckBox, AuthUserCheckBox, statusLabel});
			Scene scene = new Scene(layout, 400.0, 200.0);
			primaryStage.setTitle("Select User Type");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void SystemAdminCheckBox(Stage primaryStage) {
		try {
			CreateCategoryButton = new Button("Create Category");
			CreateCategoryButton.setOnAction((ex) -> {
				CreateCategoryName();
			});
			CategoryManager categoryManager = new CategoryManager();
			this.categoryController = new CategoryController(categoryManager);
			ListView<String> categoryListView = new ListView();
			this.categories = FXCollections.observableArrayList();
			categoryListView.setItems(this.categories);
			VBox vBox = new VBox(new Node[]{this.CreateCategoryButton, categoryListView});
			vBox.setSpacing(10.0);
			Scene scene = new Scene(vBox, 400.0, 400.0);
			scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("System Admin");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception var6) {
			Exception e = var6;
			e.printStackTrace();
		}

	}
	private void CreateCategoryName() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Enter Category");
		dialog.setHeaderText("Enter a new category name:");
		dialog.setContentText("Category:");
		dialog.showAndWait().ifPresent((categoryName) -> {
			categoryName = categoryName.trim();
			if (!categoryName.isEmpty() && !categories.contains(categoryName)) {
				categoryController.addCategory(categoryName);
				updateCategoryListView(categories);
			} else if (categories.contains(categoryName)) {
				Stage exists = new Stage();
				exists.setTitle("Category Exists");
				Label existsLabel = new Label("Category already exists.");
				Button okButton = new Button("OK");
				okButton.setOnAction((e) -> {
					exists.close();
				});
				VBox existsLayout = new VBox(10.0);
				existsLayout.getChildren().addAll(new Node[]{existsLabel, okButton});
				Scene existsScene = new Scene(existsLayout, 200.0, 100.0);
				exists.setScene(existsScene);
				exists.show();
			}

		});
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
