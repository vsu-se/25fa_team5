package com.example.local_project_intj2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private ArrayList<String> categoryList;
    private Button enterCategoryButton;
    private Button showCategoriesButton;
    private CheckBox SystemAdminCheckBox;
    private CheckBox UserCheckBox;
    private CheckBox AuthUserCheckBox;
    private Label statusLabel; // Label to display messages

    @Override
    public void start(Stage primaryStage) {
        SystemAdminCheckBox = new CheckBox("System Admin");
        UserCheckBox = new CheckBox("User");
        AuthUserCheckBox = new CheckBox("Authorized User");

        statusLabel = new Label("Select User Type");

        SystemAdminCheckBox.setOnAction(e -> SystemadminCheckBox(primaryStage));
//        UserCheckBox.setOnAction(e -> UserCheckBox(primaryStage));
//        AuthUserCheckBox.setOnAction(e -> AuthUserCheckBox(primaryStage));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(SystemAdminCheckBox, UserCheckBox, AuthUserCheckBox, statusLabel);
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setTitle("Select User Type");
        primaryStage.setScene(scene);
        primaryStage.show();
//        categoryList = new ArrayList<>();
//
//        // Set up the GUI components
//        enterCategoryButton = new Button("Enter Category Name");
//        showCategoriesButton = new Button("Show Categories");
//
//        // Layout setup
//        VBox layout = new VBox(10);  // Vertical layout with spacing of 10
//        layout.getChildren().addAll(enterCategoryButton, showCategoriesButton);
//
//        // Add action handlers
//        enterCategoryButton.setOnAction(e -> enterCategoryName());
//        showCategoriesButton.setOnAction(e -> showCategories());
//
//        // Set up the scene and stage
//        Scene scene = new Scene(layout, 400, 200);
//        primaryStage.setTitle("Category Manager");
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }

    private void SystemadminCheckBox(Stage primaryStage){
        categoryList = new ArrayList<>();

        // Set up the GUI components
        enterCategoryButton = new Button("Enter Category Name");
        showCategoriesButton = new Button("Show Categories");

        // Layout setup
        VBox layout = new VBox(10);  // Vertical layout with spacing of 10
        layout.getChildren().addAll(enterCategoryButton, showCategoriesButton);

        // Add action handlers
        enterCategoryButton.setOnAction(e -> enterCategoryName());
        showCategoriesButton.setOnAction(e -> showCategories());

        // Set up the scene and stage
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setTitle("Category Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enterCategoryName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Category");
        dialog.setHeaderText("Enter a new category name:");
        dialog.setContentText("Category:");

        dialog.showAndWait().ifPresent(categoryName -> {
            categoryName = categoryName.trim();
            if (!categoryName.isEmpty() && !categoryList.contains(categoryName)) {
                categoryList.add(categoryName);
                Stage created = new Stage();
                created.setTitle("Category Created");
                Label createdLabel = new Label("Category created successfully.");
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> created.close());

                VBox createdLayout = new VBox(10);
                createdLayout.getChildren().addAll(createdLabel, okButton);
                Scene createdScene = new Scene(createdLayout, 200, 100);
                created.setScene(createdScene);
                created.show();
            }
            else if (categoryList.contains(categoryName)) {
                Stage exists = new Stage();
                exists.setTitle("Category Exists");
                Label existsLabel = new Label("Category already exists.");
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> exists.close());

                VBox existsLayout = new VBox(10);
                existsLayout.getChildren().addAll(existsLabel, okButton);
                Scene existsScene = new Scene(existsLayout, 200, 100);
                exists.setScene(existsScene);
                exists.show();
            }
            else {
                statusLabel.setText("Please enter a valid category name.");
                //thinking that eventually we can set up some different category requirements
            }
        });
    }

    private void showCategories() {
        Stage ListCategory = new Stage();
        ListCategory.setTitle("Categories");
        Label categoryListLabel = new Label("Categories: " + String.join(", ", categoryList));
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> ListCategory.close());

        VBox categoryListLayout = new VBox(10);
        categoryListLayout.getChildren().addAll(categoryListLabel, okButton);
        Scene categoryListScene = new Scene(categoryListLayout, 200, 100);
        ListCategory.setScene(categoryListScene);
        ListCategory.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

