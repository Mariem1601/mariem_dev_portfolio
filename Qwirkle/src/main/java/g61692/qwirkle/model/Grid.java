package g61692.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Grid class represents a Qwirkle game board consisting of a 91x91 grid of tiles.
 * It allows adding and validating tile placements on the board according to Qwirkle game rules.
 */
public class Grid implements Serializable {

    private final Tile[][] tiles;
    private boolean isEmpty;

    /**
     * Creates a new empty grid with all tiles set to null.
     */
    public Grid() {
        tiles = new Tile[91][91];
        isEmpty = true;
    }

    /**
     * Returns the Tile object at the specified row and column on the grid.
     *
     * @param row the row index of the Tile object to return
     * @param col the column index of the Tile object to return
     * @return the Tile object at the specified row and column on the grid.
     * returns null if the tile is outside the grid.
     */
    public Tile get(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            return null;
        }
        return tiles[row][col];
    }

    /**
     * Adds the specified tile line to the grid in the first move of the game.
     *
     * @param d    the direction in which the tile line is to be placed
     * @param line the tile line to add to the grid
     * @return the points earned
     * @throws QwirkleException if the board is not empty or if the specified
     *                          tile line does not satisfy Qwirkle game rules
     */
    public int firstAdd(Direction d, Tile... line) {
        if (!isEmpty) {
            throw new QwirkleException("The board already contains tiles");
        }

        checkTileLineRules(line);

        int row = 45;
        int col = 45;
        for (Tile tile : line) {
            tiles[row][col] = tile;
            row += d.getDeltaRow();
            col += d.getDeltaCol();
        }
        isEmpty = false;
        //todo si qwirkle

        return line.length;
    }

    /**
     * Add a tile to a certain position respecting the rules of the game
     *
     * @param row  the row index of the Tile object to place
     * @param col  the column index of the Tile object to place
     * @param tile the tile to place
     * @return the points earned
     */
    public int add(int row, int col, Tile tile) {
        int points = verifyCanAdd(row, col, tile);

        tiles[row][col] = tile;

        deleteQwirkle(row, col);

        return points;
    }

    /**
     * Verifies if a tile can be added at the specified position on the grid and calculates the points gained by adding the tile.
     *
     * @param row  the row index of the position
     * @param col  the column index of the position
     * @param tile the tile to be added
     * @return the points gained by adding the tile
     * @throws QwirkleException if the position is not free or there are no tiles around
     */
    public int verifyCanAdd(int row, int col, Tile tile) {
        int points = 0;

        isPlaceFree(row, col);
        if (!isTileAround(row, col)) {
            throw new QwirkleException("There is no tile around.");
        }
        points += 1 + checkBoardRow(row, col, tile);
        points += 1 + checkBoardCol(row, col, tile);

        return points;
    }

    /**
     * Adds a line of tiles to the board, starting at the specified row and column position, and extending in the specified direction.
     *
     * @param row  the row index where the line of tiles starts
     * @param col  the column index where the line of tiles starts
     * @param d    the direction in which the line of tiles extends
     * @param line the tiles to be added to the line
     * @return the points earned
     * @throws QwirkleException if the tile line violates any of the Qwirkle game rules or if there are no tiles around the line
     */
    public int add(int row, int col, Direction d, Tile... line) {
        checkTileLineRules(line);
        if (!isTileAround(row, col, d, line)) {
            throw new QwirkleException("There is no tile around this line");
        }

        int posRow = row;
        int posCol = col;

        for (Tile tile : line) {
            isPlaceFree(posRow, posCol);
            checkBoardRow(posRow, posCol, tile);
            checkBoardCol(posRow, posCol, tile);
            posRow += d.getDeltaRow();
            posCol += d.getDeltaCol();
        }

        if (d == Direction.UP || d == Direction.DOWN) {
            for (Tile tile : line) {
                checkBoardCol(row, col, tile);
            }
        }

        if (d == Direction.RIGHT || d == Direction.LEFT) {
            for (Tile tile : line) {
                checkBoardRow(row, col, tile);
            }
        }

        posRow = row;
        posCol = col;
        for (Tile tile : line) {
            tiles[posRow][posCol] = tile;
            posRow += d.getDeltaRow();
            posCol += d.getDeltaCol();
        }

        posRow = row;
        posCol = col;
        for (Tile tile : line) {
            deleteQwirkle(posRow, posCol);
            posRow += d.getDeltaRow();
            posCol += d.getDeltaCol();
        }

        return countPoints(row, col, d, line);
    }

    /**
     * Adds a line of tiles to the board, based on an array of TileAtPosition objects.
     *
     * @param line the array of TileAtPosition objects representing the tiles and their positions in the line
     * @return the points earned
     * @throws QwirkleException if the tile line violates any of the Qwirkle game rules or if there are no tiles around the line
     */
    public int add(TileAtPosition... line) {
        int points = 0;

        if (line == null || line.length == 0) {
            throw new QwirkleException("The line isEmpty.");
        }
        if (line.length > 6) {
            throw new QwirkleException("The maximum allowed length for a Qwirkle line is six.");
        }
        if (line.length == 1) {
            points = add(line[0].row(), line[0].col(), line[0].tile());
        }
        if (line.length >= 2) {
            Tile[] lineOfTile = new Tile[line.length];
            for (int i = 0; i < line.length; i++) {
                lineOfTile[i] = line[i].tile();
            }
            checkTileLineRules(lineOfTile);
            boolean sameCol = line[0].col() == line[1].col();
            boolean sameRow = line[0].row() == line[1].row();

            if (sameCol == sameRow) {
                throw new QwirkleException("Les tuiles doivent appartenir à la meme ligne ou colonne");
            }


            if (sameCol) {
                for (TileAtPosition tile : line) {
                    if (tile.col() != line[0].col()) {
                        throw new QwirkleException("");
                    }
                }
            }

            if (sameRow) {
                for (TileAtPosition tile : line) {
                    if (tile.row() != line[0].row()) {
                        throw new QwirkleException("");
                    }
                }
            }

            for (TileAtPosition tilePos : line) {
                isPlaceFree(tilePos.row(), tilePos.col());
                checkBoardRow(tilePos.row(), tilePos.col(), tilePos.tile());
                checkBoardCol(tilePos.row(), tilePos.col(), tilePos.tile());
                //ajoute directement a la ligne
            }

            if (!isTileAround(line)) {
                throw new QwirkleException("There is no tile around.");
            }

            for (TileAtPosition tilePos : line) {
                tiles[tilePos.row()][tilePos.col()] = tilePos.tile();
            }

            points = countPoints(line);
        }

        for (TileAtPosition tile : line) {
            deleteQwirkle(tile.row(), tile.col());
        }

        return points;
    }

    /**
     * Checks if the board is empty.
     *
     * @return true if the board is empty; false otherwise
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Calculates the points earned by a line of tiles placed on the board in a given direction starting at a specific row and column.
     *
     * @param row  the row of the first tile in the line
     * @param col  the column of the first tile in the line
     * @param d    the direction in which the line of tiles is being placed (UP, DOWN, LEFT, or RIGHT)
     * @param line an array of tiles representing the line of tiles being placed
     * @return the number of points earned by the line of tiles according to the game rules
     */
    private int countPoints(int row, int col, Direction d, Tile... line) {
        int points = 0;
        int posRow = row;
        int posCol = col;

        if (d == Direction.UP || d == Direction.DOWN) {
            points += 1 + checkBoardCol(row, col, line[0]);
            for (Tile tile : line) {
                int pt = checkBoardRow(posRow, posCol, tile);
                if (pt != 0) {
                    points += 1;
                }
                points += pt;
                posRow += d.getDeltaRow();
                posCol += d.getDeltaCol();
            }
        }

        if (d == Direction.RIGHT || d == Direction.LEFT) {
            points += 1 + checkBoardRow(row, col, line[0]);
            for (Tile tile : line) {
                int pt = checkBoardCol(posRow, posCol, tile);
                if (pt != 0) {
                    points += 1;
                }
                points += pt;
                posRow += d.getDeltaRow();
                posCol += d.getDeltaCol();
            }
        }

        return points;
    }

    /**
     * Calculates the points earned by a line of tiles placed on the board.
     *
     * @param line an array of TileAtPosition objects representing the line of tiles being placed
     * @return the number of points earned by the line of tiles according to the game rules
     */
    private int countPoints(TileAtPosition... line) {
        int points = 0;
        boolean sameCol = line[0].col() == line[1].col();
        boolean sameRow = line[0].row() == line[1].row();

        if (sameCol) {
            points += 1 + checkBoardCol(line[0].row(), line[0].col(), line[0].tile());
            for (TileAtPosition tile : line) {
                int pt = checkBoardRow(tile.row(), tile.col(), tile.tile());
                if (pt != 0) {
                    points += 1;
                }
                points += pt;
            }
        }

        if (sameRow) {
            points += 1 + checkBoardRow(line[0].row(), line[0].col(), line[0].tile());
            for (TileAtPosition tile : line) {
                int pt = checkBoardCol(tile.row(), tile.col(), tile.tile());
                if (pt != 0) {
                    points += 1;
                }
                points += pt;
            }
        }

        return points;
    }

    /**
     * Returns a list of adjacent tiles given an array of TileAtPosition objects.
     * Two tiles are considered adjacent if they share an edge.
     *
     * @param tiles an array of TileAtPosition objects
     * @return a list of adjacent tiles
     */
    private List<List<TileAtPosition>> getAdjacentTiles(TileAtPosition[] tiles) {
        List<List<TileAtPosition>> result = new ArrayList<>();

        // On initialise la liste temporaire avec le premier point
        List<TileAtPosition> tempList = new ArrayList<>();
        tempList.add(tiles[0]);

        // On parcourt les points et on ajoute ceux qui sont adjacents dans la liste temporaire
        for (int i = 1; i < tiles.length; i++) {
            TileAtPosition currentPoint = tiles[i];

            // Si le point est adjacent au dernier point de la liste temporaire
            if (isAdjacent(currentPoint, tempList.get(tempList.size() - 1))) {
                tempList.add(currentPoint);
            } else {
                // Sinon, on ajoute la liste temporaire à la liste de résultats
                result.add(tempList);
                // On crée une nouvelle liste temporaire avec le point courant
                tempList = new ArrayList<>();
                tempList.add(currentPoint);
            }

        }

        // On ajoute la dernière liste temporaire à la liste de résultats
        result.add(tempList);

        return result;
    }

    /**
     * Returns true if two tiles are adjacent, false otherwise.
     * Two tiles are considered adjacent if they share an edge.
     *
     * @param tile1 the first TileAtPosition object
     * @param tile2 the second TileAtPosition object
     * @return true if the tiles are adjacent, false otherwise
     */
    private boolean isAdjacent(TileAtPosition tile1, TileAtPosition tile2) {

        int distance = Math.abs(tile1.row() - tile2.row()) + Math.abs(tile1.col() - tile2.col());

        return distance == 1;
    }

    /**
     * Returns true if there is at least one tile around the given tiles,
     * false otherwise.
     *
     * @param line an array of TileAtPosition objects
     * @return true if there is at least one tile around the given tiles,
     * false otherwise
     */
    private boolean isTileAround(TileAtPosition... line) {
        List<List<TileAtPosition>> adjacentTiles = getAdjacentTiles(line);

        for (List<TileAtPosition> list : adjacentTiles) {

            if (list.size() == 1) {
                return isTileAround(list.get(0).row(), list.get(0).col());
            } else {
                boolean result = false;

                for (TileAtPosition tile : list) {
                    if (tile.row() >= 0 && tile.row() < this.tiles.length &&
                            tile.col() >= 0 && tile.col() < this.tiles[0].length) {
                        result = isTileAround(tile.row(), tile.col());
                    }

                    if (result) {
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }

    /**
     * Validates the specified tile line according to Qwirkle game rules.
     *
     * @param line the tile line to validate
     * @throws QwirkleException if the tile line contains more than 6 tiles
     *                          or if the tiles in the line do not satisfy Qwirkle game rules
     */
    private void checkTileLineRules(Tile... line) {
        if (line == null || line.length == 0) {
            throw new QwirkleException("The line is empty");
        }

        if (line.length > 6) {
            throw new QwirkleException("The number of tiles must not exceed 6");
        }

        for (Tile tile : line) {
            if (tile == null) {
                throw new QwirkleException("Null Tile in the line");
            }
        }

        for (int i = 0; i < line.length - 1; i++) {
            for (int j = i + 1; j < line.length; j++) {
                if (tileNotMatch(line[i], line[j])) {
                    throw new QwirkleException("The tile line is invalid");
                }
            }
        }
    }

    /**
     * Compares two Tile objects to check if they can't be placed adjacent
     * to each other according to Qwirkle game rules.
     *
     * @param tile1 the first Tile object to compare
     * @param tile2 the second Tile object to compare
     * @return false if the two Tile objects can be placed adjacent to each
     * other, true otherwise
     */
    private boolean tileNotMatch(Tile tile1, Tile tile2) {
        boolean isSameColor = tile1.color().equals(tile2.color());
        boolean isSameShape = tile1.shape().equals(tile2.shape());
        boolean isSameColorOrShape = isSameColor || isSameShape;
        boolean isSameColorAndShape = tile1.equals(tile2);

        if (isSameColorAndShape) {
            return true;
        }
        return !isSameColorOrShape;
    }

    /**
     * Check that a certain position is free in order to place a tile there
     *
     * @param row the row index
     * @param col the column index
     * @throws QwirkleException if the position is not free
     */
    private void isPlaceFree(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            throw new QwirkleException("Tile out of the grid");
        }

        if (tiles[row][col] != null) {
            throw new QwirkleException("The position is not free.");
        }
    }

    /**
     * Checks if there is at least one tile adjacent to a given position
     *
     * @param row the row of the position to check
     * @param col the column of the position to check
     * @return false if there are no adjacent tiles, return otherwise.
     */
    private boolean isTileAround(int row, int col) {
        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) {
            throw new QwirkleException("Tile out of the grid");
        }

        int numRows = tiles.length;
        int numCols = tiles[0].length;
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(numRows - 1, row + 1);
        int startCol = Math.max(0, col - 1);
        int endCol = Math.min(numCols - 1, col + 1);
        boolean ok = false;

        for (int i = startRow; i <= endRow; i++) {
            if (i == row) {
                continue; // Skip the current position
            }
            if (tiles[i][col] != null) {
                ok = true; // A non-null value is found in vertical direction
            }
        }

        for (int j = startCol; j <= endCol; j++) {
            if (j == col) {
                continue; // Skip the current position
            }
            if (tiles[row][j] != null) {
                ok = true; // A non-null value is found in horizontal direction
            }
        }

        return ok;
    }

    /**
     * Checks if a tile is adjacent to any tile in a given direction.
     *
     * @param row   the row of the tile to check
     * @param col   the column of the tile to check
     * @param d     the direction to check
     * @param tiles the array of tiles to check for adjacency
     * @return true if there is a tile adjacent in the given direction, false otherwise
     */
    private boolean isTileAround(int row, int col, Direction d, Tile[] tiles) {
        int deltaRow = 0;
        int deltaCol = 0;
        boolean result = false;

        switch (d) {
            case RIGHT -> deltaCol = 1;
            case LEFT -> deltaCol = -1;
            case UP -> deltaRow = -1;
            case DOWN -> deltaRow = 1;
        }

        int currentRow = row;
        int currentCol = col;

        for (Tile tile : tiles) {
            if (currentRow >= 0 && currentRow < this.tiles.length &&
                    currentCol >= 0 && currentCol < this.tiles[0].length) {
                result = isTileAround(currentRow, currentCol);
            }

            if (result) {
                return true;
            }

            currentRow += deltaRow;
            currentCol += deltaCol;
        }

        return false;
    }

    /**
     * Checks if adding a given tile to a row on the board would form a valid Qwirkle
     * and return the points earned
     *
     * @param row  the row to check
     * @param col  the column of the position to add the tile
     * @param tile the tile to add
     * @return returns the number of points for the row
     * @throws QwirkleException if the row does not form a valid Qwirkle with the given tile
     */
    private int checkBoardRow(int row, int col, Tile tile) {
        List<Tile> boardRow = new ArrayList<>();
        boardRow.addAll(getTilesInDirection(row, col, 0, -1));
        boardRow.addAll(getTilesInDirection(row, col, 0, 1));

        isQuirkle(boardRow, tile);

        int points = boardRow.size();

        // if it's a qwirkle add 6 points
        if (points == 5) {
            points += 6;
        }

        return points;
    }

    private void deleteQwirkle(int row, int col) {
        List<Integer> boardRow = new ArrayList<>();
        boardRow.addAll(getTilesInDirection2(row, col, 0, -1));
        boardRow.addAll(getTilesInDirection2(row, col, 0, 1));

        List<Integer> boardCol = new ArrayList<>();
        boardCol.addAll(getTilesInDirection2(row, col, -1, 0));
        boardCol.addAll(getTilesInDirection2(row, col, 1, 0));

        if (boardCol.size() + 1 == 6) {
            for (int i = 0; i < (boardCol.size()/2) - 1; i+=2) {
                tiles[boardCol.get(i)][boardCol.get(i+1)] = null;
            }
        }

        if (boardRow.size() + 1 == 6) {
            for (int i = 0; i < (boardRow.size()/2) - 1; i+=2) {
                tiles[boardRow.get(i)][boardRow.get(i+1)] = null;
            }
        }
    }

    private List<Integer> getTilesInDirection2(int startRow, int startCol, int rowIncrement, int colIncrement) {
        List<Integer> tilesInDirection = new ArrayList<>();
        int row = startRow + rowIncrement;
        int col = startCol + colIncrement;
        while (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length && tiles[row][col] != null) {
            tilesInDirection.add(row);
            tilesInDirection.add(col);
            row += rowIncrement;
            col += colIncrement;
        }
        return tilesInDirection;
    }

    /**
     * Checks if adding a given tile to a column on the board would form a valid Qwirkle
     * and return the number of points earned
     *
     * @param row  the row to check
     * @param col  the column of the position to add the tile
     * @param tile the tile to add
     * @return returns the number of points for the col
     * @throws QwirkleException if the column does not form a valid Qwirkle with the given tile
     */
    private int checkBoardCol(int row, int col, Tile tile) {
        List<Tile> boardCol = new ArrayList<>();
        boardCol.addAll(getTilesInDirection(row, col, -1, 0));
        boardCol.addAll(getTilesInDirection(row, col, 1, 0));

        isQuirkle(boardCol, tile);

        int points = boardCol.size();

        // if it's a qwirkle add 6 points
        if (points == 5) {
            points += 6;
        }

        return points;
    }

    /**
     * Returns a list of tiles in a specific direction from a given position.
     *
     * @param startRow     the starting row
     * @param startCol     the starting column
     * @param rowIncrement the increment for the row in the given direction
     * @param colIncrement the increment for the column in the given direction
     * @return a list of tiles in the specified direction from the starting position
     */
    private List<Tile> getTilesInDirection(int startRow, int startCol, int rowIncrement, int colIncrement) {
        List<Tile> tilesInDirection = new ArrayList<>();
        int row = startRow + rowIncrement;
        int col = startCol + colIncrement;
        while (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length && tiles[row][col] != null) {
            tilesInDirection.add(tiles[row][col]);
            row += rowIncrement;
            col += colIncrement;
        }
        return tilesInDirection;
    }

    /**
     * Checks if a given list of tiles forms a valid Qwirkle.
     *
     * @param qwirkle the list of tiles to check
     * @param tile    the tile to add to the list
     * @throws QwirkleException if the list is longer than 6 or contains tiles that do not form a valid Qwirkle
     */
    private void isQuirkle(List<Tile> qwirkle, Tile tile) {
        if (qwirkle.size() > 6) {
            throw new QwirkleException("The number of tiles must not exceed 6");
        }

        for (int i = 0; i < qwirkle.size() - 1; i++) {
            for (int j = i + 1; j < qwirkle.size(); j++) {
                if (tileNotMatch(qwirkle.get(i), qwirkle.get(j))) {
                    throw new QwirkleException("The tile line is invalid");
                }
            }
        }

        for (Tile tileRow : qwirkle) {
            if (tileNotMatch(tileRow, tile)) {
                throw new QwirkleException("The tile line is invalid");
            }
        }
    }

}
