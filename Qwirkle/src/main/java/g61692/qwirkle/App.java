package g61692.qwirkle;

import g61692.qwirkle.model.Bag;
import g61692.qwirkle.model.Direction;
import g61692.qwirkle.model.Game;
import g61692.qwirkle.model.QwirkleException;
import g61692.qwirkle.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class represents an implementation of the Qwirkle game. It uses a Game object to manage the game
 * and a View object to display the game on the console. It allows players to play Qwirkle by providing a console interface.
 * The game begins by asking the user to input the number of players and their names. It then randomly chooses a player to begin the game.
 * The player can then perform one of the following actions each turn: play tiles, exchange tiles, pass, or quit the game.
 */
public class App {

    /**
     * Runs the Qwirkle game with a console interface. Prompts the user to enter the number of players and their names,
     * then initializes the game and displays the game on the console. Provides a command-line interface for players to
     * play tiles, exchange tiles, pass, or quit the game. Displays the game state after each turn.
     */
    public static void main(String[] args) {
        Game game = initialiser();

        Scanner clavier = new Scanner(System.in);
        gameLoop:
        while (!game.isOver()) {
            View.display(game.getCurrentPlayerName(), game.getCurrentPlayerHand(), game.getCurrentPlayerScore());
            System.out.print("Entrez la commande : ");
            String command = clavier.next().toLowerCase(Locale.ROOT);
            switch (command) {
                case "o" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_O(game);
                }
                case "l" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_L(game);
                }
                case "m" -> {
                    if (game.getGrid().isEmpty()) {
                        View.displayError("Veuillez jouer first d'abord");
                        break;
                    }
                    command_M(game);
                }
                case "f" -> {
                    if (!game.getGrid().isEmpty()) {
                        View.displayError("Vous avez deja joué first");
                        break;
                    }
                    command_F(game);
                }
                case "p" -> game.pass();

                case "q" -> {
                    System.out.println("Vous avez quitté la partie ! Okaaay bye");
                    break gameLoop;
                }
                case "s" -> {
                    System.out.println("Entrez un nom de fichier pour la sauvegarde : ");
                    String fileName = clavier.next();
                    game.write(fileName);
                    Bag.getInstance().writeBag(fileName);
                    break gameLoop;
                }
                case "h" -> View.displayHelp();
                default -> View.displayError("Veuillez entrez une commmande correcte");
            }
            View.display(game.getGrid());

        }

        System.out.println("Le gagnant est : " + game.getWinner());

    }

    /**
     * Initializes the game by either loading a previous game or starting a new game.
     *
     * @return the initialized Game object
     */
    private static Game initialiser() {
        Game game;
        Scanner clavier = new Scanner(System.in);
        System.out.print("Voulez-vous charger une partie précédente ? (o/n) : ");
        String response = clavier.next().toLowerCase(Locale.ROOT);

        if (response.equals("o")) {
            System.out.print("Entrez le nom du fichier de sauvegarde : ");
            String fileName = clavier.next();

            game = new Game();

            Game savedGame = game.getFromFile(fileName);
            Bag.getInstance().getFromFileBag(fileName);

            if (savedGame != null) {
                game = savedGame;
                System.out.println("La partie précédente a été chargée.");
                View.display(game.getGrid());
            } else {
                System.out.println("Erreur lors de la désérialisation du jeu. Une nouvelle partie sera créée.");
            }
        } else {
            List<String> names = playerListNames();
            game = new Game(names);

            View.displayHelp();
        }

        return game;
    }

    /**
     * Plays a tile at a specific row, column, and index.
     *
     * @param game the Game object being played
     */
    private static void command_O(Game game) {
        int row = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
        int col = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
        int i = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        try {
            game.play(row, col, i);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Executes the command for playing tiles by providing row, column, tiles and direction.
     *
     * @param game the game object.
     */
    private static void command_L(Game game) {
        int row = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
        int col = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        Direction d = getDirection();
        int[] is = new int[isSize];
        for (int i = 0; i < is.length; i++) {
            is[i] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.play(row, col, d, is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Executes the command for playing tiles by providing multiple positions and tiles.
     *
     * @param game the game object.
     */
    private static void command_M(Game game) {
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        int[] is = new int[isSize * 3];
        for (int i = 0; i < is.length - 2; i += 3) {
            is[i] = lectureEntierEntreAetB("Entrez la position de la ligne : ", 0, 90);
            is[i + 1] = lectureEntierEntreAetB("Entrez la position de la colonne : ", 0, 90);
            is[i + 2] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.play(is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Executes the command for playing the first tiles.
     *
     * @param game the game object.
     */
    private static void command_F(Game game) {
        Direction d = getDirection();
        int isSize = lectureEntierEntreAetB("Combien de tuiles souhaitez vous poser : ", 1, game.getCurrentPlayerHand().size());
        int[] is = new int[isSize];
        for (int i = 0; i < is.length; i++) {
            is[i] = lectureEntierEntreAetB("Entrez l'indice de la tuile : ", 0, game.getCurrentPlayerHand().size() - 1);
        }
        try {
            game.first(d, is);
            game.pass();
        } catch (QwirkleException e) {
            View.displayError(e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the names of the players in the game.
     *
     * @return a list of player names
     */
    private static List<String> playerListNames() {
        List<String> playerList = new ArrayList<>();

        Scanner clavier = new Scanner(System.in);

        int n = lectureEntierEntreAetB("Entrez le nombre de joueurs : ", 2, 4);

        for (int i = 0; i < n; i++) {
            System.out.println("Entrez votre nom : ");
            String nom = clavier.next();
            playerList.add(nom);
        }
        return playerList;
    }

    /**
     * Lecture robuste d'un entier.
     * Tant que l’entrée de l’utilisateur n’est pas
     * un entier la méthode demande une nouvelle entrée.
     *
     * @param message message à afficher.
     * @return l'entier saisi par l'utilisateur.
     */
    private static int lectureEntier(String message) {
        Scanner clavier = new Scanner(System.in);
        System.out.println(message);
        while (!clavier.hasNextInt()) {
            System.out.println("Ce n'est pas ce qui est demandé. " + message);
            clavier.next();
        }
        return clavier.nextInt();
    }

    /**
     * Lecture robuste d'un entier entre deux bornes
     * Tant que l’entrée de l’utilisateur n’est pas
     * un entier entre a et b, la méthode demande une nouvelle entrée.
     *
     * @param message message à afficher.
     * @param a       la borne inferieure
     * @param b       la borne superieure
     * @return l'entier saisi par l'utilisateur.
     */
    private static int lectureEntierEntreAetB(String message, int a, int b) {
        int nb = lectureEntier(message);

        while (nb < a || nb > b) {
            System.out.println("Ce n'est pas ce qui est demandé. ");
            nb = lectureEntier(message);
        }

        return nb;
    }

    /**
     * Prompts the user to enter a direction (up, down, right, or left),
     * and returns the corresponding Direction enum value.
     *
     * @return the direction entered by the user
     */
    private static Direction getDirection() {
        Scanner clavier = new Scanner(System.in);
        System.out.print("Entrez une direction (u, d, r, l) : ");
        String command = clavier.next().toLowerCase(Locale.ROOT);
        Direction direction;
        switch (command) {
            case "u" -> direction = Direction.UP;
            case "d" -> direction = Direction.DOWN;
            case "r" -> direction = Direction.RIGHT;
            case "l" -> direction = Direction.LEFT;
            default -> {
                View.displayError("Veuillez entrer une direction correcte");
                direction = getDirection();
            }
        }
        return direction;
    }

}

