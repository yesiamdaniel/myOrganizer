package ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import ui.Main;
import ui.MainGUI;

import java.io.IOException;
import java.util.List;

public abstract class ViewController {
    private boolean loaded = false;
    Node node;

    //MODIFIES: this
    //EFFECTS: Call within constructor of view controller object to load the viewControllers respective node and set
    Node load(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainGUI.class.getResource("/ui/controllers/fxml/" + fxmlFileName));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    //REQUIRES: node hasn't been presented before
    //EFFECTS: presents node on the top of given stack pane. checks for duplicates first
    public void displayOn(StackPane stackPane) {
        if (!(duplicates(stackPane))) {
            stackPane.getChildren().add(node);

            System.out.println(stackPane.getChildrenUnmodifiable());
        }
        removeAllButSelf(stackPane);
    }

    private void removeAllButSelf(StackPane stackPane) {
        node.setVisible(true);
        for (Node node : stackPane.getChildren()) {
            if (!(node.equals(this.node))) {
                node.setVisible(false);
            }
        }
    }

    //EFFECTS: returns true if node is in stackPanes children and presents node, otherwise returns false
    private boolean duplicates(StackPane stackPane) {
        List children = stackPane.getChildrenUnmodifiable();

        if (children.contains(node)) {
            node.toFront();
            return true;
        } else {
            return false;
        }
    }
}
