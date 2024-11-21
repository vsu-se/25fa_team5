package auction_system;

public class TimeException extends IllegalArgumentException {
    private String message;

    public TimeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
