package ui;

import model.DateTime;
import model.handler.TaskManager;
import network.Weather;

import java.io.IOException;
import java.util.Scanner;

public class GuiElements {
    private static TaskManager taskManager;
    static DateTime dateTime;
    private static Weather weather;
    private static String city = "vancouver";

    public GuiElements() throws IOException {
        taskManager = new TaskManager();
        taskManager.init();
        dateTime = new DateTime();
        weather = new Weather(city);
    }

    String getDate() {
        return dateTime.getCurrentDate();
    }

    String getTime() {
        return dateTime.getCurrentTime();
    }
}
