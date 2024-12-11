package auction_system;

import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
            if (auctionManager != null) {
                if(!auctionManager.containsAuction(auction)) {
                    auctionManager.addAuction(auction);
                    String itemDetails = String.format("ID: %s, Name: %s, Start date: %s, start time: %s, End date: %s, end time: %s, BIN: $%s, active: %s\n", id, name, startDate, startTime, endDate, endTime, bin, "true");
                    itemListArea.setText(itemDetails);
                    clearFields(idField, nameField, startDatePicker, startTimeField, endDatePicker, endTimeField, binField);
                }
                else {
                    throw new DuplicateAuctionException("Auction already exists. Please re-enter information.");
                }
            }
            else {
                AuctionManager aM = new AuctionManager();
                aM.addAuction(auction);
                auctionManager = aM;
                String itemDetails = String.format("ID: %s, Name: %s, Start date: %s, start time: %s, End date: %s, end time: %s, BIN: $%s, active: %s\n", id, name, startDate, startTime, endDate, endTime, bin, "true");
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
    public void submitBid(Auction selectedAuction, TextField bidField, TextArea auctionDisplayArea, User user) {
        String bidValue = bidField.getText();
        AuctionValidator validator = new AuctionValidator();
        if(validator.validateBid(bidValue)) {
            bidField.clear();
            auctionDisplayArea.setText("Bid entered is valid.");
            LocalDateTime dateTime = LocalDateTime.now();
            Bid bid = new Bid(selectedAuction.getItem().getID(), Double.parseDouble(bidValue), dateTime, user);
            boolean result = selectedAuction.addBid(bid);
            if(result) {
                auctionDisplayArea.setText("Bid successfully added to auction.");
                fileManager.saveBidInfo(bid);
            }
            else {
                throw new IllegalArgumentException("Bid with that amount already exists, please re-enter bid amount.");
            }
        }
        else {
            bidField.clear();
            auctionDisplayArea.setText("Bid isn't valid, please try again.");
        }
    }

    public int getAuctionListLength() {
        return auctionManager.getAuctionListLength();
    }

    public Auction getAuctionFromAuctionList(int index) {
        return auctionManager.getAuctionFromAuctionList(index);
    }

    public void select(ListView<Auction> activeAuctionList, TextArea auctionDisplayArea) {
        Auction selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
        auctionDisplayArea.setText(selectedAuction.toString());
    }

    public void saveData(TextArea itemListArea, String username) {
        if (!itemListArea.getText().isEmpty()) {
            fileManager.saveRegisteredUserData(username, itemListArea);
            itemListArea.clear();
        }
    }

    public void bid(ListView<Auction> activeAuctionList, TextArea auctionDisplayArea, TextField bidField, User currentUser) {
        Auction selectedAuction;
        if((auctionDisplayArea.getText().equals("Select an auction from the list on the left to view auction information."))) {
            showAlert("Select an auction", "Please select an auction from the list.");
        }
        else {
            selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
            try {
                submitBid(selectedAuction, bidField, auctionDisplayArea, currentUser);
            }
            catch (IllegalArgumentException ex) {
                auctionDisplayArea.setText(ex.getMessage());
            }
        }
    }

    public void showAuctionBids(ListView<Auction> activeAuctionList, TextArea auctionDisplayArea) {
        Auction selectedAuction;
        if((auctionDisplayArea.getText().equals("Select an auction from the list on the left to view auction information."))) {
            showAlert("Select an auction", "Please select an auction from the list");
        }
        else {
            selectedAuction = activeAuctionList.getSelectionModel().getSelectedItem();
            auctionDisplayArea.setText(selectedAuction.getBidManager().toString());
        }
    }

    public void showBidHistory(ListView<Auction> activeAuctionList, TextArea auctionDisplayArea) {
        Auction selected;
        if((auctionDisplayArea.getText().equals("Select an auction from the list on the left to view auction information."))) {
            showAlert("Select an auction", "Please select an auction from the list.");
        }
        else {
            selected = activeAuctionList.getSelectionModel().getSelectedItem();
            int id = selected.getItem().getID();
            auctionDisplayArea.setText(fileManager.buildBidManagerForBidHistory(id).toString());
        }
    }

    public void showMyAuctions(TextArea showMyAuctionsTextArea, String username) {
        ArrayList<Auction> userListedAuctions = getUserListedAuctions(username);
        for(Auction auction : userListedAuctions) {
            showMyAuctionsTextArea.appendText(auction.toString() + "-----------------------\n");
        }
    }

    public void selectBidOnAuctions(ListView<Auction> bidOnAuctionsList, TextArea displayBidOnAuctions, String username) {
        Auction selected;
        selected = bidOnAuctionsList.getSelectionModel().getSelectedItem();
        displayBidOnAuctions.setText(selected.showMyBidsData(username));
    }

    public AuctionManager buildAuctionManager() {
        return this.auctionManager = fileManager.buildAuctionManager();
    }

    public ArrayList<Auction> getActiveList() {
        return auctionManager.getActiveList();
    }

    public void sortBySoonestEndingActiveAuctions() {
        auctionManager.sortBySoonestEndingActiveAuctions();
    }

    public ArrayList<Auction> getUserWonAuctions(String username) {
        return auctionManager.getUserWonAuctions(username);
    }

    public ArrayList<Auction> getUserListedAuctions(String username) {
        return auctionManager.getUserListedAuctions(username);
    }

    public ArrayList<Auction> getUserBidOnAuctions(String username) {
        return auctionManager.getUserBidOnAuctions(username);
    }

    public AuctionManager getAuctionManager() {
        return auctionManager;
    }

    public void checkDates() {
        if(auctionManager != null) {
            auctionManager.checkDates();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public String toString() {
        return auctionManager.toString();
    }

    public static void main(String[] args) {

    }
		 
}