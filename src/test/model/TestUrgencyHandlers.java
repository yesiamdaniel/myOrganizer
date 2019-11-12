package model;

import model.handler.urgencyhandlers.Important;
import model.handler.urgencyhandlers.Normal;
import model.handler.urgencyhandlers.Urgency;
import model.handler.urgencyhandlers.Urgent;
import model.task.Chore;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestUrgencyHandlers {
    private Urgency urgent;
    private Urgency important;
    private Urgency normal;
    private Urgency normal2;
    private Task task;

    @BeforeEach
    void runBefore() {
        task = new Chore("test", new DateTime());
        urgent = new Urgent();
        important = new Important();
        normal = new Normal();
        normal2 = new Normal();
    }

    @Test
    void testAddTask() throws IOException {
        task.setUrgency(urgent);
        assertEquals(urgent, task.getUrgency());

        task.setUrgency(important);
        assertEquals(important, task.getUrgency());
        assertFalse(urgent.getTasks().contains(task));

        task.setUrgency(normal);
        assertEquals(normal, task.getUrgency());
        assertFalse(important.getTasks().contains(task));
    }

    @Test
    void testWarnings() {
        assertEquals("URGENT, FINISH IMMEDIATELY", urgent.warn());
        assertEquals("Important, finish ASAP.", important.warn());
        assertEquals("Regular. Finish when possible", normal.warn());
    }

    @Test
    void testEquals() {
        assertEquals(normal, normal2);
        assertEquals(normal.hashCode(), normal2.hashCode());
    }

    @Test
    void getUrgencyString() throws IOException {
        task.setUrgency(urgent);
        assertEquals("urgent", task.getUrgencyString());

        task.setUrgency(important);
        assertEquals("important", task.getUrgencyString());

        task.setUrgency(normal);
        assertEquals("normal", task.getUrgencyString());
    }
}
