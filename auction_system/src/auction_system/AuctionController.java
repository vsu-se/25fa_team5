package auction_system;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    public void addAuction(TextField idField, TextField nameField, TextField startDateField, TextField endDateField, TextField binField, TextArea itemListArea) {
        String id = idField.getText();
        String name = nameField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String bin = binField.getText();

        AuctionValidator validator = new AuctionValidator();
        boolean validId = validator.validateID(id);
        boolean validName = validator.validateName(name);
        boolean validBin = validator.validateBin(bin);

        if(!validId) {
            throw new IDException("ID is invalid. Please re-enter ID.");
        }
        if(!validName) {
            throw new NameException("Length of name must be greater than 0. Please re-enter name.");
        }
        if(!validBin) {
            throw new BinException("BIN price is invalid. Please re-enter BIN price.");
        }

        if(validId && validName && validBin) {
            Item item = new Item(Integer.parseInt(id), name);
            Auction auction = new Auction(item, 0, Double.parseDouble(bin));
            if(!auctionManager.containsAuction(auction)) {
                auctionManager.addAuction(auction);
                String itemDetails = String.format("ID: %s, Name: %s, Start: %s, End: %s, BIN: $%s, User: \n", id, name, startDate, endDate, bin); // currentUser.getID());
                itemListArea.appendText(itemDetails);
                clearFields(idField, nameField, startDateField, endDateField, binField);
            }
        }

//        String itemDetails = String.format("ID: %s, Name: %s, Start: %s, End: %s, BIN: $%s, User: \n", id, name, startDate, endDate, bin); // currentUser.getID());
//        itemListArea.appendText(itemDetails);
//        Item item = new Item(Integer.parseInt(id), name);
//        Auction auction = new Auction(item, 0, Double.parseDouble(bin));
//        auctionManager.addAuction(auction);
//        clearFields(idField, nameField, startDateField, endDateField, binField);
    }

    private void clearFields(TextField idField, TextField nameField, TextField startDateField, TextField endDateField, TextField binField) {
        idField.clear();
        nameField.clear();
        startDateField.clear();
        endDateField.clear();
        binField.clear();
    }

    // to add categories listed for auctions
    public void addCategories() {

    }

    @Override
    public String toString() {
        return auctionManager.toString();
    }

    public static void main(String[] args) {

    }
		 
}
