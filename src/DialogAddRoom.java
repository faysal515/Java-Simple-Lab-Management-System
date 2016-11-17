/**
 * @name DialogAddRoom.java
 * @desc Window for adding a new room
 * @version August 19, 2011
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DialogAddRoom extends JDialog
{
    private ArrayList<Room> rooms;//here all the rooms are stored
    private JTextField roomNameTextField;
    private JTextField capacityTextField;
    private JComboBox osComboBox;
    private JComboBox softwareComboBox;
    
    /**
     * @desc initializes the dialog properties
     */
    public DialogAddRoom(JDialog parent, ArrayList<Room> rooms)
    {
        // block the parent frame when this is shown
        super(parent, true);
        
        // initialize the rooms
        this.rooms = rooms;
        
        // set the title
        this.setTitle("Create a new room");
        
        // set the size of the filter window
        this.setSize(290, 150);
        
        // set the layout as grid
        this.setLayout(new GridLayout(5, 2));
        
        // show the window in the center
        this.setLocationRelativeTo(null);
        
        JLabel nameLabel = new JLabel("Room Name ", JLabel.RIGHT);
        this.add(nameLabel);
        
        this.roomNameTextField = new JTextField();
        this.add(roomNameTextField);
        
        // set the controls for filtering rooms
        JLabel capacityLabel = new JLabel("Capacity (empty if optional)", JLabel.RIGHT);
        this.add(capacityLabel);
        
        this.capacityTextField = new JTextField();
        this.add(capacityTextField);
        
        JLabel osLabel = new JLabel("Operating System ", JLabel.RIGHT);
        this.add(osLabel);//Adding the label to the JDialog
        
        this.osComboBox = new JComboBox(new String[] { "Both", "XP", "Vista" });
        this.add(osComboBox);
        
        JLabel softwareLabel = new JLabel("Software ", JLabel.RIGHT);
        this.add(softwareLabel);
        
        this.softwareComboBox = new JComboBox(new String[] {"Both", "Netbeans", "C++" });
        this.add(softwareComboBox);
        
        this.add(new JPanel());
        
        // create the button for saving
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { saveButtonClicked(); }});
        this.add(saveButton);//added to the JDialog
    }
    
    /**
     * @desc validates the inputs before saving the values into the rooms
     */
    private void saveButtonClicked()
    {//first take all the inputs 
        String roomName = this.roomNameTextField.getText().trim();
        String capacityTemp = this.capacityTextField.getText().trim();
        String os = this.osComboBox.getSelectedItem().toString();
        String softwares = this.softwareComboBox.getSelectedItem().toString();
        
        if(roomName.equals("") || capacityTemp.equals(""))//When user enters nothing
        {
            JOptionPane.showMessageDialog(this, "Provide a room name and capacity.", "Notification", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            // check if the room name is unique
            boolean nameValid = true;
            
            for(int i = 0; i < this.rooms.size(); i++)//searches the entire roomslist
            {
                if(this.rooms.get(i).getName().equalsIgnoreCase(roomName))
                    //equalsIgnoreCase() checks for matching room name 
                //even if they are of different cases(boro hater ba choto hater)
                {
                    nameValid = false;//Changes nammevalid to false to Show the user That this room name  
                    //is already inside rooms(ArrayList)
                    break;
                }
            }
            
            if(nameValid == false)
            {
                JOptionPane.showMessageDialog(this, "The room name is already being used by another room.", "Notification", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // validate if the capacity is a number
                try
                {
                    int capacity = Integer.parseInt(capacityTemp);//converting the capacity back to an int
                    
                    // everything is valid create a room
                    Room room = new Room(capacity, roomName);
                    
                    if(os.equals("Both"))
                    {
                        room.setHasVista(true);
                        room.setHasXP(true);
                    }
                    else if(os.equals("XP"))
                    {
                        room.setHasXP(true);
                    }
                    else if(os.equals("Vista"))
                    {
                        room.setHasVista(true);
                    }
                    
                    if(softwares.equals("Both"))
                    {
                        room.setHasCPlusPlus(true);
                        room.setHasNetBeans(true);
                    }
                    else if(softwares.equals("C++"))
                    {
                        room.setHasCPlusPlus(true);
                    }
                    else if(softwares.equals("Netbeans"))
                    {
                        room.setHasNetBeans(true);
                    }
                    
                    this.rooms.add(room);//add the new room to the list 
                    //message confirmation of being saved
                    JOptionPane.showMessageDialog(this, "The room has been successfully added.", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    
                    this.setVisible(false);//hides the JDialog
                }
                catch(Exception error)
                {//msg shown if alplabet is entered instead of a int
                    JOptionPane.showMessageDialog(this, "Capacity should be a number.", "Notification", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
