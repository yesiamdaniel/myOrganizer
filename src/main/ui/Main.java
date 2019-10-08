package ui;

import model.Task;
import model.TaskManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[]args) throws IOException {
        TaskManager taskManager = new TaskManager();
        handleInput(taskManager);
    }

    // EFFECTS: main handle for all major user interaction
    private static void handleInput(TaskManager tm) throws IOException {
        while (true) {
            System.out.println("All current Tasks:");
            viewAllTasks(tm);
            displayOptions();
            String optionSelected = scanner.nextLine();
            if (interpretSelection(tm, optionSelected)) {
                break;
            }
        }
    }

    // EFFECTS: inteprets the selection the user made from handleInput. Returns true if user would like to quit,
    //          and returns false if user has entered an invalid input.
    private static boolean interpretSelection(TaskManager tm, String optionSelected) throws IOException {
        if (optionSelected.equals("1")) {
            createPrompt(tm);
        } else if (optionSelected.equals("2")) {
            deletePrompt(tm);
        } else if (optionSelected.equals("3")) {
            handleAllTasks(tm);
        } else if (optionSelected.equals("4")) {
            tm.reset();
        } else if (optionSelected.equals("q")) {
            return true;
        } else {
            System.out.println("Invalid input");
        }
        return false;
    }

    // Displays all the home screen options
    // EFFECTS: Prints out a series of possible operations that a user can perform
    public static void displayOptions() {
        System.out.println("Welcome \n "
                + "To Create new task, press [1]\n "
                + "To Delete a task press [2]\n "
                + "To View all tasks press [3]\n "
                + "To reset the organizer press [4]\n "
                + "To QUIT press [q]");
    }

    // EFFECTS: prints all tasks
    private static void viewAllTasks(TaskManager tm) {
        int taskNumber = 1;
        for (Task t : tm.getAllTasks()) {
            System.out.println("Task number - " + taskNumber + " : " + t.getTaskDetails());
            taskNumber++;
        }
    }

    // Asks user what task they would like to create
    private static void createPrompt(TaskManager tm) throws IOException {
        System.out.println("What kind of task would you like to create?\n"
                + "[1] - Chore\n"
                + "[2] - Homework\n");
        String taskType = scanner.nextLine();

        if (taskType.equals("1")) {
            createChorePrompt(tm);
        } else if (taskType.equals("2")) {
            createHomeworkPrompt(tm);
        }
    }

    // EFFECTS: gathers user input to relay to homework constructor
    private static void createHomeworkPrompt(TaskManager tm) throws IOException {
        System.out.println("Enter class name: ");
        String className = scanner.nextLine();
        System.out.println("Enter homework description: ");
        String description = scanner.nextLine();
        System.out.println("Enter due date: ");
        String dueDate = scanner.nextLine();
        System.out.println("Adding: " + description + " for class " + className);
        tm.create(className, description, dueDate);
        System.out.println("Successfully added the task: " + description + ", to list");
    }

    // EFFECTS: gathers user input to relay to chore constructor
    private static void createChorePrompt(TaskManager tm) throws IOException {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        System.out.println("Adding: " + taskToAdd);
        tm.create(taskToAdd);
        System.out.println("Successfully added the task: " + taskToAdd + " to list");
    }

    // Prompts user to select a task to delete
    // EFFECTS: determines the task to delete from user input
    private static void deletePrompt(TaskManager tm) throws IOException {
        while (true) {
            System.out.println("Which task would you like to delete?");
            int taskIndexToDelete = Integer.parseInt(scanner.nextLine()) - 1;

            if (0 <= taskIndexToDelete && taskIndexToDelete < tm.getAllTasks().size()) {
                Task taskToDelete = tm.getAllTasks().get(taskIndexToDelete);
                if (confirmDelete(taskToDelete)) {
                    tm.delete(taskToDelete);
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
    private static void handleAllTasks(TaskManager tm) {
        viewAllTasks(tm);
        System.out.println("Enter task number to get more details or 'q' to go back to main menu");
        String result = scanner.nextLine();

        if (result.equals("q")) {
            System.out.println("Returning to main menu");
        } else {
            Integer index = new Integer(result);
            Task task = tm.getAllTasks().get(index - 1);
            retrieveDetails(task);

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

    // EFFECTS: Prints the details of the task, then prompts the user if they want to mark it as completed
    private static void retrieveDetails(Task task) {
        String taskDetails = task.getTaskDetails();
    }
}

