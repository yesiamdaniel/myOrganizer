package model.task;

import model.DateTime;

import java.util.HashMap;

public class Homework extends Task {
    private String className;

    // Constructs a brand new homework object from no previous data
    public Homework(String className, String description, DateTime dateTime) {
        this.className = className;
        super.setDescription(description);
        createIdentifier();
        setDateTime(dateTime);
        super.setType("homework");
    }

    // Constructs homework object from data
    public Homework(String uid, String className, String description,
                    String dueDate, String dueTime, String completed) {
        super.setIdentifier(uid);
        this.className = className;
        super.setDescription(description);

        setDateTime(dueDate, dueTime);

        if (completed.equals("true")) {
            super.setCompleted(true);
        }


        super.setType("homework");
    }

    @Override
    public void createIdentifier() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);

        sb.append("HW");
        for (int i = 0; i < 8; i++) {
            int index = (int)(allChars.length() * Math.random());
            sb.append(allChars.charAt(index));
        }
        setIdentifier(sb.toString());
    }

    public String getTaskDetails() {
        return "*Homework*\n"
                + space + className + " - " + getDescription() + "\n"
                + space + "Due: " + dueDateFormat() + "\n"
                + space + "Completed?: " + isCompleted() + "\n"
                + space + "Urgency: " + getUrgency().warn();
    }

    @Override
    public HashMap<String, String> getAllFields() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", getIdentifier());
        map.put("description", getDescription());
        map.put("className", className);

        mapDateTime(map);

        map.put("completed", String.valueOf(isCompleted()));
        map.put("urgency", getUrgencyString());

        return map;
    }


}
