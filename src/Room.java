/**
 * @name Room.java
 * @desc represents a room
 * @version August 18, 2011

 */

import java.io.*;
import java.util.*;

public class Room implements Serializable
{
    private int capacity;
    private String name;
    private boolean hasNetBeans;
    private boolean hasCPlusPlus;
    private boolean hasXP;
    private boolean hasVista;
    private ArrayList<Reservation> reservations;
    
    /**
     * @desc initializes the attributes of the room
     * @param capacity the number of computed stored
     */
    public Room(int capacity, String name)
    {
        this.capacity = capacity;
        this.hasNetBeans = false;
        this.hasCPlusPlus = false;
        this.hasXP = false;
        this.hasVista = false;
        this.name = name;
        
        this.reservations = new ArrayList<Reservation>();
    }
    
    /**
     * @desc adds a reservation
     * @return the result of adding
     */
    public void addReservation(Reservation reservation)
    {
        this.reservations.add(reservation);
    }
    
    /**
     * @desc setter methods
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
    
    public void setHasNetBeans(boolean hasNetBeans)
    {
        this.hasNetBeans = hasNetBeans;
    }
    
    public void setHasCPlusPlus(boolean hasCPlusPlus)
    {
        this.hasCPlusPlus = hasCPlusPlus;
    }
    
    public void setHasXP(boolean hasXP)
    {
        this.hasXP = hasXP;
    }
    
    public void setHasVista(boolean hasVista)
    {
        this.hasVista = hasVista;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getCapacity()
    {
        return this.capacity;
    }
    
    public boolean hasNetBeans()
    {
        return this.hasNetBeans;
    }
    
    public boolean hasCPlusPlus()
    {
        return this.hasCPlusPlus;
    }
    
    public boolean hasXP()
    {
        return this.hasXP;
    }
    
    public boolean hasVista()
    {
        return this.hasVista;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public ArrayList<Reservation> getReservations()
    {
        return this.reservations;
    }
}
