package model.handler.urgencyhandlers;

public class Normal extends Urgency {
    public Normal() {
        setUrgency(UrgencyLevels.NORMAL);
    }

    @Override
    // EFFECTS: produces a string warning individual about the level of urgency the task has
    public String warn() {
        return "Regular. Finish when possible";
    }
}

