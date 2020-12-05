package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AddEventGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;

    public AddEventGUI(Connection con, int prof_id) throws FileNotFoundException {
        JFrame frame = new JFrame("Add new Event");

        JPanel panel;
        JLabel description_label,privacy_label,name_label,start_time_label,end_time_label,location_label,venue_label;
        JTextField description_text,name_text,start_time_text,end_time_text;
        JButton submit, back;

        submit = new JButton("SUBMIT");
        back = new JButton("BACK");

        name_label = new JLabel();
        name_label.setText("Name :");
        name_text = new JTextField();
        
        description_label = new JLabel();
        description_label.setText("Description :");
        description_text = new JTextField();

        start_time_label = new JLabel();
        start_time_label.setText("Start :");
        start_time_text = new JTextField();

        end_time_label = new JLabel();
        end_time_label.setText("End :");
        end_time_text = new JTextField();
        


        Scanner inFile1 = new Scanner(new File("LOCATION.txt")).useDelimiter(",\\s*");
        String token1 = "";
        ArrayList locations = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
        	token1 = inFile1.nextLine();
        	String[] split = token1.split("\t");
        	String tok = split[0] + ", " + split[1] + ", " + split[2];
        	
            locations.add(tok);
        }
        
        inFile1 = new Scanner(new File("CITY.txt")).useDelimiter(",\\s*");
        token1 = "";
        ArrayList city = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
            token1 = inFile1.nextLine();
            city.add(token1);
        }
        
        String[][] splits = new String[locations.size()][3];
        
        inFile1 = new Scanner(new File("LOCATION.txt")).useDelimiter(",\\s*");
        inFile1.nextLine();
        int k = 0;
        while (inFile1.hasNextLine()) {
            // find next line
        	token1 = inFile1.nextLine();
        	String[] split = token1.split("\t");
        	for (int j = 0; j < 3; j++)
        		splits[k][j] = split[j];
        	k++;
        }

        
        
        String[] loc = new String[locations.size()];
        String[] cit = new String[city.size()];
        
        for (int i = 0; i < locations.size(); i++)
        	loc[i] = locations.get(i).toString();
        	

        for (int i = 0; i < city.size(); i++)
        	cit[i] = city.get(i).toString();
        
        location_label = new JLabel();
        location_label.setText("Location :");
        final JComboBox<String> location = new JComboBox<String>(loc);


        venue_label = new JLabel();
        venue_label.setText("Venue :");
        final JComboBox<String> venue = new JComboBox<String>(cit);

        privacy_label = new JLabel();
        privacy_label.setText("Privacy :");
        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
        final JComboBox<String> privacy = new JComboBox<String>(choices);

        panel = new JPanel(new GridLayout(8, 2));



        panel.add(name_label);
        panel.add(name_text);
        
        panel.add(description_label);
        panel.add(description_text);
        
        panel.add(start_time_label);
        panel.add(start_time_text);
        
        panel.add(end_time_label);
        panel.add(end_time_text);
        
        panel.add(venue_label);
        panel.add(venue);
        
        panel.add(location_label);
        panel.add(location);

        panel.add(privacy_label);
        panel.add(privacy);

        panel.add(submit);
        panel.add(back);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Social Life - Add Bookmark");

        frame.setSize(480, 480);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.show();

        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.hide();
                aesGUI = new AddEditShowGUI(con, prof_id);

            }

        });
        
        submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = null, description = null, start = null,
						end = null, sp;
				sp = "{call upsAddEvent(?,?,?,?,?,?,?,?)}";
				int ven, priv, loc;
				boolean flag = false;
				
				if (name_text.getText().length() != 0) name = name_text.getText();
				if (description_text.getText().length() != 0) description = description_text.getText();
				if (start_time_text.getText().length() != 0) start = start_time_text.getText();
				if (end_time_text.getText().length() != 0) end = end_time_text.getText();
				
				loc = location.getSelectedIndex() + 1;
				ven = venue.getSelectedIndex() + 1;
				priv = privacy.getSelectedIndex() + 1;
				
				
				try {
					flag = addEvent(prof_id, name, description, start, end, loc, ven, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Event Added :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The event was not added :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
				
			
        	
        });
    }

	private boolean addEvent(int my_id, String name, String des, String start,
			String end, int loc, int ven, int priva, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, name);
		cs.setString(3, des);
		cs.setString(4, start);
		cs.setString(5, end);
		cs.setInt(6, loc);
		cs.setInt(7, ven);
		cs.setInt(8, priva);

		cs.executeQuery();

		return true;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
