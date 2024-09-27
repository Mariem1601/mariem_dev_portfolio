package viewFx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Code;
import model.GameFacade;
import model.TurningMachineException;
import util.Observer;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The {@code GamePlayPane} class represents the user interface pane for playing the Turing Machine game.
 * It includes the game elements such as the keyboard, score, round information, and validator cards.
 */
public class GamePlayPane extends VBox implements Observer {

    /** The game facade managing the game logic. */
    private final GameFacade gameFacade;

    /** The "Back" button to return to the home page. */
    private Button btnBack;

    /** The label for displaying error messages. */
    private Label errorLabel;

    /** The grid for the keyboard. */
    private GridPane keyboard;

    /** The label displaying the score. */
    private Label score;

    /** The label displaying the current round. */
    private Label round;

    /** The text field for entering the solution code. */
    private TextField checkCodeField;

    /** The alert for displaying game results. */
    private Alert alert;

    /** The button type for replaying the game. */
    private ButtonType replayButton;

    /** The button type for quitting the game. */
    private ButtonType quitButton;

    /** The map associating validator numbers with their respective robot images. */
    private final Map<Integer, ImageView> robotImageMap = new HashMap<>();

    /** The map associating validator numbers with their respective robot letters. */
    private final Map<Integer, Character> robotLetterMap = new HashMap<>();

    /** The 2D array of keyboard buttons. */
    private ToggleButton[][] keyboardButtons;

    /**
     * Constructs a new {@code GamePlayPane} with the default problem.
     */
    public GamePlayPane() {
        gameFacade = new GameFacade();
        gameFacade.addObserver(this);
        createAndAddElements();
    }

    /**
     * Constructs a new {@code GamePlayPane} with the specified problem number.
     *
     * @param problemNumber The problem number to start the game with.
     */
    public GamePlayPane(int problemNumber) {
        gameFacade = new GameFacade(problemNumber);
        gameFacade.addObserver(this);
        createAndAddElements();
    }

    /**
     * Creates and adds UI elements to the game play pane.
     */
    public void createAndAddElements() {
        alert = new Alert(AlertType.NONE);
        createKeyboard();
        createBackButton();
        createErrorLabel();
        createCheckCodeField();

        HBox left = createLeftHBox(
                createKeyboardAndValidateBtn(),
                createButtonsVBox(),
                createCheckCodeBtn()
        );
        VBox right = createInfoGameVBox();
        HBox lowerHBox = new HBox(left, right);

        getChildren().add(createValidatorsCards());
        getChildren().add(errorLabel);
        getChildren().add(lowerHBox);
        getChildren().add(btnBack);

        setAlignment(Pos.CENTER);
    }

    /**
     * Creates the "Check Code" button.
     *
     * @return The created "Check Code" button.
     */
    private Button createCheckCodeBtn() {
        Button btnCheckCode = createButton(this::handleCheckCodeButtonClick);
        btnCheckCode.setId("btn");
        return btnCheckCode;
    }

    /**
     * Creates the text field for entering the solution code.
     */
    private void createCheckCodeField() {
        checkCodeField = new TextField();
        checkCodeField.setPromptText("Enter solution code");
        setupCodeTextField();
    }

    /**
     * Creates the VBox containing the control buttons.
     *
     * @return The created VBox with control buttons.
     */
    private VBox createButtonsVBox() {
        VBox buttonsVBox = new VBox();
        buttonsVBox.getChildren().add(createNextButton());
        buttonsVBox.getChildren().add(createUndoButton());
        buttonsVBox.getChildren().add(createRedoButton());
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(10);
        return buttonsVBox;
    }

    /**
     * Creates the VBox containing the keyboard and the "Validate" button.
     *
     * @return The created VBox with the keyboard and "Validate" button.
     */
    private VBox createKeyboardAndValidateBtn() {
        VBox keyboardVBox = new VBox();
        keyboardVBox.getChildren().addAll(keyboard, createValidateButton());
        keyboardVBox.setAlignment(Pos.CENTER);

        return keyboardVBox;
    }

