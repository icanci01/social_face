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
import javax.swing.JTextField;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class CreateProfileGUI extends JFrame implements ActionListener {
	//AllFunctionsGUI afGUI;

	public CreateProfileGUI(Connection con, int acc_id) throws FileNotFoundException {
		this.setTitle("Social Face --> Create your account");
		JLabel fn, ln, bd, ht, lo, gender, email;
		JTextField fn_t, ln_t, bd_t, email_t;
		JButton submit;
		JPanel panel;
		JFrame frame;

		String link = "www.profile" + acc_id + ".com";

		fn = new JLabel();
		fn.setText("First Name: ");
		fn_t = new JTextField();
		ln = new JLabel();
		ln.setText("Last Name:");
		ln_t = new JTextField();
		bd = new JLabel();
		bd.setText("Birthdate: ");
		bd_t = new JTextField();
		ht = new JLabel();
		ht.setText("Hometown: ");
		lo = new JLabel();
		lo.setText("Location: ");
		gender = new JLabel();
		gender.setText("Gender: ");
		email = new JLabel();
		email.setText("Email: ");
		email_t = new JTextField();

		submit = new JButton();
		submit.setText("SUBMIT");

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

		final JComboBox<String> hometown = new JComboBox<String>(cit);

		String[] gend = new String[] { "Female", "Male" };
		final JComboBox<String> gender_t = new JComboBox<String>(gend);

		panel = new JPanel();

		panel.setBackground(Color.LIGHT_GRAY);
		panel = new JPanel(new GridLayout(8, 2));
		panel.add(fn);
		panel.add(fn_t);
		panel.add(ln);
		panel.add(ln_t);
		panel.add(bd);
		panel.add(bd_t);
		panel.add(ht);
		panel.add(hometown);
		panel.add(lo);
		panel.add(location);
		panel.add(gender);
		panel.add(gender_t);
		panel.add(email);
		panel.add(email_t);

		panel.add(submit);
		add(panel, BorderLayout.CENTER);

		setTitle("Social Life - Fill Profile Data");
		frame = new JFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(480, 480);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.show();

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean flag = false;
				int prof_id = -1;

				String fn = fn_t.getText();
				String ln = ln_t.getText();
				String bd = bd_t.getText();
				int ht = hometown.getSelectedIndex() + 1;
				int lo = location.getSelectedIndex() + 1;
				int gender = gender_t.getSelectedIndex();
				String email = email_t.getText();
				String sp = "{call upsFillProfileData(?,?,?,?,?,?,?,?,?)}";

				try {
					flag = tryAddData(acc_id, fn, ln, bd, ht, lo, gender, email, link, con, sp);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag) {
					System.out.printf("Account created sucesfully\n");
					frame.setVisible(false);

					String spN = "{call upsGetProfileAccount(?)}";
					try {
						prof_id = tryGetProfID(acc_id, spN, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (prof_id != -1) {
						frame.setVisible(false);
						new AllFunctionsGUI(con, prof_id);
					}
					System.out.printf("Profile id %d\n", prof_id);

				} else
					JOptionPane.showMessageDialog(null, "Rewrite the data with the correct way :(\n", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

	}

	private boolean tryAddData(int my_id, String fn, String ln, String bd, int ht, int lo, int gender,
			String email, String link, Connection con, String sp) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, fn);
		cs.setString(2, ln);
		cs.setString(3, link);
		cs.setString(4, bd);
		cs.setInt(5, ht);
		cs.setInt(6, lo);
		cs.setInt(7, gender);
		cs.setString(8, email);
		cs.setInt(9, my_id);

		try {
			cs.executeQuery();
		} catch (SQLServerException e) {
			System.out.printf("Same\n");
			return false;
		}

		return true;
	}

	private int tryGetProfID(int acc_id, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, acc_id);
		ResultSet rs = cs.executeQuery();

		if (rs.next())
			return (rs.getInt("id"));
		else
			System.out.printf("Wrong username or password :(\n");

		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
