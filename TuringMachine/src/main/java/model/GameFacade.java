package model;

import commands.*;
import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The {@code GameFacade} class serves as a facade for the game, providing a simplified interface for the client.
 * It manages the game state, commands, and interacts with observers.
 */
public class GameFacade implements Observable {

    /** The underlying game instance. */
    private final Game game;

    /** The CommandManager object managing the execution, undo, and redo of commands. */
    private final CommandManager manager;

    /** The list of observers subscribed to the game events. */
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Constructs a {@code GameFacade} with a new randomly selected problem.
     */
    public GameFacade() {
        game = new Game();
        manager = new CommandManager();
    }

    /**
     * Constructs a {@code GameFacade} with a specified problem number.
     *
     * @param nbProblem The problem number.
     */
    public GameFacade(int nbProblem) {
        game = new Game(nbProblem);
        manager = new CommandManager();
    }

    /**
     * Chooses a three-digit code for the current round.
     *
     * @param code The code to be chosen.
     */
    public void chooseCode(int code) {
        Command command = new ChooseCodeCommand(game, code);
        manager.executeCommand(command);
    }

    /**
     * Checks a specified validator for the current code.
     *
     * @param nbValidator The number of the validator to check.
     */
    public void checkValidator(int nbValidator) {
        Command command = new CheckValidatorCommand(this, nbValidator);
        manager.executeCommand(command);
    }

    /**
     * Advances to the next round in the game.
     */
    public void nextRound() {
        Command command = new NextRoundCommand(this);
        manager.executeCommand(command);
    }

    /**
     * Verifies if a specified code is equal to the secret code of the problem.
     *
     * @param code The code to verify.
     * @return {@code true} if the codes are equal, {@code false} otherwise.
     */
    public boolean checkCode(int code) {
        return game.verifyCode(code);
    }

    /**
     * Retrieves the list of validator numbers associated with the problem.
     *
     * @return The list of validator numbers.
     */
    public List<Integer> validatorsList() {
        return new ArrayList<>(game.getProblem().getValidators());
    }

    /**
     * Retrieves the total score accumulated in the game.
     *
     * @return The total score.
     */
    public int totalScore() {
        return game.getTotalScore();
    }

    /**
     * Retrieves the CommandManager associated with the game.
     *
     * @return The CommandManager.
     */
    public CommandManager getManager() {
        return manager;
    }

    /**
     * Retrieves the state of validators (color: green, red, white).
     *
     * @return The map of validators.
     */
    public Map<Integer, String> getValidators() {
        return game.getValidators();
    }

    /**
     * Retrieves the proposed code for the current round.
     *
     * @return The proposed code.
     */
    public Code getProposedCode() {
        return game.getActualRound().getProposedCode();
    }

    /**
     * Advances to the next round in the game and notifies observers.
     */
    public void nextRoundGame() {
        game.nextRound();
        notifyObservers();
    }

    /**
     * Reverts to the previous round, restoring the state of the validators, and notifies observers.
     *
     * @param validators The state of validators to restore.
     */
    public void precedentRound(Map<Integer, String> validators) {
        game.precedentRound(validators);
        notifyObservers();
    }

    /**
     * Verifies a specified validator, updates the game state, and notifies observers.
     *
     * @param nbValidator The number of the validator to verify.
     */
    public void verifyValidator(int nbValidator) {
        game.verifyValidator(nbValidator);
        notifyObservers();
    }

    /**
     * Unselects a specified validator, updates the game state, and notifies observers.
     *
     * @param nbValidator The number of the validator to unselect.
     */
    public void unselectValidator(int nbValidator) {
        game.getActualRound().decrementScore();
        game.unselectValidator(nbValidator);
        notifyObservers();
    }

    /**
     * Checks if a code has been entered for the current round.
     *
     * @return {@code true} if a code has been entered, {@code false} otherwise.
     */
    public boolean hasEnteredCode() {
        return game.getActualRound().getProposedCode() != null;
    }

    /**
     * Retrieves the problem number associated with the game.
     *
     * @return The problem number.
     */
    public int getProblemNumber() {
        return game.getProblem().getNumberProblem();
    }

    /**
     * Retrieves the total score accumulated in the game.
     *
     * @return The total score.
     */
    public int getScore() {
        return game.getTotalScore();
    }

    /**
     * Retrieves the number of the current round.
     *
     * @return The round number.
     */
    public int getRoundNumber() {
        return game.getRoundNumber();
    }

    /**
     * Adds an observer to the list of observers if it is not already present.
     *
     * @param observer The observer to be added.
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers by calling their `update` method.
     */
    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
