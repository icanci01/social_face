package GUI;

import javax.swing.*;
import java.awt.*;
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

public class AddAlbumGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;


	public AddAlbumGUI(Connection con, int prof_id) throws FileNotFoundException {
		
		JFrame frame = new JFrame("Add new Album");

		JPanel panel;
		JLabel name_label, description_label, location_label,privacy_label;
		JTextField album_name_text, album_description_text, album_location_text;
		JButton submit, back;
		
		submit = new JButton("SUBMIT");
		back = new JButton("BACK");
		name_label = new JLabel();
		name_label.setText("Album Name :");
		album_name_text = new JTextField();
		description_label = new JLabel();
		description_label.setText("Description :");
		album_description_text = new JTextField();
		location_label = new JLabel();
		location_label.setText("Location :");
		privacy_label = new JLabel();
		privacy_label.setText("Privacy :");
		String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
		final JComboBox<String> privacy = new JComboBox<String>(choices);
		

				
		Scanner inFile1 = new Scanner(new File("src\\CITY.txt")).useDelimiter(",\\s*");
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
        
		panel = new JPanel(new GridLayout(5, 2));

		panel.add(name_label);
		panel.add(album_name_text);
		panel.add(description_label);
		panel.add(album_description_text);
		panel.add(location_label);
		panel.add(location);
		panel.add(privacy_label);
		panel.add(privacy);
		panel.add(submit);
		panel.add(back);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Add Album");
	
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
				String name, descri, link, sp;
				int r = (int)(Math.random() * (9999 - 1000) + 1000);
				int loc = 0, priv = 0;
				sp = "{call upsAddAlbum(?,?,?,?,?,?)}";
				if (album_name_text.getText().length() == 0) name = null;
				else name = album_name_text.getText();
				if (album_description_text.getText().length() == 0) descri = null;
				else descri = album_description_text.getText();
				loc = location.getSelectedIndex() + 1;
				link = "www.album" + prof_id +  "" + r +".com";
				priv = privacy.getSelectedIndex() + 1;
				boolean flag = false;
				
				try {
					flag = addAlbum(prof_id, name, descri, loc, link, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Album Added :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The album was not added :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
			
		});
	}
	

	private boolean addAlbum(int my_id, String name, 
			String descri, int loc, String link, int priva, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, name);
		cs.setString(3, descri);
		cs.setInt(4, loc);
		cs.setString(5, link);
		cs.setInt(6, priva);

		cs.executeQuery();

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
