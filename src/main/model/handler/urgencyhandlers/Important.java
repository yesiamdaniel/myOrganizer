package model.handler.urgencyhandlers;

public class Important extends Urgency {
    public Important() {
        setUrgency(UrgencyLevels.IMPORTANT);
    }

    @Override
    public String warn() {
        return "Important, finish ASAP.";
    }
}
