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
import javax.swing.*;

public class ShowProfileGUI extends JFrame {
	AddEditShowGUI aesGUI;

	public ShowProfileGUI(Connection con, int prof_id) throws FileNotFoundException, SQLException {
		JFrame frame = new JFrame("Show - Edit Profile");
		JPanel panel, interestPanel, quotesPanel, workPanel, eduPanel, endPanel;
		JLabel fn, ln, link, bd, email, ht, lc, g, v, linkt, vt, workL, edu;
		JButton back = new JButton();
		JButton save = new JButton();
		JButton delete = new JButton("DELETE");
		JTextField fnt, lnt, bdt, emailt, htt, lct, gt, worktField, edut;
		
		JLabel intLabel, quotLabel;
		intLabel = new JLabel("INTERESTS");
		quotLabel = new JLabel("QUOTES");
		JTextField intText, quotText;
		intText = new JTextField();
		quotText = new JTextField();
		JTextField workText, eduText;
		workText = new JTextField();
		eduText  = new JTextField();
		
		
		ResultSet inte = null, quot = null;
		
		ArrayList<String> intList = new ArrayList<>();
		ArrayList<String> quotList = new ArrayList<>();

		String spI = "{call upsShowInterests(?)}";
		String spQ = "{call upsShowQuotes(?)}";

		try {
			inte = retrieveInfo(prof_id, spI, con);
			quot = retrieveInfo(prof_id, spQ, con);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		inte.next();
		while (inte.next()) {
			intList.add(inte.getString("interest"));
		}
		String intArr[] = new String[intList.size()];
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = intList.get(i);
		}
		
		
		quot.next();
		while (quot.next()) {
			quotList.add(quot.getString("quote"));
		}
		
		String quotArr[] = new String[quotList.size()];
		for (int i = 0; i < quotArr.length; i++) {
			quotArr[i] = quotList.get(i);
		}
		
		interestPanel = new JPanel(new GridLayout(1, 2));
		
		final JComboBox<String> interests = new JComboBox<String>(intArr);
		interestPanel.add(interests);
		interestPanel.add(intText);
		


		final JComboBox<String> quotes = new JComboBox<String>(quotArr);
		quotesPanel = new JPanel(new GridLayout(1, 2));
		quotesPanel.add(quotes);
		quotesPanel.add(quotText);

		
		//////////////////////////
		
		ResultSet workRs = null, eduRs = null;
		
		workPanel = new JPanel(new GridLayout(1, 2));
		eduPanel = new JPanel(new GridLayout(1, 2));
		
		ArrayList<String> workList = new ArrayList<>();
		ArrayList<String> eduList = new ArrayList<>();

		String spW = "{call upsShowWork(?)}";
		String spE = "{call upsShowEducation(?)}";

		try {
			workRs = retrieveInfo(prof_id, spW, con);
			eduRs = retrieveInfo(prof_id, spE, con);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		workRs.next();
		while (workRs.next()) {
			workList.add(workRs.getString("work"));
		}
		String worArr[] = new String[workList.size()];
		for (int i = 0; i < worArr.length; i++) {
			worArr[i] = workList.get(i);
		}
		
		
		eduRs.next();
		while (eduRs.next()) {
			eduList.add(eduRs.getString("education"));
		}
		
		String eduArr[] = new String[eduList.size()];
		for (int i = 0; i < eduArr.length; i++) {
			eduArr[i] = eduList.get(i);
		}
		
		
		final JComboBox<String> works = new JComboBox<String>(worArr);
		workL = new JLabel("WORK");
		workPanel.add(works);
		workPanel.add(workText);
		


		final JComboBox<String> education = new JComboBox<String>(eduArr);
		edu = new JLabel("EDUCATION");
		eduPanel.add(education);
		eduPanel.add(eduText);

		
		//////////////////////////
		panel = new JPanel(new GridLayout(14, 2));
		endPanel = new JPanel(new GridLayout(1, 2));


		fn = new JLabel();
		fnt = new JTextField();
		ln = new JLabel();
		lnt = new JTextField();
		link = new JLabel();
		linkt = new JLabel();
		bd = new JLabel();
		bdt = new JTextField();
		email = new JLabel();
		emailt = new JTextField();
		ht = new JLabel();
		htt = new JTextField();
		lc = new JLabel();
		lct = new JTextField();
		g = new JLabel();
		gt = new JTextField();
		v = new JLabel();
		vt = new JLabel();


		Scanner inFile1 = new Scanner(new File("CITY.txt")).useDelimiter(",\\s*");
		String token1 = "";
		ArrayList city = new ArrayList<>();
		inFile1.nextLine();
		while (inFile1.hasNextLine()) {
			// find next line
			token1 = inFile1.nextLine();
			city.add(token1);
		}

		String[] gend = new String[] { "Female", "Male" };
		final JComboBox<String> gender = new JComboBox<String>(gend);
		int hom = 0;
		int loc = 0;
		int ge = 0;
		String[] cit = new String[city.size()];
		for (int i = 0; i < city.size(); i++)
			cit[i] = city.get(i).toString();
		final JComboBox<String> location = new JComboBox<String>(cit);
		final JComboBox<String> hometown = new JComboBox<String>(cit);
		ResultSet rs = null;
		String sp = "{call upsShowProfile(?)}";
		try {
			rs = retrieveProfile(prof_id, sp, con);
			rs.next();
			fnt.setText(rs.getString("first_name"));
			lnt.setText(rs.getString("last_name"));
			linkt.setText(rs.getString("link"));
			bdt.setText(rs.getString("birthdate"));
			emailt.setText(rs.getString("email"));
			loc = rs.getInt("location") - 1;
			hom = rs.getInt("hometown") - 1;
			if (rs.getString("verified") == "0")
				vt.setText("No");
			else
				vt.setText("Yes");
			ge = rs.getInt("gender");


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		gender.setSelectedIndex(ge);
		hometown.setSelectedIndex(hom);
		location.setSelectedIndex(loc);

		back.setText("BACK");

		fn.setText("First Name: ");
		ln.setText("Last Name: ");
		link.setText("Link: ");
		bd.setText("Birthdate: ");
		email.setText("Email: ");
		ht.setText("Hometown: ");
		lc.setText("Location: ");
		g.setText("Gender");
		v.setText("Verified: ");
		back.setText("BACK");
		save.setText("SAVE CHANGES");

		panel.add(fn);
		panel.add(fnt);
		panel.add(ln);
		panel.add(lnt);
		panel.add(link);
		panel.add(linkt);
		panel.add(bd);
		panel.add(bdt);
		panel.add(email);
		panel.add(emailt);
		panel.add(ht);
		panel.add(hometown);
		panel.add(lc);
		panel.add(location);
		panel.add(g);
		panel.add(gender);
		panel.add(v);
		panel.add(vt);
		panel.add(workL);
		panel.add(workPanel);
		panel.add(edu);
		panel.add(eduPanel);
		panel.add(intLabel);
		panel.add(interestPanel);
		panel.add(quotLabel);
		panel.add(quotesPanel);
		panel.add(back);
		endPanel.add(save);
		endPanel.add(delete);
		panel.add(endPanel);

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
				boolean flag = false;
				String fn = null, ln = null, bd = null, em = null, sp;
				sp = "{call upsEditProfile(?,?,?,?,?,?,?,?)}";

				boolean ed = false;
				boolean wo = false;

				int loc, hm, ge = 0;
				if (fnt.getText().length() != 0)
					fn = fnt.getText();
				if (lnt.getText().length() != 0)
					ln = lnt.getText();
				if (bdt.getText().length() != 0)
					bd = bdt.getText();
				if (emailt.getText().length() != 0)
					em = emailt.getText();

				loc = location.getSelectedIndex() + 1;
				hm = hometown.getSelectedIndex() + 1;
				ge = gender.getSelectedIndex();

				String esp = null, wsp = null;

				if (intText.getText().length() > 0) {
					String s = "{call upsAddInterest(?,?)}";
					try {
						addToProf(prof_id, intText.getText(), s, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (quotText.getText().length() > 0) {
					String s = "{call upsAddQuote(?,?)}";
					try {
						addToProf(prof_id, quotText.getText(), s, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (workText.getText().length() > 0) {
					String s = "{call addWork(?,?)}";
					try {
						addToProf(prof_id, workText.getText(), s, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (eduText.getText().length() > 0) {
					String s = "{call addEducation(?,?)}";
					try {
						addToProf(prof_id, eduText.getText(), s, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {


					flag = editProfile(prof_id, fn, ln, bd, em, ge, loc, hm, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (flag)
					JOptionPane.showMessageDialog(null, "Profile data changed :)\n", "Job Done!",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The profile data was not changed :(\n", "Error",
							JOptionPane.ERROR_MESSAGE);

				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}

		});
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (intText.getText().length() > 0) {
					String sp = "{call upsDeleteInterest(?,?)}";
					try {
						delete(prof_id, intText.getText() , sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (quotText.getText().length() > 0) {
					String sp = "{call upsDeleteQuote(?,?)}";
					try {
						delete(prof_id, quotText.getText() , sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (workText.getText().length() > 0) {
					String sp = "{call upsDeleteWork(?,?)}";
					try {
						delete(prof_id, workText.getText() , sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if (eduText.getText().length() > 0) {
					String sp = "{call upsDeleteEducation(?,?)}";
					try {
						delete(prof_id, eduText.getText() , sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
			
		});

	}
	
	private boolean delete(int prof_id, String delete, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setString(2, delete);
		return (cs.execute());
	}
	
	private boolean addToProf(int prof_id, String addOn, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setString(2, addOn);
		return (cs.execute());
	}

	private ResultSet retrieveInfo(int prof_id, String sp, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		ResultSet rs = cs.executeQuery();

		return rs;
	}

	private ResultSet retrieveProfile(int prof_id, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		ResultSet rs = cs.executeQuery();

		return rs;
	}

	private boolean editProfile(int prof_id, String fn, String ln, String bd, String em, int g, int loc, int hm,
			String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, prof_id);
		cs.setString(2, fn);
		cs.setString(3, ln);
		cs.setString(4, bd);
		cs.setInt(5, hm);
		cs.setInt(6, loc);
		cs.setInt(7, g);
		cs.setString(8, em);

		cs.execute();

		return true;
	}

	private boolean addWork(int my_id, String work, Connection con, String sp) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, work);

		try {
			cs.executeQuery();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	private boolean addEducation(int my_id, String education, Connection con, String sp) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, education);
		try {
			cs.executeQuery();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

}
