package model;

import java.io.IOException;
import java.util.ArrayList;

public interface Loadable {
    // MODIFIES: this
    // EFFECTS: loads data from data.txt
    void load(String filename) throws IOException;

    // EFFECTS: reformat data values stored in data.txt so it can be used
    ArrayList<String> handleData(String line);
}
