package controller;

import model.GameFacade;
import model.TurningMachineException;
import viewConsole.View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private GameFacade gameFacade;
    private View view;
    private boolean gameEnd;

    private static final String CODE_REGEX = "code (\\d+)";
    private static final String VALIDATOR_REGEX = "validator (\\d+)";
    private static final String VERIFY_REGEX = "verify (\\d+)";
    private static final String NEXT_ROUND_REGEX = "next";
    private static final String REDO_REGEX= "redo";
    private static final String UNDO_REGEX= "undo";
    private static final String SHOW_VALIDATORS_REGEX = "show";

    private static final Pattern CODE_PATTERN = Pattern.compile(CODE_REGEX);
    private static final Pattern VALIDATOR_PATTERN = Pattern.compile(VALIDATOR_REGEX);
    private static final Pattern VERIFY_PATTERN = Pattern.compile(VERIFY_REGEX);

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    /**
     * Starts the game application.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        boolean chooseProblem = askForSpecifiedProblem(scanner);

        if (chooseProblem) {
            createGame(scanner);
        } else {
            gameFacade = new GameFacade();
        }

        gameEnd = false;
        view = new View(gameFacade);

        view.displayHelp();
        view.displayEnterCode();

        String command;
        do {
            System.out.print("Enter a command: ");
            command = scanner.nextLine();
            handleCommand(command);
        } while (!command.equalsIgnoreCase("exit") && !gameEnd);
    }

    /**
     * Asks the user if they want to choose a specified problem.
     *
     * @param scanner Scanner object for user input.
     * @return True if the user wants to choose a specified problem, false otherwise.
     */
    private boolean askForSpecifiedProblem(Scanner scanner) {
        System.out.print("Would you like to choose a specified problem? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes") || response.equals("oui");
    }

    /**
     * Creates the game based on user input.
     *
     * @param scanner Scanner object for user input.
     */
    private void createGame(Scanner scanner) {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter the number of the problem: ");
                int nb = scanner.nextInt();
                gameFacade = new GameFacade(nb);
                validInput = true;
            } catch (TurningMachineException e) {
                System.out.println("Invalid problem number. Please provide a valid problem number.");
            }
        }
    }

    /**
     * Handles the user command during the game.
     *
     * @param command User command.
     */
    private void handleCommand(String command) {
        try {
            if (command.matches(CODE_REGEX)) {
                handleCodeCommand(command);
            } else if (command.matches(VALIDATOR_REGEX)) {
                handleValidatorCommand(command);
            } else if (command.matches(VERIFY_REGEX)) {
                handleVerifyCodeCommand(command);
            } else if (command.matches(SHOW_VALIDATORS_REGEX)) {
                view.displayValidators(gameFacade.validatorsList());
            } else if (command.matches(NEXT_ROUND_REGEX)) {
                gameFacade.nextRound();
                view.displayEnterCode();
            } else if (command.matches(UNDO_REGEX)) {
                gameFacade.getManager().undo();
            } else if (command.matches(REDO_REGEX)) {
                gameFacade.getManager().redo();
            } else if (command.equalsIgnoreCase("help")) {
                view.displayHelp();
            } else if (!command.equalsIgnoreCase("exit")) {
                System.out.println("Invalid command.");
            }
        } catch (TurningMachineException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles the user command for entering a code.
     *
     * @param command User command.
     */
    private void handleCodeCommand(String command) {
        Matcher matcherCode = CODE_PATTERN.matcher(command);
        if (matcherCode.find()) {
            int Code = Integer.parseInt(matcherCode.group(1));
            gameFacade.chooseCode(Code);
        }
    }

    /**
     * Handles the user command for checking a validator.
     *
     * @param command User command.
     */
    private void handleValidatorCommand(String command) {
        Matcher matcherValidator = VALIDATOR_PATTERN.matcher(command);
        if (matcherValidator.find()) {
            int nbValidator = Integer.parseInt(matcherValidator.group(1));
            gameFacade.checkValidator(nbValidator);
        }
    }

    /**
     * Handles the user command for verifying the entered code.
     *
     * @param command User command.
     */
    private void handleVerifyCodeCommand(String command) {
        Matcher matcherValidator = VERIFY_PATTERN.matcher(command);
        if (matcherValidator.find()) {
            int code = Integer.parseInt(matcherValidator.group(1));
            if (gameFacade.checkCode(code)) {
                view.displayWin(gameFacade.totalScore());
            } else {
                view.displayLost(gameFacade.totalScore());
            }
            gameEnd = true;
        }
    }
}
