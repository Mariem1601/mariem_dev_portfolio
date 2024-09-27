package g61692.qwirkle.model;

/**
 *  Direction represents the direction to pass from a certain position to place tiles.
 */
public enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    private final int deltaRow;
    private final int deltaCol;


    /**
     * Constructor that create a new position with the given row and column
     * deltas in parameters.
     *
     * @param row the change in the row coordinate
     * @param col the change in column coordinate
     */
    private Direction(int row, int col) {
        deltaRow = row;
        deltaCol = col;
    }

    /**
     * Returns the change in row for this direction.
     *
     * @return the change in row
     */
    public int getDeltaRow() {
        return deltaRow;
    }

    /**
     * Returns the change in column for this direction.
     *
     * @return the change in column
     */
    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Returns the opposite direction to this one.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case DOWN -> UP;
            default -> throw new IllegalStateException("Incorrect value: " + this);
        };
    }
}
