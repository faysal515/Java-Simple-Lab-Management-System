/**
 * @name DialogFilterReserved.java
 * @desc A dialog that sets the filter settings of reserved rooms
 * @version August 19, 2011
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DialogFilterReserved extends JDialog
{
    public JComboBox timeComboBox;
    public JComboBox dayComboBox;
    public JComboBox monthComboBox;
    public JComboBox yearComboBox;
    public JPanel datePanel;
    public int filterStatus = 0;
    
    /**
     * @desc Initializes the dialog filter
     * @param parent frame
     */
    public DialogFilterReserved(JDialog parent)
    {
        // set the parent window to be blocked when the filter window shows
        super(parent, true);
        
        // set the size of the filter window
        this.setSize(290, 115);
        
        // set the title of the window
        this.setTitle("Filter Reserved Rooms");
        
        // set the layout as grid
        this.setLayout(new GridLayout(3, 2));
        
        // show the window in the center
        this.setLocationRelativeTo(null);
        
        // set the controls for filtering rooms
        JLabel timeLabel = new JLabel("Time ", JLabel.RIGHT);
        this.add(timeLabel);
        
        this.timeComboBox = new JComboBox(new String[] { "Any", "8:00 AM to 9:20 AM", "9:40 AM to 10:50 AM", "11:10 AM to 12.50 PM", "1:10 PM to 2:20 PM", "2:40 PM to 4:10 PM" });
        this.add(timeComboBox);
        
        JPanel dateLabelPanel = new JPanel();
        this.add(dateLabelPanel);
        
        // get the current day, month, and year
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        
        JLabel dateLabel = new JLabel("Date (mm/dd/yyyy)", JLabel.RIGHT);
        dateLabelPanel.add(dateLabel);
        
        // compute for the months
        String[] months = new String[12];
        
        for(int i = 0; i < months.length; i++)
        {
            int m = i + 1;
            
            if(m < 10)
                months[i] = "0" + m;
            else
                months[i] = "" + m;
        }
        
        datePanel = new JPanel();
        this.monthComboBox = new JComboBox(months);
        this.monthComboBox.setSelectedIndex(month - 1);
        datePanel.add(monthComboBox);
        
        this.add(datePanel);
        
        // compute the days for the month
        String[] days = new String[31];
        
        for(int i = 0; i < days.length; i++)
        {
            int d = i + 1;
            
            if(d < 10)
                days[i] = "0" + d;
            else
                days[i] = "" + d;
        }
        
        this.dayComboBox = new JComboBox(days);
        this.dayComboBox.setSelectedIndex(day - 1);
        datePanel.add(dayComboBox);
        
        // compute for the year
        String[] years = new String[] { (year - 1) + "", year + "", (year + 1) + "" };
        
        this.yearComboBox = new JComboBox(years);
        this.yearComboBox.setSelectedIndex(1);
        datePanel.add(yearComboBox);
        
        // add an extra space to the grid to align the filter button properly
        this.add(new JPanel());
        
        // create the filter button
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { filterButtonClicked(); }});
        this.add(filterButton);
    }
    
    /**
     * @desc closes the filter dialog
     */
    private void filterButtonClicked()
    {
        // identify that the filter button was clicked
        this.filterStatus = 1;
        this.setVisible(false);
    }
}
