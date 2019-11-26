package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.w3c.dom.html.HTMLOptGroupElement;

import java.beans.EventHandler;
import java.io.IOException;
import java.util.List;

public class MainGUI extends Application {
    public StackPane stackPane;
    private boolean toggle = true;
    public Button addButton;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @FXML
    private void test() throws IOException {
        System.out.println("pressed");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("replacement.fxml"));
        AnchorPane anchorPane = loader.load();

        int size = stackPane.getChildren().size();

        if (toggle) {
            stackPane.getChildren().add(anchorPane);
            toggle = false;
        } else {
            stackPane.getChildren().remove(size - 1);
            toggle = true;
        }

    }
}
