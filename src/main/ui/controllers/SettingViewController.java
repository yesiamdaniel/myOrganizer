package ui.controllers;

import java.io.IOException;

public class SettingViewController extends ViewController {
    private String fxmlFileName = "settingsView.fxml";

    public SettingViewController() throws IOException {
        node = load(fxmlFileName);
    }
}
