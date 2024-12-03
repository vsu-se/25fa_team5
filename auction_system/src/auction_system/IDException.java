package auction_system;

public class IDException extends IllegalArgumentException {
    private String message;

    public IDException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
