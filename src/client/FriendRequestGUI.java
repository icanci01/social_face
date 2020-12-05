package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FriendRequestGUI extends JFrame {
	private final String[] header = {"ID", "FIRST NAME", "LAST NAME"};
	
	public FriendRequestGUI(Connection con, int prof_id) throws SQLException {
		
		JFrame frame = new JFrame("Friend Requests");
		JPanel panel, panel1, panel2;
		
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel(header, 0);
		DefaultTableModel model1 = new DefaultTableModel(header, 0);

		
		JButton back, show_ignored, accept, ignore, reject;
		JTextField id_text = new JTextField("Insert profile's id and choose what to do :)");
		back = new JButton("BACK");
		show_ignored = new JButton("SHOW IGNORE FRIEND REQUESTS");
		accept = new JButton("ACCEPT");
		reject = new JButton("REJECT");
		ignore = new JButton("IGNORE");
		
		ResultSet rs = null;
		String sp = "{call uspGetFriendsRequest(?)}";
		
		rs = getFriendRequest(prof_id, sp, con);
		rs.next();
	
		model1.addRow(header);
		model.addRow(header);
		while (rs.next()) {
			int id = rs.getInt("id");
			String fn = rs.getString("first_name");
			String ln = rs.getString("last_name");
			model.addRow(new Object[] {id, fn, ln});
		}
		
        panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        panel1 = new JPanel(new GridLayout(4,1));
        panel1.add(id_text);
        panel1.add(accept);
        panel1.add(ignore);
        panel1.add(reject);
        
        panel2 = new JPanel(new GridLayout(2,1));
		panel2.add(back);
		panel2.add(show_ignored);
			
		table.setModel(model);
		panel.add(table);
		panel.add(panel1);
		panel.add(panel2);
		
		frame.setSize(1080, 1080);
		frame.add(panel);
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
		
		accept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sp = "{call uspAcceptFriendRequest(?,?)}";
				boolean flag = false;
				
				if (id_text.getText().length() == 0) return;
				
				int other_id = Integer.parseInt(id_text.getText());
				try {
					flag = manageFriend(other_id,prof_id, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Friend added :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Friend did not added :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				try {
					new FriendRequestGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		ignore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sp = "{call uspIgnoreFriendRequest(?,?)}";
				boolean flag = false;
				
				if (id_text.getText().length() == 0) return;
				
				int other_id = Integer.parseInt(id_text.getText());
				try {
					flag = manageFriend(other_id,prof_id, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Friend ignored :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Friend did not ignored :(\n", "Error", JOptionPane.ERROR_MESSAGE);
				
				frame.hide();
				try {
					new FriendRequestGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		reject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sp = "{call uspRejectFriendRequest(?,?)}";
				boolean flag = false;
				
				if (id_text.getText().length() == 0) return;
				
				int other_id = Integer.parseInt(id_text.getText());
				try {
					flag = manageFriend(other_id,prof_id, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				sp = "{call uspDeleteRejectFriendRequest()}";
				CallableStatement cs;
				try {
					cs = con.prepareCall(sp);
					cs.execute();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Friend rejected :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Friend did not rejected :(\n", "Error", JOptionPane.ERROR_MESSAGE);
			
				frame.hide();
				try {
					new FriendRequestGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		show_ignored.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String sp = "{call uspGetFriendsRequestIgnore(?)}";
				ResultSet rs = null;
				
				try {
					rs = getIgnored(prof_id, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					rs.next();
					while (rs.next()) {
						int id = rs.getInt("id");
						String fn = rs.getString("first_name");
						String ln = rs.getString("last_name");
						model1.addRow(new Object[] {id, fn, ln});
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				frame.hide();
				new ShowResultsGUI(con, prof_id, model1, false, true);
			}


			
		});
		
	}
	
	private ResultSet getIgnored(int prof_id, String sp, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		return(cs.executeQuery());
	}
	
	private boolean manageFriend(int other_id, int prof_id, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, other_id);
		cs.setInt(2, prof_id);
		cs.execute();
				
		
		return true;
	}

	private ResultSet getFriendRequest(int prof_id, String sp, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		return (cs.executeQuery());
		}
	
}
