package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddVideoGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;

	public AddVideoGUI(Connection con, int prof_id) {
		JFrame frame = new JFrame("Add new Video");

		JPanel panel;
		JLabel message_label, description_label, privacy_label, album_label;
		JTextField message_text, description_text, add_album_text;
		JButton submit, back;

		submit = new JButton("SUBMIT");
		back = new JButton("BACK");
		message_label = new JLabel();
		message_label.setText("Message :");
		message_text = new JTextField();
		description_label = new JLabel();
		description_label.setText("Description :");
		description_text = new JTextField();
		privacy_label = new JLabel();
		add_album_text = new JTextField();

		privacy_label.setText("Privacy :");
		album_label = new JLabel("Add photo to album no. :");
		String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
		final JComboBox<String> privacy = new JComboBox<String>(choices);

		panel = new JPanel(new GridLayout(5, 2));

		panel.add(message_label);
		panel.add(message_text);
		panel.add(description_label);
		panel.add(description_text);
		panel.add(privacy_label);
		panel.add(privacy);
		panel.add(album_label);
		panel.add(add_album_text);
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
				ResultSet rs = null;

				String name = null, descri = null, sp;
				sp = "{call upsAddVideo(?,?,?,?,?)}";
				int priv = 0;
				if (message_text.getText().length() != 0)
					name = message_text.getText();
				if (description_text.getText().length() != 0)
					descri = description_text.getText();
				int length = (int) ((Math.random() * (9999 - 1)) + 1);
				priv = privacy.getSelectedIndex() + 1;
				boolean flag = false;

				try {
					rs = addVideo(prof_id, name, descri, length, priv, sp, con);
					if (rs != null) flag = true;
					if (add_album_text.getText().length() != 0) {
						sp = "{call upsInsertVideoToAlbum(?,?)}";
						rs.next();
						int id = rs.getInt("id");
						addToAlbum(Integer.parseInt(add_album_text.getText()), id,  sp, con);

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag)
					JOptionPane.showMessageDialog(null, "Video Added :)\n", "Job Done!",
						JOptionPane.INFORMATION_MESSAGE);
				else
				JOptionPane.showMessageDialog(null, "The video was not added :(\n", "Error",
						JOptionPane.ERROR_MESSAGE);

				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
		});
	}

	private ResultSet addVideo(int my_id, String name, String descri, int len, int priva, String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, name);
		cs.setString(3, descri);
		cs.setInt(4, len);
		cs.setInt(5, priva);

		ResultSet rs = cs.executeQuery(); 
		return (rs);
	}

	private boolean addToAlbum(int video_id, int alb_id, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);

		cs.setInt(1, video_id);
		cs.setInt(2, alb_id);

		cs.execute();

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
