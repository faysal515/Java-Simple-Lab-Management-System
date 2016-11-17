/**
 * @name DialogFilter.java
 * @desc A dialog that sets the filter settings
 * @version August 18, 2011
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DialogFilter extends JDialog
{
    public JTextField capacityTextField;
    public JComboBox osComboBox;
    public JComboBox softwareComboBox;
    public JComboBox timeComboBox;
    public JComboBox dayComboBox;
    public JComboBox monthComboBox;
    public JComboBox yearComboBox;
    public JCheckBox checkBox;
    public JPanel datePanel;
    public int filterStatus = 0;
    
    /**
     * @desc Initializes the dialog filter
     * @param parent frame
     */
    public DialogFilter(JDialog parent)
    {
        // set the parent window to be blocked when the filter window shows
        super(parent, true);
        
        // set the size of the filter window
        this.setSize(290, 200);
        
        // set the title of the window
        this.setTitle("Filter Rooms");
        
        // set the layout as grid
        this.setLayout(new GridLayout(6, 2));
        
        // show the window in the center
        this.setLocationRelativeTo(null);
        
        // set the controls for filtering rooms
        JLabel capacityLabel = new JLabel("Capacity (empty if optional)", JLabel.RIGHT);
        this.add(capacityLabel);
        
        this.capacityTextField = new JTextField();
        this.add(capacityTextField);
        
        JLabel osLabel = new JLabel("Operating System ", JLabel.RIGHT);
        this.add(osLabel);
        
        this.osComboBox = new JComboBox(new String[] { "Any", "XP", "Vista" });
        this.add(osComboBox);
        
        JLabel softwareLabel = new JLabel("Software ", JLabel.RIGHT);
        this.add(softwareLabel);
        
        this.softwareComboBox = new JComboBox(new String[] {"Any", "Netbeans", "C++" });
        this.add(softwareComboBox);
        
        JLabel timeLabel = new JLabel("Time ", JLabel.RIGHT);
        this.add(timeLabel);
        
        this.timeComboBox = new JComboBox(new String[] { "Any", "8:00 AM to 9:20 AM", "9:40 AM to 10:50 AM", "11:10 AM to 12.50 PM", "1:10 PM to 2:20 PM", "2:40 PM to 4:10 PM" });
        this.add(timeComboBox);
        
        JPanel dateLabelPanel = new JPanel();
        this.add(dateLabelPanel);
        
        JLabel dateLabel = new JLabel("Date (mm/dd/yyyy)", JLabel.RIGHT);
        dateLabelPanel.add(dateLabel);
        
        // create a check box to ignore date or not
        checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { checkBoxClicked(); }});
        checkBox.setSelected(true);
        dateLabelPanel.add(checkBox);
        
        datePanel = new JPanel();
        this.add(datePanel);
        
        // get the current day, month, and year
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        
        // compute for the months
        String[] months = new String[12 - (month - 1)];
        
        for(int i = 0; i < months.length; i++, month++)
        {
            if(month < 10)
                months[i] = "0" + month;
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
                days[i] = "0" + day;
            else
                days[i] = "" + day;
        }
        
        this.dayComboBox = new JComboBox(days);
        datePanel.add(dayComboBox);
        
        // compute for the year
        String[] years = new String[] { year + "", (year + 1) + "" };
        
        this.yearComboBox = new JComboBox(years);
        datePanel.add(yearComboBox);
        
        // add an extra space to the grid to align the filter button properly
        this.add(new JPanel());
        
        // create the filter button
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { filterButtonClicked(); }});
        this.add(filterButton);
    }
    
    /**
     * @desc enables or disables the functionality of the date
     */
    private void checkBoxClicked()
    {
        if(this.checkBox.isSelected())
        {
            // disable the date
            this.monthComboBox.setEnabled(true);
            this.dayComboBox.setEnabled(true);
            this.yearComboBox.setEnabled(true);
            this.timeComboBox.setEnabled(true);
            this.checkBox.setSelected(true);
        }
        else
        {
            // enable the date
            this.monthComboBox.setEnabled(false);
            this.dayComboBox.setEnabled(false);
            this.yearComboBox.setEnabled(false);
            this.timeComboBox.setEnabled(false);
            this.checkBox.setSelected(false);
        }
    }
    
    /**
     * @desc closes the filter dialog
     */
    private void filterButtonClicked()
    {
        // check first if the entered capacity is a number
        try
        {
            if(!this.capacityTextField.getText().equals(""))
            {
                // check if the entered value is a number
                Integer.parseInt(this.capacityTextField.getText());
            }
            
            // identify that the filter button was clicked
            this.filterStatus = 1;

            this.setVisible(false);
        }
        catch(Exception error)
        {
            // error goes here if entered value is not a number
            JOptionPane.showMessageDialog(this, "The capacity should be a number.", "Notification", JOptionPane.ERROR_MESSAGE);
        }
    }
}