    /**
     * Creates the HBox containing the keyboard, control buttons, solution code field, and "Check Code" button.
     *
     * @param keyboard The VBox containing the keyboard.
     * @param buttons The VBox containing the control buttons.
     * @param btnCheckCode The "Check Code" button.
     * @return The created HBox with the specified components.
     */
    private HBox createLeftHBox(VBox keyboard, VBox buttons, Button btnCheckCode) {
        HBox left = new HBox();
        left.getChildren().addAll(keyboard, buttons, checkCodeField, btnCheckCode);
        HBox.setHgrow(left, Priority.ALWAYS);
        left.setAlignment(Pos.CENTER);
        left.setPadding(new Insets(0, 20, 20, 20));
        left.setSpacing(40);

        return left;
    }

    /**
     * Creates the VBox containing information about the current game (problem number, score, round).
     *
     * @return The created VBox with game information.
     */
    private VBox createInfoGameVBox() {
        VBox infoGame = new VBox();
        Label problem = new Label();
        score = new Label();
        round = new Label();
        problem.setText("Problem number: " + gameFacade.getProblemNumber());
        score.setText("Score: " + gameFacade.getScore());
        round.setText("Round: " + gameFacade.getRoundNumber());

        infoGame.getChildren().addAll(problem, round, score);
        infoGame.setAlignment(Pos.TOP_RIGHT);
        infoGame.setPadding(new Insets(0, 80, 0, 0));

        return infoGame;
    }

    /**
     * Creates the label for displaying error messages.
     */
    private void createErrorLabel() {
        errorLabel = new Label();
        errorLabel.setText("Enter a code");
        errorLabel.setTextFill(Color.web("#2DB563")); // vert
        errorLabel.setId("errorLabel");
        errorLabel.setPadding(new Insets(20));
    }

