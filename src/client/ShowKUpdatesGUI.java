package GUI;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ShowKUpdatesGUI extends JFrame {
	final private String[] header = {"OBJECT", "ID", "ACTION"};
	private DefaultTableModel model = new DefaultTableModel(header, 0);


	public ShowKUpdatesGUI(Connection con, int prof_id) {
		

		JFrame frame = new JFrame("Show Updates");
        JPanel mainPanel, panelButtons;
        
        JButton album, photo, video, bookmark, event, show_all;
        album = new JButton("ALBUM");
        photo = new JButton("PHOTO");
        video = new JButton("VIDEO");
        bookmark = new JButton("BOOKMARK");
        event = new JButton("EVENT");
        show_all = new JButton("SHOW ALL");
        
        JTextField num = new JTextField();
        JButton back, search;
        back = new JButton("BACK");
        search = new JButton("SEARCH");
        
        mainPanel = new JPanel(new GridLayout(3, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBackground(Color.LIGHT_GRAY);


        panelButtons = new JPanel(new GridLayout(1, 6));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panelButtons, BorderLayout.CENTER);
        panelButtons.setBackground(Color.LIGHT_GRAY);
        panelButtons.add(album);
        panelButtons.add(photo);
        panelButtons.add(video);
        panelButtons.add(bookmark);
        panelButtons.add(event);
        panelButtons.add(show_all);
        
        mainPanel.add(panelButtons);
        mainPanel.add(num);
        
        
        mainPanel.add(back);
        
		frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.add(mainPanel);
        frame.show();
        
        
      
        String sp = "{call upsGetUpdatesIndividually(?,?,?)}";
        
        album.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ResultSet rs = null;
				String name = "album";
				if (num.getText().length() == 0) return;
				
				int k = Integer.parseInt(num.getText());
				
				try {
					rs = retrieveUpdates(prof_id, k, name, sp, con);
					addInfo(rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.hide();

				
				new ShowResultsGUI(con, prof_id, model, false, false);

			}

        });
        
        photo.addActionListener(new ActionListener() {

     			@Override
     			public void actionPerformed(ActionEvent e) {
     				// TODO Auto-generated method stub
    				ResultSet rs = null;
     				String name = "photo";
     				
    				if (num.getText().length() == 0) return;
    				
    				int k = Integer.parseInt(num.getText());
    				
    				try {
    					rs = retrieveUpdates(prof_id, k, name, sp, con);
    					addInfo(rs);
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				
    				frame.hide();

    				new ShowResultsGUI(con, prof_id, model, false, false);

     			}

             });
        
        video.addActionListener(new ActionListener() {

     			@Override
     			public void actionPerformed(ActionEvent e) {
     				// TODO Auto-generated method stub
    				ResultSet rs = null;
     				String name = "video";
     				
    				if (num.getText().length() == 0) return;
    				
    				int k = Integer.parseInt(num.getText());
    				
    				try {
    					rs = retrieveUpdates(prof_id, k, name, sp, con);
    					addInfo(rs);
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				
    				frame.hide();

    				new ShowResultsGUI(con, prof_id, model, false, false);

     			}

             });
        
        bookmark.addActionListener(new ActionListener() {

     			@Override
     			public void actionPerformed(ActionEvent e) {
     				// TODO Auto-generated method stub
    				ResultSet rs = null;
     				String name = "bookmark";
     				
    				if (num.getText().length() == 0) return;
    				
    				int k = Integer.parseInt(num.getText());
    				
    				try {
    					rs = retrieveUpdates(prof_id, k, name, sp, con);
    					addInfo(rs);
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				
    				frame.hide();

    				new ShowResultsGUI(con, prof_id, model, false, false);

     			}
     			

             });
        
        event.addActionListener(new ActionListener() {

     			@Override
     			public void actionPerformed(ActionEvent e) {
     				// TODO Auto-generated method stub
    				ResultSet rs = null;
     				String name = "event";
     				
    				if (num.getText().length() == 0) return;
    				
    				int k = Integer.parseInt(num.getText());
    				
    				try {
    					rs = retrieveUpdates(prof_id, k, name, sp, con);
    					addInfo(rs);
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				
    				frame.hide();
					
					new ShowResultsGUI(con, prof_id, model, false, false);
     			}
             	
             });
        
        show_all.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sp = "{call upsGetAllUpdates(?,?)}";
				ResultSet rs = null;
 				
				if (num.getText().length() == 0) return;
				
				int k = Integer.parseInt(num.getText());
				
				try {
					rs = retrieveUpdates(prof_id, k, sp, con);
					addInfo(rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				frame.hide();
				
				new ShowResultsGUI(con, prof_id, model, false, false);
				
			}
        	
        });
        
        back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				new AllFunctionsGUI(con, prof_id);
			}
        	
        });
        		
        		
	}
	
	private void addInfo(ResultSet rs) throws SQLException {
		model.addRow(header);
		while (rs.next()) {
			String obj = rs.getString("object");
			int obj_id = rs.getInt("object_id");
			String action = rs.getString("action");

			model.addRow(new Object[] {obj, obj_id, action});
		}
	}
	
	
	
    private ResultSet retrieveUpdates(int prof_id, int k, String name, String sp, Connection con) throws SQLException {

        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, prof_id);
        cs.setInt(2, k);
        cs.setString(3, name);
        ResultSet rs = cs.executeQuery();

        return rs;
    }
    
    private ResultSet retrieveUpdates(int prof_id, int k, String sp, Connection con) throws SQLException {

        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, prof_id);
        cs.setInt(2, k);
        ResultSet rs = cs.executeQuery();

        return rs;
    }
}
