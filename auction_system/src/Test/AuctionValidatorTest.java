package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.AuctionValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class AuctionValidatorTest {

    @Test
    @DisplayName("testValidID")
    void testValidID() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateID("1");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidID")
    void testInvalidID() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateID("one");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testValidateBinIsDouble")
    void testValidateBinIsDouble() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("21");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testValidateBinWithOneDigit")
    void testValidateBinWithOneDigit() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("1");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testValidateBinWithThreeDigits")
    void testValidateBinWithThreeDigits() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("100");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidBinIsntDouble")
    void testInValidBinIsntDouble() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("one");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testValidBinHasTwoDigitsAfterDecimal")
    void testValidBinHasTwoDigitsAfterDecimal() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("21.32");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidBinHasLessThanTwoDigitsAfterDecimal")
    void testInvalidBinHasLessThanTwoDigitsAfterDecimal() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("21.3");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidBinHasMoreThanTwoDigitsAfterDecimal")
    void testInvalidBin() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("3.14159");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidBinHasNonnumericAfterDecimal")
    void testInvalidBinHasNonnumericAfterDecimal() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("20.3t");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidBinHasNonnumericBeforeDecimal")
    void testInvalidBinHasNonnumericBeforeDecimal() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateBin("8t.21");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidBinIsNegative")
    void testInvalidBinIsNegative() {
        AuctionValidator validator = new AuctionValidator();
        assertFalse(validator.validateBin("-5"));
    }

    @Test
    @DisplayName("testInvalidBinIsNegativeAndHasTwoDecimalPlaces")
    void testInvalidBinIsNegativeAndHasTwoDecimalPlaces() {
        AuctionValidator validator = new AuctionValidator();
        assertFalse(validator.validateBin("-5.00"));
    }

    @Test
    @DisplayName("testValidateNameIsntEmpty")
    void testValidateNameIsntEmpty() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateName("name");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testValidTime")
    void testValidTime() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateTime("14:43:02");
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidTimeIsBlank")
    void testInvalidTimeIsBlank() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateTime("");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidTimeWithMaxTime")
    void testInvalidTime() {
        AuctionValidator validator = new AuctionValidator();
        LocalTime time = LocalTime.MAX;
        boolean actual = validator.validateTime(time.toString());
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidTimeWithMinTime")
    void testInvalidTimeWithMinTime() {
        AuctionValidator validator = new AuctionValidator();
        LocalTime time = LocalTime.MIN;
        boolean actual = validator.validateTime(time.toString());
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidTimeWithInvalidHours")
    void testInvalidTimeWithValidHours() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateTime("99:59:59");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidTimeWithInvalidMinutes")
    void testInvalidTimeWithInvalidMinutes() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateTime("12:90:12");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidTimeWithInvalidSeconds")
    void testInvalidTimeWithInvalidSeconds() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateTime("12:59:90");
        assertFalse(actual);
    }

    @Test
    @DisplayName("testValidDate")
    void testValidDate() {
        AuctionValidator validator = new AuctionValidator();
        String startDate = "2024-11-21";
        String endDate = "2024-11-22";
        String startTime = "12:12:12";
        String endTime = "12:12:12";
        boolean actual = validator.validateDate(startDate, endDate, startTime, endTime);
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidStartDateDayAfterEndDate")
    void testInvalidStartDateAfterEndDate() {
        AuctionValidator validator = new AuctionValidator();
        String startDate = "2024-11-22";
        String endDate = "2024-11-21";
        String startTime = "12:12:12";
        String endTime = "12:12:12";
        boolean actual = validator.validateDate(startDate, endDate, startTime, endTime);
        assertFalse(actual);
    }

    @Test
    @DisplayName("testValidateSameDatesWithDifferentTimes")
    void testValidateSameDatesWithDifferentTimes() {
        AuctionValidator validator = new AuctionValidator();
        String startDate = "2024-11-21";
        String endDate = "2024-11-21";
        String startTime = "12:12:12";
        String endTime = "13:13:13";
        boolean actual = validator.validateDate(startDate, endDate, startTime, endTime);
        assertTrue(actual);
    }

    @Test
    @DisplayName("testInvalidateSameDatesWithSameTimes")
    void testInvalidateSameDatesWithSameTimes() {
        AuctionValidator validator = new AuctionValidator();
        String startDate = "2024-11-21";
        String endDate = "2024-11-21";
        String startTime = "12:12:12";
        String endTime = "12:12:12";
        boolean actual = validator.validateDate(startDate, endDate, startTime, endTime);
        assertFalse(actual);
    }

    @Test
    @DisplayName("testInvalidateNegativeBid")
    void testInvalidateNegativeBid() {
        AuctionValidator validator = new AuctionValidator();
        String bid = "-5.00";
        assertFalse(validator.validateBid(bid));
    }

    @Test
    @DisplayName("testValidBid")
    void testValidBid() {
        AuctionValidator validator = new AuctionValidator();
        String bid = "5.00";
        assertTrue(validator.validateBid(bid));
    }

    @Test
    @DisplayName("testInvalidZeroBid")
    void testInvalidZeroBid() {
        AuctionValidator validator = new AuctionValidator();
        String bid = "0.00";
        assertFalse(validator.validateBid(bid));
    }
}

