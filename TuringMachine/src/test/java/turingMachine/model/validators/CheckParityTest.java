package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CheckParity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckParityTest {

    @Test
    void testCheckParityFirstDigit() {
        Code secretCode = new Code(345);
        Code code = new Code(141);
        CheckParity checkParity = new CheckParity(secretCode, code, 5);
        assertTrue(checkParity.checkCodeWithValidator());
    }

    @Test
    void testCheckParitySecondDigit() {
        Code secretCode = new Code(345);
        Code code = new Code(225);
        CheckParity checkParity = new CheckParity(secretCode, code, 6);
        assertTrue(checkParity.checkCodeWithValidator());
    }

    @Test
    void testCheckParityThirdDigit() {
        Code secretCode = new Code(345);
        Code code = new Code(253);
        CheckParity checkParity = new CheckParity(secretCode, code, 7);
        assertTrue(checkParity.checkCodeWithValidator());
    }

    @Test
    void testCheckParityFirstDigitFalse() {
        Code secretCode = new Code(345);
        Code code = new Code(245);
        CheckParity checkParity = new CheckParity(secretCode, code, 5);
        assertFalse(checkParity.checkCodeWithValidator());
    }

    @Test
    void testCheckParitySecondDigitFalse() {
        Code secretCode = new Code(345);
        Code code = new Code(214);
        CheckParity checkParity = new CheckParity(secretCode, code, 6);
        assertFalse(checkParity.checkCodeWithValidator());
    }

    @Test
    void testCheckParityThirdDigitFalse() {
        Code secretCode = new Code(345);
        Code code = new Code(254);
        CheckParity checkParity = new CheckParity(secretCode, code, 7);
        assertFalse(checkParity.checkCodeWithValidator());
    }
}
