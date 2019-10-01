package model;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager implements Serializable, Saveable, Loadable {

    private ArrayList<Task> allTasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "./data/data.txt";

    // EFFECTS: loads data from data.txt then constructs a list of all current tasks
    public TaskManager() throws IOException {
        load(fileName);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: loads data from data.txt
    public void load(String fileName) throws IOException {
        allTasks.clear();
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line: lines) {
            ArrayList<String> dataFragments = handleData(line);
            String taskDescription = dataFragments.get(0);
            String uid = dataFragments.get(1);
            Task t = new Task(taskDescription, uid);
            allTasks.add(t);
        }
    }

    @Override
    // EFFECTS: reformat data values stored in data.txt so it can be used
    public ArrayList<String> handleData(String line) {
        String[] fragment = line.split(" - ");
        return new ArrayList<>(Arrays.asList(fragment));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: saves part data into data.txt
    public void save(ArrayList<Task> allTasks, String fileName) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.flush();
        for (Task t: allTasks) {
            String taskDescription = t.getTaskDescription();
            String uid = t.getIdentifier();
            file.write(taskDescription + " - " + uid + "\n");
        }
        file.close();
    }

    @Override
    // EFFECTS: returns a unique 8-character long alphanumeric
    public String setIdentifier() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = (int)(allChars.length() * Math.random());

            sb.append(allChars.charAt(index));
        }
        return sb.toString();
    }

    // Returns list of all current tasks
    // EFFECT: returns list of all current tasks
    List<Task> getAllTasks() {
        return allTasks;
    }

    // Handles user input from home screen
    // EFFECTS: Based on user input determines which action user wishes to perform
    @SuppressWarnings("IfCanBeSwitch")
    public void handleInput() throws IOException {
        while (true) {
            viewAllTasks();
            displayOptions();
            String optionSelected = scanner.nextLine();
            if (optionSelected.equals("1")) {
                createPrompt();
            } else if (optionSelected.equals("2")) {
                deletePrompt();
            } else if (optionSelected.equals("3")) {
                handleAllTasks();
            } else if (optionSelected.equals("q")) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of tasks in data.txt then reloads the data from the file
    private void reloadData() throws IOException {
        save(allTasks, fileName);
        load(fileName);
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
    private void createPrompt() throws IOException {
        System.out.println("Enter task description: ");
        String taskToAdd = scanner.nextLine();
        System.out.println("Adding: " + taskToAdd);
        create(taskToAdd);
    }

    // Creates and adds a task
    // MODIFIES: this
    // EFFECTS: creates a new task and adds it to the to-do list of all current tasks
    void create(String taskDescription) throws IOException {
        String identifier = setIdentifier();
        Task newTask = new Task(taskDescription, identifier);
        allTasks.add(newTask);
        save(allTasks, fileName);
        System.out.println("Successfully added the task: " + taskDescription + " to list");
    }

    // Prompts user to select a task to delete
    // EFFECTS: determines the task to delete from user input
    private void deletePrompt() throws IOException {
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
    void delete(Task taskToDelete) throws IOException {
        allTasks.remove(taskToDelete);
        reloadData();
        System.out.println("Successfully removed " + taskToDelete.getTaskDescription() + " from the list");
    }

    // Prints out all the current tasks to the user
    // EFFECTS: prints out all current tasks to the user
    private void handleAllTasks() throws IOException {
        viewAllTasks();
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

    private void viewAllTasks() {
        int taskNumber = 1;
        System.out.println("All current Tasks:");
        for (Task t : allTasks) {
            System.out.println("Task number - " + taskNumber + " : " + t.getTaskDescription());
            taskNumber++;
        }
    }

    // EFFECTS: Prints the details of the task, then prompts the user if they want to mark it as completed
    private void retrieveDetails(Task task) {
        System.out.println("Task description: " + task.getTaskDescription() + "\n"
                + "Task completed? " + task.isCompleted());
        System.out.println("Do you wish to make this task as completed? (y/n)");
    }

    // Marks the task as completed
    // MODIFIES: task
    // EFFECTS: sets the task to be completed
    void markAsCompleted(Task task) {
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

