package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ShowResultsGUI extends JFrame {

	public ShowResultsGUI(Connection con, int prof_id, DefaultTableModel model, boolean friend, boolean ignore) {

		JFrame frame = new JFrame("Search Results");

		JPanel panel, panel1;
		JButton back = new JButton("BACK");
		JTable table = new JTable();

		JButton add_friend = new JButton("Add Friend");
		JTextField add_text = new JTextField("Insert profile's id to add as a friend :)");

		JButton unignore = new JButton("Undo ignore");
		JTextField uni = new JTextField("Insert profile's id to undo ignore :)");

		table.setModel(model);

		panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(table);
		back.setSize(40, 40);
		if (!friend)
			panel.add(back);

		frame.setTitle("Advance Search");
		frame.setSize(1080, 1080);
		frame.add(panel);

		if (friend) {
			panel1 = new JPanel(new GridLayout(3, 1));
			panel1.add(back);
			panel1.add(add_friend);
			panel1.add(add_text);
			panel.add(panel1);
		}

		if (ignore) {
			panel1 = new JPanel(new GridLayout(3, 1));
			panel1.add(back);
			panel1.add(unignore);
			panel1.add(uni);
			panel.add(panel1);
		}

		frame.setLocationRelativeTo(null);
		frame.show();

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				new AllFunctionsGUI(con, prof_id);

			}

		});

		add_friend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (add_text.getText().length() == 0)
					return;

				int receiver = Integer.parseInt(add_text.getText());
				String sp = "{call upsSendFriendRequest(?,?)}";
				boolean flag = false;

				try {
					flag = sendFriendRequest(prof_id, receiver, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag)
					JOptionPane.showMessageDialog(null, "Friend Request sent :)\n", "Job Done!",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Friend Request did not sent :(\n", "Error",
							JOptionPane.ERROR_MESSAGE);

				frame.hide();
				new SearchUserGUI(con, prof_id);
			}

		});

		unignore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (uni.getText().length() == 0)
					return;

				int receiver = Integer.parseInt(uni.getText());
				String sp = "{call upsMakePending(?,?)}";
				boolean flag = false;

				try {
					flag = unignore(receiver, prof_id, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag)
					JOptionPane.showMessageDialog(null, "Undo Ignore completed :)\n", "Job Done!",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "No Undo Ignore completed :(\n", "Error",
							JOptionPane.ERROR_MESSAGE);

				frame.hide();
				try {
					new FriendRequestGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

	}

	private boolean unignore(int other_id, int prof_id, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, other_id);
		cs.setInt(2, prof_id);
		cs.execute();

		return true;
	}

	private boolean sendFriendRequest(int prof_id, int receiver, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setInt(2, receiver);
		cs.execute();

		return true;
	}

}
