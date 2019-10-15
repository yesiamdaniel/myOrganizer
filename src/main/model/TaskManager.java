package model;


import java.io.*;
import java.util.Scanner;

public class TaskManager extends DataHandler {


    private Scanner scanner = new Scanner(System.in);
    private String choreFileName = "./data/choreDaa.txt";

    // EFFECTS: loads data then constructs separate lists for all current tasks of each category
    public TaskManager() throws IOException {
        super.instantiateChores();
        super.instantiateHomeworks();
    }

    // Creates and adds a task
    // MODIFIES: this
    // EFFECTS: creates a new task and adds it to the to-do list of all current tasks
    public void create(String taskDescription) throws IOException {
        Task newChore = new Chore(taskDescription);
        super.addNewChore(newChore);
        save(super.getAllChores());
    }

    public void create(String className, String description, String dueDate) throws IOException {
        Task newHomework = new Homework(className, description, dueDate);
        super.addNewHomework(newHomework);
        save(super.getAllHomework());
    }

    // EFFECTS: throws TooManyIncompleteException if there are more than 10 incomplete tasks
    public void checkTooMany() throws TooManyIncompleteException {
        int counter = 0;
        for (Task t :super.getAllTasks()) {
            if (!t.isCompleted()) {
                counter++;
            }
        }
        if (counter > 10) {
            throw new TooManyIncompleteException();
        }
    }

    // Removes a task from the to-do list
    // MODIFIES: this
    // EFFECTS: removes the selected task from list
    public void delete(Task taskToDelete) throws IOException {
        String taskType = taskToDelete.getType();
        if (taskType.equals("chore")) {
            super.removeChore(taskToDelete);
        } else if (taskType.equals("homework")) {
            super.removeHomework(taskToDelete);
        }
        super.reloadData();
        System.out.println("Successfully removed " + taskToDelete.getDescription() + " from the list");
    }










}

