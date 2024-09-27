package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CountOneNumber} class represents a validator that counts occurrences of a specific number
 * in both the secret code and the proposed code. This validator checks if the count is the same for the specified number.
 */
public class CountOneNumber extends Validator {

    /**
     * Constructs a {@code CountOneNumber} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CountOneNumber(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator > 10 || nbValidator < 8) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Counts occurrences of a specific number in both the secret code and the proposed code.
     * Checks if the count is the same for the specified number.
     *
     * @return {@code true} if the count is the same for the specified number, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 8 -> {
                return checkValidator(1);
            }
            case 9 -> {
                return checkValidator(3);
            }
            case 10 -> {
                return checkValidator(4);
            }
        }
        throw new TurningMachineException("Invalid Validator.");
    }

    /**
     * Checks if the count is the same for the specified number in both the secret code and the proposed code.
     *
     * @param number The specified number to count.
     * @return {@code true} if the count is the same for the specified number, {@code false} otherwise.
     */
    private boolean checkValidator(int number) {
        int count1 = countOccurrences(number, first_secret, second_secret, third_secret);
        int count2 = countOccurrences(number, first, second, third);

        return count1 == count2;
    }

    /**
     * Counts occurrences of a specific number in an array of values.
     *
     * @param number The specified number to count.
     * @param values The array of values in which occurrences are counted.
     * @return The count of occurrences of the specified number in the array.
     */
    private int countOccurrences(int number, int... values) {
        int count = 0;
        for (int value : values) {
            if (value == number) {
                count++;
            }
        }
        return count;
    }
}
