/**
 * @name Dialog Manage Rooms
 * @desc Displays all rooms
 * @version August 19, 2011
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class DialogManageRooms extends JDialog
{
    private JTable roomsTable;//to show the Arraylist in a table
    private ArrayList<Room> rooms;//here we put all the rooms created
    
    /**
     * @desc Initializes the room management attributes
     */
    public DialogManageRooms(JFrame parent, ArrayList<Room> rooms)
    {
        // block the parent window upon showing this form
        super(parent, true);
        
        // initialize the rooms
        this.rooms = rooms;
        
        // set the title of the window
        this.setTitle("Room Management");
        
        // set the size of the window
        this.setSize(500, 300);
        
        // set the layout of the page
        this.setLayout(new BorderLayout());
        
        // show the window on the center
        this.setLocationRelativeTo(null);
        
        // add a toolbar
        JToolBar toolbar = new JToolBar();
        this.add(BorderLayout.PAGE_START, toolbar);
        
        // create a button for adding a new room
        JButton addANewRoomButton = new JButton("Add a New Room");
        addANewRoomButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { addNewRoomButtonClicked(); }});
        toolbar.add(addANewRoomButton);
        
        // create a delete for deleting a room
        JButton deleteRoomButton = new JButton("Delete Room");
        deleteRoomButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { deleteRoomClicked(); }});
        toolbar.add(deleteRoomButton);
                
        // create a table where the rooms are going to be displayed
        this.roomsTable = new JTable(new DefaultTableModel(new Object[0][0], new String[] { "Name", "Capacity", "Netbeans", "C++", "XP", "Vista" }));
        
        // create a scroll pane where to put the table
        JScrollPane roomsTableScrollPane = new JScrollPane(this.roomsTable);
        this.getContentPane().add(BorderLayout.CENTER, roomsTableScrollPane);
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
                hasNetBeans = "yes";//Zibon Faysal
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
     * @desc shows the window for adding a new room
     */
    private void addNewRoomButtonClicked()
    {
        DialogAddRoom dialog = new DialogAddRoom(this, this.rooms);
        dialog.setVisible(true);
        
        // refresh the table
        this.showAllRooms();
    }
    
    /**
     * @desc deletes the selected rooms
     */
    private void deleteRoomClicked()
    {
        if(this.roomsTable.getSelectedRows().length == 0)
        {
            JOptionPane.showMessageDialog(this, "Select the room you would like to delete.", "Notification", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // confirm for deletion
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected room?");
        
        // do not continue delete if no
        if(option == JOptionPane.NO_OPTION || option == JOptionPane.CANCEL_OPTION)
            return;
        
        // delete the room
        int[] indices = this.roomsTable.getSelectedRows();
        
        for(int i = indices.length - 1; i >= 0; i--)
        {
            // delete the room
            ((DefaultTableModel)this.roomsTable.getModel()).removeRow(indices[i]);
            this.rooms.remove(indices[i]);
        }
    }
}
