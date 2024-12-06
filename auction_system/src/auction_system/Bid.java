package auction_system;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Bid {

    private double bidValue;
    private int itemID;
    private User user;
    private LocalDate dateOfBid;
    private LocalTime timeOfBid;

    public Bid(int itemID, double bidValue, LocalDateTime dateTime, User user) {
        this.itemID = itemID;
        this.bidValue = bidValue;
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        dateOfBid = date;
        timeOfBid = time.truncatedTo(ChronoUnit.SECONDS);
        this.user = user;
    }

    public Bid(double bidValue, User user) {
        this.bidValue = bidValue;
        this.user = user;
    }

    public Bid(double bidValue) {
        this.bidValue = bidValue;
    }

    public double getBidValue() {
        return bidValue;
    }

    public LocalDate getDate() {
        return dateOfBid;
    }

    public LocalTime getTime() {
        return timeOfBid;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Bid bid) {
            return this.getBidValue() == bid.getBidValue();
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Item ID: " + itemID + ", bid amount: " + bidValue + ", date: " + dateOfBid + ", time: " + timeOfBid
                + ", user: " + user.getName() + "\n";
    }

    public static void main(String[] arg) {

    }
}
