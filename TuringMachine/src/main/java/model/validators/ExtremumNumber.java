package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * The {@code ExtremumNumber} class represents a validator that checks whether the position of the extreme
 * (maximum or minimum) number in the proposed code matches the position of the extreme number in the secret code.
 * The validator is parameterized to find either the maximal or minimal value.
 */
public class ExtremumNumber extends Validator {

    /**
     * Constructs an {@code ExtremumNumber} validator with the specified secret code, proposed code,
     * and validator number. Throws an exception if the validator number is invalid for this type of validator.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid for this type of validator.
     */
    public ExtremumNumber(Code secretCode, Code code, int nbValidator) {
        super(secretCode, code, nbValidator);
        if (nbValidator > 15 || nbValidator < 14) {
            throw new TurningMachineException("Invalid number for this validator.");
        }
    }

    /**
     * Checks whether the position of the extreme (maximum or minimum) number in the proposed code matches
     * the position of the extreme number in the secret code. The validator is parameterized to find either
     * the maximal or minimal value.
     *
     * @return {@code true} if the positions match, {@code false} otherwise.
     */
    public boolean checkCodeWithValidator() {
        switch (nbValidator) {
            case 14 -> {
                return checkValidator(false);
            }
            case 15 -> {
                return checkValidator(true);
            }
        }
        throw new TurningMachineException("Invalid Validator.");
    }

    /**
     * Checks whether the position of the extreme (maximum or minimum) number in the proposed code matches
     * the position of the extreme number in the secret code. The validator is parameterized to find either
     * the maximal or minimal value.
     *
     * @param findMaximal If {@code true}, find the maximal value; otherwise, find the minimal value.
     * @return {@code true} if the positions match, {@code false} otherwise.
     */
    private boolean checkValidator(boolean findMaximal) {
        if (findMaximal) {
            return findExtremeIndex(true, first_secret, second_secret, third_secret) == findExtremeIndex(true, first, second, third);
        } else {
            return findExtremeIndex(false, first_secret, second_secret, third_secret) == findExtremeIndex(false, first, second, third);
        }
    }

    /**
     * Finds the index of the extreme (maximum or minimum) number in the array of values.
     *
     * @param findMaximal If {@code true}, find the maximal value; otherwise, find the minimal value.
     * @param values      The array of values in which the extreme index is found.
     * @return The index of the extreme number in the array.
     */
    private int findExtremeIndex(boolean findMaximal, int... values) {
        int extremeIndex = 0;

        for (int i = 1; i < values.length; i++) {
            if ((findMaximal && values[i] > values[extremeIndex]) ||
                    (!findMaximal && values[i] < values[extremeIndex])) {
                extremeIndex = i;
            }
        }

        return extremeIndex;
    }
}