    /**
     * Handles the "Check Code" button click event.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    private void handleCheckCodeButtonClick(ActionEvent actionEvent) {
        try {
            String enteredCode = checkCodeField.getText();
            int codeAsInt = Integer.parseInt(enteredCode);
            if(gameFacade.checkCode(codeAsInt)){
                showAlert("Congratulations", "You won!");
            }else{
                showAlert("Game Over", "You lose.");
            }
        } catch (TurningMachineException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }

    /**
     * Sets up the text field for entering the solution code with a specific format.
     */
    private void setupCodeTextField() {
        StringConverter<Integer> converter = new IntegerStringConverter();
        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, null,
                change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-5]{0,3}")) {
                        return change;
                    } else {
                        return null;
                    }
                });
        checkCodeField.setTextFormatter(textFormatter);
    }

    /**
     * Creates a button with the specified event handler.
     *
     * @param handler The event handler for the button.
     * @return The created button.
     */
    private Button createButton(EventHandler<ActionEvent> handler) {
        Button button = new Button("Check Code");
        button.setAlignment(Pos.BOTTOM_CENTER);
        button.setId("btn-round");
        button.setOnAction(handler);
        return button;
    }

    /**
     * Creates the "Back" button to return to the home page.
     */
    private void createBackButton() {
        btnBack = new Button("<- Back to home page");
        btnBack.setAlignment(Pos.BOTTOM_CENTER);
        btnBack.setPadding(new Insets(20));
        btnBack.setId("btnBack");
    }

    /**
     * Creates the "Validate" button for confirming the selected code.
     *
     * @return The created "Validate" button.
     */
    private Button createValidateButton() {
        Button btnValidate = new Button("Validate");
        btnValidate.setId("btn");
        btnValidate.setAlignment(Pos.BOTTOM_CENTER);

        btnValidate.setOnAction(event -> {
            try {
                errorLabel.setText("");
                int code = getCodeSelected(keyboard);
                gameFacade.chooseCode(code);
                hideKeyboard(gameFacade.hasEnteredCode());
            }
            catch (TurningMachineException e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setTextFill(Color.RED);
            }

        });

        return btnValidate;
    }

    /**
     * Creates the "Next Round" button for advancing to the next round.
     *
     * @return The created "Next Round" button.
     */
    private Button createNextButton() {
        Button btnNext = new Button("Next round");
        btnNext.setAlignment(Pos.BOTTOM_CENTER);
        btnNext.setId("btn");

        btnNext.setOnAction(event -> {
            try {
                gameFacade.nextRound();
                clearButtonStyles();
                errorLabel.setText("");
                hideKeyboard(gameFacade.hasEnteredCode());
            } catch (TurningMachineException e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setTextFill(Color.RED);
            }
        });

        return btnNext;
    }

    /**
     * Creates the "Undo" button for reverting the last action.
     *
     * @return The created "Undo" button.
     */
    private Button createUndoButton() {
        Button btnUndo = new Button("UNDO");
        btnUndo.setAlignment(Pos.BOTTOM_CENTER);
        btnUndo.setId("btn");

        btnUndo.setOnAction(e -> {
            try {
                gameFacade.getManager().undo();
                errorLabel.setText("");
                hideKeyboard(gameFacade.hasEnteredCode());
                if (gameFacade.getProposedCode() != null) {
                    resetButtonStyles();
                }

            } catch (TurningMachineException e1) {
                errorLabel.setText(e1.getMessage());
                errorLabel.setTextFill(Color.RED);
            }
        });

        return btnUndo;
    }

    /**
     * Creates the "Redo" button for redoing the last undone action.
     *
     * @return The created "Redo" button.
     */
    private Button createRedoButton() {
        Button btnRedo = new Button("REDO");
        btnRedo.setAlignment(Pos.BOTTOM_CENTER);
        btnRedo.setId("btn");

        btnRedo.setOnAction(e -> {
            try {
                gameFacade.getManager().redo();
                errorLabel.setText("");
                hideKeyboard(gameFacade.hasEnteredCode());
            } catch (TurningMachineException e1) {
                errorLabel.setText(e1.getMessage());
                errorLabel.setTextFill(Color.RED);
            }
        });

        return btnRedo;
    }

    /**
     * Creates a horizontal box containing cards for each validator.
     *
     * @return The created horizontal box containing validator cards.
     */
    private HBox createValidatorsCards() {
        char[] robotLetter = {'A', 'B', 'C', 'D', 'E', 'F'};
        HBox validators = new HBox();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double imageSizePercentage = 0.15;

        validators.setPrefWidth(screenWidth);
        int index = 0;

        for (Integer v : gameFacade.validatorsList()) {
            VBox validatorVBox = createValidatorVBox(v, robotLetter[index], imageSizePercentage * screenWidth);
            validators.getChildren().add(validatorVBox);
            robotLetterMap.put(v, robotLetter[index]);
            index++;
        }

        return validators;
    }

    /**
     * Creates a vertical box for a validator, containing the robot image and the corresponding card image.
     *
     * @param validator   The validator number.
     * @param robotLetter The letter associated with the robot.
     * @param imageSize   The size of the images in the validator box.
     * @return The created vertical box for the validator.
     */
    private VBox createValidatorVBox(Integer validator, char robotLetter, double imageSize) {
        VBox validatorVBox = new VBox();
        validatorVBox.setSpacing(20);
        validatorVBox.setPadding(new Insets(20, 0, 20, 0));

        String card = "/validatorPictures/card" + validator + ".png";
        Image cardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card), "Image not found: " + card));
        ImageView cardImageView = createImageView(cardImg, imageSize);

        ImageView robotImageView = createValidatorImageView(robotLetter);
        robotImageMap.put(validator, robotImageView);

        robotImageView.setOnMouseClicked(e -> handleValidatorClick(validator));

        validatorVBox.getChildren().addAll(robotImageView, cardImageView);
        validatorVBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(validatorVBox, Priority.ALWAYS);
        validatorVBox.setMaxWidth(Double.MAX_VALUE);

        return validatorVBox;
    }

    /**
     * Handles the click event on a validator.
     *
     * @param validator The validator number.
     */
    private void handleValidatorClick(Integer validator) {
        try {
            gameFacade.checkValidator(validator);
            errorLabel.setText("");
        } catch (TurningMachineException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setTextFill(Color.RED);
        }
    }

    /**
     * Creates an image view for the robot associated with a validator.
     *
     * @param robotLetter The letter associated with the robot.
     * @return The created image view for the robot.
     */
    private ImageView createValidatorImageView(char robotLetter) {
        String robotPathWhite = "/robotPictures/robot" + robotLetter + "white.png";

        Image robotImgWhite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(robotPathWhite), "Image not found: " + robotPathWhite));

        ImageView robotImageView = createImageView(robotImgWhite, 70);
        robotImageView.setId("btnRobot");

        return robotImageView;
    }

    /**
     * Creates an image view with the specified image and size.
     *
     * @param image   The image to display in the image view.
     * @param taille  The size of the image view.
     * @return The created image view.
     */
    private ImageView createImageView(Image image, double taille) {
        // Création d'un ImageView avec l'image fournie
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(taille);  // Largeur de l'image
        imageView.setPreserveRatio(true);

        return imageView;
    }

    /**
     * Adds a centered image view to a grid at the specified column and row.
     *
     * @param grid      The grid to which the image view is added.
     * @param image     The image to display in the image view.
     * @param columnIndex The column index in the grid.
     * @param rowIndex    The row index in the grid.
     * @param imageSize   The size of the image view.
     */
    private void addCenteredImageViewToGrid(GridPane grid, Image image, int columnIndex, int rowIndex, int imageSize) {
        ImageView imageView = createImageView(image, imageSize);
        grid.add(imageView, columnIndex, rowIndex);
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);
    }

    /**
     * Hides or shows the keyboard based on the specified flag.
     *
     * @param hide If true, the keyboard is hidden; otherwise, it is shown.
     */
    private void hideKeyboard(boolean hide) {
        for (Node node : keyboard.getChildren()) {
            if (node instanceof ToggleButton button) {
                button.setDisable(hide);
            }
        }
    }

    /**
     * Clears the styles of all buttons in the keyboard.
     */
    private void clearButtonStyles() {
        for (Node node : keyboard.getChildren()) {
            if (node instanceof ToggleButton button) {
                button.getStyleClass().remove("active");
                button.setSelected(false); // Désélectionner le bouton
            }
        }
    }

    /**
     * Handles the button click event in the keyboard, applying styles to the buttons.
     *
     * @param keyboard The grid pane containing the keyboard buttons.
     * @param column   The column index of the clicked button.
     * @param button   The clicked toggle button.
     */
    private void handleButtonClick(GridPane keyboard, int column, ToggleButton button) {
        // Remove the "active" class from all buttons in the column
        keyboard.getChildren().stream()
                .filter(node -> node instanceof ToggleButton)
                .map(node -> (ToggleButton) node)
                .filter(btn -> GridPane.getColumnIndex(btn) == column)
                .forEach(btn -> btn.getStyleClass().remove("active"));

        // Apply the "active" class to the specific button
        button.getStyleClass().add("active");
    }

    /**
     * Retrieves the selected code from the keyboard.
     *
     * @param keyboard The grid pane containing the keyboard buttons.
     * @return The integer code selected by the user.
     */
    private int getCodeSelected(GridPane keyboard) {
        StringBuilder value = new StringBuilder();
        for (Node node : keyboard.getChildren()) {
            if (node instanceof ToggleButton toggleButton) {
                if (toggleButton.isSelected()) {
                    value.append(toggleButton.getText());
                }
            }
        }
        if (value.toString().length()!=3) {
            throw new TurningMachineException("Enter a code.");
        }
        return Integer.parseInt(value.toString());
    }

    /**
     * Gets the "Back" button.
     *
     * @return The "Back" button.
     */
    public Button getBtnBack() {
        return btnBack;
    }

    /**
     * Colors the validator associated with the given number with the specified color and robot letter.
     *
     * @param nbValidator The number of the validator.
     * @param color       The color to apply to the validator.
     * @param robotLetter The letter associated with the robot.
     */
    public void colorValidator(int nbValidator, String color, char robotLetter) {
        ImageView robotButton = robotImageMap.get(nbValidator);
        if (robotButton != null) {
            String face = "/robotPictures/robot" + robotLetter + color + ".png";

            Image newRobotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(face)));
            robotButton.setImage(newRobotImage);
        }
    }

    /**
     * Displays an alert with the specified title and content text.
     *
     * @param title       The title of the alert.
     * @param contentText The content text of the alert.
     */
    private void showAlert(String title, String contentText) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initModality(Modality.APPLICATION_MODAL);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        replayButton = new ButtonType("Replay", ButtonBar.ButtonData.BACK_PREVIOUS);
        quitButton = new ButtonType("Quit", ButtonBar.ButtonData.FINISH);

        alert.getButtonTypes().add(replayButton);
        alert.getButtonTypes().add(quitButton);

        // Close the main window after displaying the pop-up
        alert.showAndWait();
    }

    /**
     * Creates the keyboard containing shape images and initializes the keyboard buttons.
     */
    private void createKeyboard() {
        keyboard = new GridPane();
        keyboard.setHgap(10);
        keyboard.setVgap(10);
        keyboard.setPadding(new Insets(10));

        addShapeImagesToGrid();
        createKeyboardButtons();
    }

    /**
     * Adds shape images (triangle, square, circle) to the keyboard grid.
     */
    private void addShapeImagesToGrid() {
        addCenteredImageViewToGrid(keyboard, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/triangle.png"))), 0, 0, 25);
        addCenteredImageViewToGrid(keyboard, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/square.png"))), 1, 0, 25);
        addCenteredImageViewToGrid(keyboard, new Image(Objects.requireNonNull(getClass().getResourceAsStream("/shapes/circle.png"))), 2, 0, 25);
    }

    /**
     * Creates the keyboard buttons and initializes the toggle group.
     */
    private void createKeyboardButtons() {
        keyboardButtons = new ToggleButton[3][5];

        for (int col = 0; col < 3; col++) {
            ToggleGroup toggleGroup = new ToggleGroup();

            int value = 5;
            for (int row = 1; row <= 5; row++) {
                ToggleButton button = createToggleButton(Integer.toString(value), toggleGroup, col, row);
                keyboardButtons[col][row - 1] = button;
                value--;
            }
        }
    }

    /**
     * Creates a toggle button with the specified text and adds it to the keyboard grid.
     *
     * @param text       The text to display on the button.
     * @param toggleGroup The toggle group for the button.
     * @param col        The column index in the keyboard grid.
     * @param row        The row index in the keyboard grid.
     * @return The created toggle button.
     */
    private ToggleButton createToggleButton(String text, ToggleGroup toggleGroup, int col, int row) {
        ToggleButton button = new ToggleButton(text);
        button.setMinSize(40, 40);
        button.setMaxSize(40, 40);
        button.setToggleGroup(toggleGroup);
        keyboard.add(button, col, row);
        button.getStyleClass().add("column" + col);

        int finalCol = col;
        button.setOnMousePressed(e -> handleButtonClick(keyboard, finalCol, button));

        return button;
    }

    /**
     * Resets the styles of the keyboard buttons based on the proposed code.
     */
    private void resetButtonStyles() {
        Code code = gameFacade.getProposedCode();

        int first = code.getFirst();
        int second = code.getSecond();
        int third = code.getThird();

        clearButtonStyles(); // Deselect all buttons first

        // Add the "active" class to buttons corresponding to the digits of the code
        keyboardButtons[0][5 - first].getStyleClass().add("active");
        keyboardButtons[0][5 - first].setSelected(true);

        keyboardButtons[1][5 - second].getStyleClass().add("active");
        keyboardButtons[1][5 - second].setSelected(true);

        keyboardButtons[2][5 - third].getStyleClass().add("active");
        keyboardButtons[2][5 - third].setSelected(true);
    }

    /**
     * Gets the alert instance.
     *
     * @return The alert instance.
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * Gets the "Replay" button type.
     *
     * @return The "Replay" button type.
     */
    public ButtonType getReplayButtonType() {
        return replayButton;
    }

    /**
     * Gets the "Quit" button type.
     *
     * @return The "Quit" button type.
     */
    public ButtonType getQuitButtonType() {
        return quitButton;
    }

    /**
     * Updates the UI components based on the current state of the game.
     */
    @Override
    public void update() {
        for (Map.Entry<Integer, String> entry : gameFacade.getValidators().entrySet()) {
            Integer validator = entry.getKey();
            String color = entry.getValue();
            char robotLetter = robotLetterMap.get(validator);
            colorValidator(validator, color, robotLetter);
        }

        score.setText("Score: " + gameFacade.getScore());
        round.setText("Round: " + gameFacade.getRoundNumber());
    }

}
