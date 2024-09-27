package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code MostFrequenceParity} class represents a validator that checks whether the parity distribution
 * of even and odd numbers is the same in the proposed code and the secret code. The validator aims to identify
 * whether the majority of numbers in both codes are even or odd.
 */
public class MostFrequenceParity extends Validator {

    /**
     * Constructs a {@code MostFrequenceParity} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public MostFrequenceParity(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 16) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks whether the parity distribution of even and odd numbers is the same in the proposed code and the secret code.
     * The validator aims to identify whether the majority of numbers in both codes are even or odd.
     *
     * @return {@code true} if the parity distribution is the same, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        int nbPair_secret = countEvenNumbers(first_secret, second_secret, third_secret);
        int nbPair_code = countEvenNumbers(first, second, third);

        int nbOdd_secret = 3 - nbPair_secret;
        int nbOdd_code = 3 - nbPair_code;

        return ((nbPair_secret > nbOdd_secret) && (nbPair_code > nbOdd_code)) || ((nbPair_secret < nbOdd_secret) && (nbPair_code < nbOdd_code));
    }

    /**
     * Counts the number of even numbers in the given array of values.
     *
     * @param values The array of values in which even numbers are counted.
     * @return The number of even numbers in the array.
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
