package viewFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.TurningMachineException;

import java.util.Arrays;

/**
 * The {@code ProblemChooserPane} class represents the user interface pane for choosing a problem in the Turing Machine game.
 * It contains a combo box for selecting a problem ID, a "LOAD" button to load the selected problem, and a "Back" button to return to the home page.
 */
public class ProblemChooserPane extends VBox {

    /** The "LOAD" button. */
    private Button btnLoad;

    /** The "Back" button. */
    private Button btnBack;

    /** The combo box for selecting a problem ID. */
    private ComboBox<Integer> problemChoice;

    /**
     * Constructs a new {@code ProblemChooserPane} with a combo box, "LOAD" button, and "Back" button.
     */
    public ProblemChooserPane() {
        addLabels();
        addProblemChoiceComboBox();
        addLoadButton();
        addBackButton();

        setStyle("-fx-background-color: white;");
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
    }

    /**
     * Adds labels for the "Problem Search" and "Choose Game ID" sections.
     */
    private void addLabels() {
        Label prblmSearch = new Label("Problem Search");
        prblmSearch.setId("labelProblem");

        Label choose = new Label("Choose Game ID");
        choose.setId("labelChoose");

        getChildren().addAll(prblmSearch, choose);
    }

    /**
     * Adds a combo box for selecting a problem ID.
     */
    private void addProblemChoiceComboBox() {
        problemChoice = new ComboBox<>(
                FXCollections.observableArrayList(
                        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
                )
        );

        problemChoice.setMaxWidth(Double.MAX_VALUE);
        setMargin(problemChoice, new Insets(10, 10, 10, 10));
        problemChoice.setId("comboBoxPrblm");

        getChildren().add(problemChoice);
    }

    /**
     * Adds a "LOAD" button.
     */
    private void addLoadButton() {
        btnLoad = createButton("LOAD", "btnLoad");
        getChildren().add(btnLoad);
    }

    /**
     * Adds a "Back" button.
     */
    private void addBackButton() {
        btnBack = createButton("<- Back to home page", "btnBack");
        btnBack.setAlignment(Pos.BOTTOM_CENTER);
        btnBack.setId("btnBack");

        getChildren().add(btnBack);
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
        button.setMaxWidth(Double.MAX_VALUE);
        setMargin(button, new Insets(10, 10, 10, 10));
        button.setId(id);
        return button;
    }

    /**
     * Gets the selected problem ID from the combo box.
     *
     * @return The selected problem ID.
     * @throws TurningMachineException If no problem ID is selected.
     */
    public int getProblemChoiceValue() {
        if (problemChoice.getValue() == null) {
            throw new TurningMachineException("You have to choose a problem number");
        }
        return problemChoice.getValue();
    }

    /**
     * Gets the "LOAD" button.
     *
     * @return The "LOAD" button.
     */
    public Button getBtnLoad() {
        return btnLoad;
    }

    /**
     * Gets the "Back" button.
     *
     * @return The "Back" button.
     */
    public Button getBtnBack() {
        return btnBack;
    }
}
