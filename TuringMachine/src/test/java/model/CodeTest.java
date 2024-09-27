package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeTest {

    @Test
    void testValidCode() {
        Code validCode = new Code(345);
        assertEquals(3, validCode.getFirst());
        assertEquals(4, validCode.getSecond());
        assertEquals(5, validCode.getThird());
    }

    @Test
    void testInvalidCode() {
        assertThrows(TurningMachineException.class, () -> {
            new Code(678); // Invalid code with digits greater than 5
        });
    }

    @Test
    void testEquals() {
        Code code1 = new Code(123);
        Code code2 = new Code(123);
        Code code3 = new Code(455);

        assertTrue(code1.equals(code2)); // Same codes should be equal
        assertFalse(code1.equals(code3)); // Different codes should not be equal
    }

    @Test
    void testCopyConstructor() {
        Code originalCode = new Code(345);
        Code copiedCode = new Code(originalCode);

        assertEquals(originalCode.getFirst(), copiedCode.getFirst());
        assertEquals(originalCode.getSecond(), copiedCode.getSecond());
        assertEquals(originalCode.getThird(), copiedCode.getThird());
    }
}