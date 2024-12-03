package auction_system;

public class BinException extends IllegalArgumentException {
    private String message;

    public BinException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
