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

public class EditBookmarkGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;
	ShowBookmarksGUI sbGUI;

	public EditBookmarkGUI(Connection con, int prof_id, int bookmark_id) throws FileNotFoundException {
		JFrame frame = new JFrame("Edit Bookmark");
		JPanel panel;

		JLabel link, name, caption, description, message, priv;
		link = new JLabel("Link: ");
		name = new JLabel("Name: ");
		caption = new JLabel("Caption: ");
		description = new JLabel("Description: ");
		message = new JLabel("Message: ");
		priv = new JLabel("Privacy: ");

		JTextField linkt, namet, captiont, descriptiont, messaget;
		linkt = new JTextField();
		namet = new JTextField();
		captiont = new JTextField();
		descriptiont = new JTextField();
		messaget = new JTextField();

		JButton back = new JButton();
		JButton save = new JButton();

		int pri = 0;
		String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
		final JComboBox<String> privacy = new JComboBox<String>(choices);


		
		ResultSet rs = null;
		String sp = "{call upsShowBookmark(?,?)}";

		try {
			rs = retrieveBookmark(prof_id, bookmark_id, sp, con);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs.next();
			linkt.setText(rs.getString("link"));
			namet.setText(rs.getString("name"));
			captiont.setText(rs.getString("caption"));
			descriptiont.setText(rs.getString("description"));
			messaget.setText(rs.getString("message"));
			pri = rs.getInt("privacy") - 1;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		panel = new JPanel(new GridLayout(7, 2));

		back.setText("BACK");
		save.setText("SAVE CHANGES");

		panel.add(link);
		panel.add(linkt);
		panel.add(name);
		panel.add(namet);
		panel.add(caption);
		panel.add(captiont);
		panel.add(description);
		panel.add(descriptiont);
		panel.add(message);
		panel.add(messaget);
		
		privacy.setSelectedIndex(pri);
		panel.add(priv);
		panel.add(privacy);

		panel.add(back);
		panel.add(save);

		panel.setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Show Bookmark Data");

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
					sbGUI = new ShowBookmarksGUI(con, prof_id);
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
				String link = null, name = null, caption = null,
						description = null, message = null, sp;
				
				sp = "{call upsEditBookmark(?,?,?,?,?,?,?,?)}";

				int priv;

				boolean flag = false;

				if (linkt.getText().length() != 0) link = linkt.getText();
				if (namet.getText().length() != 0) name = namet.getText();
				if (captiont.getText().length() != 0) caption = captiont.getText();
				if (descriptiont.getText().length() != 0) description = descriptiont.getText();
				if (messaget.getText().length() != 0) message = messaget.getText();
				
				
				priv = privacy.getSelectedIndex() + 1;

				try {
					flag = editBookmark(bookmark_id, prof_id, link, name, caption, description, message, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag)
					JOptionPane.showMessageDialog(null, "Bookmark data changed :)\n", "Job Done!",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The bookmark data was not changed :(\n", "Error",
							JOptionPane.ERROR_MESSAGE);

				frame.hide();

				try {
					sbGUI = new ShowBookmarksGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

	}

	private ResultSet retrieveBookmark(int prof_id, int bookmark_id, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setInt(2, bookmark_id);
		ResultSet rs = cs.executeQuery();

		return rs;
	}

	private boolean editBookmark(int bookmark_id, int prof_id, String link, String name, String caption, 
			String description, String message, int priv, String sp,
			Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, bookmark_id);
		cs.setInt(2, prof_id);
		cs.setString(3, link);
		cs.setString(4, name);
		cs.setString(5, caption);
		cs.setString(6, description);
		cs.setString(7, message);
		cs.setInt(8, priv);

		cs.execute();

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}
}
