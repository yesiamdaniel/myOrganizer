package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskManager {
    private TaskManager taskManager;
    private String choreFileName = "./data/choreData.txt";

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
        taskManager.reset();
    }

    @Test
    void testCreateChore() throws IOException {
        taskManager.create("Wash dishes");
        Task task1 = taskManager.getAllTasks().get(0);

        Task c = new Chore("testing", "true", "12345678");
        taskManager.addNewChore(c);

        assertTrue(taskManager.getAllTasks().contains(task1));
        assertTrue(taskManager.getAllTasks().contains(c));

    }

    @Test
    void testCreateHomework() throws IOException {
        taskManager.create("CPSC 121", "Midterm Friday", "Friday");
        Task task1 = taskManager.getAllTasks().get(0);


        assertTrue(taskManager.getAllTasks().contains(task1));
    }

    @Test
    void testDeleteChore() throws IOException {
        taskManager.create("Wash dishes");
        Task taskToDelete = taskManager.getAllTasks().get(0);

        assertTrue(taskManager.getAllTasks().contains(taskToDelete));

        taskManager.delete(taskToDelete);
        assertFalse(taskManager.getAllTasks().contains(taskToDelete));
    }

    @Test
    void testDeleteHomework() throws IOException {
        taskManager.create("CPSC 121", "Midterm Friday", "Friday");
        Task taskToDelete = taskManager.getAllTasks().get(0);

        assertTrue(taskManager.getAllTasks().contains(taskToDelete));

        taskManager.delete(taskToDelete);
        assertFalse(taskManager.getAllTasks().contains(taskToDelete));
    }


    @Test
    void testMarkAsCompleted() throws IOException {
        Task taskToComplete = new Chore("Wash dishes");

        taskManager.create("Wash dishes");
        taskToComplete.setCompleted(true);
        assertTrue(taskToComplete.isCompleted());
    }

    @Test
    void testSaveChores() throws IOException {
        taskManager.save(createChoreArray());
        assertEquals(2, Files.readAllLines(Paths.get(choreFileName)).size());
    }

    @Test
    void testLoad() throws IOException {
        taskManager.reset();
        taskManager.save(createChoreArray());
        taskManager.instantiateChores();

        assertEquals(2, taskManager.getAllTasks().size());
    }

    // EFFECTS: creates 2 tasks and returns those tasks as a list
    private ArrayList<Task> createChoreArray() {
        Task t = new Chore("Wash the dishes");
        Task t2 = new Chore("Do homework");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t);
        taskList.add(t2);
        return taskList;
    }



}
