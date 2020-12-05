package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AddBookmarkGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;

    public AddBookmarkGUI(Connection con, int prof_id){
        JFrame frame = new JFrame("Add new Bookmark");

        JPanel panel;
        JLabel message_label, description_label,privacy_label,name_label,caption_label,link_label;
        JTextField message_text, description_text,name_text,caption_text,link_text;
        JButton submit, back;

        submit = new JButton("SUBMIT");
        back = new JButton("BACK");

        link_label = new JLabel();
        link_label.setText("Link :");
        link_text = new JTextField();
        
        name_label = new JLabel();
        name_label.setText("Name :");
        name_text = new JTextField();

        caption_label = new JLabel();
        caption_label.setText("Caption :");
        caption_text = new JTextField();

        description_label = new JLabel();
        description_label.setText("Description :");
        description_text = new JTextField();

        message_label = new JLabel();
        message_label.setText("Message :");
        message_text = new JTextField();
       
        privacy_label = new JLabel();
        privacy_label.setText("Privacy :");
        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
        final JComboBox<String> privacy = new JComboBox<String>(choices);

        panel = new JPanel(new GridLayout(7, 2));

        panel.add(link_label);
        panel.add(link_text);
        
        panel.add(name_label);
        panel.add(name_text);
        
        panel.add(caption_label);
        panel.add(caption_text);
        
        panel.add(description_label);
        panel.add(description_text);
        
        panel.add(message_label);
        panel.add(message_text);
       
        panel.add(privacy_label);
        panel.add(privacy);
        
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
				String link = null, name = null, capt = null, descri = null,
						mess = null, sp;
				sp = "{call upsAddBookmark(?,?,?,?,?,?,?)}";
				boolean flag = false;
				
				if (link_text.getText().length() != 0) link = link_text.getText();
				if (name_text.getText().length() != 0) name = name_text.getText();
				if (caption_text.getText().length() != 0) capt = caption_text.getText();
				if (description_text.getText().length() != 0) descri = description_text.getText();
				if (message_text.getText().length() != 0) mess = message_text.getText();
				
				int priv = privacy.getSelectedIndex() + 1;
				
				try {
					flag = addBookmark(prof_id, link, name, capt, descri, mess, priv, sp, con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (flag)
					JOptionPane.showMessageDialog(null, "Bookmark Added :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The bookmark was not added :(\n", "Error", JOptionPane.ERROR_MESSAGE);

				frame.hide();
				aesGUI = new AddEditShowGUI(con, prof_id);
			}
        	
        });
    }
    
	private boolean addBookmark(int my_id, String link, String name, String capt,
			String descri, String mess, int priva, String sp, Connection con) throws SQLException {

		CallableStatement cs;
		cs = con.prepareCall(sp);
		cs.setInt(1, my_id);
		cs.setString(2, link);
		cs.setString(3, name);
		cs.setString(4, capt);
		cs.setString(5, descri);
		cs.setString(6, mess);
		cs.setInt(7, priva);

		cs.executeQuery();

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
