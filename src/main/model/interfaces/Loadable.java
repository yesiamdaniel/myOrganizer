package model.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Loadable {
    // MODIFIES: this
    // EFFECTS: loads data from given file name
    void load(String filename) throws IOException;

    // REQUIRES: data must be formatted so that each field  is separated by " - "
    // MODIFIES: this
    // EFFECTS: creates chore objects from data and adds to a list
    void instantiateChores(HashMap<String, String> map) throws IOException;

    // EFFECTS: creates homework objects from data and adds to a list
    void instantiateHomework(HashMap<String, String> map) throws IOException;
}
