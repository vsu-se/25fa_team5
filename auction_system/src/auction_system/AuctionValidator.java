package auction_system;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AuctionValidator {
    public AuctionValidator() {

    }

    public boolean validateID(String ID) {
        int result;
        boolean valid;
        try {
            result = Integer.parseInt(ID);
            valid = true;
            return valid;
        }
        catch (NumberFormatException e) {
            valid = false;
            return valid;
        }
    }

    public boolean validateDate(String stringStartDate, String stringEndDate, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localStartTime = null;
        LocalTime localEndTime = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (validateTime(startTime) && validateTime(endTime)) {
            localStartTime = LocalTime.parse(startTime, formatter);
            localEndTime = LocalTime.parse(endTime, formatter);
            startDate = LocalDate.parse(stringStartDate);
            endDate = LocalDate.parse(stringEndDate);
            startDateTime = LocalDateTime.of(startDate, localStartTime);
            endDateTime = LocalDateTime.of(endDate, localEndTime);
            if(startDateTime.isBefore(endDateTime)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean validateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime localTime = LocalTime.parse(time, formatter);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private int periodCounter(String bin) {
        int numPeriods = 0;
        for(int i = 0; i < bin.length(); i++) {
            if(String.valueOf(bin.charAt(i)).equals(".")) {
                numPeriods++;
            }
        }
        return numPeriods;
    }

    private boolean checkPeriodPos(String bin) {
        if(bin.indexOf(".") == bin.length() - 3) {
            return true;
        }
        else {
            return false;
        }
    }

    private String splitStringAtPeriod(String bin) {
        return bin.substring(bin.length() - 2);
    }

    private String splitStringBeforePeriod(String bin) {
        return bin.substring(0, bin.length() - 3);
    }

    private boolean checkForNumbers(String s) {
        boolean hasAllNumbers;
        int binNumber;
        try {
            binNumber = Integer.parseInt(s);
            hasAllNumbers = true;
            return hasAllNumbers;
        } catch (NumberFormatException e) {
            hasAllNumbers = false;
            return hasAllNumbers;
        }
    }

    public boolean validateBin(String bin) {
        boolean result;
        if(periodCounter(bin) == 0) {
            return result = checkForNumbers(bin);
        }
        else if(periodCounter(bin) == 1) {
            if(checkPeriodPos(bin)) {
                String s1 = splitStringAtPeriod(bin);
                String s2 = splitStringBeforePeriod(bin);
                if(checkForNumbers(s1) && checkForNumbers(s2)) {
                    return result = true;
                }
                else {
                    return result = false;
                }
            }
            else {
                return result = false;
            }
        }
        else {
            return result = false;
        }
    }

    public boolean validateName(String name) {
        if(!name.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateBid(String bidValue) {
        if(validateBin(bidValue)) {
            double bid = Double.parseDouble(bidValue);
            if(bid > 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

}
