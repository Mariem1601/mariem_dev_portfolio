package commands;

import model.TurningMachineException;

import java.util.Stack;

/**
 * The CommandManager class manages the execution, undo, and redo of commands
 * in a command pattern design.
 */
public class CommandManager {

    /**
     * The stack to keep track of executed commands for undo.
     */
    private final Stack<Command> undoStack = new Stack<>();

    /**
     * The stack to keep track of undone commands for redo.
     */
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Executes the given command, adds it to the undo stack, and clears the redo stack.
     *
     * @param command The command to be executed.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Quand une nouvelle commande est exécutée, l'historique redo est effacé
    }

    /**
     * Undoes the last executed command by popping it from the undo stack,
     * executing its undo operation, and adding it to the redo stack.
     *
     * @throws TurningMachineException If the undo stack is empty.
     */
    public void undo() throws TurningMachineException {
        if (undoStack.isEmpty()) {
            throw new TurningMachineException("Undo failed: No commands to undo.");
        }

        Command command = undoStack.pop();
        command.unexecute();
        redoStack.push(command);
    }

    /**
     * Redoes the last undone command by popping it from the redo stack,
     * executing its operation, and adding it back to the undo stack.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            throw new TurningMachineException("Redo failed: No commands to redo");
        }

        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command); // Remettre la commande dans la pile undo
    }
}

