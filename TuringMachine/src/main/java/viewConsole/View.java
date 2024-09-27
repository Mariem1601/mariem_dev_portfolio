package viewConsole;

import model.GameFacade;
import util.Observer;

import java.util.List;
import java.util.Map;

/**
 * The {@code View} class represents the user interface for the Turning Machine game.
 * It implements the {@code Observer} interface to receive updates from the game model.
 */
public class View implements Observer {

    /**
     * The {@code GameFacade} object to interact with the game model.
     */
    private final GameFacade gameFacade;

    /**
     * Constructs a {@code View} object with the specified {@code GameFacade}.
     *
     * @param gameFacade The {@code GameFacade} object.
     */
    public View(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        gameFacade.addObserver(this);
    }

    /**
     * Displays the help menu with available commands for the Turing Machine game.
     */
    public void displayHelp() {
        System.out.println("T U R I N G - M A C H I N E");
        System.out.println("TuringMachine command:");
        System.out.println("- choose a code : code <code>");
        System.out.println("- check a validator : validator <nbValidator>");
        System.out.println("- verify the code : verify <code>");
        System.out.println("- move to next round : next");
        System.out.println("- undo the last command : undo");
        System.out.println("- redo the command : redo");
        System.out.println("- show validators : show");
        System.out.println("- help : help");
        System.out.println("- quit : exit");
    }

    /**
     * Displays a message indicating a win and the total score.
     *
     * @param scoreTotal The total score.
     */
    public void displayWin(int scoreTotal) {
        System.out.println("You won ! You found the secretCode with a score of  " + scoreTotal + " !");
    }

    /**
     * Displays a message indicating a loss and the total score.
     *
     * @param scoreTotal The total score.
     */
    public void displayLost(int scoreTotal) {
        System.out.println("You lost ! With a score of " + scoreTotal + " !");
    }

    /**
     * Displays a message prompting the user to choose a code.
     */
    public void displayEnterCode() {
        System.out.println("Choose a code with the code command");
    }

    /**
     * Displays the list of validators with their descriptions.
     *
     * @param validators The list of validator numbers.
     */
    public void displayValidators(List<Integer> validators) {
        for (Integer v : validators) {
            System.out.println("Validator number " + v + " : " + validatorDescription(v));
        }
    }

    /**
     * Displays the states of validators (red, green, or white).
     *
     * @param validatorsState A map containing validator numbers and their states.
     */
    public void displayValidatorsStates(Map<Integer, String> validatorsState) {
        System.out.println("Validators State :");
        validatorsState.forEach((validatorNb, state) -> {
            String colorCode = "";

            switch (state.toLowerCase()) {
                case "red" -> colorCode = "\u001B[31m";
                case "green" -> colorCode = "\u001B[32m";
                default -> {
                }
            }

            System.out.println(colorCode + "- validator " + validatorNb + "\u001B[0m");
        });
    }

    /**
     * Returns the description of a validator based on its number.
     *
     * @param nbValidator The number of the validator.
     * @return The description of the validator.
     */
    private String validatorDescription(int nbValidator) {
        String description = "";
        switch (nbValidator) {
            case 1 -> description = "Compare le premier chiffre du code avec 1";
            case 2 -> description = "Compare le premier chiffre avec 3";
            case 3 -> description = "Compare le deuxième chiffre avec 3";
            case 4 -> description = "Compare le deuxième chiffre avec 4";
            case 5 -> description = "Vérifie la parité du premier chiffre du code";
            case 6 -> description = "Vérifie la parité du deuxième chiffre";
            case 7 -> description = "Vérifie la parité du troisième chiffre";
            case 8 -> description = "Compte combien de fois la valeur 1 apparaît dans le code";
            case 9 -> description = "Compte combien de fois la valeur 3 apparaît";
            case 10 -> description = "Compte combien de fois la valeur 4 apparaît";
            case 11 -> description = "Compare le premier chiffre du code avec le deuxième";
            case 12 -> description = "Compare le premier chiffre du code avec le troisième";
            case 13 -> description = "Compare le deuxième chiffre du code avec le troisième";
            case 14 -> description = "Détermine quel chiffre est strictement le plus petit";
            case 15 -> description = "Détermine quel chiffre est strictement le plus grand";
            case 16 -> description = "Détermine s’il y a plus de chiffre pairs ou impairs dans le code";
            case 17 -> description = "Compte combien de chiffres dans le code sont pairs";
            case 18 -> description = "Détermine si la somme des chiffres du code est paire ou impaire";
            case 19 -> description = "Compare la somme du premier du deuxième chiffre du code avec la valeur 6";
            case 20 -> description = "Détermine si un chiffre du code se répète, et si oui, combien de fois";
            case 21 -> description = "Détermine si un chiffre du code se trouve en exactement deux exemplaires dans le code (mais pas trois)";
            case 22 -> description = "Détermine si les trois chiffres du code sont en ordre croissant ou décroissant";
        }
        return description;
    }

    /**
     * Updates the view when notified by the game model.
     */
    @Override
    public void update() {
        displayValidatorsStates(gameFacade.getValidators());
    }
}
