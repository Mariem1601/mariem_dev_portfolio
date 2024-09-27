package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CountOneNumber;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountOneNumberTest {

    @Test
    void testCountOccurrencesOfOne() {
        Code secretCode = new Code(123);
        Code code = new Code(124);
        int nbValidator = 8;
        CountOneNumber countOneNumberValidator = new CountOneNumber(secretCode, code, nbValidator);

        boolean result = countOneNumberValidator.checkCodeWithValidator();

        assertTrue(result);
    }

    @Test
    void testCountOccurrencesOfThree() {
        Code secretCode = new Code(335);
        Code code = new Code(153);
        int nbValidator = 9;
        CountOneNumber countOneNumberValidator = new CountOneNumber(secretCode, code, nbValidator);

        boolean result = countOneNumberValidator.checkCodeWithValidator();

        assertFalse(result);
    }

    @Test
    void testCountOccurrencesOfFour() {
        Code secretCode = new Code(441);
        Code code = new Code(424);
        int nbValidator = 10;
        CountOneNumber countOneNumberValidator = new CountOneNumber(secretCode, code, nbValidator);

        boolean result = countOneNumberValidator.checkCodeWithValidator();

        assertTrue(result);
    }

    @Test
    void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);
        Code code = new Code(124);
        int invalidValidatorNumber = 11;

        assertThrows(TurningMachineException.class, () -> new CountOneNumber(secretCode, code, invalidValidatorNumber));
    }
}
