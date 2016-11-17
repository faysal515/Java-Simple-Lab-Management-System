/**
 * @name Dialog Book A room
 * @desc A tool where to book manually
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DialogBookARoom extends JDialog
{
    private JTable roomsTable;
    private ArrayList<Room> rooms;
    
    /**
     * @desc Initializes the items needed for booking a room
     * @param parent Main window
     */
    public DialogBookARoom(JFrame parent, ArrayList<Room> rooms)
    {
        // block the main window when this dialog shows
        super(parent, true);
        
        // set the title of the window
        this.setTitle("Reserve a Room");
        
        // set the size to be 500 by 300
        this.setSize(500, 300);
        
        // set the dialog to show at center
        this.setLocationRelativeTo(null);
        
        // set the layout to be a border layout
        this.setLayout(new BorderLayout());
        
        // create a menu bar to be placed on the top
        JToolBar toolbar = new JToolBar();
        this.getContentPane().add(BorderLayout.PAGE_START, toolbar);
        
        // create buttons for managing the rooms
        JButton reserveRoomButton = new JButton("Reserve Room");
        reserveRoomButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { reserveSelectedRoom(); }});
        toolbar.add(reserveRoomButton);
        toolbar.addSeparator();
        
        // create a button for showing all rooms
        JButton showAllRoomsButton = new JButton("Show All Rooms");
        showAllRoomsButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { showAllRooms(); }});
        toolbar.add(showAllRoomsButton);
        
        // create a button that would filter rooms
        JButton filterRoomsButton = new JButton("Filter Rooms");
        filterRoomsButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { filterRoomsButtonClick(); }});
        toolbar.add(filterRoomsButton);
        
        // create a table where the rooms are going to be displayed
        this.roomsTable = new JTable(new DefaultTableModel(new Object[0][0], new String[] { "Name", "Capacity", "Netbeans", "C++", "XP", "Vista" }));
        
        // create a scroll pane where to put the table
        JScrollPane roomsTableScrollPane = new JScrollPane(this.roomsTable);
        this.getContentPane().add(BorderLayout.CENTER, roomsTableScrollPane);
        
        // initialize the rooms
        this.rooms = rooms;
        this.showAllRooms();
    }
    
    /**
     * @desc displays all the rooms
     */
    private void showAllRooms()
    {
        // remove all items from the table
        while(this.roomsTable.getRowCount() > 0)
            ((DefaultTableModel)this.roomsTable.getModel()).removeRow(0);
        
        // display the unreserved rooms for a particular time into the table
        for(int i = 0; i < rooms.size(); i++)
        {
            Room room = rooms.get(i);
            
            String hasNetBeans = "no";
            String hasCPlusPlus = "no";
            String hasXP = "no";
            String hasVista = "no";
            
            // check the availability of the components in the room
            if(room.hasNetBeans())
                hasNetBeans = "yes";
            if(room.hasCPlusPlus())
                hasCPlusPlus = "yes";
            if(room.hasXP())
                hasXP = "yes";
            if(room.hasVista())
                hasVista = "yes";
            
            ((DefaultTableModel)this.roomsTable.getModel()).addRow(new String[] { room.getName(), room.getCapacity() + "", hasNetBeans, hasCPlusPlus, hasXP, hasVista });
        }
    }
    
    /**
     * @desc shows only rooms that are based on the specs
     */
    private void filterRoomsButtonClick()
    {
        // show the window for filtering
        DialogFilter dialog = new DialogFilter(this);
        dialog.setVisible(true);
        
        // check if the filter button was clicked
        if(dialog.filterStatus == 1)
        {
            // start filtering, clear first the content of the table
            while(this.roomsTable.getRowCount() > 0)
                ((DefaultTableModel)this.roomsTable.getModel()).removeRow(0);
            
            // copy all rooms as possible searched rooms
            ArrayList<Room> searchedRooms = (ArrayList<Room>)this.rooms.clone();
            
            // check if there is a specific number of capacity
            if(!dialog.capacityTextField.getText().equals(""))
            {
                int capacity = Integer.parseInt(dialog.capacityTextField.getText());
                
                // filter the searched rooms removing the capacity that does not fit
                for(int i = searchedRooms.size() - 1; i >= 0; i--)
                {
                    Room room = searchedRooms.get(i);
                    
                    if(room.getCapacity() != capacity&&room.getCapacity()<capacity)
                        // remove the room from the searched rooms
                        searchedRooms.remove(i);
                }
            }
            
            // check if there is a specific OS to choose from
            if(!dialog.osComboBox.getSelectedItem().toString().equals("Any"))
            {
                String os = dialog.osComboBox.getSelectedItem().toString();
                
                if(os.equals("XP"))
                {
                    // remove all vista rooms
                    for(int i = searchedRooms.size() - 1; i >= 0; i--)
                    {
                        Room room = searchedRooms.get(i);
                        
                        if(room.hasXP() == false)
                            searchedRooms.remove(i);
                    }
                }
                else if(os.equals("Vista"))
                {
                    // remove all xp rooms
                    for(int i = searchedRooms.size() - 1; i >= 0; i--)
                    {
                        Room room = searchedRooms.get(i);
                        
                        if(room.hasVista() == false)
                            searchedRooms.remove(i);
                    }
                }
            }
            
            // check if there is a specific software to choose from
            if(!dialog.softwareComboBox.getSelectedItem().toString().equals("Any"))
            {
                String software = dialog.softwareComboBox.getSelectedItem().toString();
                
                if(software.equals("C++"))
                {
                    // remove all netbeans rooms
                    for(int i = searchedRooms.size() - 1; i >= 0; i--)
                    {
                        Room room = searchedRooms.get(i);
                        
                        if(room.hasCPlusPlus() == false)
                            searchedRooms.remove(i);
                    }
                }
                else if(software.equals("Netbeans"))
                {
                    // remove all C++ rooms
                    for(int i = searchedRooms.size() - 1; i >= 0; i--)
                    {
                        Room room = searchedRooms.get(i);
                        
                        if(room.hasNetBeans() == false)
                            searchedRooms.remove(i);
                    }
                }
            }
            
            // check if for a specific time and date
            if(dialog.checkBox.isSelected() == true)
            {
                // get all rooms that are available on the specific date,
                // rooms that nobody is reserved
                for(int i = searchedRooms.size() - 1; i >= 0; i--)
                {
                    Room room = searchedRooms.get(i);
                    
                    String time = dialog.timeComboBox.getSelectedItem().toString();
                    String date = dialog.monthComboBox.getSelectedItem() + "-" + dialog.dayComboBox.getSelectedItem() + "-" + dialog.yearComboBox.getSelectedItem();
                    
                    if(time.equals("Any"))
                    {
                        // these are the available time for the selected date
                        boolean eightTaken = false;
                        boolean nineTaken = false;
                        boolean elevenTaken = false;
                        boolean oneTaken = false;
                        boolean twoTaken = false;

                        // check if the room has ANY avialable room on the selected date
                        // get the reservations of the room for validation
                        ArrayList<Reservation> reservations = room.getReservations();

                        for(int j = 0; j < reservations.size(); j++)
                        {
                            Reservation reservation = reservations.get(j);

                            // check if the reservation hits the same date
                            if(reservation.getDate().equals(date))
                            {
                                // check if the reservation from 8 to 12 if everything occupied
                                if(reservation.getTime().equals("8:00 AM to 9:20 AM"))
                                    eightTaken = true;
                                else if(reservation.getTime().equals("9:40 AM to 10:50 AM"))
                                    nineTaken = true;
                                else if(reservation.getTime().equals("11:10 AM to 12:50 PM"))
                                    elevenTaken = true;
                                else if(reservation.getTime().equals("1:10 AM to 2:20 PM"))
                                    oneTaken = true;
                                else if(reservation.getTime().equals("2:40 to 4:10 PM"))
                                    twoTaken = true;
                            }
                        }

                        // if all time are taken this room is invalid
                        if(eightTaken == true && nineTaken == true && elevenTaken == true && oneTaken == true && twoTaken == true)
                            searchedRooms.remove(i);
                    }
                    else
                    {
                        boolean taken = false;

                        // check if the room is available at 8 at the given date
                        ArrayList<Reservation> reservations = room.getReservations();

                        for(int j = 0; j < reservations.size(); j++)
                        {
                            Reservation reservation = reservations.get(j);
                            System.out.println(reservation.getDate());
                            System.out.println(reservation.getTime());

                            // check if the reservation hits the same date
                            if(reservation.getDate().equals(date))
                            {
                                // check if the reservation from 8 to 12 if everything occupied
                                if(reservation.getTime().equals(time))
                                {
                                    taken = true;
                                    break;
                                }
                            }
                        }

                        // if all time are taken this room is invalid
                        if(taken == true)
                            searchedRooms.remove(i);
                    }
                }
            }
            
            // display the final result of the searched rooms
            for(int i = 0; i < searchedRooms.size(); i++)
            {
                Room room = searchedRooms.get(i);
                
                String hasNetBeans = "no";
                String hasCPlusPlus = "no";
                String hasXP = "no";
                String hasVista = "no";

                // check the availability of the components in the room
                if(room.hasNetBeans())
                    hasNetBeans = "yes";
                if(room.hasCPlusPlus())
                    hasCPlusPlus = "yes";
                if(room.hasXP())
                    hasXP = "yes";
                if(room.hasVista())
                    hasVista = "yes";

                ((DefaultTableModel)this.roomsTable.getModel()).addRow(new String[] { room.getName(), room.getCapacity() + "", hasNetBeans, hasCPlusPlus, hasXP, hasVista });
            }
        }
    }
    
    /**
     * @desc gets the selected row and do a reserve
     */
    private void reserveSelectedRoom()
    {
        // check if there are rows selected
        if(this.roomsTable.getSelectedRows().length == 0)
        {
            JOptionPane.showMessageDialog(this, "Select a room to reserve", "Notification", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String roomName = this.roomsTable.getModel().getValueAt(this.roomsTable.getSelectedRow(), 0).toString();
        
        // find the room name in the list of rooms
        DialogReserve dialog = new DialogReserve(this, this.rooms, roomName);
        dialog.setVisible(true);
    }
}
