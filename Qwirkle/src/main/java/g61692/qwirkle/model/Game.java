package g61692.qwirkle.model;

import java.io.*;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class represents a game of Qwirkle.
 * It contains a grid, an array of players, and the index of the current player.
 * The game can be played by adding tiles to the grid and removing them from the player's hands.
 * The game can also be passed to the next player.
 */
public class Game implements Serializable {
    private final Grid grid;
    private final Player[] players;
    private int currentPlayer;

    /**
     * Constructor for the Game class.
     *
     * @param names A list of player names. The number of names should be between 2 and 4, inclusive.
     * @throws QwirkleException If the number of player names is incorrect.
     */
    public Game(List<String> names) {
        if (names.size() < 2 || names.size() > 4) {
            throw new QwirkleException("The number of players is incorrect");
        }

        players = new Player[names.size()];
        for (int i = 0; i < names.size(); i++) {
            players[i] = new Player(names.get(i));
        }

        grid = new Grid();
        currentPlayer = 0;
    }

    /**
     * Constructs a new Game object.
     * Initializes the grid and sets the players array to an empty array.
     */
    public Game() {
        grid = new Grid();
        players = new Player[0];
    }

    /**
     * Adds tiles to the grid for the first move of the game.
     *
     * @param d  The direction in which the tiles will be added.
     * @param is The indexes of the tiles to be added to the grid.
     * @throws QwirkleException If the current player is not the first player.
     */
    public void first(Direction d, int... is) {
        if (!grid.isEmpty()) {
            throw new QwirkleException("This is not the first play");
        }
        Tile[] line = getLine(is);
        int points = grid.firstAdd(d, line);

        players[currentPlayer].addScore(points);
        players[currentPlayer].remove(line);
        players[currentPlayer].refill();
    }

    /**
     * Adds a single tile to the grid.
     *
     * @param row   The row of the cell where the tile will be added.
     * @param col   The column of the cell where the tile will be added.
     * @param index The index of the tile in the current player's hand.
     */
    public void play(int row, int col, int index) {
        int points = grid.add(row, col, players[currentPlayer].getHand().get(index));

        players[currentPlayer].addScore(points);
        players[currentPlayer].remove(players[currentPlayer].getHand().get(index));
        players[currentPlayer].refill();
    }
    

    /**
     * Adds a line of tiles to the grid.
     *
     * @param row     The row of the cell where the tiles will be added.
     * @param col     The column of the cell where the tiles will be added.
     * @param d       The direction in which the tiles will be added.
     * @param indexes The indexes of the tiles in the current player's hand.
     */
    public void play(int row, int col, Direction d, int... indexes) {
        Tile[] line = getLine(indexes);
        int points = grid.add(row, col, d, line);

        players[currentPlayer].addScore(points);
        players[currentPlayer].remove(line);
        players[currentPlayer].refill();
    }

    /**
     * Adds multiple tiles to the grid.
     *
     * @param is An array of integers representing the positions and indexes of the tiles to be added.
     */
    public void play(int... is) {
        TileAtPosition[] tiles = new TileAtPosition[is.length / 3];
        int j = 0;
        for (int i = 0; i < is.length - 2; i = i + 3) {
            tiles[j] = new TileAtPosition(is[i], is[i + 1], players[currentPlayer].getHand().get(is[i + 2]));
            j++;
        }

        int points = grid.add(tiles);

        players[currentPlayer].addScore(points);

        Tile[] tilesToRemove = new Tile[is.length / 3];
        int k = 0;
        for (int i = is.length - 3; i >= 0; i = i - 3) {
            tilesToRemove[k] = players[currentPlayer].getHand().get(is[i + 2]);
            k++;
        }
        players[currentPlayer].remove(tilesToRemove);

        players[currentPlayer].refill();
    }

    /**
     * Returns the name of the current player.
     *
     * @return the name of the current player
     */
    public String getCurrentPlayerName() {
        return players[currentPlayer].getName();
    }

    /**
     * Returns the hand of the current player.
     *
     * @return a list of tiles representing the hand of the current player
     */
    public List<Tile> getCurrentPlayerHand() {
        return players[currentPlayer].getHand();
    }

    /**
     * Returns the score of the current player.
     *
     * @return the score of the current player
     */
    public int getCurrentPlayerScore() {
        return players[currentPlayer].getScore();
    }

    /**
     * Returns a GridView object representing the grid of the game.
     *
     * @return a GridView object representing the grid of the game
     */
    public GridView getGrid() {
        return new GridView(grid);
    }

    /**
     * Passes the turn to the next player.
     */
    public void pass() {
        if (currentPlayer == players.length - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the current player has no tiles in hand and the bag is empty with no possible plays, false otherwise
     */
    public boolean isOver() {

        if (players[currentPlayer].getHand().size()==0) {
            players[currentPlayer].addScore(6);
            return true;
        }

        return Bag.getInstance().size() == 0 && canNotPlay();
    }

    /**
     * Checks if no player can make a valid move on the grid.
     *
     * @return true if no player can make a valid move, false otherwise
     */
    public boolean canNotPlay() {

        if (grid.isEmpty()) {
            return false;
        }

        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                for (Player player : players) {
                    for (Tile tile : player.getHand()) {
                        try {
                            grid.verifyCanAdd(i, j, tile);
                            return false;
                        }
                        catch (QwirkleException ignored) {
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Retrieves the name of the player with the highest score as the winner.
     *
     * @return the name of the player with the highest score
     */
    public String getWinner() {
        int maxPoints = 0;
        String winner = "";
        for (Player player : players) {
            if (player.getScore() > maxPoints) {
                maxPoints = player.getScore();
                winner = player.getName();
            }
        }

        return winner;
    }

    /**
     * Returns an array of Tile objects representing a line of tiles in the player's hand.
     *
     * @param indexes the indexes of the tiles in the player's hand that make up the line
     * @return an array of Tile objects representing the line of tiles in the player's hand
     */
    private Tile[] getLine(int... indexes) {
        Tile[] line = new Tile[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            line[i] = players[currentPlayer].getHand().get(indexes[i]);
        }

        return line;
    }

    /**
     * Writes the current Game object to a serialized file with the specified file name.
     *
     * @param fileName the name of the file to write the Game object to
     * @throws QwirkleException if an error occurs during the save operation
     */
    public void write(String fileName) {
        fileName += ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.close();
            System.out.println("Party saved !");
        }
        catch (IOException e) {
            throw new QwirkleException("Erreur de sauvegarde de la partie");
        }
    }

    /**
     * Restores a Game object from a serialized file with the specified file name.
     *
     * @param fileName the name of the file to restore the Game object from
     * @return the restored Game object
     * @throws QwirkleException if an error occurs during the restoration process
     */
    public Game getFromFile(String fileName) {
        Game game;
        fileName += ".ser";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            game = (Game) in.readObject();
            in.close();
            System.out.println("la partie sauvegardé" + fileName);
        } catch (IOException | ClassNotFoundException e) {
            throw new QwirkleException("Erreur de restauration de la partie");
        }
        return game;
    }
}
