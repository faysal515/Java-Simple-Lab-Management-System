/**
 * @name DialogReservedRooms.java
 * @desc shows a window showing all the reserved rooms
 * @version August 19, 2011
 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DialogReservedRooms extends JDialog
{
    private JTable roomsTable;
    private ArrayList<Room> rooms;
    
    /**
     * @desc initializes the window
     */
    public DialogReservedRooms(JFrame parent, ArrayList<Room> rooms)
    {
        // block the parent window when this form is shown
        super(parent, true);
        
        // set the title of the window
        this.setTitle("Reserved Rooms");
        
        // set the size to be 500 by 300
        this.setSize(500, 300);
        
        // set the dialog to show at center
        this.setLocationRelativeTo(null);
        
        // set the layout to be a border layout
        this.setLayout(new BorderLayout());
        
        // create a menu bar to be placed on the top
        JToolBar toolbar = new JToolBar();
        this.getContentPane().add(BorderLayout.PAGE_START, toolbar);
        
        // create a button for removing reserved rooms
        JButton unBookButton = new JButton("Unbook Room");
        unBookButton.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e) { unbookButtonClick(); }});
        toolbar.add(unBookButton);
        
        // create a button that would show all rooms
        JButton showAllButton = new JButton("Show all Rooms");
        showAllButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { showAllRooms(); }});
        toolbar.addSeparator();
        toolbar.add(showAllButton);
        
        // create a button for filtering reserved rooms
        JButton filterButton = new JButton("Filter Rooms");
        filterButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { filterReservedRooms(); } });
        toolbar.add(filterButton);
        toolbar.addSeparator();
        
        JButton reserveARoom = new JButton("Book a Room");
        reserveARoom.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { reserveARoom(); }});
        toolbar.add(reserveARoom);
        
        // initialize the rooms
        this.rooms = rooms;
        
        // create a table where to display all the reserved rooms
        roomsTable = new JTable(new DefaultTableModel(new Object[0][0], new String[] { "Room Name", "Time Reserved", "Date Reserved", "Faculty Name", "Subject" }));
        
        // create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        this.add(BorderLayout.CENTER, scrollPane);
        
        this.showAllRooms();
    }
    
    /**
     * @desc removes the selected booked room
     */
    private void unbookButtonClick()
    {
        // check if there are selected rows
        if(this.roomsTable.getSelectedRows().length == 0)
        {
            JOptionPane.showMessageDialog(this, "Select  a reserved room that you want to unbook.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // confirm for unbooking
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to unbook this room?");
        
        if(option == JOptionPane.NO_OPTION || option == JOptionPane.CANCEL_OPTION)
            return;
        //row length is reduced by i (mane jei koyta row select hobe sei kowekta delete hobe)
        for(int i = this.roomsTable.getSelectedRows().length - 1; i >= 0; i--)
        {
            int index = this.roomsTable.getSelectedRows()[i];
            
            // get the name of the room, the date and the time
            String roomName = this.roomsTable.getModel().getValueAt(index, 0).toString();
            String timeReserved = this.roomsTable.getModel().getValueAt(index, 1).toString();
            String dateReserved = this.roomsTable.getModel().getValueAt(index, 2).toString();
            
            // find the room in the rooms
            for(int j = 0; j < this.rooms.size(); j++)
            {
                Room room = this.rooms.get(j);
                
                if(room.getName().equals(roomName))
                {
                    // now that we found the room, delete the reservation inside it
                    for(int k = 0; k < room.getReservations().size(); k++)
                    {
                        Reservation reservation = room.getReservations().get(k);
                        
                        if(reservation.getTime().equals(timeReserved) && reservation.getDate().equals(dateReserved))
                        {
                            // we found the reservation to delete so we remove it
                            room.getReservations().remove(k);
                            break;
                        }
                    }
                    
                    break;
                }
            }
            
            // we remove the row now from the jtable
            ((DefaultTableModel)this.roomsTable.getModel()).removeRow(index);
        }
    }
    
    /**
     * @desc displays all the rooms
     */
    private void showAllRooms()
    {
        // remove all items from the table
        while(this.roomsTable.getRowCount() > 0)
            ((DefaultTableModel)this.roomsTable.getModel()).removeRow(0);
        
        // display all the reservation dates in the table
        for(int i = 0; i < rooms.size(); i++)
        {
            Room room = rooms.get(i);
            
            // for each room get the reservations
            for(int j = 0; j < room.getReservations().size(); j++)
            {
                Reservation reservation = room.getReservations().get(j);
                
                // for each reservation display the details
                ((DefaultTableModel)roomsTable.getModel()).addRow(new String[] { room.getName(), reservation.getTime(), reservation.getDate(), reservation.getFacultyName(), reservation.getSubject() });
            }
        }
    }
    
    /**
     * @desc shows a window for filtering
     */
    private void filterReservedRooms()
    {
        // show the window for filtering
        DialogFilterReserved dialog = new DialogFilterReserved(this);
        dialog.setVisible(true);
        
        // check if the filter button was clicked
        if(dialog.filterStatus == 1)
        {
            String date = dialog.monthComboBox.getSelectedItem() + "-" + dialog.dayComboBox.getSelectedItem() + "-" + dialog.yearComboBox.getSelectedItem();
            String time = dialog.timeComboBox.getSelectedItem().toString();
            
            // start filtering, clear first the content of the table
            while(this.roomsTable.getRowCount() > 0)
                ((DefaultTableModel)this.roomsTable.getModel()).removeRow(0);
            
            // display all the reservation dates in the table
            for(int i = 0; i < rooms.size(); i++)
            {
                Room room = rooms.get(i);

                // for each room get the reservations
                for(int j = 0; j < room.getReservations().size(); j++)
                {
                    Reservation reservation = room.getReservations().get(j);
                    
                    if(time.equals("Any") && reservation.getDate().equals(date))
                        // for each reservation display the details
                        ((DefaultTableModel)roomsTable.getModel()).addRow(new String[] { room.getName(), reservation.getTime(), reservation.getDate(), reservation.getFacultyName(), reservation.getSubject() });
                    else if(reservation.getTime().equals(time) && reservation.getDate().equals(date))
                        // for each reservation display the details
                        ((DefaultTableModel)roomsTable.getModel()).addRow(new String[] { room.getName(), reservation.getTime(), reservation.getDate(), reservation.getFacultyName(), reservation.getSubject() });
                }
            }
        }
    }
    
    /**
     * @desc shows the reserve a room window
     */
    private void reserveARoom()
    {
        DialogBookARoom dialog = new DialogBookARoom(null, this.rooms);
        dialog.setVisible(true);
        
        // refresh the rooms to check for added new rooms
        this.showAllRooms();
    }
}
