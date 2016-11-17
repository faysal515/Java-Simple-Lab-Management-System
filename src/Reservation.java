/**
 * @name Reservation.java
 * @desc Represents a reservation for a room
 * @version August 19, 2011
 
 */

import java.io.*;

public class Reservation implements Serializable
{
    private String facultyName;
    private String subject;
    private String time;
    private String date;
    
    /**
     * @desc Initializes the attributes of the reservation
     * @param time time to be used
     * @param day day to be used
     */
    public Reservation(String facultyName, String subject, String time, String date)
    {
        this.facultyName = facultyName;
        this.subject = subject;
        this.time = time;
        this.date = date;
    }
    
    /**
     * @desc getter methods
     */
    public String getTime()
    {
        return this.time;
    }
    
    public String getDate()
    {
        return this.date;
    }
    
    public String getFacultyName()
    {
        return this.facultyName;
    }
    
    public String getSubject()
    {
        return this.subject;
    }
}
