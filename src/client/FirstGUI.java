package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.sql.Connection;
import java.sql.SQLException;


public class FirstGUI extends JFrame implements ActionListener {
	LoginGUI lGUI;
	CreateAccountGUI aGUI;
	Connection con = null;
	
	public FirstGUI(Connection con) {
		this.con = con;
		
		JFrame frame = new JFrame("Social Face");
		JPanel panel = new JPanel();

		JButton sign_up, login, exit;
		sign_up = new JButton();
		login = new JButton();
		exit = new JButton("Exit");

		sign_up.setText("Sign up");
		login.setText("Login");

		panel.add(sign_up);
		panel.add(login);
		panel.add(exit);
		panel.setBackground(Color.LIGHT_GRAY);

		frame.add(panel);
		frame.setSize(360, 360);
		frame.setLocationRelativeTo(null);
		frame.show();
		
		sign_up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.hide();
				aGUI = new CreateAccountGUI(con);
				
			}
		});
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.hide();
				lGUI = new LoginGUI(con, false);
			}
		});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "BYE :(\n", "Job Done!", JOptionPane.WARNING_MESSAGE);

				
				System.exit(0);
				
			}
		
		});

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}