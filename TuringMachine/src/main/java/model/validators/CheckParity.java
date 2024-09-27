package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CheckParity} class represents a validator that checks the parity of a specific digit
 * in the secret code against the corresponding digit in the proposed code.
 * This validator has different behavior based on the associated validator number.
 */
public class CheckParity extends Validator {

    /**
     * Constructs a {@code CheckParity} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CheckParity(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator > 7 || nbValidator < 5) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks the parity of a specific digit in the secret code against the corresponding digit in the proposed code.
     * The behavior depends on the associated validator number.
     *
     * @return {@code true} if the parity of the digits matches, {@code false} otherwise.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 5 -> {
                return checkValidator(first_secret, first);
            }
            case 6 -> {
                return checkValidator(second_secret, second);
            }
            case 7 -> {
                return checkValidator(third_secret, third);
            }
        }
        throw new TurningMachineException("Invalid Validator.");
    }

    /**
     * Checks the parity of the given digits.
     *
     * @param digit_secretCode The digit from the secret code.
     * @param digit_code       The corresponding digit from the proposed code.
     * @return {@code true} if the parity of the digits matches, {@code false} otherwise.
     */
    private boolean checkValidator(int digit_secretCode, int digit_code) {
        return (digit_secretCode % 2 == 0 && digit_code % 2 == 0) ||
                (digit_secretCode % 2 != 0 && digit_code % 2 != 0);
    }
}
