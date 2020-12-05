package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPhotoGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;

    public AddPhotoGUI(Connection con, int prof_id){
        JFrame frame = new JFrame("Add new Photo");

        JPanel panel, panel1;
        JLabel directory_label, privacy_label, album_label;
        JTextField directory_text, add_album_text;
        JButton submit, back;

        submit = new JButton("SUBMIT");
        back = new JButton("BACK");
        directory_label = new JLabel();
        directory_label.setText("Directory :");
        album_label = new JLabel("Add photo to album no. :");
        directory_text = new JTextField();
        add_album_text = new JTextField();
        privacy_label = new JLabel();
        privacy_label.setText("Privacy :");
        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
        final JComboBox<String> privacy = new JComboBox<String>(choices);

        panel = new JPanel(new GridLayout(4, 2));

        panel.add(directory_label);
        panel.add(directory_text);
        panel.add(privacy_label);
        panel.add(privacy);
        panel.add(album_label);
        panel.add(add_album_text);
        panel.add(submit);
        panel.add(back);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Social Life - Add Album");

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
        
        submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ResultSet rs = null;
				
				String dir = null;
				String sp = "{call upsAddPhoto(?,?,?,?,?,?)}";
				int r = (int)(Math.random() * (9999 - 1000) + 1000);
				int priv = 0;
				String link = "www.photo" + prof_id + "" + r + ".com";
				int height = (int)((Math.random() * (1080 - 260)) + 260);
				int width  = (int)((Math.random() * (1080 - 260)) + 260);
				if (directory_text.getText().length() != 0) dir = directory_text.getText();
				priv = privacy.getSelectedIndex() + 1;
				boolean flag = false;
				
				try {
					
					rs = addPhoto(prof_id, dir, height, width, link, priv, sp, con);
					if (rs != null) flag = true;
					if (add_album_text.getText().length() != 0) {
						sp = "{call upsInsertPhotoToAlbum(?,?)}";
						rs.next();
						int id = rs.getInt("id");
						addToAlbum(Integer.parseInt(add_album_text.getText()), id,  sp, con);

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Photo Added :)\n", "Job Done!",
						JOptionPane.INFORMATION_MESSAGE);
				else
				JOptionPane.showMessageDialog(null, "The photo was not added :(\n", "Error",
						JOptionPane.ERROR_MESSAGE);
				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
        	
        });
    }
    
	private ResultSet addPhoto(int my_id, String dir, 
			int height, int width, String link, int priva, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, dir);
		cs.setInt(3, height);
		cs.setInt(4, width);
		cs.setString(5, link);
		cs.setInt(6, priva);

		return(cs.executeQuery());

	}
	
	private boolean addToAlbum(int video_id, int alb_id, String sp, Connection con) throws SQLException {
		CallableStatement cs;
		cs = con.prepareCall(sp);

		cs.setInt(1, video_id);
		cs.setInt(2, alb_id);

		cs.execute();

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
