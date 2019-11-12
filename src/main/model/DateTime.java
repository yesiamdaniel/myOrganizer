package model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTime {
    private LocalDate date;
    private LocalTime time;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy");


    // EFFECTS: constructs DateTime object and sets date and time to null. For use in main to get
    //          current time
    public DateTime() {
        this.time = null;
        this.date = null;
    }

    // REQUIRES: date in form: yyyy-MM-dd, time in form h:mm a or hh:mm
    // EFFECTS: Constructs DateTime object given date and/or time. Used to initialize date and time in task.
    public DateTime(String date, String time) {
        if (date.equals("null")) {
            this.date = null;
        } else {
            this.date = LocalDate.parse(date);
        }

        if (time.equals("null")) {
            this.time = null;
        } else {
            try {
                this.time = LocalTime.parse(time, timeFormatter);
            } catch (Exception e) {
                this.time = LocalTime.parse(time);
            }

        }
    }

    // EFFECTS: returns true if time field is null
    public boolean isTimeNull() {
        return time == null;
    }

    // EFFECTS: returns true if date field is null
    public boolean isDateNull() {
        return date == null;
    }

    // EFFECTS: if time isnt null returns time field formatted as "h:mm a", otherwise return null
    public String getTime() {
        if (isTimeNull()) {
            return null;
        } else {
            return time.format(timeFormatter);
        }
    }

    // EFFECTS: if time isn't null Returns current date formatted as  "hh:mm" otherwise return null. For use in storing
    //          time in data.
    public String getRawTime() {
        if (isTimeNull()) {
            return null;
        } else {
            return time.toString();
        }
    }

    // EFFECTS: if date isn't null Returns current date formatted as "EEEE MMMM d, yyyy" otherwise return null
    public String getDate() {
        if (isDateNull()) {
            return null;
        } else {
            return date.format(dateFormatter);
        }
    }

    // EFFECTS: Returns current date formatted as "yyyy-MM-dd" for  use in storing values
    public String getRawDate() {
        if (isDateNull()) {
            return null;
        } else {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    // EFFECTS: Returns current time as formatted string "h:mm a"
    public String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    // EFFECTS: Returns current date as formatted string "EEEE MMMM d, yyyy"
    public String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy"));
    }

    // TODO: Write tests
    // REQUIRES: date must be in form "yyyy-mm-dd"
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    // TODO: Write tests
    public void setTime(String time) {
        this.time = LocalTime.parse(time, timeFormatter);
    }
}
