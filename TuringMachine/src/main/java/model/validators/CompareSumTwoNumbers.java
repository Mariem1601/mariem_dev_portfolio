package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code CompareSumTwoNumbers} class represents a validator that compares the sum of two digits
 * in the secret code against the sum of the corresponding digits in the proposed code.
 * This validator checks if the sums meet a specified condition.
 */
public class CompareSumTwoNumbers extends Validator {

    /**
     * Constructs a {@code CompareSumTwoNumbers} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public CompareSumTwoNumbers(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 19) {
            throw new TurningMachineException("Invalid validator");
        }
    }

    /**
     * Compares the sum of two digits in the secret code against the sum of the corresponding digits in the proposed code.
     * The condition for the comparison is specified in the validator logic.
     *
     * @return {@code true} if the sums match the specified condition, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        int sum_code = first + second;
        int sum_secret = first_secret + second_secret;
        return checkValidator(sum_code, sum_secret);
    }

    /**
     * Checks the condition specified for comparing the sums of two numbers.
     *
     * @param nb1 The sum of the digits from the proposed code.
     * @param nb2 The sum of the digits from the secret code.
     * @return {@code true} if the sums match the specified condition, {@code false} otherwise.
     */
    private boolean checkValidator(int nb1, int nb2) {
        return (nb1 < 6 && nb2 < 6) ||
                (nb1 == 6 && nb2 == 6) ||
                (nb1 > 6 && nb2 > 6);
    }
}
