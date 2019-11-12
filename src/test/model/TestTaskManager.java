package model;


import model.handler.TaskManager;
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
    private DateTime dateTime;

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
        taskManager.reset();

        dateTime = new DateTime("2000-01-18", "9:00 PM");
    }

    @Test
    void testCreateChore() throws IOException {
        try {
            taskManager.create("Wash dishes", dateTime);
            Task task1 = taskManager.getAllTasks().get(0);
            assertTrue(taskManager.getAllTasks().contains(task1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testCreateChore exception thrown");
            fail();
        }
    }

    @Test
    void testCreateChoreNoDateTime() throws IOException {
        try {
            taskManager.create("Wash dishes", new DateTime());
            Task task1 = taskManager.getAllTasks().get(0);
            assertTrue(taskManager.getAllTasks().contains(task1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testCreateChore exception thrown");
            fail();
        }
    }

    @Test
    void testCreateHomework() throws IOException {
        try {
            taskManager.create("CPSC 121", "Midterm Friday", dateTime);
            Task task1 = taskManager.getAllTasks().get(0);
            assertTrue(taskManager.getAllTasks().contains(task1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testCreateHomework exception thrown");
            fail();
        }
    }

    @Test
    void testCreateHomeworkNoDateTime() throws IOException {
        try {
            taskManager.create("CPSC 121", "Midterm Friday", new DateTime());
            Task task1 = taskManager.getAllTasks().get(0);
            assertTrue(taskManager.getAllTasks().contains(task1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testCreateHomework exception thrown");
            fail();
        }
    }

    @Test
    void testDeleteChore() throws IOException {
        try {
            taskManager.create("Wash dishes", dateTime);
            Task taskToDelete = taskManager.getAllTasks().get(0);

            taskManager.delete(taskToDelete);
            assertFalse(taskManager.getAllTasks().contains(taskToDelete));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testDeleteChore exception thrown");
            fail();
        }
    }

    @Test
    void testDeleteHomework() throws IOException {
        try {
            taskManager.create("CPSC 121", "Midterm Friday", dateTime);
            Task taskToDelete = taskManager.getAllTasks().get(0);

            taskManager.delete(taskToDelete);
            assertFalse(taskManager.getAllTasks().contains(taskToDelete));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testDeleteHomework exception thrown");
            fail();
        }
    }

    @Test
    void testSaveChores() throws IOException {
        taskManager.save(createChore());
        assertEquals(14, Files.readAllLines(Paths.get(choreFileName)).size());
    }

    @Test
    void testSaveHomework() throws IOException {
        taskManager.save(createHomework());
        assertEquals(16, Files.readAllLines(Paths.get(homeworkFilename)).size());
    }

    @Test
    void testLoadChores() throws IOException {
        createChore();
        taskManager.reload();

        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void testLoadHomework() throws IOException {
        createHomework();
        taskManager.reload();

        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void testUpdate() throws IOException {
        taskManager.save(createChore());

        taskManager.create("save me", new DateTime());

        assertTrue(taskManager.update());
        assertEquals(21, Files.readAllLines(Paths.get(choreFileName)).size());


    }

    // EFFECTS: creates 2 chores and returns those tasks as a list
    private ArrayList<Task> createChore() throws IOException {
        Task t1 = taskManager.create("Wash the dishes", dateTime);
        Task t2 = taskManager.create("Do homework", new DateTime());

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t1);
        taskList.add(t2);
        return taskList;
    }

    // EFFECTS: creates 2 homework and returns those tasks as a list
    private ArrayList<Task> createHomework() throws IOException {
        Task t = taskManager.create("test", "test", dateTime);
        Task t2 = taskManager.create("test2", "test2", new DateTime());
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(t);
        taskList.add(t2);
        return taskList;
    }



}
