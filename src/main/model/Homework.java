package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homework extends Task {

    private String className;
    private String dueDate;


    // Constructs a brand new homework object from no previous data
    public Homework(String className, String description, String dueDate) {
        this.className = className;
        this.dueDate = dueDate;
        super.setDescription(description);
        super.createIdentifier();
        super.setType("homework");
    }

    // Constructs a brand new homework object from no previous data
    public Homework(String className, String description, String dueDate, String completed, String uid) {
        this.className = className;
        this.dueDate = dueDate;

        if (completed.equals("true")) {
            super.setCompleted(true);
        }
        super.setDescription(description);
        super.setIdentifier(uid);
        super.setType("homework");
    }

    @Override
    public String getTaskDetails() {
        return null;
    }

    @Override
    public ArrayList<String> getAllFields() {
        List<String> l = Arrays.asList(className, dueDate, super.getDescription(),
                String.valueOf(super.isCompleted()), super.getIdentifier());
        ArrayList<String> fields = new ArrayList<>(l);
        return fields;
    }
}
