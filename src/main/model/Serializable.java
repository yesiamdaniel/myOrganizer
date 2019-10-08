package model;

public interface Serializable {

    // MODIFIES: this
    // EFFECTS: creates a unique serial number for a task
    void createIdentifier();
}
