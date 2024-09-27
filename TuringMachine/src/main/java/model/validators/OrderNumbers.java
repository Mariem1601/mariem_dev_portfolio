package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code OrderNumbers} class represents a validator that checks whether the order of numbers
 * in the proposed code is the same as the order of numbers in the secret code. The validator aims
 * to identify if the numbers are in ascending or descending order.
 */
public class OrderNumbers extends Validator {

    /**
     * Constructs an {@code OrderNumbers} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public OrderNumbers(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator != 22) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks whether the order of numbers in the proposed code is the same as the order of numbers in the secret code.
     * The validator aims to identify if the numbers are in ascending or descending order.
     *
     * @return {@code true} if the order is the same, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        return checkOrder(first_secret, second_secret, third_secret) == checkOrder(first, second, third);
    }

    /**
     * Checks the order of three numbers and returns a code indicating whether they are in ascending,
     * descending, or no particular order.
     *
     * @param nb1 The first number.
     * @param nb2 The second number.
     * @param nb3 The third number.
     * @return An integer code: 1 for ascending order, 2 for descending order, and 0 for no particular order.
     */
    private int checkOrder(int nb1, int nb2, int nb3) {
        if (nb2 >= nb1 && nb3 >= nb2) {
            return 1; // Ascending order
        } else if (nb3 <= nb2 && nb2 <= nb1) {
            return 2; // Descending order
        }
        return 0; // No particular order
    }

}

