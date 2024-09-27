package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CompareOneNumber;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompareOneNumberTest {

    @Test
    void testCompareFirstDigitWithOne() {
        Code secretCode = new Code(123);
        Code code = new Code(153);
        CompareOneNumber validator = new CompareOneNumber(secretCode, code, 1);
        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testCompareFirstDigitWithThree() {
        Code secretCode = new Code(452);
        Code code = new Code(312);
        CompareOneNumber validator = new CompareOneNumber(secretCode, code, 2);
        assertFalse(validator.checkCodeWithValidator());
    }

    @Test
    void testCompareSecondDigitWithThree() {
        Code secretCode = new Code(321);
        Code code = new Code(523);
        CompareOneNumber validator = new CompareOneNumber(secretCode, code, 3);
        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testCompareSecondDigitWithFour() {
        Code secretCode = new Code(231);
        Code code = new Code(124);
        CompareOneNumber validator = new CompareOneNumber(secretCode, code, 4);
        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);
        Code code = new Code(454);
        assertThrows(TurningMachineException.class, () -> new CompareOneNumber(secretCode, code, 6));
    }
}
