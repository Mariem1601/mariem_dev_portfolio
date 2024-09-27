package turingMachine.model.validators;

import static org.junit.jupiter.api.Assertions.*;

import model.Code;
import model.TurningMachineException;
import model.validators.CompareTwoNumbers;
import org.junit.jupiter.api.Test;

public class CompareTwoNumbersTest {

    @Test
    public void testCompareFirstAndSecond() {
        Code secretCode = new Code(135);
        Code code = new Code(124);
        int nbValidator = 11;

        CompareTwoNumbers validator = new CompareTwoNumbers(secretCode, code, nbValidator);
        assertTrue(validator.checkCodeWithValidator(), "Failed to compare first and second digits.");
    }

    @Test
    public void testCompareFirstAndThird() {
        Code secretCode = new Code(352);
        Code code = new Code(311);
        int nbValidator = 12;

        CompareTwoNumbers validator = new CompareTwoNumbers(secretCode, code, nbValidator);
        assertTrue(validator.checkCodeWithValidator(), "Failed to compare first and third digits.");
    }

    @Test
    public void testCompareSecondAndThird() {
        Code secretCode = new Code(451);
        Code code = new Code(342);
        int nbValidator = 13;

        CompareTwoNumbers validator = new CompareTwoNumbers(secretCode, code, nbValidator);
        assertTrue(validator.checkCodeWithValidator(), "Failed to compare second and third digits.");
    }

    @Test
    public void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);
        Code code = new Code(452);
        int nbValidator = 14;

        assertThrows(TurningMachineException.class, () -> {
            new CompareTwoNumbers(secretCode, code, nbValidator);
        }, "Expected TurningMachineException for invalid validator number.");
    }

    @Test
    public void testInvalidCode() {
        assertThrows(TurningMachineException.class, () -> {
            new Code(678);
        }, "Expected TurningMachineException for invalid code.");
    }
}
