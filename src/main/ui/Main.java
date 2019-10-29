package ui;

import model.handler.urgencyhandlers.Important;
import model.handler.urgencyhandlers.Normal;
import model.handler.urgencyhandlers.Urgent;
import model.task.Task;
import model.handler.TaskManager;
import model.TooManyIncompleteException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static TaskManager taskManager;
    private static Scanner scanner = new Scanner(System.in);
    private static Urgent urgent;
    private static Important important;
    private static Normal normal;

    public static void main(String[]args) throws IOException {
        initialize();
        handleInput();
    }

    private static void initialize() throws IOException {
        System.out.println("Initializing...\n");

        taskManager = new TaskManager();
        taskManager.init();

        urgent = TaskManager.getUrgent();
        important = TaskManager.getImportant();
        normal = TaskManager.getNormal();
    }

    // EFFECTS: main handle for all major user interaction
    private static void handleInput() throws IOException {
        while (true) {
            System.out.println("All current Tasks:");
            viewAllTasks();
            displayOptions();
            String optionSelected = scanner.nextLine();
            if (interpretSelection(optionSelected)) {
                break;
            }
        }
    }

    // EFFECTS: interprets the selection the user made from handleInput. Returns true if user would like to quit,
    //          and returns false if user has entered an invalid input.
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
        } else {
            System.out.println("Invalid input");
        }
        return false;
    }

    // Displays all the home screen options
    // EFFECTS: Prints out a series of possible operations that a user can perform
    private static void displayOptions() {
        System.out.println("\nWelcome \n "
                + "To Create new task, press [1]\n "
                + "To Delete a task press [2]\n "
                + "To View all tasks press [3]\n "
                + "To reset the organizer press [4]\n "
                + "To QUIT press [q]");
    }

    // EFFECTS: prints all tasks
    private static void viewAllTasks() {
        int taskNumber = 1;
        StringBuilder styleLinesSB = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            styleLinesSB.append("==");
        }
        String styleLines = styleLinesSB.toString();
        for (Task t : taskManager.getAllTasks()) {
            System.out.println(styleLines + "\n"
                    + "Task number - " + taskNumber + " :\n"
                    + "  " + t.getTaskDetails() + "\n"
                    + styleLines);
            taskNumber++;
        }
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
        System.out.println("Enter due date: ");
        String dueDate = scanner.nextLine();
        System.out.println("Adding: " + description + " for class " + className);
        taskManager.create(className, description, dueDate);
        System.out.println("Successfully added the task: " + description + ", to list");
    }

    // EFFECTS: gathers user input to relay to chore constructor
    private static void createChorePrompt() throws IOException {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        Task taskAdded = taskManager.create(taskToAdd);

        switchUrgency(taskAdded);

        System.out.println("Adding: " + taskToAdd);
        System.out.println("Successfully added the task: " + taskToAdd + " to list");
    }

    // MODIFIES: taskAdded
    // EFFECTS: assigns urgency to task
    private static void switchUrgency(Task taskAdded) {
        urgencyPrompt();
        switch (scanner.nextLine()) {
            case "1":
                taskAdded.setUrgency(normal);
                break;
            case "2":
                taskAdded.setUrgency(important);
                break;
            case "3":
                taskAdded.setUrgency(urgent);
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
                + "[3] - Urgent\n");
    }

    // Prompts user to select a task to delete
    // EFFECTS: determines the task to delete from user input
    private static void deletePrompt() throws IOException {
        while (true) {
            System.out.println("Which task would you like to delete?");
            viewAllTasks();
            int taskIndexToDelete = Integer.parseInt(scanner.nextLine()) - 1;

            if (0 <= taskIndexToDelete && taskIndexToDelete < taskManager.getAllTasks().size()) {
                Task taskToDelete = taskManager.getAllTasks().get(taskIndexToDelete);
                if (confirmDelete(taskToDelete)) {
                    taskManager.delete(taskToDelete);
                    break;
                }
            } else {
                System.out.println("Task number entered is out of range!");
            }
        }
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
                retrieveDetails(task);
                markTaskCompletedPrompt(task);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid task number");
                handleAllTasks();
            }
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

    // EFFECTS: Prints the details of the task, then prompts the user if they want to mark it as completed
    private static void retrieveDetails(Task task) {
        String taskDetails = task.getTaskDetails();
    }
}

