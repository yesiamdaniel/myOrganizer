package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface Saveable {
    // MODIFIES: this
    // EFFECTS: saves part data into data.txt
    void save(ArrayList<Task> allTasks, String filename) throws IOException;
}
