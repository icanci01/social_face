package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

public class SearchSimpleUserGUI extends JFrame {
	private final String[] header = {"ID", "First Name", "Last Name", "Link", "Birthdate", "Email", "Hometown", "Location",
			"Gender", "Verified" };

	private DefaultTableModel model = new DefaultTableModel(header, 0);

	SearchUserGUI suGUI;

	public SearchSimpleUserGUI(Connection con, int prof_id) throws FileNotFoundException {
		JFrame frame = new JFrame("Simple Search User");

		JPanel panel, panel_results;

		JButton back = new JButton();
		JButton search = new JButton();
		JLabel fname, lname, bdate, city;
		JTextField fnamet, lnamet, bdatet;
		JTable table = new JTable();

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

		fname = new JLabel("First Name: ");
		fnamet = new JTextField();
		lname = new JLabel("Last Name: ");
		lnamet = new JTextField();
		bdate = new JLabel("Birthdate: ");
		bdatet = new JTextField();
		city = new JLabel("City: ");

		panel = new JPanel(new GridLayout(5, 2));

		back.setText("BACK");

		search.setText("SEARCH");

		panel.setSize(480, 480);
		panel.add(fname);
		panel.add(fnamet);
		panel.add(lname);
		panel.add(lnamet);
		panel.add(bdate);
		panel.add(bdatet);
		panel.add(city);
		panel.add(location);
		panel.add(back);
		panel.add(search);

		frame.setTitle("Search for Profiles");
		frame.setSize(480, 480);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.show();

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				suGUI = new SearchUserGUI(con, prof_id);
			}

		});

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int a = 0;
				int b = 0;
				int c = 0;
				int d = 0;
				if (fnamet.getText().length() != 0)
					a = 1;
				if (lnamet.getText().length() != 0)
					b = 10;
				if (bdatet.getText().length() != 0)
					c = 100;
				if (location.getSelectedIndex() != 0)
					d = 1000;
				int result = a + b + c + d;

				System.out.printf("%d", result);
				ResultSet rs = null;
				switch (result) {
				case 0: {
					JOptionPane.showMessageDialog(null, "Please insert at least one value :|\n", "Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
				case 1: {

					String sp = "{call uspGetProfilesName(?)}";
					try {
						rs = getProfileFname(fnamet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;

					// LOC BIRTH LA FR
				}
				case 10: {
					String sp = "{call uspGetProfilesLastName(?)}";
					try {
						rs = getProfileLName(lnamet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				case 11: {
					String sp = "{call upsGetProfilesFirstLast(?,?)}";
					try {
						rs = getProfileFnameLname(fnamet.getText(),lnamet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				// bdate search
				case 100: {
					String sp = "{call uspGetProfilesBirthdate(?)}";
					try {
						rs = getProfileBdate(bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				// bdate , fname search
				case 101: {
					String sp = "{call uspGetProfilesFirstBdate(?,?)}";
					try {
						rs = getProfileFnameBdate(fnamet.getText(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//bdate , lname search
				case 110: {
					String sp = "{call uspGetProfilesFirstBdate(?,?)}";
					try {
						rs = getProfileLnameBdate(lnamet.getText(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//bdate,fname,lname search
				case 111: {
					String sp = "{call uspGetProfilesFirstLasBirthday(?,?,?)}";
					try {
						rs = getProfileFLB(fnamet.getText(), lnamet.getText(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//city search
				case 1000: {
					String sp = "{call uspGetProfilesCity(?)}";
					try {
						rs = getProfileCity(location.getSelectedIndex(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//city , fname search
				case 1001: {
					String sp = "{call uspGetProfilesFirstCity(?,?)}";
					try {
						rs = getProfileFnameCity(fnamet.getText(),location.getSelectedIndex(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//city , lname search
				case 1010: {
					String sp = "{call uspGetProfilesLastCity(?,?)}";
					try {
						rs = getProfileLnameCity(lnamet.getText(),location.getSelectedIndex(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//fname,lname,city
				case 1011: {
					String sp = "{call uspGetProfilesFirstLastCity(?,?,?)}";
					try {
						rs = getProfileFLC(fnamet.getText(), lnamet.getText(), location.getSelectedIndex(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				// city,bdate search
				case 1100: {
					String sp = "{call upsGetProfileCityBdate(?,?)}";
					try {
						rs = getProfileCB(location.getSelectedIndex(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//city , bdate , fname search
				case 1101: {
					String sp = "{call uspGetProfilesFirstCityBdate(?,?,?)}";
					try {
						rs = getProfileFCB(fnamet.getText(),location.getSelectedIndex(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				//city , bdate , lname
				case 1110: {
					String sp = "{call uspGetProfilesLastCityBdate(?,?,?)}";
					try {
						rs = getProfileLCB(lnamet.getText(),location.getSelectedIndex(), bdatet.getText(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}

				case 1111: {
					String sp = "{call uspGetProfilesFirstLastCityBdate(?,?,?,?)}";
					try {
						rs = getProfileFLBC(fnamet.getText(), lnamet.getText(), bdatet.getText(),
								location.getSelectedIndex(), sp, con);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				}

				if (rs != null) {
					try {
						addInfo(rs, cit);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.hide();
					
					new ShowResultsGUI(con, prof_id, model, true, false);
				}
			}

		});

	}
	
	private void addInfo(ResultSet rs, String[] city) throws SQLException {
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

			model.addRow(new Object[] {id, fnameS, lnameS, linkS, bdateS, emailS, hometownS, location, gen, ver });
		}
	}
	//search fname
	private ResultSet getProfileFname(String fname, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, fname);
		ResultSet rs = cs.executeQuery();

		return rs;
	}

	//search lname
	private ResultSet getProfileLName(String lname, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, lname);
		
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search fname , lname
	private ResultSet getProfileFnameLname(String fname,String lname, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, fname);
		cs.setString(2, lname);
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search bdate
	private ResultSet getProfileBdate(String bdate, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//searrch bdate , fname
	private ResultSet getProfileFnameBdate(String fname,String bdate, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, bdate);
		cs.setString(2, fname);
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search bdate , lname
	private ResultSet getProfileLnameBdate(String lname,String bdate, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, bdate);
		cs.setString(2, lname);
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search fname , lname , bdate
	private ResultSet getProfileFLB(String first, String last, String bdate, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, first);
		cs.setString(2, last);
		cs.setString(3, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search city
	private ResultSet getProfileCity(int city, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, city);
		
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search fname,city
	private ResultSet getProfileFnameCity(String fname,int city, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, fname);
		cs.setInt(2, city);
		
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search lname, city
	private ResultSet getProfileLnameCity(String lname,int city, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, lname);
		cs.setInt(2, city);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search fname,lname,city
	private ResultSet getProfileFLC(String first, String last, int city, String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, first);
		cs.setString(2, last);
		cs.setInt(3, city);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search city, bdate
	private ResultSet getProfileCB(int city, String bdate,String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, city);
		cs.setString(2, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search fname,city,bdate
	private ResultSet getProfileFCB(String fname,int city, String bdate,String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, fname);
		cs.setInt(2, city);
		cs.setString(3, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}
	//search lname,city,bdate
	private ResultSet getProfileLCB(String lname,int city, String bdate,String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, lname);
		cs.setInt(2, city);
		cs.setString(3, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}

	private ResultSet getProfileFLBC(String first, String last, String bdate, int city, String sp, Connection con)
			throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, first);
		cs.setString(2, last);
		cs.setInt(3, city);
		cs.setString(4, bdate);

		ResultSet rs = cs.executeQuery();

		return rs;
	}

}
