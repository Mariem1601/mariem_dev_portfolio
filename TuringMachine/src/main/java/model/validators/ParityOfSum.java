package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code ParityOfSum} class represents a validator that checks whether the parity (even or odd)
 * of the sum of numbers in the proposed code matches the parity of the sum of numbers in the secret code.
 */
public class ParityOfSum extends Validator {

    /**
     * Constructs a {@code ParityOfSum} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public ParityOfSum(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 18) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks whether the parity (even or odd) of the sum of numbers in the proposed code matches
     * the parity of the sum of numbers in the secret code.
     *
     * @return {@code true} if the parity of the sums matches, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        int sum_secret = sum(first_secret, second_secret, third_secret);
        int sum_code = sum(first, second, third);

        return (sum_secret % 2 == 0) == (sum_code % 2 == 0);
    }

    /**
     * Calculates the sum of an array of numbers.
     *
     * @param values The array of numbers.
     * @return The sum of the numbers.
     */
    private int sum(int... values) {
        int count = 0;
        for (int value : values) {
            count += value;
        }
        return count;
    }
}

