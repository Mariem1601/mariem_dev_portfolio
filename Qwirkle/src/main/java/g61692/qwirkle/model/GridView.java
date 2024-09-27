package g61692.qwirkle.model;

/**
 * The GridView class represents a view for a Grid object, allowing access to individual tiles
 * within the grid. It provides methods for getting a tile at a specific row and column, and checking if the grid is empty.
 */
public class GridView {

    /**
     * The grid associated with this view.
     */
    private final Grid grid;

    /**
     * Creates a new GridView object with the specified grid.
     *
     * @param grid the grid to be associated with this view
     */
    public GridView(Grid grid) {
        this.grid = grid;
    }

    /**
     * Returns the tile at the specified row and column within the associated grid.
     *
     * @param row the row of the tile to get
     * @param col the column of the tile to get
     * @return the tile at the specified row and column, or null if the tile is empty
     */
    public Tile get(int row, int col) {
        return grid.get(row, col);
    }

    /**
     * Returns whether or not the associated grid is empty.
     *
     * @return {@code true} if the grid is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return grid.isEmpty();
    }
}
