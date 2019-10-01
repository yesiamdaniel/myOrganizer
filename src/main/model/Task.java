package model;

public class Task {

    private String taskDescription;
    private boolean completed;
    private String identifier;


    // Constructs the task
    // EFFECTS: Constructs the task and sets its completion to false
    public Task(String taskDescription, String identifier) {
        this.taskDescription = taskDescription;
        this.completed = false;
        this.identifier = identifier;
    }

    // EFFECTS: Returns the task description
    public String getTaskDescription() {
        return this.taskDescription;
    }

    //EFFECTS: Returns the completion status of the task
    public boolean isCompleted() {
        return completed;
    }

    // EFFECTS: returns the unique identifier for this task
    public String getIdentifier() {
        return identifier;
    }

    // MODIFIES: this
    // EFFECTS: Sets the task completion status to the status given
    public void setCompleted(boolean status) {
        completed = status;
    }


}
