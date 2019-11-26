package ui.controllers;

import java.io.IOException;

public class ViewViewController extends ViewController {
    private String fxmlFileName = "viewView.fxml";

    public ViewViewController() throws IOException {
        node = load(fxmlFileName);
    }
}
