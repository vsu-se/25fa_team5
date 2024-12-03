package auction_system;

public class NameException extends IllegalArgumentException {
    private String message;

    public NameException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
