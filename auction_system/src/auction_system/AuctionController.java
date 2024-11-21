package auction_system;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;

public class AuctionController {
	private AuctionManager auctionManager;

	public AuctionController(AuctionManager auctionManager) {
		this.auctionManager = auctionManager;
	}

    //    public void addAuction(Auction auction) {
//        if(!auctionManager.containsAuction(auction)) {
//            auctionManager.addAuction(auction);
//        }
//        else throw new IllegalArgumentException("Cannot add, auction ID already exists");
//    }

    public void addAuction(TextField idField, TextField nameField, DatePicker startDatePicker, TextField startTimeField, DatePicker endDatePicker, TextField endTimeField, TextField binField, TextArea itemListArea) {
        String id = idField.getText();
        String name = nameField.getText();
    //    String startDate = startDateField.getText();
        String startDate = String.valueOf(startDatePicker.getValue());
        String startTime = startTimeField.getText();
        String endDate = String.valueOf(endDatePicker.getValue());
        String endTime = endTimeField.getText();
        String bin = binField.getText();

        AuctionValidator validator = new AuctionValidator();
        boolean validId = validator.validateID(id);
        boolean validName = validator.validateName(name);
        boolean validBin = validator.validateBin(bin);
        boolean validStartTime = validator.validateTime(startTime);
        boolean validEndTime = validator.validateTime(endTime);

        if(!validId) {
            throw new IDException("ID is invalid. Please re-enter ID.");
        }
        if(!validName) {
            throw new NameException("Length of name must be greater than 0. Please re-enter name.");
        }
        if(!validBin) {
            throw new BinException("BIN price is invalid. Please re-enter BIN price.");
        }
        if(!validStartTime || !validEndTime) {
            throw new TimeException("Time is invalid. Please re-enter time.");
        }

        if(validId && validName && validBin && validStartTime && validEndTime) {
            Item item = new Item(Integer.parseInt(id), name);
            Auction auction = new Auction(item, 0, Double.parseDouble(bin));
            if(!auctionManager.containsAuction(auction)) {
                auctionManager.addAuction(auction);
                String itemDetails = String.format("ID: %s, Name: %s, Start date: %s, start time: %s, End: %s, BIN: $%s, User: \n", id, name, startDate, startTime, endDate, bin); // currentUser.getID());
                itemListArea.appendText(itemDetails);
                clearFields(idField, nameField, startDatePicker, startTimeField, endDatePicker, endTimeField, binField);
            }
        }

//        String itemDetails = String.format("ID: %s, Name: %s, Start: %s, End: %s, BIN: $%s, User: \n", id, name, startDate, endDate, bin); // currentUser.getID());
//        itemListArea.appendText(itemDetails);
//        Item item = new Item(Integer.parseInt(id), name);
//        Auction auction = new Auction(item, 0, Double.parseDouble(bin));
//        auctionManager.addAuction(auction);
//        clearFields(idField, nameField, startDateField, endDateField, binField);
    }

    private void clearFields(TextField idField, TextField nameField, DatePicker startDatePicker, TextField startTimeField, DatePicker endDatePicker, TextField endTimeField, TextField binField) {
        idField.clear();
        nameField.clear();
        startDatePicker.setValue(null);
        startTimeField.clear();
        endDatePicker.setValue(null);
        endTimeField.clear();
        binField.clear();
    }

    public int getAuctionListLength() {
        return auctionManager.getAuctionListLength();
    }

    public Auction getAuctionFromAuctionList(int index) {
        return auctionManager.getAuctionFromAuctionList(index);
    }

    // needs to reworked, will show auctions from all users instead of the one logged in
    public void showMyAuctions(TextArea registeredUserData) {
        String auctionData = "";
        registeredUserData.clear();
        for(int i = 0; i < getAuctionListLength(); i++) {
            Auction auction = getAuctionFromAuctionList(i);
            auctionData += auction.toString() + "\n";
            registeredUserData.setText(auctionData);
        }
    }

    @Override
    public String toString() {
        return auctionManager.toString();
    }

    public static void main(String[] args) {

    }
		 
}
