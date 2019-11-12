package model.task;

import model.DateTime;
import model.interfaces.Serializable;
import model.handler.urgencyhandlers.Urgency;
import model.observer.Subject;
import org.omg.DynamicAny.DynArray;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;

public abstract class Task extends Subject implements Serializable {
    private String identifier;
    private String description;
    private DateTime dateTime;
    private boolean completed = false;
    private Urgency urgency;

    private String type;
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

    // MODIFIES: this
    // EFFECTS: sets the task description
    public void setDescription(String description) {
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: sets the date time given date and time. For use in object instantiation from data
    public void setDateTime(String date, String time) {
        dateTime = new DateTime(date, time);
    }

    // MODIFIES: this
    // EFFECTS: sets the dateTime object of task if datetime passed. For use in new object creation
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    // MODIFIES: this
    // EFFECTS: Sets the task completion status to the status given
    public void setCompleted(boolean status) {
        completed = status;
    }

    // MODIFIES: this
    // EFFECTS: adds urgency handler to task and adds task to urgency handler
    public void setUrgency(Urgency u) throws IOException {
        if ((urgency == null)) {
            setUrgencyHelper(u);
        } else if (!(urgency.equals(u))) {
            urgency.removeTask(this);
            setUrgencyHelper(u);
        }

    }

    // MODIFIES: this
    // EFFECTS: set urgency field to u, then notifies observer
    private void setUrgencyHelper(Urgency u) throws IOException {
        urgency = u;
        urgency.addTask(this);
        notify(this, "Urgency");
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

    // REQUIRES: task type must be one of: chore or homework
    // MODIFIES: this
    // EFFECTS: sets the task type
    public void setType(String type) {
        this.type = type;
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

    // EFFECTS: returns type of task
    public String getType() {
        return type;
    }

    // EFFECTS: returns dateTime
    public DateTime getDateTime() {
        return dateTime;
    }

    // EFFECTS: prints out a description of the task to the console
    public abstract String getTaskDetails();

    // EFFECTS: returns a list of strings with all the fields that define the task
    public abstract HashMap<String, String> getAllFields();

    // TODO: heavy thrower of null pointer exception. Refactor as try catch
    // EFFECTS: creates map for date time. Helper for getAllFields.
    protected void mapDateTime(HashMap<String, String> map) {
        if (getDateTime().isDateNull()) {
            map.put("dueDate", "null");
            map.put("dueTime", "null");
        } else {
            map.put("dueDate", getDateTime().getRawDate());
            if (getDateTime().isTimeNull()) {
                map.put("dueTime", "null");
            } else {
                map.put("dueTime", getDateTime().getRawTime());
            }
        }
    }

    protected String dueDateFormat() {
        if (!(getDateTime().getDate() == null)) {
            if (!(getDateTime().getTime() == null)) {
                return getDateTime().getDate() + " at " + getDateTime().getTime();
            } else {
                return getDateTime().getDate();
            }
        }
        return "No date set";
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




}
