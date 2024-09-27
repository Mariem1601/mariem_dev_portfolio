package g61692.qwirkle.model;

/**
 * Exception thrown when an error occurs in the Qwirkle game.
 */
public class QwirkleException extends RuntimeException {

    /**
     * Constructs a new QwirkleException with the specified detail message.
     *
     * @param message the detail message of the exception.
     */
    public QwirkleException(String message) {
        super(message);
    }
}
