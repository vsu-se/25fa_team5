package auction_system;

public class DuplicateAuctionException extends IllegalArgumentException {
    private String message;

    public DuplicateAuctionException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
