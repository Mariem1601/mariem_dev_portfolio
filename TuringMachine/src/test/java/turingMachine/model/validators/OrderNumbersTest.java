package turingMachine.model.validators;

import model.Code;
import model.TurningMachineException;
import model.validators.OrderNumbers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderNumbersTest {

    @Test
    void testAscendingOrder() {
        Code secretCode = new Code(123);
        Code code = new Code(123);

        OrderNumbers orderNumbersValidator = new OrderNumbers(secretCode, code, 22);

        assertTrue(orderNumbersValidator.checkCodeWithValidator());
    }

    @Test
    void testDescendingOrder() {
        Code secretCode = new Code(543);
        Code code = new Code(543);

        OrderNumbers orderNumbersValidator = new OrderNumbers(secretCode, code, 22);

        assertTrue(orderNumbersValidator.checkCodeWithValidator());
    }

    @Test
    void testRandomOrder() {
        Code secretCode = new Code(321);
        Code code = new Code(213);

        OrderNumbers orderNumbersValidator = new OrderNumbers(secretCode, code, 22);

        assertFalse(orderNumbersValidator.checkCodeWithValidator());
    }

    @Test
    void testEqualNumbers() {
        Code secretCode = new Code(222);
        Code code = new Code(222);

        OrderNumbers orderNumbersValidator = new OrderNumbers(secretCode, code, 22);

        assertTrue(orderNumbersValidator.checkCodeWithValidator());
    }

    @Test
    void testInvalidValidatorNumber() {
        Code secretCode = new Code(123);
        Code code = new Code(123);

        assertThrows(TurningMachineException.class, () -> new OrderNumbers(secretCode, code, 1));
    }

    @Test
    void testInvalidCode() {
        assertThrows(TurningMachineException.class, () -> new Code(678));
    }
}
