package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class EditEventGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;
	ShowEventGUI seGUI;

	public EditEventGUI(Connection con, int prof_id, int event_id) throws FileNotFoundException {
		JFrame frame = new JFrame("Edit Photo");
		JPanel panel;
		
		String startD;
		
		JLabel  name, des, start, end, loc, ven, priv;
		JTextField namet, dest, startt, endt;
		
		JButton back = new JButton();
		JButton save = new JButton();
		
		int  pri = 0, loca = 0, venu = 0;
		
		String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
		final JComboBox<String> privacy = new JComboBox<String>(choices);
		

        Scanner inFile1 = new Scanner(new File("CITY.txt")).useDelimiter(",\\s*");
        String token1 = "";
        ArrayList city = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
            token1 = inFile1.nextLine();
            city.add(token1);
        }

        String[] cit = new String[city.size()];
        for (int i = 0; i < city.size(); i++)
            cit[i] = city.get(i).toString();
        final JComboBox<String> location = new JComboBox<String>(cit);

        
       

        inFile1 = new Scanner(new File("LOCATION.txt")).useDelimiter(",\\s*");
        token1 = "";
        ArrayList locations = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
        	token1 = inFile1.nextLine();
        	String[] split = token1.split("\t");
        	String tok = split[0] + ", " + split[1] + ", " + split[2];
        	
            locations.add(tok);
        }
        
        String[] locArray = new String[locations.size()];
        for (int i = 0; i < locations.size(); i++)
            locArray[i] = locations.get(i).toString();
        final JComboBox<String> venue = new JComboBox<String>(locArray);
        
        
    
		panel = new JPanel(new GridLayout(8, 2));

		name = new JLabel("Name: ");
		des = new JLabel("Description: ");
		start = new JLabel("Start Time: ");
		end = new JLabel("End Time: ");
		loc = new JLabel("Location: ");
		ven = new JLabel("Venue: ");
		priv = new JLabel("Privacy: ");
		
		namet = new JTextField();
		dest = new JTextField();
		startt = new JTextField();
		endt = new JTextField();
		
		
		
		
        ResultSet rs = null;
        String sp = "{call upsShowEvent(?,?)}";
		
		try {
			rs = retrieveEvent(prof_id, event_id, sp, con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.next();
			namet.setText(rs.getString("name"));
			dest.setText(rs.getString("description"));
			startt.setText(rs.getString("start_time"));
			endt.setText(rs.getString("end_time"));
			loca = rs.getInt("location") - 1;
			venu = rs.getInt("venue") - 1;
			pri = rs.getInt("privacy") - 1;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		back.setText("BACK");
		save.setText("SAVE CHANGES");
				
		panel.add(name);
		panel.add(namet);
		panel.add(des);
		panel.add(dest);
		
		panel.add(start);
		panel.add(startt);
		
		panel.add(end);
		panel.add(endt);
		
		location.setSelectedIndex(loca);
		panel.add(loc);
		panel.add(location);
		
		venue.setSelectedIndex(venu);
		panel.add(ven);
		panel.add(venue);
		
		privacy.setSelectedIndex(pri);
		panel.add(priv);
		panel.add(privacy);

		panel.add(back);
		panel.add(save);
		

		panel.setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Show Profile Data");

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
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = null, desc = null, start = null, end = null, sp;
				sp = "{call upsEditEvent(?,?,?,?,?,?,?,?,?)}";

				int loc, ven, priv;
				
				boolean flag = false;
				
				
				if (namet.getText().length() != 0) name = namet.getText();
				if (dest.getText().length() != 0) desc = dest.getText();
				if (startt.getText().length() != 0) start = startt.getText();
				if (endt.getText().length() != 0) end = endt.getText();
				
				loc = location.getSelectedIndex() + 1;
				ven = venue.getSelectedIndex() + 1;
				priv = privacy.getSelectedIndex() + 1;
			
				try {
					flag = editEvent(event_id, prof_id, name, desc, start, end, loc, ven, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Event data changed :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The event data was not changed :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				
				try {
					seGUI = new ShowEventGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}

		});
		
		
	}

	

	private ResultSet retrieveEvent(int prof_id, int album_id, String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setInt(2, album_id);
		ResultSet rs = cs.executeQuery();
		
		return rs;
	}


	private boolean editEvent(int event_id, int prof_id, String name, String desc, String start, String end,
			int loc, int ven, int priv,	String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, event_id);
		cs.setInt(2, prof_id);
		cs.setString(3, name);
		cs.setString(4, desc);
		cs.setString(5, start);
		cs.setString(6, end);
		cs.setInt(7, loc);
		cs.setInt(8, ven);
		cs.setInt(9, priv);
		
		cs.execute();
		
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
