package ui.controllers;

import java.io.IOException;

public class AddViewController extends ViewController {
    private String fxmlFileName = "addView.fxml";

    public AddViewController() throws IOException {
        node = load(fxmlFileName);
    }

}
