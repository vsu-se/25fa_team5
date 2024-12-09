package auction_system;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AuctionController {
	private AuctionManager auctionManager;
    private FileManager fileManager = new FileManager();

	public AuctionController(AuctionManager auctionManager) {
		this.auctionManager = auctionManager;
	}

    //    public void addAuction(Auction auction) {
//        if(!auctionManager.containsAuction(auction)) {
//            auctionManager.addAuction(auction);
//        }
//        else throw new IllegalArgumentException("Cannot add, auction ID already exists");
//    }

    public void addAuction(TextField idField, TextField nameField, DatePicker startDatePicker, TextField startTimeField, DatePicker endDatePicker, TextField endTimeField, TextField binField, TextArea itemListArea, String username) {
        String id = idField.getText();
        String name = nameField.getText();
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
        boolean validDates = false;

        if(!validId) {
            throw new IDException("ID is invalid. Please re-enter ID.");
        }
        if(!validName) {
            throw new NameException("Length of name must be greater than 0. Please re-enter name.");
        }
        if(!validStartTime || !validEndTime) {
            throw new TimeException("Start time or end time is invalid. Please re-enter time.");
        }
        if(validStartTime && validEndTime) {
            validDates = validator.validateDate(startDate, endDate, startTime, endTime);
        }
        if(!validDates) {
            throw new IllegalArgumentException("Start date and time of auction must be before the end date and time. Please re-enter date and time information.");
        }
        if(!validBin) {
            throw new BinException("BIN price is invalid. Please re-enter BIN price.");
        }


        if(validId && validName && validBin && validDates) {
            Item item = new Item(Integer.parseInt(id), name);
            LocalDate localStartDate = LocalDate.parse(startDate);
            LocalTime localStartTime = LocalTime.parse(startTime);
            LocalDate localEndDate = LocalDate.parse(endDate);
            LocalTime localEndTime = LocalTime.parse(endTime);

            Auction auction = new Auction(item, localStartDate, localEndDate, localStartTime, localEndTime, Double.parseDouble(bin));
            auction.setUser(username);
            if(!auctionManager.containsAuction(auction)) {
                auctionManager.addAuction(auction);
                String itemDetails = String.format("ID: %s, Name: %s, Start date: %s, start time: %s, End date: %s, end time: %s, BIN: $%s, User: placeholder, active: %s\n", id, name, startDate, startTime, endDate, endTime, bin, "true");
                itemListArea.setText(itemDetails);
                clearFields(idField, nameField, startDatePicker, startTimeField, endDatePicker, endTimeField, binField);
            }
        }
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

    // when a bid is submitted...
    // info of bidder saved, bid amount saved (if valid)
    // date and time of bid saved
    // this info needs to be saved somewhere
    public void submitBid(Auction selectedAuction, TextField bidField, User user) {
        String bidValue = bidField.getText();
        AuctionValidator validator = new AuctionValidator();
        if(validator.validateBid(bidValue)) {
            bidField.clear();
            bidField.setText("bid is valid!");
            LocalDateTime dateTime = LocalDateTime.now();
            Bid bid = new Bid(selectedAuction.getItem().getID(), Double.parseDouble(bidValue), dateTime, user);
            bidField.clear();
            boolean result = selectedAuction.addBid2(bid);
            if(result) {
                bidField.setText(String.valueOf(result));
                fileManager.saveBidInfo(bid);
            }
            else {
                throw new IllegalArgumentException("Bid with that amount already exists, please re-enter bid amount.");
            }
        }
        else {
            bidField.clear();
            bidField.setText("bid isn't valid :(");
        }
    }

    public int getAuctionListLength() {
        return auctionManager.getAuctionListLength();
    }

    public Auction getAuctionFromAuctionList(int index) {
        return auctionManager.getAuctionFromAuctionList(index);
    }

    // needs to changed, will show auctions from all users instead of the one logged in
    public void showMyAuctions(TextArea registeredUserData) {
        registeredUserData.clear();
        registeredUserData.setText(toString());
    }

    @Override
    public String toString() {
        return auctionManager.toString();
    }

    public static void main(String[] args) {

    }
		 
}