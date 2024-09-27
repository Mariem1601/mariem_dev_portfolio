package g61692.qwirkle.view;

import g61692.qwirkle.model.*;

import java.util.List;

/**
 * The View class provides methods for displaying the game board and game messages to the console.
 */
public class View {

    /**
     * Displays the game board in the console.
     *
     * @param grid the game board to display.
     */
    public static void display(GridView grid) {
        int minRow = 45;
        int maxRow = 45;
        int minCol = 45;
        int maxCol = 45;

        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                if (grid.get(i, j) != null) {
                    if (i < minRow) {
                        minRow = i;
                    }
                    if (i > maxRow) {
                        maxRow = i;
                    }
                    if (j < minCol) {
                        minCol = j;
                    }
                    if (j > maxCol) {
                        maxCol = j;
                    }
                }
            }
        }

        for (int i = minRow; i <= maxRow; i++) {
            System.out.print(i + " |");
            for (int j = minCol; j <= maxCol; j++) {
                Tile tile = grid.get(i, j);
                if (tile != null) {
                    displayTile(tile.color(), tile.shape());
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }

        System.out.print("    ");
        for (int i = minCol; i <= maxCol; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Taille du Sac de Tuiles : " + Bag.getInstance().size());
    }

    /**
     * Displays a player's hand in the console.
     *
     * @param name the name of the player whose hand is being displayed.
     * @param hand the player's hand to display.
     */
    public static void display(String name, List<Tile> hand, int score) {
        System.out.print("Player " + name + " : ");

        for (Tile tile : hand) {
            displayTile(tile.color(), tile.shape());
            System.out.print(" ");
        }

        System.out.print(", Score " + score);
        System.out.println();
    }

    /**
     * Displays a single tile with the given color and shape in the console using ANSI escape codes for color.
     *
     * @param color the color of the tile
     * @param shape the shape of the tile
     */
    private static void displayTile(Color color, Shape shape) {
        String colorCode = switch (color) {
            case RED -> "\u001B[31m";
            case BLUE -> "\u001B[34m";
            case YELLOW -> "\u001B[33m";
            case GREEN -> "\u001B[32m";
            case ORANGE -> "\u001B[38;5;208m";
            case PURPLE -> "\u001B[38;5;165m";
        };

        String shapeDrawn = switch (shape) {
            case ROUND -> " O ";
            case PLUS -> " + ";
            case CROSS -> " X ";
            case STAR -> " * ";
            case SQUARE -> "[ ]";
            case DIAMOND -> "< >";
        };

        System.out.print(colorCode + shapeDrawn + "\u001B[0m");
    }

    /**
     * Displays a help message with information on the commands that can be used in the game.
     */
    public static void displayHelp() {
        System.out.println("Q W I R K L E");
        System.out.println("Qwirkle command:");
        System.out.println("- play 1 tile : o <row> <col> <i>");
        System.out.println("- play line: l <row> <col> <direction> <i1> [<i2>]");
        System.out.println("- play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]");
        System.out.println("- play first : f [<direction>] <f1> [<f2> …]");
        System.out.println("- pass : p");
        System.out.println("- quit : q");
        System.out.println("- save : s");
        System.out.println("   i : index in list of tiles");
        System.out.println("   d : direction in l (left), r (right), u (up), d(down)");

    }

    /**
     * Displays an error message in the console.
     *
     * @param message the error message to display.
     */
    public static void displayError(String message) {
        System.out.println();
        System.out.println("\u001B[31m" + message + "\u001B[0m");
        System.out.println();
    }
}
