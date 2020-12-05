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

public class EditAlbumGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;
	ShowAlbumsGUI saGUI;
	
	public EditAlbumGUI(Connection con, int prof_id, int album_id) throws FileNotFoundException {
		JFrame frame = new JFrame("Edit Album");
		JPanel panel;
		JLabel  name, desc, lc, priv, photo, photot;
		JButton back = new JButton();
		JButton save = new JButton();
		JTextField namet, desct;
		
		int loc = 0, pri = 0;
		
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


    
		panel = new JPanel(new GridLayout(6, 2));

		name = new JLabel();
		namet = new JTextField();
		desc = new JLabel();
		desct = new JTextField();
		lc = new JLabel();	
		priv = new JLabel();
		photo = new JLabel("Photos in album: ");
		photot = new JLabel();

		back.setText("BACK");
		
		name.setText("Name: ");
		desc.setText("Description Name: ");
		lc.setText("Location: ");
		priv.setText("Privacy: ");

		
        ResultSet rs = null;
        String sp = "{call upsShowAlbum(?,?)}";
		
		try {
			rs = retrieveAlbum(prof_id, album_id, sp, con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.next();
			namet.setText(rs.getString("name"));
			desct.setText(rs.getString("description"));
			pri = rs.getInt("privacy") - 1;
			loc = rs.getInt("location") - 1;
			int count = rs.getInt("count");
			photot.setText(count + "");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		back.setText("BACK");
		save.setText("SAVE CHANGES");
		
		panel.add(name);
		panel.add(namet);
		panel.add(desc);
		panel.add(desct);

		location.setSelectedIndex(loc);
		panel.add(lc);
		panel.add(location);
		
		privacy.setSelectedIndex(pri);
		panel.add(priv);
		panel.add(privacy);
		panel.add(photo);
		panel.add(photot);

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
				try {
					new ShowAlbumsGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String n = null, desc = null, sp;
				int loc, priv;
				boolean flag = false;
				
				sp = "{call upsEditAlbum(?,?,?,?,?,?)}";
				
				if (namet.getText().length() != 0) n = namet.getText();
				if (desct.getText().length() != 0) desc = desct.getText();
				
				loc = location.getSelectedIndex() + 1;
				priv = privacy.getSelectedIndex() + 1;
			
				try {
					flag = editAlbum(album_id, prof_id, n, desc, loc, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Album data changed :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The album data was not changed :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				
				try {
					saGUI = new ShowAlbumsGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}

		});
		
		
	}

	

	private ResultSet retrieveAlbum(int prof_id, int album_id, String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setInt(2, album_id);
		ResultSet rs = cs.executeQuery();
		
		return rs;
	}


	private boolean editAlbum(int album_id, int prof_id, String name, String desc, int loc, int priv,
			String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, album_id);
		cs.setInt(2, prof_id);
		cs.setString(3, name);
		cs.setString(4, desc);
		cs.setInt(5, loc);
		cs.setInt(6, priv);
		
		cs.execute();
		
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
