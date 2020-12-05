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
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class EditVideoGUI extends JFrame implements ActionListener {

    public EditVideoGUI(Connection con, int prof_id, int video_id) throws FileNotFoundException {
        JFrame frame = new JFrame("Edit Video");
        JPanel panel;

        String startD;

        JLabel  dis, length, mes,priv, lengtht;

        JTextField dist,mest;

        JButton back = new JButton();
        JButton save = new JButton();

        int  pri = 0;

        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };
        final JComboBox<String> privacy = new JComboBox<String>(choices);

        panel = new JPanel(new GridLayout(5, 2));

        dis = new JLabel("Description: ");
        dist = new JTextField();
        length = new JLabel("Length: ");
        lengtht = new JLabel();
        mes = new JLabel("Message: ");
        mest = new JTextField();
        priv = new JLabel("Privacy: ");



        ResultSet rs = null;
        String sp = "{call upsShowVideo(?,?)}";

        try {
            rs = retrieveVideo(prof_id, video_id, sp, con);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            rs.next();
            dist.setText(rs.getString("description"));
            lengtht.setText(rs.getString("length"));
            mest.setText(rs.getString("message"));
            pri = rs.getInt("privacy") - 1;;
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        back.setText("BACK");
        save.setText("SAVE CHANGES");



        panel.add(dis);
        panel.add(dist);
        
        panel.add(length);
        panel.add(lengtht);


        panel.add(mes);
        panel.add(mest);



        privacy.setSelectedIndex(pri);
        panel.add(priv);
        panel.add(privacy);

        panel.add(back);
        panel.add(save);


        panel.setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Social Life - Show Video Data");

        frame.setSize(480, 480);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.show();

        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.hide();
    			try {
					new ShowVideosGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            }

        });

        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String dir = null, link = null, sp;
                sp = "{call upsEditVideo(?,?,?,?,?)}";

                int priv;

                boolean flag = false;

                String description ="";
                String message ="";
                if (dist.getText().length() != 0)
                    description = dist.getText();

                if (mest.getText().length() != 0)
                    message = mest.getText();

                priv = privacy.getSelectedIndex() + 1;

                try {
                    flag = editVideo(video_id, prof_id, description, message, priv, sp, con);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                if (flag)
                    JOptionPane.showMessageDialog(null, "Video data changed :)\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "The video data was not changed :(\n", "Error", JOptionPane.ERROR_MESSAGE);

                frame.hide();

                try {
                   new ShowVideosGUI(con, prof_id);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

        });


    }



    private ResultSet retrieveVideo(int prof_id, int video_id, String sp, Connection con) throws SQLException {

        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, prof_id);
        cs.setInt(2, video_id);
        ResultSet rs = cs.executeQuery();

        return rs;
    }


    private boolean editVideo(int video_id, int prof_id, String description,String message, int priv,
                              String sp, Connection con) throws SQLException {

        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, video_id);
        cs.setInt(2, prof_id);
        cs.setString(3, message);
        cs.setString(4, description);
        cs.setInt(5,priv);

        cs.execute();

        return true;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
