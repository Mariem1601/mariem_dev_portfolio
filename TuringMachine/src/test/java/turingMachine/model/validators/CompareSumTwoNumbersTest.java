package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CompareSumTwoNumbers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompareSumTwoNumbersTest {

    @Test
    void testSumLessThan6() {
        Code secretCode = new Code(123);
        Code code = new Code(145);
        CompareSumTwoNumbers validator = new CompareSumTwoNumbers(secretCode, code, 19);

        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testSumEquals6() {
        Code secretCode = new Code(245);
        Code code = new Code(334);
        CompareSumTwoNumbers validator = new CompareSumTwoNumbers(secretCode, code, 19);

        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testSumGreaterThan6() {
        Code secretCode = new Code(435);
        Code code = new Code(451);
        CompareSumTwoNumbers validator = new CompareSumTwoNumbers(secretCode, code, 19);

        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);
        Code code = new Code(145);

         TurningMachineException exception = assertThrows(TurningMachineException.class, () -> {
            CompareSumTwoNumbers validator = new CompareSumTwoNumbers(secretCode, code, 1); // Invalid validator number
        });

        assertEquals("Invalid validator", exception.getMessage());
    }
}
