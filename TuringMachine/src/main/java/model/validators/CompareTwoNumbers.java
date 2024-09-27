package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CompareTwoNumbers} class represents a validator that compares two pairs of digits
 * in the secret code against the corresponding pairs of digits in the proposed code.
 * This validator checks if each pair of digits meets a specified condition.
 */
public class CompareTwoNumbers extends Validator {

    /**
     * Constructs a {@code CompareTwoNumbers} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CompareTwoNumbers(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator > 13 || nbValidator < 11) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Compares two pairs of digits in the secret code against the corresponding pairs of digits in the proposed code.
     * The condition for the comparison is specified in the validator logic.
     *
     * @return {@code true} if each pair of digits meets the specified condition, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 11 -> {
                return checkValidator(first_secret, second_secret, first, second);
            }
            case 12 -> {
                return checkValidator(first_secret, third_secret, first, third);
            }
            case 13 -> {
                return checkValidator(second_secret, third_secret, second, third);
            }
        }
        throw new TurningMachineException("Invalid Validator.");
    }

    /**
     * Checks the condition specified for comparing two pairs of digits.
     *
     * @param secret1 The first digit of the secret code pair.
     * @param secret2 The second digit of the secret code pair.
     * @param nb1     The first digit of the proposed code pair.
     * @param nb2     The second digit of the proposed code pair.
     * @return {@code true} if each pair of digits meets the specified condition, {@code false} otherwise.
     */
    private boolean checkValidator(int secret1, int secret2, int nb1, int nb2) {
        return (secret1 < secret2 && nb1 < nb2) ||
                (secret1 == secret2 && nb1 == nb2) ||
                (secret1 > secret2 && nb1 > nb2);
    }
}
