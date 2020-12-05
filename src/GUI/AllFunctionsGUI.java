package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class AllFunctionsGUI extends JFrame implements ActionListener {
	AddEditShowGUI aesGUI;
	SearchUserGUI suGUI;

    public AllFunctionsGUI(Connection con, int prof_id){
    	JFrame frame = new JFrame("Profile Functions");
        JPanel panel;
        JButton add_edit_show, search_obj,search_user,show_f_request,show_friends,show_album_info,show_k_updates,exit;

        add_edit_show = new JButton("ADD / EDIT / SHOW PROFILE DATA");
        search_obj  = new JButton("SEARCH OBJECT");
        search_user = new JButton("SEARCH USER");
        show_k_updates = new JButton("VIEW UPDATES/CHANGES");
        show_f_request = new JButton("SHOW FRIEND REQUESTS");
        show_friends = new JButton("SHOW ALL FRIENDS");
        exit = new JButton("Logout");
        
        panel = new JPanel(new GridLayout(4, 1));
        panel.add(add_edit_show);
        panel.add(search_obj);
        panel.add(search_user);
        panel.add(show_f_request);
        panel.add(show_friends);
        panel.add(show_k_updates);
        panel.add(exit);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.LIGHT_GRAY);

		frame.setSize(480, 480);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        
        add_edit_show.addActionListener(e -> {
			frame.setVisible(false);
			aesGUI = new AddEditShowGUI(con, prof_id);
		});
        
        search_obj.addActionListener(e -> {
            this.setVisible(false);
            try {
				new SearchObjectGUI(con, prof_id);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        search_user.addActionListener(e -> {
			frame.setVisible(false);
			suGUI = new SearchUserGUI(con, prof_id);
		});
        
        show_friends.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					new ShowFriendsGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
        show_f_request.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					new FriendRequestGUI(con, prof_id);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
        show_k_updates.addActionListener(e -> {
			frame.setVisible(false);
			new ShowKUpdatesGUI(con, prof_id);
		});

        exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				JOptionPane.showMessageDialog(null, "Have a nice day :)\n", "Logged Out", JOptionPane.DEFAULT_OPTION);

				new LoginGUI(con, false);

			}
        	
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        
    }
    
}

