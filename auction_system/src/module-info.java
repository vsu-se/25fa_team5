/**
 * 
 */
/**
 * 
 */

//module cs4321 {
//    exports auction_system;
//    requires org.junit.jupiter.api;
//}

module cs4321 {
        requires javafx.controls;
        requires javafx.graphics;
        requires java.desktop;
        requires javafx.base;
        requires org.junit.jupiter.api;

        opens auction_system to javafx.graphics, javafx.fxml;
        //exports auction_system;
        }
