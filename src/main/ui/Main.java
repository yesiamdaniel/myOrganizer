package ui;

import model.Task;

import java.util.Scanner;

public class Main {

    private static boolean initialized = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[]args) {
        initialize();
        confirmInitialization();
        handleInput();
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

    private static void handleInput() {
        while (true) {
            displayOptions();
            String optionSelected = scanner.nextLine();
            if (optionSelected.equals("1")) {
                create();
            } else if (optionSelected.equals("2")) {
                delete();
            } else if (optionSelected.equals("3")) {
                viewAllTasks();
            } else if (optionSelected.equals("q")) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    private static void displayOptions() {
        System.out.println("Welcome \n "
                + "To Create new task, press [1]\n "
                + "To Delete a task press [2]\n "
                + "To View all tasks press [3]\n "
                + "To QUIT press [q]");
    }

    private static void create() {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        System.out.println("Adding: " + taskToAdd);
        new Task(taskToAdd);
        System.out.println("Successfully added the task: " + taskToAdd + "to list");
    }

    private static void delete() {
        while (true) {
            int taskNumber = 1;
            System.out.println("Which task would you like to delete?");
            for (Task t : Task.allTasks) {
                System.out.println("[" + taskNumber + "]" + " - " + t.taskDescription);
                taskNumber++;
            }
            int taskIndexToDelete = Integer.parseInt(scanner.nextLine()) - 1;
            if (0 < taskIndexToDelete && taskIndexToDelete < Task.allTasks.size() + 1) {
                Task taskToDelete = Task.allTasks.get(taskIndexToDelete);
                if (confirmDelete(taskToDelete)) {
                    taskToDelete.deleteTask();
                    break;
                }
            } else {
                System.out.println("Task number entered is out of range!");
            }
        }
    }

    private static void viewAllTasks() {
        int taskNumber = 1;
        for (Task t: Task.allTasks) {
            System.out.println("Task number - " + taskNumber + " : " + t.taskDescription);
            taskNumber++;
        }
    }

    private static boolean confirmDelete(Task taskToDelete) {
        System.out.println("Are you sure you would like to delete the task: "
                + taskToDelete.taskDescription + "? [y/n]");
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
}
