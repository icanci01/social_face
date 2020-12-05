package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class SearchObjectGUI extends JFrame {

	public SearchObjectGUI(Connection con, int prof_id) throws FileNotFoundException {
		this.setTitle("Social Face  --> Search for an object");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("logo.png").getImage());
		this.getContentPane().setBackground(new Color(0x47bff5));
		this.setBounds(0, 0, 1280, 720);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// MAIN UPPER PANEL
		JPanel upper = new JPanel();
		upper.setPreferredSize(new Dimension(300, 100));
		JLabel message = new JLabel();
		message.setText("Please select from the list below");
		message.setHorizontalTextPosition(SwingConstants.CENTER);
		message.setVerticalTextPosition(SwingConstants.BOTTOM);
		message.setForeground(new Color(0x001A5F));
		message.setFont(new Font("Comic Sans", Font.PLAIN, 50));
		message.setIconTextGap(20);
		message.setVerticalAlignment(SwingConstants.CENTER);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		upper.setBackground(new Color(0x47bff5));
		upper.add(message);

		// GCHRIS 12
		JPanel lower = new JPanel();
		lower.setPreferredSize(new Dimension(300, 100));
		JTextField input = new JTextField();
		input.setPreferredSize(new Dimension(200, 50));
		lower.add(input);

		JPanel mainP = new JPanel();
		mainP.setBackground(new Color(0x0031A0));
		mainP.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainP.setPreferredSize(new Dimension(300, 200));
		JButton search_album, search_photo, search_video, search_bookmark, search_event, back;

		search_album = new JButton("Search Album");
		search_album.setPreferredSize(new Dimension(200, 50));
		search_album.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		search_album.setFocusable(false);

		search_photo = new JButton("Search Photo");
		search_photo.setPreferredSize(new Dimension(200, 50));
		search_photo.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		search_photo.setFocusable(false);

		search_video = new JButton("Search Video");
		search_video.setPreferredSize(new Dimension(200, 50));
		search_video.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		search_video.setFocusable(false);

		search_bookmark = new JButton("Search Bookmark");
		search_bookmark.setPreferredSize(new Dimension(200, 50));
		search_bookmark.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		search_bookmark.setFocusable(false);

		search_event = new JButton("Search Event");
		search_event.setPreferredSize(new Dimension(200, 50));
		search_event.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		search_event.setFocusable(false);

		back = new JButton("BACK");
		back.setPreferredSize(new Dimension(200, 50));
		back.setFont(new Font("Comic Sans", Font.ITALIC, 20));
		back.setFocusable(false);

		// ADD ON GCHRIS12

		mainP.add(search_album);
		mainP.add(search_photo);
		mainP.add(search_video);
		mainP.add(search_bookmark);
		mainP.add(search_event);
		mainP.add(back);

		this.add(upper, BorderLayout.NORTH);
		this.add(mainP, BorderLayout.CENTER);
		this.add(lower, BorderLayout.SOUTH);
		this.setVisible(true);

		String[] privacy = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };

		Scanner inFile1 = new Scanner(new File("src\\CITY.txt")).useDelimiter(",\\s*");
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

		inFile1 = new Scanner(new File("src\\LOCATION.txt")).useDelimiter(",\\s*");
		token1 = "";
		ArrayList locations = new ArrayList<>();
		inFile1.nextLine();
		while (inFile1.hasNextLine()) {
			// find next line
			token1 = inFile1.nextLine();
			String[] split = token1.split("\t");
			String tok = split[0] + ", " + split[1] + ", " + split[2];

			locations.add(tok);
		}
		String[] loc = new String[locations.size()];
		for (int i = 0; i < locations.size(); i++)
			loc[i] = locations.get(i).toString();
		final JComboBox<String> venue = new JComboBox<String>(loc);

		JTable table = new JTable();

		search_album.addActionListener(e -> {
			ResultSet rs;

			// trigger search for album
			String[] header = { "ID", "NAME", "DESCRIPTION", "LOCATION", "PRIVACY", "LINK" };
			String sp = "{call upsGetAlbumByName(?,?)}";
			String inp = input.getText();
			boolean flag = inp.length() > 0 ? true : false;
			try {
				if (flag) {
					rs = foo(sp, prof_id, inp, con);
					createAlbum(header, rs, privacy, cit, con, prof_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/////////////////////////////////////////////////////////

		search_photo.addActionListener(e -> {
			// trigger search for album
			String[] header = { "ID", "HEIGHT", "WIDTH", "DIRECTORY", "LINK", "PRIVACY" };
			String sp = "{call upsGetPhotoByPrivacy(?,?)}";

			String inp = input.getText();
			boolean flag = inp.length() > 0 ? true : false;
			ResultSet rs = null;

			try {
				if (flag) {
					rs = foo(sp, prof_id, inp, con);
					createPhoto(header, rs, privacy, con, prof_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		search_video.addActionListener(e -> {
			// trigger search for album

			String[] header = { "ID", "LENGTH", "DESCRIPTION", "MESSAGE", "PRIVACY" };
			String sp = "{call upsGetVideoByName(?,?)}";

			String inp = input.getText();
			boolean flag = inp.length() > 0 ? true : false;
			ResultSet rs;

			try {
				if (flag) {
					rs = foo(sp, prof_id, inp, con);
					createVideo(header, rs, privacy, con, prof_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		search_bookmark.addActionListener(e -> {

			// trigger search for album
			String sp = "{call upsGetBookmarkByName(?,?)}";
			String[] header = { "ID", "LINK", "NAME", "CAPTION", "DESCRIPTION", "MESSAGE", "PRIVACY" };

			ResultSet rs = null;

			String inp = input.getText();
			boolean flag = inp.length() > 0 ? true : false;
			try {
				if (flag) {
					rs = foo(sp, prof_id, inp, con);
					createBookmark(header, rs, privacy, con, prof_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		search_event.addActionListener(e -> {

			// trigger search for album
			String sp = "{call upsGetEventByName2(?,?)}";
			String[] header = { "ID", "NAME", "DESCRIPTION", "START TIME", "END TIME", "LOCATION", "VENUE", "PRIVACY" };
			ResultSet rs;

			String inp = input.getText();
			boolean flag = inp.length() > 0 ? true : false;

			try {
				if (flag) {
					rs = foo(sp, prof_id, inp, con);
					createEvent(header, rs, loc, cit, privacy, con, prof_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		back.addActionListener(e -> {
			this.setVisible(false);
			new AllFunctionsGUI(con, prof_id);
		});
	}

	private ResultSet foo(String sp, int prof_id, String search, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setString(1, search);
		cs.setInt(2, prof_id);
		ResultSet rs = cs.executeQuery();

		return rs;
	}
	
	private void createAlbum (String[] head, ResultSet rs, String[] choices, String[] cit, Connection con, int prof_id) throws SQLException {
		 DefaultTableModel model = new DefaultTableModel(head, 1);
			model.addRow(head); 
		
		while(rs.next()) {
			 
         	int id = rs.getInt("id");
             String n = rs.getString("name");
             String d = rs.getString("description");
             String l = cit[rs.getInt("location")-1];
             String p = choices[rs.getInt("privacy")-1];
             String link = rs.getString("link");
             model.addRow(new Object[]{id, n, d, l,p,link});
         }
		 
		 this.setVisible(false);
		new ShowResultsGUI(con, prof_id, model, false, false);
	}

	private void createPhoto(String[] head, ResultSet rs, String[] choices, Connection con, int prof_id)
			throws SQLException {
		DefaultTableModel model = new DefaultTableModel(head, 1);
		model.addRow(head);
		while (rs.next()) {
			int id = rs.getInt("id");
			int hght = rs.getInt("height");
			int wdth = rs.getInt("width");
			String dir = rs.getString("directory");
			String link = rs.getString("link");
			String p = choices[rs.getInt("privacy") - 1];

			model.addRow(new Object[] { id, hght, wdth, dir, link, p });
		}
		this.setVisible(false);
		new ShowResultsGUI(con, prof_id, model, false, false);
	}

	private void createVideo(String[] head, ResultSet rs, String[] choices, Connection con, int prof_id)
			throws SQLException {
		DefaultTableModel model = new DefaultTableModel(head, 1);
		model.addRow(head);

		while (rs.next()) {
			int id = rs.getInt("id");
			int length = rs.getInt("length");
			String dis = rs.getString("description");
			String mes = rs.getString("message");
			String p = choices[rs.getInt("privacy") - 1];

			model.addRow(new Object[] { id, length, dis, mes, p });
		}

		this.setVisible(false);
		new ShowResultsGUI(con, prof_id, model, false, false);
	}

	private void createBookmark(String[] head, ResultSet rs, String[] choices, Connection con, int prof_id)
			throws SQLException {
		DefaultTableModel model = new DefaultTableModel(head, 1);
		model.addRow(head);

		while (rs.next()) {
			int id = rs.getInt("id");
			String link = rs.getString("link");
			String name = rs.getString("name");
			String caption = rs.getString("caption");
			String description = rs.getString("description");
			String message = rs.getString("message");
			String p = choices[rs.getInt("privacy") - 1];

			model.addRow(new Object[] { id, link, name, caption, description, message, p });
		}

		this.setVisible(false);
		new ShowResultsGUI(con, prof_id, model, false, false);
	}

	private DefaultTableModel createEvent(String[] head, ResultSet rs, String[] loc, String[] cit, String[] choices,
			Connection con, int prof_id) throws SQLException {
		DefaultTableModel model = new DefaultTableModel(head, 1);
		model.addRow(head);

		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String desc = rs.getString("description");
			String start = rs.getString("start_time");
			String end = rs.getString("end_time");
			String lo = loc[rs.getInt("venue") - 1];
			String ven = cit[rs.getInt("location") - 1];
			String priv = choices[rs.getInt("privacy") - 1];

			model.addRow(new Object[] { id, name, desc, start, end, lo, ven, priv });
		}

		this.setVisible(false);
		new ShowResultsGUI(con, prof_id, model, false, false);
		return model;
	}

}
