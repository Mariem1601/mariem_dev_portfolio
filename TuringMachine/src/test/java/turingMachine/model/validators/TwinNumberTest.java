package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.TwinNumber;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TwinNumberTest {

    @Test
    public void testValidTwinNumber() {
        Code secretCode = new Code(123); // Example secret code
        Code validCode = new Code(132); // TwinNumber validator should pass
        TwinNumber twinNumberValidator = new TwinNumber(secretCode, validCode, 21);
        assertTrue(twinNumberValidator.checkCodeWithValidator());
    }

    @Test
    public void testInvalidTwinNumber() {
        Code secretCode = new Code(453); // Example secret code
        Code invalidCode = new Code(445); // TwinNumber validator should fail
        TwinNumber twinNumberValidator = new TwinNumber(secretCode, invalidCode, 21);
        assertFalse(twinNumberValidator.checkCodeWithValidator());
    }

    @Test
    public void testInvalidValidatorNumber() {
        Code secretCode = new Code(123); // Example secret code
        Code validCode = new Code(132); // TwinNumber validator should pass
        try {
            TwinNumber twinNumberValidator = new TwinNumber(secretCode, validCode, 22); // Invalid validator number
            fail("Expected TurningMachineException");
        } catch (TurningMachineException e) {
            assertEquals("Invalid number for this validator.", e.getMessage());
        }
    }

    @Test
    public void testInvalidCode() {
        // Creating a code with an invalid combination
        try {
            Code invalidCode = new Code(666);
            fail("Expected TurningMachineException");
        } catch (TurningMachineException e) {
            assertEquals("The code proposed is invalid.", e.getMessage());
        }
    }

    @Test
    public void testEqualsMethod() {
        Code code1 = new Code(123);
        Code code2 = new Code(123);
        assertTrue(code1.equals(code2));
    }
}
