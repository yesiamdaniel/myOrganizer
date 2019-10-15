package model;

import java.util.ArrayList;

public abstract class Task implements Serializable {

    private String description;
    private boolean completed = false;
    private String identifier;
    private String type;

    // MODIFIES: this
    // EFFECTS: sets the identifier to a random 8 character alphanumeric string
    @Override
    public void createIdentifier() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = (int)(allChars.length() * Math.random());
            sb.append(allChars.charAt(index));
        }
        identifier = sb.toString();
    }

    // REQUIRES: identifier to set must be 8 characters long
    // MODIFIES: this
    // EFFECTS: sets the identifier
    void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    // EFFECTS: returns the unique identifier for this task
    String getIdentifier() {
        return identifier;
    }

    // EFFECTS: Returns the task description
    public String getDescription() {
        return this.description;
    }

    // MODIFIES: this
    // EFFECTS: sets the task description
    public void setDescription(String description) {
        this.description = description;
    }

    // REQUIRES: task type must be one of: chore or homework
    // MODIFIES: this
    // EFFECTS: sets the task type
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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

    // EFFECTS: prints out a description of the task to the console
    public abstract String getTaskDetails();

    // EFFECTS: returns a list of strings with all the fields that define the task
    public abstract ArrayList<String> getAllFields();
}
