package model;

import java.util.ArrayList;

public class Chore extends Task {

    // Constructs brand new chore
    public Chore(String taskDescription) {
        super.setDescription(taskDescription);
        createIdentifier();
        super.setType("chore");
    }

    // Constructs chore from file
    public Chore(String taskDescription, String completed, String uid) {
        super.setDescription(taskDescription);

        if (completed.equals("true")) {
            super.setCompleted(true);
        }

        super.setIdentifier(uid);
        super.setType("chore");
    }

    @Override
    public String getTaskDetails() {
        return getDescription() + " || Task completed?: " + isCompleted();
    }

    @Override
    public ArrayList<String> getAllFields() {
        return null;
    }
}
