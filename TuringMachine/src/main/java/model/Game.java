package model;

import model.validators.Validator;
import model.validators.ValidatorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code Game} class represents the game state and logic.
 * It manages rounds, scores, and interacts with the problem, validators, and codes.
 */
public class Game {

    /** The list of rounds in the game. */
    private final List<Round> rounds;

    /** The total score accumulated in the game. */
    private int totalScore;

    /** The problem associated with the game. */
    private final Problem problem;

    /** The map to store the state of each validator (color: green, red, white). */
    private Map<Integer, String> validators = new HashMap<>();

    /**
     * Constructs a {@code Game} object with the specified problem number.
     *
     * @param nbProblem The problem number.
     */
    public Game(int nbProblem) {
        rounds = new ArrayList<>();
        totalScore = 0;
        GameProblems problems = new GameProblems();
        problem = problems.getProblems().get(nbProblem - 1);
    }

    /**
     * Constructs a {@code Game} object with a randomly selected problem.
     */
    public Game() {
        rounds = new ArrayList<>();
        totalScore = 0;
        GameProblems problems = new GameProblems();
        List<Problem> problemsList = problems.getProblems();
        int randomIndex = (int) (Math.random() * problemsList.size());
        problem = problemsList.get(randomIndex);
    }

    /**
     * Chooses a three-digit code for the current round.
     *
     * @param code The code to be chosen.
     * @throws TurningMachineException If the code cannot be chosen.
     */
    public void chooseCode(int code) {
        if (rounds.isEmpty()) {
            rounds.add(new Round()); // if it's the first round of the game
        }
        if (getActualRound().isRoundStarted()) {
            getActualRound().setProposedCode(new Code(code));
        } else {
            throw new TurningMachineException("You have to move to the next round to choose a new code.");
        }
    }

    /**
     * Unselects the current code for the current round.
     */
    public void unChooseCode() {
        getActualRound().unSetProposedCode();
    }

    /**
     * Verifies a specified validator for the current code.
     *
     * @param nbValidator The number of the validator to verify.
     * @return {@code true} if the code is valid according to the validator, {@code false} otherwise.
     * @throws TurningMachineException If an error occurs during the validation.
     */
    public boolean verifyValidator(int nbValidator) {
        if (rounds.isEmpty() || getActualRound().isRoundStarted()) {
            throw new TurningMachineException("You need to enter a code.");
        }
        if (getActualRound().getScore() == 3) {
            throw new TurningMachineException("You have already used three validators. Move to the next round.");
        }
        Validator validator = chooseValidator(nbValidator);
        rounds.get(rounds.size() - 1).incrementScore();
        totalScore++;

        if (validator.checkCodeWithValidator()) {
            validators.put(nbValidator, "green");
            return true;
        } else {
            validators.put(nbValidator, "red");
            return false;
        }
    }

    /**
     * Chooses the appropriate validator based on its number.
     *
     * @param nbValidator The number of the validator to choose.
     * @return The chosen validator.
     * @throws TurningMachineException If the validator number is incorrect.
     */
    private Validator chooseValidator(int nbValidator) {
        List<Integer> validatorsList = problem.getValidators();
        for (Integer v : validatorsList) {
            if (nbValidator == v) {
                Code code = rounds.get(rounds.size() - 1).getProposedCode();
                return ValidatorFactory.createValidator(problem.getSecretCode(), code, nbValidator);
            }
        }
        throw new TurningMachineException("Incorrect number of the validator.");
    }

    /**
     * Gets the problem associated with the game.
     *
     * @return The problem.
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Gets the total score accumulated in the game.
     *
     * @return The total score.
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Gets the actual round being played.
     *
     * @return The actual round.
     * @throws TurningMachineException If there are no rounds.
     */
    public Round getActualRound() {
        int index = rounds.size() - 1;
        if (index < 0) {
            throw new TurningMachineException("Rounds is empty.");
        }
        return rounds.get(index);
    }

    /**
     * Verifies if a specified code is equal to the secret code of the problem.
     *
     * @param code The code to verify.
     * @return {@code true} if the codes are equal, {@code false} otherwise.
     */
    public boolean verifyCode(int code) {
        return problem.getSecretCode().equals(new Code(code));
    }

    /**
     * Moves to the next round in the game.
     *
     * @throws TurningMachineException If the next round cannot be started.
     */
    public void nextRound() {
        if (rounds.isEmpty() || getActualRound().getScore() == 0) {
            throw new TurningMachineException("You have to play before moving to another round.");
        }
        rounds.add(new Round());

        for (Integer v : problem.getValidators()) {
            validators.put(v, "white");
        }
    }

    /**
     * Reverts to the previous round, restoring the state of the validators.
     *
     * @param validators The state of validators to restore.
     */
    public void precedentRound(Map<Integer, String> validators) {
        rounds.remove(rounds.size() - 1);
        this.validators = new HashMap<>(validators);
    }

    /**
     * Gets the state of validators (color: green, red, white).
     *
     * @return The map of validators.
     */
    public Map<Integer, String> getValidators() {
        return new HashMap<>(validators);
    }

    /**
     * Unselects a specified validator, changing its state to white.
     *
     * @param nbValidator The number of the validator to unselect.
     */
    public void unselectValidator(int nbValidator) {
        validators.put(nbValidator, "white");
        totalScore--;
    }

    /**
     * Gets the number of the current round.
     *
     * @return The round number.
     */
    public int getRoundNumber() {
        return rounds.size();
    }
}
