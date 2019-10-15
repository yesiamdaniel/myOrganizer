package model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DataHandler implements Saveable, Loadable {
    private ArrayList<Task> allChores = new ArrayList<>();
    private ArrayList<Task> allHomework = new ArrayList<>();

    private String choreFilename = "./data/choreData.txt";
    private String homeworkFilename = "./data/homeworkData.txt";

    @Override
    // EFFECTS: reformat data values stored in data file so it can be used
    public ArrayList<String> handleData(String line) {
        String[] fragment = line.split(" - ");
        return new ArrayList<>(Arrays.asList(fragment));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: saves task data into respective data file
    public void save(ArrayList<Task> tasksToSave) throws IOException {
        int iter = 1;
        FileWriter file = null;
        for (Task t: tasksToSave) {
            String filename = "./data/" + t.getType() + "Data.txt";

            while (iter == 1) {
                file = clearFileAndReturn(filename);
                iter = 0;
            }

            if (t.getType().equals("homework")) {
                saveHomework(file, t);
            }
            if (t.getType().equals("chore")) {
                saveChore(file, t);
            }
        }
        if (file != null) {
            file.close();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data and returns each line in a list
    public List<String> loadData(String fileName) throws IOException {
        try {
            Files.readAllLines(Paths.get(fileName));
        } catch (NoSuchFileException i) {
            System.out.println("file Error encountered... Attempting to resolve...");
            FileWriter file = new FileWriter(fileName);
            file.close();
            System.out.println("Successfully resolved!");
        } finally {
            return Files.readAllLines(Paths.get(fileName));
        }
    }

    // REQUIRES: data must be formatted so that each field  is separated by " - "
    // MODIFIES: this
    // EFFECTS: creates chore objects from data and adds to allChores
    public void instantiateChores() throws IOException {
        for (String line: loadData(choreFilename)) {
            ArrayList<String> choreFields = handleData(line);

            String description = choreFields.get(0);
            String completed = choreFields.get(1);
            String uid = choreFields.get(2);

            Task t = new Chore(description, completed, uid);
            allChores.add(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given chore to allChores and saves to file
    public void addNewChore(Task c) throws IOException {
        allChores.add(c);
        save(allChores);
    }

    // MODIFIES: this
    // EFFECTS: removes given chore from allChores and removes from file
    public void removeChore(Task c) throws IOException {
        allChores.remove(c);
        if (allChores.isEmpty()) {
            FileWriter file = new FileWriter(choreFilename);
            file.flush();
        } else {
            save(allChores);
        }
    }

    // REQUIRES: data must be formatted so that each field is separated by " - "
    // MODIFIES: this
    // EFFECTS: creates homework objects from data and adds to allChores
    public void instantiateHomeworks() throws IOException {
        for (String line: loadData(homeworkFilename)) {
            ArrayList<String> homeworkFields = handleData(line);

            String className = homeworkFields.get(0);
            String description = homeworkFields.get(1);
            String dueDate = homeworkFields.get(2);
            String completed = homeworkFields.get(3);
            String uid = homeworkFields.get(4);

            Task t = new Homework(className, description, dueDate, completed, uid);
            allHomework.add(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given homework to allHomework and saves to file
    public void addNewHomework(Task h) throws IOException {
        allHomework.add(h);
        save(allHomework);
    }

    // MODIFIES: this
    // EFFECTS: removes given homework from allHomework and removes from file
    public void removeHomework(Task h) throws IOException {
        allHomework.remove(h);
        if (allHomework.isEmpty()) {
            FileWriter file = new FileWriter(homeworkFilename);
            file.flush();
        } else {
            save(allHomework);
        }
    }


    // EFFECTS: returns a list of all current chores
    public ArrayList<Task> getAllChores() {
        return allChores;
    }

    // EFFECTS: returns a list of all current homework
    public ArrayList<Task> getAllHomework() {
        return allHomework;
    }

    // EFFECTS: returns list of all current tasks
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(allChores);
        allTasks.addAll(allHomework);

        return allTasks;
    }

    // REQUIRES: a predefined iterator field that is of type int and equal to 1
    // MODIFIES: data
    // EFFECTS: clears all contents of the given file and then returns it
    private FileWriter clearFileAndReturn(String filename) throws IOException {
        FileWriter newFile = new FileWriter(filename);
        newFile.flush();
        return newFile;
    }

    private void saveChore(FileWriter file, Task t) throws IOException {
        file.write(t.getDescription() + " - " + t.isCompleted() + " - " + t.getIdentifier()
                + "\n");
    }

    private void saveHomework(FileWriter file, Task t) throws IOException {
        ArrayList<String> fields = t.getAllFields();
        String className = fields.get(0);
        String description = fields.get(1);
        String dueDate = fields.get(2);
        String completed = fields.get(3);
        String uid = fields.get(4);
        String contents = className + " - " + description + " - " + dueDate + " - " + completed + " - " + uid
                + "\n";
        file.write(contents);
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of tasks in the data file then reloads the data from the file
    public  void reloadData() throws IOException {
        save(getAllTasks());
        allHomework.clear();
        allChores.clear();

        instantiateChores();
        instantiateHomeworks();
    }

    // MODIFIES: this
    // EFFECTS: clears all data
    public void reset() throws IOException {
        allHomework.clear();
        allChores.clear();

        new FileWriter(homeworkFilename);
        new FileWriter(choreFilename);
    }

    // MODIFIES: this
    // EFFECTS: clears all data
    public void reset(String file) throws IOException {
        allHomework.clear();
        allChores.clear();

        new FileWriter(homeworkFilename);
        new FileWriter(choreFilename);
    }

}

