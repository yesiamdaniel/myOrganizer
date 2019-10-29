package model.handler;


import model.handler.urgencyhandlers.Important;
import model.handler.urgencyhandlers.Normal;
import model.handler.urgencyhandlers.Urgent;
import model.task.Chore;
import model.task.Homework;
import model.task.Task;
import model.TooManyIncompleteException;

import java.io.*;
import java.util.Scanner;

public class TaskManager extends DataHandler {
    static Urgent urgent;
    static Important important;
    static Normal normal;

    public TaskManager() {
        urgent = new Urgent();
        important = new Important();
        normal = new Normal();
    }

    private Scanner scanner = new Scanner(System.in);

    public void init() throws IOException {
        super.load(super.choreFilename);
        super.load(super.homeworkFilename);
    }

    public static Important getImportant() {
        return important;
    }

    public static Normal getNormal() {
        return normal;
    }

    public static Urgent getUrgent() {
        return urgent;
    }

    // Creates and adds a task
    // MODIFIES: this
    // EFFECTS: creates a new chore and adds it  list of all current chores and returns it
    public Task create(String taskDescription) throws IOException {
        Task newChore = new Chore(taskDescription);
        super.addNewChore(newChore);
        save(super.getAllChores());
        return newChore;
    }

    // MODIFIES: this
    // EFFECTS: create new homework and add it to list of all homework
    public Task create(String className, String description, String dueDate) throws IOException {
        Task newHomework = new Homework(className, description, dueDate);
        super.addNewHomework(newHomework);
        save(super.getAllHomework());
        return newHomework;
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
        taskToDelete.remove();
        super.reloadData();
        System.out.println("Successfully removed " + taskToDelete.getDescription() + " from the list");
    }










}

