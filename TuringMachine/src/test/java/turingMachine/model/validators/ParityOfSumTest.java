package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.ParityOfSum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParityOfSumTest {

    @Test
    public void testValidEvenSum() {
        Code secretCode = new Code(123);
        Code code = new Code(451);
        ParityOfSum parityOfSumValidator = new ParityOfSum(secretCode, code, 18);

        assertTrue(parityOfSumValidator.checkCodeWithValidator());
    }

    @Test
    public void testValidOddSum() {
        Code secretCode = new Code(111);
        Code code = new Code(333);
        ParityOfSum parityOfSumValidator = new ParityOfSum(secretCode, code, 18);

        assertTrue(parityOfSumValidator.checkCodeWithValidator());
    }

    @Test
    public void testInvalidNumberForValidator() {
        Code secretCode = new Code(123);
        Code code = new Code(453);

        TurningMachineException exception = assertThrows(TurningMachineException.class, () -> {
            new ParityOfSum(secretCode, code, 17);
        });

        assertEquals("Invalid number for this validator.", exception.getMessage());
    }

    @Test
    public void testInvalidEvenSum() {
        Code secretCode = new Code(111);
        Code code = new Code(222);
        ParityOfSum parityOfSumValidator = new ParityOfSum(secretCode, code, 18);

        assertFalse(parityOfSumValidator.checkCodeWithValidator());
    }

    @Test
    public void testInvalidOddSum() {
        Code secretCode = new Code(123);
        Code code = new Code(452);
        ParityOfSum parityOfSumValidator = new ParityOfSum(secretCode, code, 18);

        assertFalse(parityOfSumValidator.checkCodeWithValidator());
    }
}
