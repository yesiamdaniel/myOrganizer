package model.task;

import model.interfaces.Serializable;
import model.handler.urgencyhandlers.Urgency;

import java.util.HashMap;
import java.util.Objects;

public abstract class Task implements Serializable {
    private String description;
    private boolean completed = false;
    private String identifier;
    private String type;
    private Urgency urgency;
    protected String space = "   ";

    // MODIFIES: this
    // EFFECTS: sets the identifier to a random 8 character alphanumeric string
    @Override
    public abstract void createIdentifier();

    // REQUIRES: identifier to set must be 8 characters long
    // MODIFIES: this
    // EFFECTS: sets the identifier
    void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    // REQUIRES: task type must be one of: chore or homework
    // MODIFIES: this
    // EFFECTS: sets the task type
    public void setType(String type) {
        this.type = type;
    }

    // MODIFIES: this
    // EFFECTS: adds urgency handler to task and adds task to urgency handler
    public void setUrgency(Urgency u) {
        if ((urgency == null)) {
            urgency = u;
            urgency.addTask(this);
        } else if (!(urgency.equals(u))) {
            urgency.removeTask(this);
            urgency = u;
            urgency.addTask(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes urgency from task
    public void removeUrgency() {
        if (urgency != null) {
            if (urgency.getTasks().contains(this)) {
                urgency.removeTask(this);
                urgency = null;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the task completion status to the status given
    public void setCompleted(boolean status) {
        completed = status;
    }

    // EFFECTS: returns the unique identifier for this task
    public String getIdentifier() {
        return identifier;
    }

    // EFFECTS: returns urgency as string
    public String getUrgencyString() {
        if (!(urgency == null)) {
            switch (urgency.getUrgency()) {
                case IMPORTANT: return "important";
                case URGENT: return "urgent";
                default: return "normal";
            }
        }
        return "normal";
    }

    // EFFECTS: returns urgency object
    public Urgency getUrgency() {
        return urgency;
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

    // EFFECTS: returns type of task
    public String getType() {
        return type;
    }

    //EFFECTS: Returns the completion status of the task
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return description.equals(task.description)
                && identifier.equals(task.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, identifier);
    }

    // EFFECTS: prints out a description of the task to the console
    public abstract String getTaskDetails();

    // EFFECTS: returns a list of strings with all the fields that define the task
    public abstract HashMap<String, String> getAllFields();


}
