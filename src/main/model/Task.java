package model;

import java.util.ArrayList;

public class Task {

    private String taskDescription;
    private boolean completed;

    // Constructs the task
    // EFFECTS: Constructs the task and sets its completion to false
    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        this.completed = false;
    }

    // EFFECTS: Returns the task description
    public String getTaskDescription() {
        return this.taskDescription;
    }

    //EFFECTS: Returns the completion status of the task
    public boolean isCompleted() {
        return completed;
    }

    // MODIFIES: this
    // EFFECTS: Sets the task completion status to the status given
    public void setCompleted(boolean status) {
        completed = status;
    }
}
