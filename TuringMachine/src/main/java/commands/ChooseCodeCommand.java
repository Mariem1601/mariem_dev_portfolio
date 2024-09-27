package commands;

import model.Game;

/**
 * The {@code ChooseCodeCommand} class implements the {@code Command} interface
 * and represents a command to choose a code in the game.
 * It encapsulates the logic to execute and unexecute the action of choosing a code.
 */
public class ChooseCodeCommand implements Command {

    /**
     * The game object responsible for managing the game state.
     */
    private final Game game;

    /**
     * The code value to be chosen in the game.
     */
    private final int code;

    /**
     * Constructs a new {@code ChooseCodeCommand} with the specified game and code.
     *
     * @param game the game object managing the game state.
     * @param code the code value to be chosen in the game.
     */
    public ChooseCodeCommand(Game game, int code) {
        this.game = game;
        this.code = code;
    }

    /**
     * Executes the command by choosing the specified code in the game.
     */
    @Override
    public void execute() {
        game.chooseCode(code);
    }

    /**
     * Unexecutes the command by unchoosing the previously chosen code in the game.
     */
    @Override
    public void unexecute() {
        game.unChooseCode();
    }
}
