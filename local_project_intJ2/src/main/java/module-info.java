module com.example.local_project_intj2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.local_project_intj2 to javafx.fxml;
    exports com.example.local_project_intj2;
}