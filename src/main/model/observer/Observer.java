package model.observer;

import model.task.Task;

import java.io.IOException;

public interface Observer {
    // EFFECTS: called from subject to update
    public boolean update() throws IOException;
}
