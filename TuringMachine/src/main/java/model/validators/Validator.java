package model.validators;

import model.Code;
import model.TurningMachineException;

/**
 * An abstract class representing a validator used in the game.
 */
public abstract class Validator {

    /**
     * The number associated with the validator.
     */
    protected final int nbValidator;

    /**
     * The first secret digit of the validator.
     */
    protected final int first_secret;

    /**
     * The second secret digit of the validator.
     */
    protected final int second_secret;

    /**
     * The third secret digit of the validator.
     */
    protected final int third_secret;

    /**
     * The first digit of the proposed code.
     */
    protected final int first;

    /**
     * The second digit of the proposed code.
     */
    protected final int second;

    /**
     * The third digit of the proposed code.
     */
    protected final int third;

    /**
     * Constructs a validator with the specified secret code, proposed code, and validator number.
     *
     * @param secretCode    The secret code against which the proposed code is checked.
     * @param proposedCode  The code proposed by the player.
     * @param nbValidator   The number associated with the validator.
     * @throws TurningMachineException If the validator number is invalid.
     */
    public Validator(Code secretCode, Code proposedCode, int nbValidator) {
        if (nbValidator < 1 || nbValidator > 22) {
            throw new TurningMachineException("Invalid validator");
        }
        this.nbValidator = nbValidator;

        this.first_secret = secretCode.getFirst();
        this.second_secret = secretCode.getSecond();
        this.third_secret = secretCode.getThird();
        this.first = proposedCode.getFirst();
        this.second = proposedCode.getSecond();
        this.third = proposedCode.getThird();
    }

    /**
     * Checks the proposed code against the validator.
     *
     * @return True if the proposed code is valid according to the validator, false otherwise.
     */
    public abstract boolean checkCodeWithValidator();

}

