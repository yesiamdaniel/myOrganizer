package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskManager {
    private TaskManager taskManager;
    private String identifier = "test";
    private String fileName = "./data/test.txt";

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
    }

    @Test
    void testCreate() throws IOException {
        taskManager.create("Wash dishes");
        Task task1 = taskManager.getAllTasks().get(0);

        taskManager.create("Take out garbage");
        Task task2 = taskManager.getAllTasks().get(1);

        assertTrue(taskManager.getAllTasks().contains(task1));
        assertTrue(taskManager.getAllTasks().contains(task2));

    }

    @Test
    void testDelete() throws IOException {
        taskManager.create("Wash dishes");
        Task taskToDelete = taskManager.getAllTasks().get(0);

        assertTrue(taskManager.getAllTasks().contains(taskToDelete));

        taskManager.delete(taskToDelete);
        assertFalse(taskManager.getAllTasks().contains(taskToDelete));
    }

    @Test
    void testMarkAsCompleted() throws IOException {
        Task taskToComplete = new Task("Wash dishes", identifier);

        taskManager.create("Wash dishes");
        taskManager.markAsCompleted(taskToComplete);
        assertTrue(taskToComplete.isCompleted());
    }

    @Test
    void testSave() throws IOException {
        ArrayList<Task> taskList = createTaskArray();

        taskManager.save(taskList, fileName);
        assertEquals(2, Files.readAllLines(Paths.get(fileName)).size());
    }

    @Test
    void testLoad() throws IOException {
        taskManager.save(createTaskArray(), fileName);
        taskManager.load(fileName);

        assertEquals(2, taskManager.getAllTasks().size());
    }

    // EFFECTS: add 2 test tasks to test file and returns those tasks as a list
    private ArrayList<Task> createTaskArray() throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.flush();
        Task t = new Task("Wash the dishes", "test");
        Task t2 = new Task("Do homework", "test2");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t);
        taskList.add(t2);
        return taskList;
    }



}
