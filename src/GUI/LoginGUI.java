package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Vector;
import javax.swing.*;
import java.sql.*;

public class LoginGUI extends JFrame implements ActionListener {
	CreateProfileGUI cpGUI;
	AllFunctionsGUI afGUI;
	FirstGUI fGUI;

	JFrame frame;
	JPanel panel;
	JLabel user_label, password_label, message;
	JTextField userName_text;
	JPasswordField password_text;
	JButton submit, back;
	Connection con = null;

	LoginGUI(Connection con, boolean newbie) {
		this.con = con;

		frame = new JFrame("Social Face - Login");

		// Username Label
		user_label = new JLabel();
		user_label.setText("User Name :");
		userName_text = new JTextField();
		// Password Label
		password_label = new JLabel();
		password_label.setText("Password :");
		password_text = new JPasswordField();
		// Submit
		submit = new JButton("SUBMIT");
		panel = new JPanel(new GridLayout(3, 2));
		panel.add(user_label);
		panel.add(userName_text);
		panel.add(password_label);
		panel.add(password_text);
		message = new JLabel();
		back = new JButton("BACK");
		panel.add(back);
		panel.add(submit);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adding the listeners to components..
		submit.addActionListener(this);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Login");
		panel.setBackground(Color.LIGHT_GRAY);

		frame.add(panel);
		frame.setSize(480, 480);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				fGUI = new FirstGUI(con);

			}

		});
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = userName_text.getText();
				String password = password_text.getText();
				String storProcName = "{call upsLogin(?,?)}";
				int id = -1;
				
				if (userName_text.getText().length() == 0) username = null;
				else username = userName_text.getText();
			
				if (password_text.getText().length() == 0) password = null;
				else password = password_text.getText();
				
				try {
					id = tryLogin(username, password, storProcName, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (id != -1) {
					if (newbie) {
						frame.setVisible(false);
						try {
							cpGUI = new CreateProfileGUI(con, id);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						String spN = "{call upsGetProfileAccount(?)}";
						int prof_id = -1;

						try {
							prof_id = tryGetProfID(id, spN, con);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (prof_id != -1) {
							frame.setVisible(false);
							afGUI = new AllFunctionsGUI(con, prof_id);
						}
					}
				}

			}
		});
	}

	private int tryLogin(String u, String p, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, u);
		cs.setString(2, p);
		ResultSet rs = cs.executeQuery();

		if (rs.next())
			return (rs.getInt("id"));
		else
			JOptionPane.showMessageDialog(null, "Wrong username or password :(\n", "Error", JOptionPane.ERROR_MESSAGE);

		return -1;
	}

	private int tryGetProfID(int acc_id, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, acc_id);
		ResultSet rs = cs.executeQuery();

		if (rs.next())
			return (rs.getInt("id"));

		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}