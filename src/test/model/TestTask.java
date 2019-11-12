package model;

import model.handler.TaskManager;
import model.task.Chore;
import model.task.Homework;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    private TaskManager taskManager;
    private DateTime dateTime;
    private Task task;
    private String choreUid = "CHtesting";
    private String homeworkUid = "HWtesting";

    @BeforeEach
    void runBefore() throws IOException {
        taskManager = new TaskManager();
        dateTime = new DateTime("2000-01-18", "9:00 PM");
        taskManager.create("Wash the dishes", dateTime);
    }

    @Test
    void testSetCompleted() {
        task = taskManager.getAllTasks().get(0);

        assertFalse(task.isCompleted());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    // Tests constructor for chore when created in main
    void testTwoFieldChoreCreate() throws IOException {
        try {
            Chore twoFields = new Chore("test1", dateTime);
            assertEquals("test1", twoFields.getDescription());
            assertEquals(dateTime, twoFields.getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testChoreCreate exception thrown");
            fail();
        }
    }

    @Test
    // Tests constructor for chore when created from file
    void testFiveFieldChoreCreate() throws IOException {
        try {
            Chore fiveFields = new Chore("test2", "2000-01-18", "21:00",
                    "false", choreUid);
            assertEquals("test2", fiveFields.getDescription());
            assertEquals("21:00", fiveFields.getDateTime().getRawTime());
            assertEquals("2000-01-18", fiveFields.getDateTime().getRawDate());
            assertEquals(choreUid, fiveFields.getIdentifier());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testChoreCreate exception thrown");
            fail();
        }
    }

    @Test
    // Tests constructor for chore when created from file with dateTime null
    void testFiveFieldChoreCreateNullDateTimes() throws IOException {
        try {
            Chore fiveFields = new Chore("test2", "null", "null",
                    "false", choreUid);
            assertEquals("test2", fiveFields.getDescription());
            assertNull(fiveFields.getDateTime().getRawTime());
            assertNull(fiveFields.getDateTime().getRawDate());
            assertEquals(choreUid, fiveFields.getIdentifier());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testChoreCreate exception thrown");
            fail();
        }
    }

    @Test
    // Tests constructor for homework when created in main
    void testThreeFieldHomeworkCreate() throws IOException {
        try {
            Homework threeFields = new Homework("testClass", "test3", dateTime);
            assertEquals("test3", threeFields.getDescription());
            assertEquals(dateTime, threeFields.getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testHomeworkCreate exception thrown");
            fail();
        }
    }

    @Test
    // Tests constructor for homework when created from file
    void testFiveFieldHomeworkCreate() throws IOException {
        try {
            Homework fiveFields = new Homework(homeworkUid, "testClass2", "test4",
                    "2000-01-18", "21:00", "true");
            assertEquals("test4", fiveFields.getDescription());
            assertEquals("21:00", fiveFields.getDateTime().getRawTime());
            assertEquals("2000-01-18", fiveFields.getDateTime().getRawDate());
            assertEquals(homeworkUid, fiveFields.getIdentifier());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testChoreCreate exception thrown");
            fail();
        }
    }

    @Test
        // Tests constructor for homework when created from file
    void testFiveFieldHomeworkCreateNullDateTimes() throws IOException {
        try {
            Homework fiveFields = new Homework(homeworkUid, "testClass3", "test5",
                    "null", "null", "true");
            assertEquals("test5", fiveFields.getDescription());
            assertNull(fiveFields.getDateTime().getRawTime());
            assertNull(fiveFields.getDateTime().getRawDate());
            assertEquals(homeworkUid, fiveFields.getIdentifier());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("testChoreCreate exception thrown");
            fail();
        }
    }

    @Test
    void testMarkAsCompleted() throws IOException {
        Task taskToComplete = new Chore("Wash dishes", dateTime);

        taskToComplete.setCompleted(true);
        assertTrue(taskToComplete.isCompleted());
    }
}
