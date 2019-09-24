package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private ArrayList<Task> allTasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Constructs a task manager
    // EFFECTS: Prompts the user with the home screen
    public TaskManager() {
    }

    // Returns list of all current tasks
    // EFFECT: returns list of all current tasks
    public List<Task> getAllTasks() {
        return allTasks;
    }

    // Handles user input from home screen
    // EFFECTS: Based on user input determines which action user wishes to perform
    public void handleInput() {
        while (true) {
            displayOptions();
            String optionSelected = scanner.nextLine();
            if (optionSelected.equals("1")) {
                createPrompt();
            } else if (optionSelected.equals("2")) {
                deletePrompt();
            } else if (optionSelected.equals("3")) {
                viewAllTasks();
            } else if (optionSelected.equals("q")) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // Displays all the home screen options
    // EFFECTS: Prints out a series of possible operations that a user can perform
    private void displayOptions() {
        System.out.println("Welcome \n "
                + "To Create new task, press [1]\n "
                + "To Delete a task press [2]\n "
                + "To View all tasks press [3]\n "
                + "To QUIT press [q]");
    }

    // Asks user what task they would like to create
    private void createPrompt() {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        System.out.println("Adding: " + taskToAdd);
        create(taskToAdd);
    }

    // Creates and adds a task
    // MODIFIES: this
    // EFFECTS: creates a new task and adds it to the to-do list of all current tasks
    public void create(String taskToAdd) {
        Task newTask = new Task(taskToAdd);
        allTasks.add(newTask);
        System.out.println("Successfully added the task: " + taskToAdd + " to list");
    }

    // Prompts user to select a task to delete
    // EFFECTS: determines the task to delete from user input
    private void deletePrompt() {
        while (true) {
            int taskNumber = 1;
            System.out.println("Which task would you like to delete?");
            for (Task t : allTasks) {
                System.out.println("[" + taskNumber + "]" + " - " + t.getTaskDescription());
                taskNumber++;
            }
            int taskIndexToDelete = Integer.parseInt(scanner.nextLine()) - 1;
            if (0 <= taskIndexToDelete && taskIndexToDelete < allTasks.size()) {
                Task taskToDelete = allTasks.get(taskIndexToDelete);
                if (confirmDelete(taskToDelete)) {
                    delete(taskToDelete);
                    break;
                }
            } else {
                System.out.println("Task number entered is out of range!");

            }
        }
    }

    // Removes a task from the to-do list
    // MODIFIES: this
    // EFFECTS: removes the selected task from list
    public void delete(Task taskToDelete) {
        allTasks.remove(taskToDelete);
        System.out.println("Successfully removed " + taskToDelete.getTaskDescription() + " from the list");
    }

    // Prints out all the current tasks to the user
    // EFFECTS: prints out all current tasks to the user
    private void viewAllTasks() {
        int taskNumber = 1;
        for (Task t : allTasks) {
            System.out.println("Task number - " + taskNumber + " : " + t.getTaskDescription());
            taskNumber++;
        }
        System.out.println("Enter task number to get more details or 'q' to go back to main menu");
        String result = scanner.nextLine();
        if (result.equals("q")) {
            System.out.println("Returning to main menu");
        } else {
            Integer index = new Integer(result);
            Task task = allTasks.get(index - 1);
            retrieveDetails(task);
            String completedTaskPrompt = scanner.nextLine();
            if (completedTaskPrompt.equals("y")) {
                markAsCompleted(task);
            }
        }
    }

    // EFFECTS: Prints the details of the task, then prompts the user if they want to mark it as completed
    private void retrieveDetails(Task task) {
        System.out.println("Task description: " + task.getTaskDescription() + "\n"
                + "Task completed?" + task.isCompleted());
        System.out.println("Do you wish to make this task as completed? (y/n)");
    }

    // Marks the task as completed
    // MODIFIES: task
    // EFFECTS: sets the task to be completed
    public void markAsCompleted(Task task) {
        task.setCompleted(true);
        System.out.println("Successfully marked as completed");
    }

    // Affirms deletion with user
    // EFFECTS: returns true if user wishes to delete the selected item, otherwise false
    private boolean confirmDelete(Task taskToDelete) {
        System.out.println("Are you sure you would like to delete the task: "
                + taskToDelete.getTaskDescription() + "? [y/n]");
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

