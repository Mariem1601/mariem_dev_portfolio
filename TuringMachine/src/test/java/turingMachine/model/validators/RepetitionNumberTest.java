package turingMachine.model.validators;

import static org.junit.jupiter.api.Assertions.*;

import model.Code;
import model.TurningMachineException;
import model.validators.RepetitionNumber;
import org.junit.jupiter.api.Test;

public class RepetitionNumberTest {

    @Test
    public void testRepetitionNumberWithMatchingCode() {
        // Arrange
        Code secretCode = new Code(112); // Example secret code with repetition
        Code code = new Code(113); // Example code with matching repetition
        int nbValidator = 20;

        // Act
        RepetitionNumber repetitionNumberValidator = new RepetitionNumber(secretCode, code, nbValidator);
        boolean result = repetitionNumberValidator.checkCodeWithValidator();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testRepetitionNumberWithNonMatchingCode() {
        // Arrange
        Code secretCode = new Code(123); // Example secret code without repetition
        Code code = new Code(452); // Example code without repetition
        int nbValidator = 20;

        // Act
        RepetitionNumber repetitionNumberValidator = new RepetitionNumber(secretCode, code, nbValidator);
        boolean result = repetitionNumberValidator.checkCodeWithValidator();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testRepetitionNumberWithInvalidValidatorNumber() {
        // Arrange
        Code secretCode = new Code(112);
        Code code = new Code(113);
        int nbValidator = 15; // Invalid validator number for RepetitionNumber

        // Act and Assert
        assertThrows(TurningMachineException.class, () -> new RepetitionNumber(secretCode, code, nbValidator));
    }
}
