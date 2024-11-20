package auction_system;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

//    public boolean validateStartDate(String s) {
//        boolean validFormat = validateDateFormat(s);
//        boolean validMonth;
//        boolean validDay;
//        boolean validYear;
//        return validateDateFormat(s);
//    }
//
//    private boolean validateDateFormat(String s) {
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        try {
//            Date date = dateFormat.parse(s);
//            return true;
//        } catch (ParseException e) {
//            return false;
//        }
//    }

//    private boolean validateDateNumbers(String s) {
//        boolean validMonth;
//        boolean validDay;
//        boolean validYear;
//        return false;
//    }
//
//    public boolean validateEndDate(String s) {
//        return validateDateFormat(s);
//    }

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

    public boolean allValid() {
        return false;
    }

    public static void main(String[] args) {

    }

}
