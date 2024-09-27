package viewFx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.TurningMachineException;

import java.util.Objects;



/**
 * The {@code ApplicationFx} class is the entry point for the JavaFX application.
 * It extends {@code Application} and provides the main method to launch the application.
 */
public class ApplicationFx extends Application {

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Override the start method to configure the primary stage and set up the initial scene.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        HBox rootHBox = createRootHBox();
        Scene scene = new Scene(rootHBox, Color.WHITE);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        configurePrimaryStage(primaryStage, scene);
    }

    /**
     * Creates the root HBox for the application.
     *
     * @return The root HBox.
     */
    private HBox createRootHBox() {
        HBox rootHBox = new HBox();
        rootHBox.setStyle("-fx-background-color: white;");
        rootHBox.setAlignment(Pos.CENTER);

        InitialPane initialPane = createInitialPane(rootHBox);
        rootHBox.getChildren().add(initialPane);

        return rootHBox;
    }

    /**
     * Creates the initial pane for the application.
     *
     * @param rootHBox The root HBox.
     * @return The initial pane.
     */
    private InitialPane createInitialPane(HBox rootHBox) {
        InitialPane initialPane = new InitialPane();
        VBox startVBox = new VBox();
        startVBox.getChildren().add(initialPane);

        ProblemChooserPane problemPane = createProblemChooserPane(rootHBox, initialPane);
        initialPane.getBtnProblem().setOnAction(e -> changePage(rootHBox, problemPane));
        initialPane.getBtnPlay().setOnAction(e -> createAndChangeToGamePlayPane(rootHBox, initialPane));

        return initialPane;
    }

    /**
     * Creates the problem chooser pane for the application.
     *
     * @param rootHBox   The root HBox.
     * @param initialPane The initial pane.
     * @return The problem chooser pane.
     */
    private ProblemChooserPane createProblemChooserPane(HBox rootHBox, InitialPane initialPane) {
        ProblemChooserPane problemPane = new ProblemChooserPane();
        problemPane.getBtnBack().setOnAction(e -> changePage(rootHBox, initialPane));
        problemPane.getBtnLoad().setOnAction(e -> loadProblemAndCreateGamePlayPane(rootHBox, initialPane, problemPane));
        return problemPane;
    }

    /**
     * Creates and changes to the game play pane.
     *
     * @param rootHBox     The root HBox.
     * @param initialPane  The initial pane.
     */
    private void createAndChangeToGamePlayPane(HBox rootHBox, InitialPane initialPane) {
        GamePlayPane gamePlayPane = new GamePlayPane();
        changePage(rootHBox, gamePlayPane);

        gamePlayPane.getBtnBack().setOnAction(event -> changePage(rootHBox, initialPane));
        setupAlertActions(gamePlayPane, rootHBox, initialPane);
    }

    /**
     * Loads a problem and creates the game play pane.
     *
     * @param rootHBox   The root HBox.
     * @param initialPane The initial pane.
     * @param problemPane The problem chooser pane.
     */
    private void loadProblemAndCreateGamePlayPane(HBox rootHBox, InitialPane initialPane, ProblemChooserPane problemPane) {
        try {
            int problemNumber = problemPane.getProblemChoiceValue();
            GamePlayPane gamePlayPane = new GamePlayPane(problemNumber);
            changePage(rootHBox, gamePlayPane);

            gamePlayPane.getBtnBack().setOnAction(event -> changePage(rootHBox, initialPane));
            setupAlertActions(gamePlayPane, rootHBox, initialPane);
        } catch (TurningMachineException ignored) {
        }
    }

    /**
     * Sets up alert actions for the game play pane.
     *
     * @param gamePlayPane The game play pane.
     * @param rootHBox     The root HBox.
     * @param initialPane  The initial pane.
     */
    private void setupAlertActions(GamePlayPane gamePlayPane, HBox rootHBox, InitialPane initialPane) {
        gamePlayPane.getAlert().setOnCloseRequest(event -> {
            if (gamePlayPane.getAlert().getResult() == gamePlayPane.getReplayButtonType()) {
                changePage(rootHBox, initialPane);
            } else if (gamePlayPane.getAlert().getResult() == gamePlayPane.getQuitButtonType()) {
                Platform.exit();
            }
        });
    }

    /**
     * Configures the primary stage with the provided scene.
     *
     * @param primaryStage The primary stage.
     * @param scene         The scene to set on the primary stage.
     */
    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Turing Machine");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Changes the current page in the root HBox.
     *
     * @param root     The root HBox.
     * @param newContent The new content to set in the root HBox.
     */
    private void changePage(HBox root, VBox newContent) {
        root.setFillHeight(true);
        root.getChildren().setAll(newContent);
    }
}
