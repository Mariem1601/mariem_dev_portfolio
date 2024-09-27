package g61692.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player implements Serializable {

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The tiles in the player's hand.
     */
    private final List<Tile> hand;

    /**
     * The score of the player
     */
    private int score;

    /**
     * Creates a new player with the specified name and randomly generated hand of 6 tiles.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        hand.addAll(List.of(Bag.getInstance().getRandomTiles(6)));
    }

    /**
     * Returns an unmodifiable view of the tiles in the player's hand.
     *
     * @return an unmodifiable view of the tiles in the player's hand
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(hand);
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the score of the player.
     *
     * @return the score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds the given number of points earned to the player's score.
     *
     * @param points the number of points to add to the player's score
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * Refills the player's hand with random tiles from the bag, up to a maximum of 6 tiles.
     */
    public void refill() {
        if (Bag.getInstance().size() != 0) {
            hand.addAll(List.of(Bag.getInstance().getRandomTiles(6 - hand.size())));
        }
    }

    /**
     * Removes the specified tiles from the player's hand.
     *
     * @param ts the tiles to remove from the player's hand
     */
    public void remove(Tile... ts) {
        hand.removeAll(List.of(ts));
    }
}
