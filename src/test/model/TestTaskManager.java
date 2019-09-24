package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskManager {
    private TaskManager taskManager;

    @BeforeEach
    void runBefore() {
        taskManager = new TaskManager();
    }

    @Test
    void testCreate() {
        taskManager.create("Wash dishes");
        Task task1 = taskManager.getAllTasks().get(0);

        taskManager.create("Take out garbage");
        Task task2 = taskManager.getAllTasks().get(1);

        assertTrue(taskManager.getAllTasks().contains(task1));
        assertTrue(taskManager.getAllTasks().contains(task2));

        assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    void testDelete() {
        taskManager.create("Wash dishes");
        Task taskToDelete = taskManager.getAllTasks().get(0);

        assertTrue(taskManager.getAllTasks().contains(taskToDelete));

        taskManager.delete(taskToDelete);
        assertFalse(taskManager.getAllTasks().contains(taskToDelete));
    }

    @Test
    void testMarkAsCompleted() {
        Task taskToComplete = new Task("Wash dishes");

        taskManager.create("Wash dishes");
        taskManager.markAsCompleted(taskToComplete);
        assertTrue(taskToComplete.isCompleted());
    }
}
