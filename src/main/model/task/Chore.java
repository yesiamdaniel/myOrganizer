package model.task;


import model.DateTime;

import java.util.HashMap;

public class Chore extends Task {

    // TODO: remove from tests
    // Constructs brand new chore
//    public Chore(String taskDescription) {
//        super.setDescription(taskDescription);
//        createIdentifier();
//        super.setType("chore");
//    }

    // Constructs brand new chore given description and datetime
    public Chore(String taskDescription, DateTime dateTime) {
        super.setDescription(taskDescription);
        createIdentifier();
        super.setType("chore");
        setDateTime(dateTime);
    }

    // Constructs chore from file
    // REQUIRES: dueDate in form yyyy-MM-dd, dueTime in form hh:mm (24 time)
    public Chore(String taskDescription, String dueDate, String dueTime, String completed, String uid) {
        super.setDescription(taskDescription);
        super.setDateTime(dueDate, dueTime);

        if (completed.equals("true")) {
            super.setCompleted(true);
        }

        super.setIdentifier(uid);
        super.setType("chore");
    }

    @Override
    public void createIdentifier() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);
        sb.append("CH");

        for (int i = 0; i < 6; i++) {
            int index = (int) (allChars.length() * Math.random());
            sb.append(allChars.charAt(index));
        }
        setIdentifier(sb.toString());
    }

    public String getTaskDetails() {
        return "*Chore*\n"
                + space + getDescription() + "\n"
                + space + "Do by: " + dueDateFormat() + "\n"
                + space + "Completed?: " + isCompleted() + "\n"
                + space + "Urgency: " + getUrgency().warn();
    }

    @Override
    public HashMap<String, String> getAllFields() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", getIdentifier());
        map.put("description", getDescription());

        mapDateTime(map);

        map.put("completed", String.valueOf(isCompleted()));
        map.put("urgency", getUrgencyString());


        return map;
    }
}

