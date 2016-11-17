/**
 * @name Main.java
 * @desc entry point of the program
 * @version August 18, 2011
*ID -1110113042, 1110114042
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Main extends JFrame implements WindowListener
{
    private ArrayList<Room> rooms;
    
    /**
     * @desc initializes the main window
     */
    public Main()
    {
        // set the title of the window
        super("    Lab Booking Management   ");
        
        // set the size of the window to 300 by 150
        this.setSize(300, 150);
        
        // set the frame as closeable when the "x" button has been clicked
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // show the screen on the center
        this.setLocationRelativeTo(null);
        
        // set the layout as grid layout where there are 3 rows and 0 columns
        this.setLayout(new GridLayout(3, 0));
        
        // create the button for booking a room
        JButton bookRoomButton = new JButton("Reserve A Room");
        bookRoomButton.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e) { bookRoomButtonClick(); }});
        this.getContentPane().add(bookRoomButton);
        
        // create a button for viewing the reserved rooms
        JButton viewReservedRoomsButton = new JButton("View Reserved Rooms");
        viewReservedRoomsButton.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e) { viewBookedRooms(); }});
        this.getContentPane().add(viewReservedRoomsButton);
    
        // createa button for managing rooms
        JButton manageRoomsButton = new JButton("Manage Rooms");
        manageRoomsButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { manageRooms(); }});
        this.getContentPane().add(manageRoomsButton);
        
        // add a window action listener
        this.addWindowListener(this);
    }
    
    /**
     * @desc shows the window for booking
     */
    private void bookRoomButtonClick()
    {
        DialogBookARoom dialog = new DialogBookARoom(this, this.rooms);
        dialog.setVisible(true);
    }
    
    /**
     * @desc displays a window showing all the booked rooms
     */
    private void viewBookedRooms()
    {
        DialogReservedRooms dialog = new DialogReservedRooms(this, this.rooms);
        dialog.setVisible(true);
    }
    
    /**
     * @desc shows the window for managing
     */
    private void manageRooms()
    {
        DialogManageRooms dialog = new DialogManageRooms(this, this.rooms);
        dialog.setVisible(true);
    }
    
    /**
     * @desc loads all rooms and data from a text file
     */
    @Override
    public void windowOpened(WindowEvent e) 
    {
        try
        {
            InputStream file = new FileInputStream("reservations.dat");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream ( buffer );
            
            this.rooms = (ArrayList<Room>)input.readObject();
            input.close();
        }
        catch(Exception error)
        {
            System.out.println("Initialized new values");
            
//            // when the file cannot be loaded, we load default values
            this.rooms = new ArrayList<Room>();
            
            // create 5 temporary rooms
            Room room1 = new Room(10, "Sac 501");
            room1.setHasCPlusPlus(true);
            room1.setHasNetBeans(true);
            room1.setHasXP(true);

                Room room2 = new Room(15, "Sac 502");
            room2.setHasNetBeans(true);
            room2.setHasXP(true);
            room2.setHasVista(true);

            Room room3 = new Room(20, "Sac 503");
            room3.setHasXP(true);
            room3.setHasCPlusPlus(true);

            Room room4 = new Room(15, "Sac 504");
            room4.setHasVista(true);
            room4.setHasNetBeans(true);

            Room room5 = new Room(10, "Sac 505");
            room5.setHasVista(true);
            room5.setHasCPlusPlus(true);

            this.rooms.add(room1);
            this.rooms.add(room2);
            this.rooms.add(room3);
            this.rooms.add(room4);
            this.rooms.add(room5);
        }
    }
    
    /**
     * @desc saves all rooms and data to a text file
     */
    @Override
    public void windowClosing(WindowEvent e) 
    {
        try
        {
            OutputStream file = new FileOutputStream("reservations.dat");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            
            // write the object into the file
            output.writeObject(this.rooms);
            output.close();
        }
        catch(Exception error)
        {
            error.printStackTrace();
        }
    }
    
    /**
     * @desc unused window events
     */
    @Override
    public void windowClosed(WindowEvent e)  { }

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    /**
     * @desc entry point of the program
     * @param args unused arguments
     */
    public static void main(String[] args)
    {
        // set the UI as for windows UI
        try
        {
           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        // set and start the Main window
        Main main = new Main();
        main.setVisible(true);
    }
}
