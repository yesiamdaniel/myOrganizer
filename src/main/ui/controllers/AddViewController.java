package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DateTime;
import model.handler.TaskManager;
import model.handler.urgencyhandlers.Urgency;
import ui.GuiElements;

import java.io.IOException;
import java.util.HashMap;

public class AddViewController extends ViewController {
    private String fxmlFileName = "addView.fxml";
    @FXML public Button submitButton;
    private Button choreButton;
    private Button homeworkButton;
    @FXML private TextField classTextField;
    @FXML private TextField descriptionTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextField dueTimeTextField;
    @FXML private ToggleButton normal;
    @FXML private ToggleButton important;
    @FXML private ToggleButton urgent;

    // Constructor
    public AddViewController() throws IOException {
        node = load(fxmlFileName);
        choreButton = (Button) node.lookup("#choreButton");
        homeworkButton = (Button) node.lookup("#homeworkButton");

        choreButton.setOnAction(e -> {
            try {
                presentChoreCreator();
            } catch (IOException ex) {
                System.out.println("ERROR in AddViewController init");
                ex.printStackTrace();
            }
        });

        homeworkButton.setOnAction(e -> {
            try {
                presentHomeworkCreator();
            } catch (IOException ex) {
                System.out.println("ERROR in AddViewController init");
                ex.printStackTrace();
            }
        });
    }

    private void presentHomeworkCreator() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/controllers/fxml/homeworkCreator.fxml"));
        Stage window = new Stage();

        initFields(root);
        classTextField = (TextField) root.lookup("#classTextField");


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("CreateChore");
        submitButton.setOnAction(e -> {
            try {
                interpretChoreInput();
            } catch (IOException ex) {
                System.out.println("ERROR interpreting input");
                ex.printStackTrace();
            }
            window.close();
        });
        window.setScene(new Scene(root));
        window.showAndWait();
    }

    // Initializes all fields. On submit calls interpretInput then closes
    private void presentChoreCreator() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/controllers/fxml/choreCreator.fxml"));
        Stage window = new Stage();

        initFields(root);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("CreateChore");
        submitButton.setOnAction(e -> {
            try {
                interpretChoreInput();
            } catch (IOException ex) {
                System.out.println("ERROR interpreting input");
                ex.printStackTrace();
            }
            window.close();
        });
        window.setScene(new Scene(root));
        window.showAndWait();
    }

    // EFFECTS: initializes fields from root
    private void initFields(Parent root) {
        submitButton = (Button) root.lookup("#submitButton");
        descriptionTextField = (TextField) root.lookup("#descriptionTextField");
        dueTimeTextField = (TextField) root.lookup("#dueTimeTextField");
        datePicker = (DatePicker) root.lookup("#datePicker");
        normal = (ToggleButton) root.lookup("#normal");
        important = (ToggleButton) root.lookup("#important");
        urgent = (ToggleButton) root.lookup("#urgent");
    }

    // REQUIRES: valid non empty date, description
    private void interpretChoreInput() throws IOException {
        HashMap<String, Object> map = new HashMap<>();

        map.put("description", descriptionTextField.getText());
        map.put("dateTime", new DateTime(datePicker.getValue().toString(), dueTimeTextField.getText()));
        map.put("urgency", switchUrgency());

        GuiElements.createChore(map);
    }

    // REQUIRES: valid non empty date, description
    private void interpretHomeworkInput() throws IOException {
        HashMap<String, Object> map = new HashMap<>();

        map.put("description", descriptionTextField.getText());
        map.put("className", classTextField.getText());
        map.put("dateTime", new DateTime(datePicker.getValue().toString(), dueTimeTextField.getText()));
        map.put("urgency", switchUrgency());

        GuiElements.createHomework(map);
    }

    private Urgency switchUrgency() {
        if (normal.isSelected()) {
            return TaskManager.getNormal();
        } else if (important.isSelected()) {
            return TaskManager.getImportant();
        } else if (urgent.isSelected()) {
            return TaskManager.getUrgent();
        }
        return TaskManager.getNormal();
    }
}


