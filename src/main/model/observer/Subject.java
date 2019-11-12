package model.observer;

import model.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observerList = new ArrayList<Observer>();

    // MODIFIES: this
    // EFFECTS: adds associated taskManager to observers
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    // TODO: sout what is changed that prompts for saving, make everything that prompts for
    //      saving call notify
    // EFFECTS: calls observer update method
    protected void notify(Task task, String updateType) throws IOException {
        for (Observer o: observerList) {
            System.out.println(updateType + " update detected");
            o.update();
        }
    }
}
