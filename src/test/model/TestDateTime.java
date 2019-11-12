package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDateTime {
    private DateTime dateTime;
    private String date = "2020-01-18";
    private String time = "9:00 PM";

    @BeforeEach
    void runBefore() {
        dateTime = new DateTime(date, time);
    }

    @Test
    void testGetDate() {
        assertEquals("Saturday January 18, 2020", dateTime.getDate());
    }

    @Test
    void testGetTime() {
        assertEquals(time, dateTime.getTime());
    }

    @Test
    void testGetRawTime() {
        assertEquals("21:00", dateTime.getRawTime());
    }

    @Test
    void testSetTime() {
        dateTime.setTime("3:30 PM");
        assertEquals("3:30 PM", dateTime.getTime());
    }

    @Test
    void testSetDate() {
        dateTime.setDate("2000-01-18");
        assertEquals("2000-01-18", dateTime.getRawDate());
    }

}
