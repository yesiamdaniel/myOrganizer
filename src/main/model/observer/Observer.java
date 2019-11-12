package model.observer;

import model.task.Task;

import java.io.IOException;

public interface Observer {
    public boolean update() throws IOException;
}
