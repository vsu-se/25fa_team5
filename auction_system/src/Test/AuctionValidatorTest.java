package Test;

import static org.junit.jupiter.api.Assertions.*;

import auction_system.AuctionValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("testValidateNameIsntEmpty")
    void testValidateNameIsntEmpty() {
        AuctionValidator validator = new AuctionValidator();
        boolean actual = validator.validateName("name");
        assertTrue(actual);
    }
}

