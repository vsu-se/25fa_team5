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


    public boolean validateBin(String bin) {
        boolean result;
        if(!validateBinisDouble(bin)) {
            result = false;
            return result;
        }
        else {
            if (bin.length() >= 3) {
                int periodPos = bin.length() - 3;
                String charAtPeriodPos = String.valueOf(bin.charAt(periodPos));
                if(charAtPeriodPos.equals(".")) {
                    result = true;
                    return result;
                }
                else {
                    result = false;
                    return result;
                }
            }
            else {
                result = true;
                return true;
            }
        }
    }

    private boolean validateBinisDouble(String bin) {
        double result;
        boolean valid;
        try {
            result = Double.parseDouble(bin);
            valid = true;
            return valid;
        } catch (IllegalArgumentException e) {
            valid = false;
            return valid;
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
