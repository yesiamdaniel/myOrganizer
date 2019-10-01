package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    private TaskManager taskManager;
    private Task task;

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
        taskManager.create("Wash the dishes");
    }

    @Test
    void testSetCompleted() {
        task = taskManager.getAllTasks().get(0);

        assertFalse(task.isCompleted());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }
}
