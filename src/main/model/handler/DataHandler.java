package model.handler;

import model.handler.urgencyhandlers.Urgency;
import model.handler.urgencyhandlers.Urgent;
import model.interfaces.Loadable;
import model.interfaces.Saveable;
import model.task.Chore;
import model.task.Homework;
import model.task.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class DataHandler implements Saveable, Loadable {
    protected ArrayList<Task> allChores = new ArrayList<>();
    protected ArrayList<Task> allHomework = new ArrayList<>();

    String choreFilename = "./data/choreData.txt";
    String homeworkFilename = "./data/homeworkData.txt";

    @Override

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

    // MODIFIES: file
    // EFFECTS: saves chore objects to file
    private void saveChore(FileWriter file, Task t) throws IOException {
        HashMap<String, String> fields = t.getAllFields();

        String uid = fields.get("uid");
        String description = fields.get("description");
        String completed = fields.get("completed");
        String urgency = fields.get("urgency");

        file.write(">>>\n"
                + "uid:" + uid
                + "\ndescription:" + description
                + "\ncompleted:" + completed
                + "\nurgency:" + urgency
                + "\n");
    }

    // MODIFIES: file
    // EFFECTS: saves homework objects to file
    private void saveHomework(FileWriter file, Task t) throws IOException {
        HashMap<String, String> fields = t.getAllFields();
        String uid = fields.get("uid");
        String description = fields.get("description");
        String className = fields.get("className");
        String dueDate = fields.get("dueDate");
        String completed = fields.get("completed");
        String urgency = fields.get("urgency");

        file.write(">>>\n"
                + "uid:" + uid
                + "\ndescription:" + description
                + "\nclassName:" + className
                + "\ndueDate:" + dueDate
                + "\ncompleted:" + completed
                + "\nurgency:" + urgency
                + "\n");
    }

    // EFFECTS: loads all raw data and passes to segmentData() if file contents are not empty.
    //          Initiates instantiation sequence to instantiate all tasks
    public void load(String fileName) throws IOException {
        List<String> rawData = null;
        try {
            rawData =  Files.readAllLines(Paths.get(fileName));
        } catch (NoSuchFileException i) {
            System.out.println("file Error encountered... Attempting to resolve...");
            new FileWriter(fileName);
            System.out.println("Successfully resolved!");
            rawData = Files.readAllLines(Paths.get(fileName));
        } finally {
            assert rawData != null;
            if (!rawData.isEmpty()) {
                segmentData(rawData);
            }
        }
    }

    // REQUIRES: rawData is not empty
    // EFFECTS: parses through all lines of data and passes array of lines that correspond to a HashMap
    //          to hashData()
    private void segmentData(List<String> rawData) throws IOException {
        ArrayList<String> hashSoFar = new ArrayList<>();
        for (String line : rawData) {
            if (line.equals(">>>")) {
                hashData(hashSoFar);
                hashSoFar.clear();
            } else {
                hashSoFar.add(line);
            }
        }
        hashData(hashSoFar);
    }

    // EFFECTS: given lines that correspond to a map, creates HashMap for object then passes map to instantiate()
    private void hashData(ArrayList<String> hashLines) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        if (!hashLines.isEmpty()) {
            for (String line : hashLines) {
                String[] fragment = line.split(":");
                ArrayList<String> keyValue = new ArrayList<>(Arrays.asList(fragment));
                try {
                    String key = keyValue.get(0);
                    String value = keyValue.get(1);
                    map.put(key, value);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    System.out.println("DATA FORMAT ERROR");
                    System.exit(1);
                }

            }
            instantiate(map);
        }
    }

    // REQUIRES: uid must contain 2 character type identifier as its first 2 characters
    // EFFECTS: based on type of object map corresponds to, calls the respective object constructor helper function
    private void instantiate(HashMap<String, String> map) throws IOException {
        switch (map.get("uid").substring(0,2)) {
            case "CH":
                instantiateChores(map);
                break;
            case "HW":
                instantiateHomework(map);
                break;
            default:
        }
    }

    // MODIFIES: this
    // EFFECTS: creates chore object from given map and adds to allChores
    public void instantiateChores(HashMap<String, String> map) throws IOException {
        String description = map.get("description");
        String completed = map.get("completed");
        String uid = map.get("uid");
        String urgency = map.get("urgency");

        Task t = new Chore(description, completed, uid);
        t.setUrgency(switchUrgency(urgency));
        allChores.add(t);
    }

    // MODIFIES: this
    // EFFECTS: creates homework object from data and adds to allChores
    public void instantiateHomework(HashMap<String, String> map) throws IOException {
        String uid = map.get("uid");
        String description = map.get("description");
        String className = map.get("className");
        String dueDate = map.get("dueDate");
        String completed = map.get("completed");
        String urgency = map.get("urgency");

        Task t = new Homework(className, description, dueDate, completed, uid);
        t.setUrgency(switchUrgency(urgency));
        allHomework.add(t);
    }

    private Urgency switchUrgency(String urgency) {
        switch (urgency) {
            case "normal":
                return TaskManager.normal;
            case "important":
                return TaskManager.important;
            case "urgent":
                return TaskManager.urgent;
            default:
                System.out.println("DATA URGENCY FORMAT ERROR");
                System.exit(2);
                return new Urgent();
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
    void removeChore(Task c) throws IOException {
        allChores.remove(c);

        if (allChores.isEmpty()) {
            FileWriter file = new FileWriter(choreFilename);
            file.flush();
        } else {
            save(allChores);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given homework to allHomework and saves to file
    void addNewHomework(Task h) throws IOException {
        allHomework.add(h);
        save(allHomework);
    }

    // MODIFIES: this
    // EFFECTS: removes given homework from allHomework and removes from file
    void removeHomework(Task h) throws IOException {
        allHomework.remove(h);
        if (allHomework.isEmpty()) {
            FileWriter file = new FileWriter(homeworkFilename);
            file.flush();
        } else {
            save(allHomework);
        }
    }

    // Dont belong here
    // EFFECTS: returns a list of all current chores
//    ArrayList<Task> getAllChores() {
//        return allChores;
//    }

//    // EFFECTS: returns a list of all current homework
//    ArrayList<Task> getAllHomework() {
//        return allHomework;
//    }

//    // EFFECTS: returns list of all current tasks
//    public ArrayList<Task> getAllTasks() {
//        ArrayList<Task> allTasks = new ArrayList<>();
//        allTasks.addAll(allChores);
//        allTasks.addAll(allHomework);
//
//        return allTasks;
//    }

    // REQUIRES: a predefined iterator field that is of type int and equal to 1
    // MODIFIES: data
    // EFFECTS: clears all contents of the given file and then returns it
    private FileWriter clearFileAndReturn(String filename) throws IOException {
        FileWriter newFile = new FileWriter(filename);
        newFile.flush();
        return newFile;
    }

    // Doesnt belong here
//    // MODIFIES: this
//    // EFFECTS: saves the current state of tasks in the data file then reloads the data from the file
//    void reloadData() throws IOException {
//        save(getAllTasks());
//        allHomework.clear();
//        allChores.clear();
//
//        load(choreFilename);
//        load(homeworkFilename);
//    }

    // MODIFIES: this
    // EFFECTS: clears all data
    public void reset() throws IOException {
        allHomework.clear();
        allChores.clear();

        new FileWriter(homeworkFilename);
        new FileWriter(choreFilename);
    }
}

