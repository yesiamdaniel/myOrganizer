package model.task;

import java.util.HashMap;

public class Homework extends Task {
    private String className;
    private String dueDate;

    // Constructs a brand new homework object from no previous data
    public Homework(String className, String description, String dueDate) {
        this.className = className;
        this.dueDate = dueDate;
        super.setDescription(description);
        createIdentifier();
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

    @Override
    public String getTaskDetails() {
        return "*Homework*\n "
                + "  " + className + " - " + getDescription() + "\n"
                + "   Due: " + dueDate
                + "   Urgency: " + getUrgency().warn();
    }

    @Override
    public HashMap<String, String> getAllFields() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", getIdentifier());
        map.put("description", getDescription());
        map.put("className", className);
        map.put("dueDate", dueDate);
        map.put("completed", String.valueOf(isCompleted()));
        map.put("urgency", getUrgencyString());

        return map;
    }
}
