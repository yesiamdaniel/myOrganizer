package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGuiPractice extends Application implements Initializable {

    private Button button1;
    public ToggleGroup difficulty;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GuiFXML.fxml"));

        primaryStage.setTitle("Hello World");

        primaryStage.setScene(new Scene(root, 800, 500));

        primaryStage.show();
    }


    public void handleButtonClick(ActionEvent actionEvent) {
        System.out.println("Handle button click");
        button1.setText("hello");
    }

    @Override
    // Method is called right when gui opens
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("iniializing...");
    }

    public void logIn() {
        System.out.println("Logging in");
    }
}



















//// Everytime you want to implement event actions you must implement EventHandler
//public class MainGui extends Application implements EventHandler<ActionEvent> {
//
//    private Button button;
//    private Button button2;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Oraganizer");
//
//        button = new Button("Click me!");
//        button2 = new Button("Another button");
//        button2.setTranslateY(70);
//
//        // Sets delegate class which is EventHandler to look for Handle method
//        button.setOnAction(this);
//
//        // Handling events inline with method call using inner classes -> handle is now inline with call
//        // Not required to check event source
////        button2.setOnAction(new EventHandler<ActionEvent>() {
////            @Override
////            public void handle(ActionEvent event) {
////                System.out.println("Another button Clicked");
////
////            }
////        });
//
//        // Using lambda to compact code
//        button2.setOnAction(e -> {
//            System.out.println("Another button pressed");
//            System.out.println("Very cool");
//        });
//
//        TextField textField = new TextField("Enter Text");
//
//        StackPane layout = new StackPane();
//        layout.setBackground(new Background(new BackgroundFill(Color.TOMATO, null, null)));
//        layout.getChildren().add(button);
//        layout.getChildren().add(button2);
//        layout.getChildren().add(textField);
//
//        Scene scene = new Scene(layout, 300, 250);
//        scene.setFill(Color.BLACK);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    @Override
//    public void handle(ActionEvent event) {
//        // Get source identifies the object that called the handle
//        if (event.getSource() == button) {
//            // If source is button then do vv
//            System.out.println("CLicked");
//        }
//    }
//}


