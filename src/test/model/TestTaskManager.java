package model;

import com.sun.xml.internal.ws.wsdl.writer.document.Import;
import model.handler.TaskManager;
import model.handler.urgencyhandlers.Important;
import model.handler.urgencyhandlers.Normal;
import model.handler.urgencyhandlers.Urgency;
import model.handler.urgencyhandlers.Urgent;
import model.task.Chore;
import model.task.Homework;
import model.task.Task;
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
    private String homeworkFilename = "./data/homeworkData.txt";

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
        assertEquals(10, Files.readAllLines(Paths.get(choreFileName)).size());
    }

    @Test
    void testLoadChores() throws IOException {
        taskManager.save(createChoreArray());
        taskManager.load(choreFileName);

        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void testLoadHomework() throws IOException {
        taskManager.save(createHomeworkArray());
        taskManager.load(homeworkFilename);

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

    private ArrayList<Task> createHomeworkArray() {
        Task t = new Homework("test", "test", "1234");
        Task t2 = new Homework("test2", "test2", "1234");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t);
        taskList.add(t2);
        return taskList;
    }



}
