package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * A factory class for creating instances of the {@link Validator} class based on the provided parameters.
 */
public class ValidatorFactory {

    /**
     * Creates a validator based on the provided secret code, proposed code, and validator number.
     *
     * @param secretCode   The secret code against which the proposed code is checked.
     * @param code         The code proposed by the player.
     * @param nbValidator  The number associated with the validator.
     * @return A specific type of validator based on the provided validator number.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public static Validator createValidator(Code secretCode, Code code, int nbValidator) {
        switch (nbValidator) {
            case 1, 2, 3, 4 -> {
                return new CompareOneNumber(secretCode, code, nbValidator);
            }
            case 5, 6, 7 -> {
                return new CheckParity(secretCode, code, nbValidator);
            }
            case 8, 9, 10 -> {
                return new CountOneNumber(secretCode, code, nbValidator);
            }
            case 11, 12, 13 -> {
                return new CompareTwoNumbers(secretCode, code, nbValidator);
            }
            case 14, 15 -> {
                return new ExtremumNumber(secretCode, code, nbValidator);
            }
            case 16 -> {
                return new MostFrequenceParity(secretCode, code, nbValidator);
            }
            case 17 -> {
                return new CountPair(secretCode, code, nbValidator);
            }
            case 18 -> {
                return new ParityOfSum(secretCode, code, nbValidator);
            }
            case 19 -> {
                return new CompareSumTwoNumbers(secretCode, code, nbValidator);
            }
            case 20 -> {
                return new RepetitionNumber(secretCode, code, nbValidator);
            }
            case 21 -> {
                return new TwinNumber(secretCode, code, nbValidator);
            }
            case 22 -> {
                return new OrderNumbers(secretCode, code, nbValidator);
            }
            default -> throw new TurningMachineException("Invalid validator.");
        }
    }

}
