package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.controllers.*;

import java.io.IOException;

public class MainGUI extends Application {
    private GuiElements guiElements = new GuiElements();
    public  StackPane stackPane;
    public AnchorPane mainScreen;

    public Label welcomeLabel;
    public Label dateLabel;

    public ToggleGroup toggleGroup;
    public ToggleButton addButton;
    public ToggleButton viewButton;
    public ToggleButton deleteButton;
    public ToggleButton settingsButton;

    private ToggleButton currentSelected;

    private static ViewController addViewController;
    private static ViewController viewViewController;
    private static ViewController deleteViewController;
    private static ViewController settingsViewController;

    public MainGUI() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        instantiateFields(root);

        primaryStage.show();
    }

    // MODIFIES: this
    // EFFECTS: instantiates all controllers and ui elements
    private void instantiateFields(Parent root)  {
        try {
            addViewController = new AddViewController();
            viewViewController = new ViewViewController();
            deleteViewController = new DeleteViewController();
            settingsViewController = new SettingViewController();

            setDateLabel(root);
            setTimeLabel(root);
        } catch (IOException e) {
            System.out.println("ERROR IN LOADING FIELDS");
        }
    }

    // ActionEvent handler for buttons on main screen.
    //EFFECTS: presents respective view over stack pane
    @FXML
    public void presentView(ActionEvent actionEvent) throws IOException {
        ToggleButton source = (ToggleButton) actionEvent.getSource();

        // If selected button re-clicked return to home
        if (!(currentSelected == null)) {
            if (currentSelected.equals(source)) {
                hideAllButMain();
                currentSelected = null;
                return;
            }
        }

        if (addButton.equals(source)) {
            addViewController.displayOn(stackPane);
        } else if (viewButton.equals(source)) {
            viewViewController.displayOn(stackPane);
        } else if (deleteButton.equals(source)) {
            deleteViewController.displayOn(stackPane);
        } else if (settingsButton.equals(source)) {
            settingsViewController.displayOn(stackPane);
        }

        currentSelected = source;
    }

    // EFFECTS: hides all but main
    private void hideAllButMain() {
        mainScreen.setVisible(true);
        for (Node node : stackPane.getChildren()) {
            if (!(node.equals(mainScreen))) {
                node.setVisible(false);
            }
        }
    }

    private void setDateLabel(Parent root) {
        dateLabel = (Label) root.getScene().lookup("#dateLabel");
        dateLabel.setText(guiElements.getDate());
    }

    private void setTimeLabel(Parent root) {
        Label timeLabel = (Label) root.getScene().lookup("#timeLabel");
        timeLabel.setText(guiElements.getTime());
    }

}
