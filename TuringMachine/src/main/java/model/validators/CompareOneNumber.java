package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CompareOneNumber} class represents a validator that compares one digit
 * in the secret code against the corresponding digit in the proposed code.
 * This validator has different behavior based on the associated validator number.
 */
public class CompareOneNumber extends Validator {

    /**
     * Constructs a {@code CompareOneNumber} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CompareOneNumber(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator > 4 || nbValidator < 1) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Compares one digit in the secret code against the corresponding digit in the proposed code.
     * The behavior depends on the associated validator number.
     *
     * @return {@code true} if the digits match the specified condition, {@code false} otherwise.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 1 -> {
                return checkValidator(first_secret, first, 1);
            }
            case 2 -> {
                return checkValidator(first_secret, first, 3);
            }
            case 3 -> {
                return checkValidator(second_secret, second, 3);
            }
            case 4 -> {
                return checkValidator(second_secret, second, 4);
            }
        }
        throw new TurningMachineException("Invalid Validator.");
    }

    /**
     * Checks the condition specified for comparing the digits.
     *
     * @param digit_secretCode The digit from the secret code.
     * @param digit_code       The corresponding digit from the proposed code.
     * @param number           The threshold number for the comparison.
     * @return {@code true} if the digits match the specified condition, {@code false} otherwise.
     */
    private boolean checkValidator(int digit_secretCode, int digit_code, int number) {
        return (digit_secretCode < number && digit_code < number) ||
                (digit_secretCode == number && digit_code == number) ||
                (digit_secretCode > number && digit_code > number);
    }
}
