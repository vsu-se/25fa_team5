module AuctionGui {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.base;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
}
