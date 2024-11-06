package com.example.local_project_intj2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Label categoryLabel;
    private Button enterCategoryButton;
    private Button addCategoryButton;
    private TextField categoryTextField;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
//
//    protected void onEnterCategoryButtonClick() {
//        welcomeText.setText("Enter Category Name");
//        categoryTextField = new TextField("Category Name");
//        addCategoryButton = new Button("Add Category");
//    }
//    protected void onaddCategoryButtonClick() {
//        String categoryName = categoryTextField.getText();
//        categoryController.addCategory(categoryName);
//        updateCategoryListView(categories);
//        categoryTextField.clear();
//    }

}