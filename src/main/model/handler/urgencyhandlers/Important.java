package model.handler.urgencyhandlers;

public class Important extends Urgency {
    public Important() {
        setUrgency(UrgencyLevels.IMPORTANT);
    }

    @Override
    // EFFECTS: produces a string warning individual about the level of urgency the task has
    public String warn() {
        return "Important, finish ASAP.";
    }
}
