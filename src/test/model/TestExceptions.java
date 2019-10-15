package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class TestExceptions {
    private TaskManager taskManager;

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
        taskManager.reset();
    }

    @Test
    void testTooManyIncompleteException() throws IOException {
        try {
            taskManager.checkTooMany();
        } catch (TooManyIncompleteException e) {
            fail();
        }

        populateTasks();

        try {
            taskManager.checkTooMany();
        } catch (TooManyIncompleteException e) {
            System.out.println("Success");
        }
    }

    private void populateTasks() throws IOException {
        for(int i = 0; i < 10; i++) {
            taskManager.create("test");
        }
    }
}

