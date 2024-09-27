package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.MostFrequenceParity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MostFrequenceParityTest {

    @Test
    public void testBothCodesHaveMorePairs() {
        Code secretCode = new Code(242);
        Code code = new Code(442);
        MostFrequenceParity validator = new MostFrequenceParity(secretCode, code, 16);
        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    public void testBothCodesHaveMoreOdds() {
        Code secretCode = new Code(135);
        Code code = new Code(351);
        MostFrequenceParity validator = new MostFrequenceParity(secretCode, code, 16);
        assertTrue(validator.checkCodeWithValidator());
    }

    @Test
    public void testBothCodesHaveEqualPairsAndOdds() {
        Code secretCode = new Code(242);
        Code code = new Code(135);
        MostFrequenceParity validator = new MostFrequenceParity(secretCode, code, 16);
        assertFalse(validator.checkCodeWithValidator());
    }

    @Test
    public void testSecretCodeHasMorePairs() {
        Code secretCode = new Code(241);
        Code code = new Code(135);
        MostFrequenceParity validator = new MostFrequenceParity(secretCode, code, 16);
        assertFalse(validator.checkCodeWithValidator());
    }

    @Test
    public void testSecretCodeHasMoreOdds() {
        Code secretCode = new Code(135);
        Code code = new Code(241);
        MostFrequenceParity validator = new MostFrequenceParity(secretCode, code, 16);
        assertFalse(validator.checkCodeWithValidator());
    }

    @Test
    public void testInvalidValidatorNumber() {
        Code secretCode = new Code(135);
        Code code = new Code(242);
        assertThrows(TurningMachineException.class, () -> new MostFrequenceParity(secretCode, code, 15));
    }
}
