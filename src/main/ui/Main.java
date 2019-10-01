package ui;

import model.Task;
import model.TaskManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

    private static boolean initialized = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[]args) throws IOException {
        initialize();
        confirmInitialization();
        TaskManager taskManager = new TaskManager();
        taskManager.handleInput();
    }

    private static void initialize() {
        System.out.println("Initializing...");
        initialized = true;
    }

    private static void confirmInitialization() {
        if (initialized) {
            System.out.println("Successfully initialized!");
        }
    }
}
