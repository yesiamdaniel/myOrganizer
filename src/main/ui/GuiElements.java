package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import model.DateTime;
import model.handler.TaskManager;
import model.handler.urgencyhandlers.Urgency;
import model.task.Task;
import network.Weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GuiElements {
    public static TaskManager taskManager;
    static DateTime dateTime;
    private static Weather weather;
    private static String city = "vancouver";

    GuiElements() throws IOException {
        taskManager = new TaskManager();
        taskManager.init();
        dateTime = new DateTime();
        weather = new Weather(city);
    }

    // MODIFIES: this
    // EFFECTS: given map that contains description, dateTime, and urgency, constructs a chore object
    public static void createChore(Map<String, Object> map) throws IOException {
        String description = (String) map.get("description");
        DateTime dateTime = (DateTime) map.get("dateTime");
        Urgency urgency = (Urgency) map.get("urgency");

        Task taskAdded = taskManager.create(description, dateTime);
        taskAdded.setUrgency(urgency);
    }

    // MODIFIES: this
    // EFFECTS: given map that contains description, dateTime, and urgency, constructs a chore object
    public static void createHomework(Map<String, Object> map) throws IOException {
        String description = (String) map.get("description");
        String className = (String) map.get("className");
        DateTime dateTime = (DateTime) map.get("dateTime");
        Urgency urgency = (Urgency) map.get("urgency");

        Task taskAdded = taskManager.create(description, dateTime);
        taskAdded.setUrgency(urgency);
    }

    ObservableList<Task> getAllTasks() {
        return FXCollections.observableList(taskManager.getAllTasks());

    }

    String getDate() {
        return dateTime.getCurrentDate();
    }

    String getTime() {
        return dateTime.getCurrentTime();
    }

    String getCity() {
        return weather.getWeatherLocation();
    }

    String getTemp() {
        return String.valueOf(weather.getCurrentTemp());
    }

    String getWeatherConditions() {
        return weather.getWeatherDescription();
    }

}
