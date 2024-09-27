package commands;

import model.GameFacade;

import java.util.Map;

/**
 * The {@code NextRoundCommand} class implements the {@code Command} interface
 * and represents a command to proceed to the next round in the game.
 * It encapsulates the logic to execute and unexecute the action of advancing to the next round.
 */
public class NextRoundCommand implements Command {

    /**
     * The game facade object responsible for managing the game state.
     */
    private final GameFacade gameFacade;

    /**
     * A map representing the state of validators before the execution of the command.
     */
    private Map<Integer, String> validators;

    /**
     * Constructs a new {@code NextRoundCommand} with the specified game facade.
     *
     * @param gameFacade the game facade object managing the game state.
     */
    public NextRoundCommand(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    /**
     * Executes the command by advancing to the next round in the game.
     * It also saves the state of validators before the execution for possible unexecution.
     */
    @Override
    public void execute() {
        validators = gameFacade.getValidators();
        gameFacade.nextRoundGame();
    }

    /**
     * Unexecutes the command by reverting to the previous round in the game.
     * It restores the state of validators to the state saved before the execution.
     */
    @Override
    public void unexecute() {
        gameFacade.precedentRound(validators);
    }
}

