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

public class EditPhotoGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;
	ShowPhotoGUI spGUI;

	public EditPhotoGUI(Connection con, int prof_id, int photo_id) throws FileNotFoundException {
		JFrame frame = new JFrame("Edit Photo");
		JPanel panel;
		
		String startD;
		
		JLabel  dir, hght, wdt, link, priv;
		JLabel hghtt, wdtt, linkt;
		JTextField dirt;
		
		JButton back = new JButton();
		JButton save = new JButton();
		
		int  pri = 0;
		
		String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
		final JComboBox<String> privacy = new JComboBox<String>(choices);
    
		panel = new JPanel(new GridLayout(6, 2));

		dir = new JLabel("Directory: ");
		dirt = new JTextField();
		hght = new JLabel("Height: ");
		wdt = new JLabel("Width: ");
		link = new JLabel("Link: ");
		priv = new JLabel("Privacy: ");
		
		hghtt = new JLabel();
		wdtt = new JLabel();
		linkt = new JLabel();
		
		
        ResultSet rs = null;
        String sp = "{call upsShowPhoto(?,?)}";
		
		try {
			rs = retrievePhoto(prof_id, photo_id, sp, con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.next();
			dirt.setText(rs.getString("directory"));
			hghtt.setText(rs.getString("height"));
			wdtt.setText(rs.getString("width"));
			linkt.setText(rs.getString("link"));
			
			pri = rs.getInt("privacy") - 1;;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		back.setText("BACK");
		save.setText("SAVE CHANGES");
		
		startD = dirt.getText();
		
		panel.add(dir);
		panel.add(dirt);
		panel.add(hght);
		panel.add(hghtt);
		
		panel.add(wdt);
		panel.add(wdtt);
		
		panel.add(link);
		panel.add(linkt);
		
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
				String dir = null, link = null, sp;
				sp = "{call upsEditPhoto(?,?,?,?,?,?)}";

				int hght, wdt, priv;
				
				boolean flag = false;
				
				
				if (dirt.getText().length() != 0) dir = dirt.getText();
				if (dir.equalsIgnoreCase(startD)) {
					hght = Integer.parseInt(hghtt.getText());
					wdt = Integer.parseInt(wdtt.getText());
				}
				else {
					hght = (int)((Math.random() * (1080 - 240) + 240));
					wdt = (int)((Math.random() * (1080 - 240) + 240));
				}
				
				if (linkt.getText().length() != 0) link = linkt.getText();
				
				priv = privacy.getSelectedIndex() + 1;
			
				try {
					flag = editAlbum(photo_id, prof_id, dir, hght, wdt, priv, sp, con);
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
					spGUI = new ShowPhotoGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}

		});
		
		
	}

	

	private ResultSet retrievePhoto(int prof_id, int album_id, String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setInt(2, album_id);
		ResultSet rs = cs.executeQuery();
		
		return rs;
	}


	private boolean editAlbum(int photo_id, int prof_id, String dir, int hght, int wdt, int priv,
			String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, photo_id);
		cs.setInt(2, prof_id);
		cs.setString(3, dir);
		cs.setInt(4, hght);
		cs.setInt(5, wdt);
		cs.setInt(6, priv);
		
		cs.execute();
		
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
