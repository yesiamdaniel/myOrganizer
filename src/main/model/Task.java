package model;

import java.util.ArrayList;

public class Task {

    public static ArrayList<Task> allTasks = new ArrayList<>();
    public String taskDescription;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
        allTasks.add(this);
    }

    public void deleteTask() {
        allTasks.remove(this);
        System.out.println("Successfully removed " + this.taskDescription + " from the list");
    }
}
