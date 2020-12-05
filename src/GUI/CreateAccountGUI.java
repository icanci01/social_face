package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class CreateAccountGUI extends JFrame implements ActionListener {
	LoginGUI lGUI;
	FirstGUI fGUI;

	JPanel panel;
	JLabel user_label, password_label, message;
	JTextField userName_text;
	JPasswordField password_text;
	JButton submit, back;


	CreateAccountGUI(Connection con) {

		this.setTitle("Social Face - Create new Account");
		this.setIconImage(new ImageIcon("logo.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(0x47bff5));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBounds(0,0,1280,720);
		this.setResizable(false);

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
		back = new JButton("BACK");
		panel = new JPanel(new GridLayout(3,2));
		panel.add(user_label);
		panel.add(userName_text);
		panel.add(password_label);
		panel.add(password_text);
		panel.add(back);
		panel.add(submit);

		// Adding the listeners to components..
		submit.addActionListener(this);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Create Account");
		panel.setLocation(0, 0);
		panel.setBackground(Color.LIGHT_GRAY);


		this.add(panel);
		this.setSize(480, 480);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		back.addActionListener(e -> {
			this.setVisible(false);
			fGUI = new FirstGUI(con);
		});
		
		submit.addActionListener(e -> {
			String username = userName_text.getText();
			String password = password_text.getText();
			String storProcName = "{call upsCreateAccount(?,?)}";

			if (userName_text.getText().length() == 0) username = null;
			else username = userName_text.getText();

			if (password_text.getText().length() == 0) password = null;
			else password = password_text.getText();

			boolean flag = false;

			try {
				flag = tryCreateAccount(username, password, storProcName, con);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (flag) {
				System.out.printf("Account created successfully\n");
				this.setVisible(false);
				lGUI = new LoginGUI(con, true);

			}
			else  JOptionPane.showMessageDialog(null, "The username is taken :(\n", "Error", JOptionPane.ERROR_MESSAGE);

		});
	}

	private boolean tryCreateAccount(String u, String p, String sp, Connection con) throws SQLException {
		
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, u);
		cs.setString(2, p);
		
		try {
			cs.executeQuery();
		} catch (SQLServerException e) {
			System.out.printf("Same\n");
			return false;
		}
	
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
