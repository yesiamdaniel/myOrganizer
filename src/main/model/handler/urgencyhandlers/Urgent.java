package model.handler.urgencyhandlers;

import model.task.Task;

import java.util.ArrayList;
import java.util.Objects;

public class Urgent extends Urgency  {
    public Urgent() {
        setUrgency(UrgencyLevels.URGENT);
    }

    @Override
    // EFFECTS: produces a string warning individual about the level of urgency the task has
    public String warn() {
        return "URGENT, FINISH IMMEDIATELY";
    }
}
