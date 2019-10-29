package model.handler.urgencyhandlers;

public class Normal extends Urgency {
    public Normal() {
        setUrgency(UrgencyLevels.NORMAL);
    }

    @Override
    public String warn() {
        return "Regular. Finish when possible";
    }
}

