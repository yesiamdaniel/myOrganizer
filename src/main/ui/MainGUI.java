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

import java.awt.Button;
import java.io.IOException;

public class MainGUI extends Application {
    public  StackPane stackPane;
    public AnchorPane mainScreen;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        instantiateFields();
        primaryStage.show();
    }

    private void instantiateFields()  {
        try {
            addViewController = new AddViewController();
            viewViewController = new ViewViewController();
            deleteViewController = new DeleteViewController();
            settingsViewController = new SettingViewController();
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

}
