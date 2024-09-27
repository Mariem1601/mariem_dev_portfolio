package g61692.qwirkle.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bag represents the bag of tiles.
 */
public class Bag implements Serializable {

    private List<Tile> tiles;
    private static Bag instance = new Bag();

    /**
     * Private constructor to avoid creating multiple tile bags.
     * Create a bag that contains 108 tiles.
     */
    private Bag() {
        tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (Color color : Color.values()) {
                for (Shape shape : Shape.values()) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
    }

    /**
     * Returns the singleton instance of the Bag class.
     *
     * @return the instance of the Bag class.
     */
    public static Bag getInstance() {
        return instance;
    }

    /**
     * This method draws a random number of tiles from the bag.
     * If the bag is empty, none are drawn. And if there's not enough,
     * we draw what remains.
     *
     * @param n the number of tiles to draw.
     * @return an array containing the drawn tiles.
     */
    public Tile[] getRandomTiles(int n) {
        List<Tile> drawnTiles = new ArrayList<>();
        if (n<=0 || n>6) {
            throw new QwirkleException("The number of tiles is <=0 or >6");
        }

        if (tiles.isEmpty()) {
            return null;
        }

        if (n >= size()) {
            n = size();
            // on prend tout ce qui reste
            for (int i = 0; i < n; i++) {
                drawnTiles.add(tiles.get(n));
                tiles.remove(n);
            }
        }

        else {
            for (int i = 0; i < n; i++) {
                int indice = (int) (Math.random() * size());
                drawnTiles.add(tiles.get(indice));
                tiles.remove(indice);
            }
        }

        return drawnTiles.toArray(new Tile[0]);
    }

    /**
     * This method returns the number of remaining tiles.
     *
     * @return the number of remaining tiles.
     */
    public int size() {
        return tiles.size();
    }

    /**
     * Resets the bag by creating a new empty list of tiles.
     */
    public void resetBag() {
        tiles = new ArrayList<>();
    }

    /**
     * Writes the Bag object to a serialized file with the specified file name.
     *
     * @param fileName the name of the file to write the Bag object to
     * @throws QwirkleException if an error occurs during the save operation
     */
    public void writeBag(String fileName) {
        fileName += "Bag.ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            out.close();
            System.out.println("Bag saved !");
        }
        catch (IOException e) {
            throw new QwirkleException("Erreur de sauvegarde du Bag");
        }
    }

    /**
     * Restores the Bag object from a serialized file with the specified file name.
     *
     * @param fileName the name of the file to restore the Bag object from
     * @throws QwirkleException if an error occurs during the restoration process
     */
    public void getFromFileBag(String fileName) {
        fileName += "Bag.ser";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            instance = (Bag) in.readObject();
            in.close();
            System.out.println("Le Bag est restauré");
        }
        catch (IOException | ClassNotFoundException e) {
            throw new QwirkleException("Erreur de restauration du Bag");
        }
    }

}
