package model;

/**
 * The {@code TurningMachineException} class represents a custom exception for the Turning Machine game.
 * It extends the {@code RuntimeException} class.
 */
public class TurningMachineException extends RuntimeException {

    /**
     * Constructs a {@code TurningMachineException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public TurningMachineException(String message) {
        super(message);
    }
}
