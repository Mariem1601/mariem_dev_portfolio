package viewFx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * The {@code InitialPane} class represents the initial user interface pane for the Turing Machine game.
 * It contains buttons for starting the game and searching for problems.
 */
public class InitialPane extends VBox {

    /** The "PLAY" button. */
    private final Button btnPlay;

    /** The "PROBLEM SEARCH" button. */
    private final Button btnProblem;

    /**
     * Constructs a new {@code InitialPane} with buttons for starting the game and searching for problems.
     */
    public InitialPane() {
        // Adding images
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/turingmachine.png"), "Image not found: turingmachine.png"));
        ImageView imageView = createImageView(image1);
        getChildren().add(imageView);

        // Creating buttons
        btnPlay = createButton("PLAY", "btnPlay");
        btnProblem = createButton("PROBLEM SEARCH", "btnProblem");

        // Set the background to white
        setStyle("-fx-background-color: white;");

        // Set padding and alignment
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
    }

    /**
     * Creates a button with the specified text and ID.
     *
     * @param text The text to display on the button.
     * @param id   The ID to set for the button.
     * @return The created button.
     */
    private Button createButton(String text, String id) {
        Button button = new Button(text);
        button.setAlignment(Pos.CENTER);
        button.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(button);
        setMargin(button, new Insets(10, 10, 10, 10));
        button.setId(id);
        return button;
    }

    /**
     * Creates an {@code ImageView} with the specified image.
     *
     * @param image The image to display in the {@code ImageView}.
     * @return The created {@code ImageView}.
     */
    private ImageView createImageView(Image image) {
        // Creating an ImageView with the provided image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);  // Image width
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Gets the "PLAY" button.
     *
     * @return The "PLAY" button.
     */
    public Button getBtnPlay() {
        return btnPlay;
    }

    /**
     * Gets the "PROBLEM SEARCH" button.
     *
     * @return The "PROBLEM SEARCH" button.
     */
    public Button getBtnProblem() {
        return btnProblem;
    }
}
