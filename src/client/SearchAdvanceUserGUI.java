package GUI;

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
import javax.swing.table.DefaultTableModel;

public class SearchAdvanceUserGUI extends JFrame{
	private final String[] header = {"ID", "First Name", "Last Name", "Link", "Birthdate", "Email", "Hometown", "Location",
			"Gender", "Verified", "Work", "Education" };

	private DefaultTableModel model = new DefaultTableModel(header, 0);
	
	SearchUserGUI suGUI;


	public SearchAdvanceUserGUI(Connection con, int prof_id) throws FileNotFoundException {
		JFrame frame = new JFrame();
		JPanel panel;
		
		JLabel education_label, work_label;
		education_label = new JLabel("Education: ");
		work_label = new JLabel("Work: ");
		
		JTextField education_text, work_text;
		education_text = new JTextField();
		work_text = new JTextField();
		
		JButton back, search;
		back = new JButton("BACK");
		search = new JButton("SEARCH");
		
        panel = new JPanel(new GridLayout(3, 2));
		panel.setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.add(education_label);
        panel.add(education_text);
        panel.add(work_label);
        panel.add(work_text);
        panel.add(back);
        panel.add(search);
		
		frame.setTitle("Advance Search");
		frame.setSize(480, 480);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.show();
		
		Scanner inFile1 = new Scanner(new File("CITY.txt")).useDelimiter(",\\s*");
		String token1 = "";
		ArrayList cityList = new ArrayList<>();
		cityList.add("NONE");
		inFile1.nextLine();
		while (inFile1.hasNextLine()) {
			// find next line
			token1 = inFile1.nextLine();
			cityList.add(token1);
		}

		String[] cit = new String[cityList.size()];
		for (int i = 0; i < cityList.size(); i++)
			cit[i] = cityList.get(i).toString();
		final JComboBox<String> location = new JComboBox<String>(cit);
		
		
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int choice = 0;
				if (education_text.getText().length() > 0 &&
						work_text.getText().length() > 0) choice = 3;
				else if (education_text.getText().length() > 0 &&
						work_text.getText().length() == 0) choice = 2;
				else if (education_text.getText().length() == 0 &&
						work_text.getText().length() > 0) choice = 1;
				
				
				ResultSet rs = null;
				
				switch (choice) {
				case 0 : {
					JOptionPane.showMessageDialog(null, "Please insert at least one value :|\n", "Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
				// work no edu
				case 1 : {
					try {
						rs = getProfilesByWork(work_text.getText(), con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				}
				// no work, edu
				case 2: {
					try {
						rs = getProfilesByEdu(education_text.getText(), con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				}
				// work and edu
				case 3: {
					try {
						rs = getProfilesByEduWork(education_text.getText(), work_text.getText(), con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				}
				}
				
				if (rs != null) {
					try {
						addInfo(rs, cit, choice);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.hide();
					
					new ShowResultsGUI(con, prof_id, model, true, false);
				}
				
			}
			
		});
		
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				suGUI = new SearchUserGUI(con, prof_id);
			}
			
		});
	}
	
	private void addInfo(ResultSet rs, String[] city, int choice) throws SQLException {
		model.addRow(header);
		while (rs.next()) {
			int id = rs.getInt("id");
			String fnameS = rs.getString("first_name");
			String lnameS = rs.getString("last_name");
			String linkS = rs.getString("link");

			String bdateS = rs.getString("birthdate");
			String emailS = rs.getString("email") + "";
			String hometownS = city[rs.getInt("hometown")];

			String location = city[rs.getInt("location")];
			String gen, ver;
			String work = null, edu = null;
			int gender = rs.getInt("gender");
			if (gender == 0)
				gen = "Female";
			else
				gen = "Male";
			int verified = rs.getInt("verified");
			if (verified == 0)
				ver = "No";
			else
				ver = "Yes";
			
			if (choice == 1) {
				work = rs.getString("work");
				edu = "";
			}
			
			if (choice == 2) {
				edu = rs.getString("education");
				work = "";
			}
			
			if (choice == 3) {
				work = rs.getString("work");
				edu = rs.getString("education");
			}

			model.addRow(new Object[] {id, fnameS, lnameS, linkS, bdateS, emailS, hometownS, location, gen, ver, work, edu});
		}
	}
	
	private ResultSet getProfilesByWork(String work, Connection con) throws SQLException {
		String sp = "{call upsGetProfileByWork(?)}";
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, work);
		ResultSet rs = cs.executeQuery();
		
		return rs;

	}
	
	private ResultSet getProfilesByEdu(String educ, Connection con) throws SQLException {
		String sp = "{call upsGetProfileByEducation(?)}";
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, educ);
		ResultSet rs = cs.executeQuery();
		
		return rs;

	}
	
	private ResultSet getProfilesByEduWork(String educ, String work, Connection con) throws SQLException {
		String sp = "{call upsGetProfilesEducationWork(?,?)}";
		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, educ);
		cs.setString(2, work);
		ResultSet rs = cs.executeQuery();
		
		return rs;

	}
	
	
}
