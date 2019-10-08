package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Loadable {
    // MODIFIES: this
    // EFFECTS: loads data from given file name
    List<String> loadData(String filename) throws IOException;

    // EFFECTS: reformat data values stored in data file so it can be used to create objects
    ArrayList<String> handleData(String line);

    // REQUIRES: data must be formatted so that each field  is separated by " - "
    // MODIFIES: this
    // EFFECTS: creates chore objects from data and adds to a list
    void instantiateChores() throws IOException;

    // EFFECTS: creates homework objects from data and adds to a list
    void instantiateHomeworks() throws IOException;
}
