package ui.controllers;

import java.io.IOException;

public class DeleteViewController extends ViewController {
    private String fxmlFileName = "deleteView.fxml";

    public DeleteViewController() throws IOException {
        node = load(fxmlFileName);
    }
}
