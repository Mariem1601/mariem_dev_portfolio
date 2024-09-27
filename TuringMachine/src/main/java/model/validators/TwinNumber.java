package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code TwinNumber} class represents a validator that checks if there are twin numbers
 * in the proposed code compared to the twin numbers in the secret code.
 */
public class TwinNumber extends Validator {

    /**
     * Constructs a {@code TwinNumber} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public TwinNumber(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 21) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks if there are twin numbers in the proposed code compared to the twin numbers in the secret code.
     *
     * @return {@code true} if there are twin numbers, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        return isTwin(first_secret, second_secret, third_secret) == isTwin(first, second, third);
    }

    /**
     * Determines if there are twin numbers in an array.
     *
     * @param values The array of numbers.
     * @return {@code true} if there are twin numbers, {@code false} otherwise.
     */
    private boolean isTwin(int... values) {
        if ((values[0] == values[1]) && (values[1] == values[2])) {
            return false;
        }
        return (values[0] == values[1]) || (values[0] == values[2]) || (values[1] == values[2]);
    }
}

