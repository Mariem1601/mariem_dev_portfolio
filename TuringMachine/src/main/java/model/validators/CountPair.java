package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CountPair} class represents a validator that counts the number of pairs (even numbers)
 * in both the secret code and the proposed code. This validator checks if the count of pairs is the same.
 */
public class CountPair extends Validator {

    /**
     * Constructs a {@code CountPair} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CountPair(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 17) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Counts the number of pairs (even numbers) in both the secret code and the proposed code.
     * Checks if the count of pairs is the same.
     *
     * @return {@code true} if the count of pairs is the same, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        int nbPair_secret = countEvenNumbers(first_secret, second_secret, third_secret);
        int nbPair_code = countEvenNumbers(first, second, third);

        return nbPair_secret == nbPair_code;
    }

    /**
     * Counts the number of even numbers in an array of values.
     *
     * @param values The array of values in which the number of even numbers is counted.
     * @return The count of even numbers in the array.
     */
    private int countEvenNumbers(int... values) {
        int count = 0;
        for (int value : values) {
            if (value % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}

