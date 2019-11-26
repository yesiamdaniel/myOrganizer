package ui;

import model.DateTime;
import network.Weather;
import model.task.Task;
import model.handler.TaskManager;
import model.TooManyIncompleteException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static TaskManager taskManager;
    private static Scanner scanner = new Scanner(System.in);
    private static DateTime dateTime;
    private static Weather weather;
    private static String city = "vancouver";

    public static void main(String[]args) throws IOException {
        initialize();
        handleInput();
    }

    private static void initialize() throws IOException {
        System.out.println("Initializing...\n");

        taskManager = new TaskManager();
        taskManager.init();
        dateTime = new DateTime();
        weather = new Weather(city);

    }

    // EFFECTS: main loop for all major user interaction
    private static void handleInput() throws IOException {
        while (true) {
            System.out.println("All current Tasks:");
            viewAllTasks();

            greeting();
            displayOptions();

            String optionSelected = scanner.nextLine();
            if (interpretSelection(optionSelected)) {
                break;
            }
        }
    }

    // EFFECTS: prints welcome message and displays temperature
    private static void greeting() {
        System.out.println("Welcome back!\n"
                + "It is " + dateTime.getCurrentDate() + "\n"
                + "The current time is " + dateTime.getCurrentTime() + "\n"
                + weather.createWeatherReport() + "\n");
    }

    // EFFECTS: interprets the selection the user made from handleInput. Returns true if user would like to quit,
    //          and returns false otherwise
    private static boolean interpretSelection(String optionSelected) throws IOException {
        if (optionSelected.equals("1")) {
            createPrompt();
        } else if (optionSelected.equals("2")) {
            deletePrompt();
        } else if (optionSelected.equals("3")) {
            handleAllTasks();
        } else if (optionSelected.equals("4")) {
            taskManager.reset();
        } else if (optionSelected.equals("q")) {
            return true;
        } else if (optionSelected.equals("c")) {
            changeCityPrompt();
        } else {
            System.out.println("Invalid input");
        }
        return false;
    }

    private static void changeCityPrompt() {
        System.out.println("Enter city name or press q to go back: ");
        String newCity = scanner.nextLine();
        if (weather.isCityValid(newCity)) {
            weather.getWeather(newCity);
            weather.createWeatherReport();
        } else if (newCity.equals("q")) {
            return;
        } else {
            System.out.println("Invalid input, Try again.");
            changeCityPrompt();
        }
    }

    // Displays all the home screen options
    // EFFECTS: Prints out a series of possible operations that a user can perform
    private static void displayOptions() {
        System.out.println("To Create new task, press [1]\n "
                + "To Delete a task press [2]\n "
                + "To View all tasks press [3]\n "
                + "To reset the organizer press [4]\n "
                + "To QUIT press [q]\n "
                + "To Change city press [c]");
    }

    // EFFECTS: prints all tasks
    private static void viewAllTasks() {
        int taskNumber = 1;
        String lineSeparator = lineSeparators();

        // TODO: iterator here
        for (Task t : taskManager.getAllTasks()) {
            if (taskNumber == 1) {
                System.out.println(lineSeparator);
            }
            System.out.println("Task number - " + taskNumber + " :\n"
                    + "  " + t.getTaskDetails() + "\n"
                    + lineSeparator);
            taskNumber++;
        }
        System.out.println("\n");
    }

    private static String lineSeparators() {
        StringBuilder styleLinesSB = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            styleLinesSB.append("==");
        }
        return styleLinesSB.toString();
    }

    // Asks user what task they would like to create
    private static void createPrompt() throws IOException {
        try {
            taskManager.checkTooMany();
        } catch (TooManyIncompleteException e) {
            System.out.println("Cannot create: There are too many incomplete tasks!");
            return;
        }
        printTaskTypes();
        String taskType = scanner.nextLine();

        if ("1".equals(taskType)) {
            createChorePrompt();
        } else if ("2".equals(taskType)) {
            createHomeworkPrompt();
        } else if ("q".equals(taskType)) {
            return;
        } else {
            System.out.println("Invalid input, try again.");
            createPrompt();
        }
    }

    private static void printTaskTypes() {
        System.out.println("What kind of task would you like to create?\n"
                + "[1] - Chore\n"
                + "[2] - Homework\n"
                + "[q] go back\n");
    }

    // EFFECTS: gathers user input to relay to homework constructor
    private static void createHomeworkPrompt() throws IOException {
        System.out.println("Enter class name: ");
        String className = scanner.nextLine();

        System.out.println("Enter homework description: ");
        String description = scanner.nextLine();

        DateTime dateTime = dueDatePrompt();


        Task taskAdded = taskManager.create(className, description, dateTime);
        switchUrgency(taskAdded);

        System.out.println("Adding: " + description + " for class " + className + "\n");

        System.out.println("Successfully added the task: " + description + ", to list");
    }

    // EFFECTS: gathers user input to relay to chore constructor
    private static void createChorePrompt() throws IOException {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        DateTime dateTime = dueDatePrompt();
        Task taskAdded = taskManager.create(taskToAdd, dateTime);

        switchUrgency(taskAdded);

        System.out.println("Adding: " + taskToAdd);
        System.out.println("Successfully added the task: " + taskToAdd + " to list\n");
    }

    // EFFECTS: Asks user if they would like to set a due date for their task
    private static DateTime dueDatePrompt() {
        System.out.println("Does this task have a due date? (y/n)");

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("y")) {
                DateTime dateTime = new DateTime();
                getDueDate(dateTime);
                return dateTime;
            } else if (userInput.equals("n")) {
                return new DateTime();
            } else {
                System.out.println("Inablid input, Try again");
            }
        }
    }

    // REQUIRES: user must enter due date in from: yyyy-MM-dd
    // MODIFIES: dateTime
    // EFFECTS: Gets due date from user
    private static void getDueDate(DateTime dateTime) {
        System.out.println("Enter due date (yyyy-MM-dd): ");

        String dueDate = scanner.nextLine();
        try {
            dateTime.setDate(dueDate);
            getDueTime(dateTime);
        } catch (Exception e) {
            System.out.println("Invalid input. Try again");
            getDueDate(dateTime);
        }
    }

    // REQUIRES: user must enter time in form: h:mm a
    // MODIFIES: dateTime
    // EFFECTS: Asks user if dueDate has a time. If yes gets due time from user otherwise returns
    private static void getDueTime(DateTime dateTime) {
        System.out.println("Does this task have a due time? (y/n)");
        if (scanner.nextLine().equals("y")) {
            System.out.println("Enter due time (h:mm AM/PM): ");

            String duetTime = scanner.nextLine();
            try {
                dateTime.setTime(duetTime);
            } catch (Exception e) {
                System.out.println("Invalid input. Try again");
                getDueTime(dateTime);
            }
        }
    }

    // MODIFIES: taskAdded
    // EFFECTS: assigns urgency to task
    private static void switchUrgency(Task taskAdded) throws IOException {
        urgencyPrompt();
        switch (scanner.nextLine()) {
            case "1":
                taskAdded.setUrgency(TaskManager.getNormal());
                break;
            case "2":
                taskAdded.setUrgency(TaskManager.getImportant());
                break;
            case "3":
                taskAdded.setUrgency(TaskManager.getUrgent());
                break;
            default:
                System.out.println("Inavlid input");
                switchUrgency(taskAdded);
        }
    }

    // EFFECTS: displays options for selecting urgency level
    private static void urgencyPrompt() {
        System.out.println("Select urgency level of task.\n"
                + "[1] - Normal\n"
                + "[2] - Important\n"
                + "[3] - Urgent");
    }

    // Prompts user to select a task to delete
    // EFFECTS: determines the task to delete from user input
    private static void deletePrompt() throws IOException {
        while (true) {
            System.out.println("Which task would you like to delete?");
            viewAllTasks();
            String userInput = scanner.nextLine();
            if (userInput.equals("q")) {
                break;
            }
            Task taskToDelete = validTaskToDeleteInput(userInput);
            if (confirmDelete(taskToDelete)) {
                taskManager.delete(taskToDelete);
                break;
            }
        }
    }

    private static Task validTaskToDeleteInput(String userInput) throws IOException {
        Task taskToReturn = null;
        try {
            int taskIndexToDelete = Integer.parseInt(userInput) - 1;
            taskToReturn = taskManager.getAllTasks().get(taskIndexToDelete);
        } catch (NumberFormatException e) {
            System.out.println("Input is not a number! Try again.");
            deletePrompt();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Task number entered is out of range! Try again.");
            deletePrompt();
        }
        return taskToReturn;
    }

    // Affirms deletion with user
    // EFFECTS: returns true if user wishes to delete the selected item, otherwise false
    private static boolean confirmDelete(Task taskToDelete) {
        System.out.println("Are you sure you would like to delete the task: "
                + taskToDelete.getDescription() + "? [y/n]");
        String result = scanner.nextLine();
        while (true) {
            if (result.equals("y")) {
                return true;
            } else if (result.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // Prints out all the current tasks to the user
    // EFFECTS: prints out all current tasks to the user and handles marking tasks as completed
    private static void handleAllTasks() {
        viewAllTasks();
        System.out.println("Enter task number to get more details or 'q' to go back to main menu");
        String result = scanner.nextLine();

        if (result.equals("q")) {
            System.out.println("Returning to main menu");
        } else {
            Integer index = new Integer(result);
            try {
                Task task = taskManager.getAllTasks().get(index - 1);
                System.out.println(task.getTaskDetails());
                markTaskCompletedPrompt(task);
                changeUrgencyPrompt(task);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid task number");
                handleAllTasks();
            }
        }
    }

    // EFFECTS: Prompt to change urgency
    private static void changeUrgencyPrompt(Task task) {
        System.out.println("Do you wish to change the urgency of this task? (y/n)");
        String userPrompt = scanner.nextLine();
        if (userPrompt.equals("y")) {
            try {
                switchUrgency(task);
                System.out.println("Successfully changed urgency to " + task.getUrgencyString() + "\n");
            } catch (Exception e) {
                System.out.println("Switch urgency ERROR");
                System.exit(-1);
            }
        } else if (userPrompt.equals("n")) {
            return;
        } else {
            System.out.println("Invalid input, Try again.");
            changeUrgencyPrompt(task);
        }
    }

    private static void markTaskCompletedPrompt(Task task) {
        System.out.println("Do you wish to make this task as completed? (y/n)");
        String completedTaskPrompt = scanner.nextLine();
        if (completedTaskPrompt.equals("y")) {
            task.setCompleted(true);
            System.out.println("Successfully marked as completed");
        } else {
            task.setCompleted(false);
        }
    }
}

