package commands;

/**
 * Command interface with undo method
 *
 */
public interface Command {
    /**
     * Executes the specified command
     */
    void execute();

    /**
     * Undoes the specified command
     */
    void unexecute();
}
