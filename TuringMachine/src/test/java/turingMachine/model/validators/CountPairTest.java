package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.CountPair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountPairTest {

    @Test
    void testCountPairWithEvenNumbers() {
        Code secretCode = new Code(242);
        Code code = new Code(424);

        CountPair countPairValidator = new CountPair(secretCode, code, 17);
        boolean result = countPairValidator.checkCodeWithValidator();

        assertTrue(result, "Expected even numbers count to match.");
    }

    @Test
    void testCountPairWithOddNumbers() {
        Code secretCode = new Code(135);
        Code code = new Code(113);

        CountPair countPairValidator = new CountPair(secretCode, code, 17);
        boolean result = countPairValidator.checkCodeWithValidator();

        assertTrue(result, "Expected even numbers count to match.");
    }

    @Test
    void testCountPairWithEqualEvenNumbers() {
        Code secretCode = new Code(244);
        Code code = new Code(244);

        CountPair countPairValidator = new CountPair(secretCode, code, 17);
        boolean result = countPairValidator.checkCodeWithValidator();

        assertTrue(result, "Expected even numbers count to match.");
    }

    @Test
    void testCountPairWithEqualOddNumbers() {
        Code secretCode = new Code(135);
        Code code = new Code(135);

        CountPair countPairValidator = new CountPair(secretCode, code, 17);
        boolean result = countPairValidator.checkCodeWithValidator();

        assertTrue(result, "Expected even numbers count to match.");
    }

    @Test
    void testCountPairWithMixedNumbers() {
        Code secretCode = new Code(135);
        Code code = new Code(241);

        CountPair countPairValidator = new CountPair(secretCode, code, 17);
        boolean result = countPairValidator.checkCodeWithValidator();

        assertFalse(result, "Expected even numbers count not to match.");
    }

}
