package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code RepetitionNumber} class represents a validator that checks the repetition of numbers
 * in the proposed code compared to the repetition in the secret code.
 */
public class RepetitionNumber extends Validator {

    /**
     * Constructs a {@code RepetitionNumber} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public RepetitionNumber(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 20) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks the repetition of numbers in the proposed code compared to the repetition in the secret code.
     *
     * @return {@code true} if the repetition matches, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        int repetition_secret = repetition(first_secret, second_secret, third_secret);
        int repetition_code = repetition(first, second, third);

        return repetition_code == repetition_secret;
    }

    /**
     * Calculates the repetition count of numbers in an array.
     *
     * @param values The array of numbers.
     * @return The repetition count.
     */
    private int repetition(int... values) {
        int count = 0;
        if ((values[0] == values[1]) && (values[1] == values[2])) {
            count = 3;
        } else if ((values[0] == values[1]) || (values[0] == values[2]) || (values[1] == values[2])) {
            count = 2;
        }
        return count;
    }
}
