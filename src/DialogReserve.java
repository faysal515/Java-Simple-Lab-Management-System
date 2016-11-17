/**
 * @name DialogReserve.java
 * @desc window for finally reserving
 * @date August 19, 2011
 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DialogReserve extends JDialog
{
    private JTextField nameTextField;
    private JTextField subjectTextField;
    private JComboBox timeComboBox;
    private JComboBox dayComboBox;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;
    private JPanel datePanel;
    
    private String roomName;
    private ArrayList<Room> rooms;
    
    /**
     * @desc initializes the defaults of the dialog
     * @param parent Pertains to the book a room dialog
     * @param rooms available rooms
     * @param roomName room to reserve
     */
    public DialogReserve(JDialog parent, ArrayList<Room> rooms, String roomName)
    {
        // block the parent window when this shows
        super(parent, true);
        
        // initialize the global properties
        this.roomName = roomName;
        this.rooms = rooms;
        
        // set the title of the window
        this.setTitle("Reserving Room " + roomName);
        
        // set the size of the window to be 300, by 150 in size
        this.setSize(300, 170);
        
        // set the layout as grid layout
        this.setLayout(new GridLayout(5, 2));
        
        // set the labels to be used
        JLabel nameLabel = new JLabel("Faculty Name ", JLabel.RIGHT);
        this.add(nameLabel);
        
        nameTextField = new JTextField();
        this.add(nameTextField);
        
        JLabel subjectLabel = new JLabel("Subject ", JLabel.RIGHT);
        this.add(subjectLabel);
        
        subjectTextField = new JTextField();
        this.add(subjectTextField);
        
        JLabel timeLabel = new JLabel("Time ", JLabel.RIGHT);
        this.add(timeLabel);
        
        this.timeComboBox = new JComboBox(new String[] { "8:00 AM to 9:20 AM", "9:40 AM to 10:50 AM", "11:10 AM to 12.50 PM", "1:10 PM to 2:20 PM", "2:40 PM to 4:10 PM" });
        this.add(timeComboBox);
        
        JPanel dateLabelPanel = new JPanel();
        this.add(dateLabelPanel);
        
        JLabel dateLabel = new JLabel("Date (mm/dd/yyyy)", JLabel.RIGHT);
        dateLabelPanel.add(dateLabel);
        
        datePanel = new JPanel();
        this.add(datePanel);
        
        // get the current day, month, and year
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        
        // compute for the months
        String[] months = new String[12 - (month - 1)];//how many strings will be created is determined
        
        for(int i = 0; i < months.length; i++, month++)
        {
            if(month < 10)
                months[i] = "0" + month;//just to display 0+i (07,08 etc )
            else
                months[i] = "" + month;
        }
        
        this.monthComboBox = new JComboBox(months);
        datePanel.add(monthComboBox);
        
        // compute the days for the month
        String[] days = new String[31 - (day - 1)];
        
        for(int i = 0; i < days.length; i++, day++)
        {
            if(day < 10)
                days[i] = "0" + day;//just to display 0+i (07,08 etc )
            else
                days[i] = "" + day;
        }
        
        this.dayComboBox = new JComboBox(days);
        datePanel.add(dayComboBox);
        
        // compute for the year
        String[] years = new String[] { year + "", (year + 1) + "" };//creating 2 years automatically
        
        this.yearComboBox = new JComboBox(years);
        datePanel.add(yearComboBox);
        
        // add an extra space to the grid to align the filter button properly
        this.add(new JPanel());
        
        // create the filter button
        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { reserveButtonClick(); }});
        this.add(reserveButton);
        
        // show the screen on the center
        this.setLocationRelativeTo(null);
    }
    
    /**
     * @desc adds a reservation
     */
    private void reserveButtonClick()
    {
        // validate the fields
        String facultyName = this.nameTextField.getText().trim();
        String subject = this.subjectTextField.getText().trim();
        String time = this.timeComboBox.getSelectedItem().toString();
        String date = this.monthComboBox.getSelectedItem() + "-" + this.dayComboBox.getSelectedItem() + "-" + this.yearComboBox.getSelectedItem();
        
        // validate if the faculty name and subject is provided
        if(facultyName.equals("") || subject.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Provide a faculy name and subject", "Notification", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // look for the room in the rooms given the room name
        Room room = null;
        
        for(int i = 0; i < this.rooms.size(); i++)
        {
            room = this.rooms.get(i);
            
            if(room.getName().equals(this.roomName))
                // we found the room so we break out of the loop
                break;
        }
        
        // validate if the given time and date is free
        boolean taken = false;

        // check if the room is available at 8 at the given date
        ArrayList<Reservation> reservations = room.getReservations();

        for(int j = 0; j < reservations.size(); j++)
        {
            Reservation reservation = reservations.get(j);

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
        {
            JOptionPane.showMessageDialog(this, "The time and date you have chosen has been reserved by someone else.", "Notification", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "The room has been successfully reserved on the given date and time.", "Notification", JOptionPane.INFORMATION_MESSAGE);
            //finally the room is created.
            room.addReservation(new Reservation(facultyName, subject, time, date));
            
            this.setVisible(false);
        }
    }
}
