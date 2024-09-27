package commands;

import model.GameFacade;

/**
 * The CheckValidatorCommand class implements the Command interface
 * and represents a command to check a validator in the game.
 * It encapsulates the logic to execute and unexecute the action of verifying a validator.
 */
public class CheckValidatorCommand implements Command {

    /**
     * The game facade responsible for managing the game state.
     */
    private final GameFacade gameFacade;

    /**
     * The number identifier of the validator to be checked.
     */
    private final int nbValidator;

    /**
     * Constructs a new {@code CheckValidatorCommand} with the specified game facade
     * and validator number.
     *
     * @param gameFacade  the game facade managing the game state.
     * @param nbValidator the number identifier of the validator to be checked.
     */
    public CheckValidatorCommand(GameFacade gameFacade, int nbValidator) {
        this.gameFacade = gameFacade;
        this.nbValidator = nbValidator;
    }

    /**
     * Executes the command by verifying the specified validator in the game.
     */
    @Override
    public void execute() {
        gameFacade.verifyValidator(nbValidator);
    }

    /**
     * Unexecutes the command by unselecting the specified validator in the game.
     */
    @Override
    public void unexecute() {
        gameFacade.unselectValidator(nbValidator);
    }
}
