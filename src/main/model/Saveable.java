package model;

import java.io.IOException;
import java.util.ArrayList;

public interface Saveable {
    // MODIFIES: this
    // EFFECTS: saves part data into given filename
    void save(ArrayList<Task> tasksToSave) throws IOException;
}
