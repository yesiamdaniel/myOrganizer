package model;

import model.handler.TaskManager;
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
    void testThrowTooManyIncompleteException() throws IOException {
        populateTasks();

        try {
            taskManager.checkTooMany();
            fail();
        } catch (TooManyIncompleteException e) {
            System.out.println("Success");
        }
    }

    @Test
    void testNotThrowTooManyIncompleteException() throws IOException {
        try {
            taskManager.checkTooMany();
            System.out.println("Success");
        } catch (TooManyIncompleteException e) {
            fail();
        }
    }

    private void populateTasks() throws IOException {
        for(int i = 0; i < 11; i++) {
            taskManager.create("test", new DateTime());
        }
    }
}

