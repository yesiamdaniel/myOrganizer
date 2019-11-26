package model.handler;


import model.DateTime;
import model.handler.urgencyhandlers.Important;
import model.handler.urgencyhandlers.Normal;
import model.handler.urgencyhandlers.Urgent;
import model.observer.Observer;
import model.task.Chore;
import model.task.Homework;
import model.task.Task;
import model.TooManyIncompleteException;

import java.io.*;

public class TaskManager extends DataHandler implements Observer {
    // TODO: make tm iterable
    static Urgent urgent;
    static Important important;
    static Normal normal;

    public TaskManager() {
        urgent = new Urgent();
        important = new Important();
        normal = new Normal();
    }

    // MODIFIES: this
    // EFFECTS: loads all chores from data file
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
    // EFFECTS: creates a new chore and adds it  list of all current chores, saves and returns it
    public Task create(String taskDescription, DateTime dateTime) throws IOException {
        Task newChore = new Chore(taskDescription, dateTime);
        super.addTask(newChore);
        save(getAllChores());
        newChore.addObserver(this);
        return newChore;
    }

    // MODIFIES: this
    // EFFECTS: create new homework and add it to list of all homework then saves and returns
    public Task create(String className, String description, DateTime dateTime) throws IOException {
        Task newHomework = new Homework(className, description, dateTime);
        super.addTask(newHomework);
        save(getAllHomework());
        newHomework.addObserver(this);
        return newHomework;
    }

    // EFFECTS: throws TooManyIncompleteException if there are more than 10 incomplete tasks
    public void checkTooMany() throws TooManyIncompleteException {
        int counter = 0;
        for (Task t :getAllTasks()) {
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
        taskToDelete.removeUrgency();
        reload();
        System.out.println("Successfully removed " + taskToDelete.getDescription() + " from the list");
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of tasks in the data file then reloads the taskmanager data
    public void reload() throws IOException {
        save(getAllTasks());
        allHomework.clear();
        allChores.clear();

        load(choreFilename);
        load(homeworkFilename);
    }


    @Override
    // EFFECTS: called from subject to re-save all tasks
    public boolean update() throws IOException {
        save(getAllTasks());
        return true;
    }
}

