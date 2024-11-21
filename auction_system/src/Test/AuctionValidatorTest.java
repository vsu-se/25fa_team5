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
}

