package model.handler.urgencyhandlers;

import model.task.Task;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Urgency {
    public enum UrgencyLevels { NORMAL, IMPORTANT, URGENT }

    private ArrayList<Task> tasks = new ArrayList<>();
    private UrgencyLevels urgency;

    // EFFECTS: returns urgency level
    public UrgencyLevels getUrgencyString() {
        return urgency;
    }

    // MODIFIES: this
    // EFFECTS: add task to urgentTasks and assigns this urgency handler to the task
    public void addTask(Task t) {
        if ((!tasks.contains(t))) {
            tasks.add(t);
            t.setUrgency(this);
        }
    }

    public void removeTask(Task t) {
        tasks.remove(t);
        t.setUrgency(null);
    }

    // MODIFIES: this
    // EFFECTS: sets urgency to UrgencyLevels passed
    public void setUrgency(UrgencyLevels u) {
        urgency = u;
    }

    // EFFECTS: produces prompt telling user about urgency status
    public abstract String warn();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Urgency urgency1 = (Urgency) o;
        return Objects.equals(tasks, urgency1.tasks)
                && urgency == urgency1.urgency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, urgency);
    }
}
