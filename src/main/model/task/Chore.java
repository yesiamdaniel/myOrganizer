package model.task;

import java.util.HashMap;

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


    @Override
    public String getTaskDetails() {
        return "*Chore*\n"
                + "   " + getDescription() + "\n"
                + "   Completed?: " + isCompleted() + "\n"
                + "   Urgency: " + getUrgency().warn();
    }

    @Override
    public HashMap<String, String> getAllFields() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", getIdentifier());
        map.put("description", getDescription());
        map.put("completed", String.valueOf(isCompleted()));
        map.put("urgency", getUrgencyString());

        return map;
    }
}

