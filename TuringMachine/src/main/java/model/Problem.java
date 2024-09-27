package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Problem} class represents a problem in the game, including the problem number, secret code, and validators.
 */
public class Problem {

    /** The number of the problem. */
    private final int numberProblem;

    /** The secret code for the problem. */
    private final Code secretCode;

    /** The list of validators associated with the problem. */
    private final List<Integer> validators;

    /**
     * Constructs a {@code Problem} object with the specified problem number, secret code, and validators.
     *
     * @param numberProblem The number of the problem.
     * @param secretCode The secret code for the problem.
     * @param validators The list of validators associated with the problem.
     */
    public Problem(int numberProblem, String secretCode, List<Integer> validators) {
        this.numberProblem = numberProblem;
        this.secretCode = new Code(Integer.parseInt(secretCode));
        this.validators = new ArrayList<>(validators);
    }

    /**
     * Retrieves the number of the problem.
     *
     * @return The number of the problem.
     */
    public int getNumberProblem() {
        return numberProblem;
    }

    /**
     * Retrieves the secret code for the problem.
     *
     * @return The secret code for the problem.
     */
    public Code getSecretCode() {
        return new Code(secretCode);
    }

    /**
     * Retrieves the list of validators associated with the problem.
     *
     * @return The list of validators associated with the problem.
     */
    public List<Integer> getValidators() {
        return new ArrayList<>(validators);
    }
}

