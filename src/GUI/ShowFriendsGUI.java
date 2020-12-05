package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowFriendsGUI extends JFrame {
    private final String[] header = {"ID", "FIRST NAME", "LAST NAME"};
    private ResultSet mostPop = null;

    public ShowFriendsGUI(Connection con, int prof_id) {

        JFrame frame = new JFrame("Show Friends");
        JPanel panel, panel1;
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(header, 0);

        JButton most_popular = new JButton("FIND MOST POPULAR FRIEND");
        JButton back = new JButton("BACK");
        JButton delete = new JButton("DELETE");
        JTextField text = new JTextField("Insert profile's id to delete from friends");

        ResultSet rs = null;
        ResultSet mostPop = null;
        String sp = "{call upsSeeAllFriends(?)}";

        try {
            rs = retrieveFriends(prof_id, sp, con);
            rs.next();

            model.addRow(header);
            while (rs.next()) {
                int id = rs.getInt("id");
                String fn = rs.getString("first_name");
                String ln = rs.getString("last_name");
                model.addRow(new Object[]{id, fn, ln});
            }

            panel = new JPanel(new GridLayout(1, 2));
            table.setModel(model);
            panel.add(table);

            panel1 = new JPanel(new GridLayout(4, 1));
            panel1.add(text);
            panel1.add(delete);
            panel1.add(most_popular);
            panel1.add(back);

            panel.add(panel1);

            panel.setBackground(Color.LIGHT_GRAY);
            panel1.setBackground(Color.LIGHT_GRAY);

            frame.setSize(1080, 1080);
            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            back.addActionListener(e -> {
                frame.setVisible(false);
                new AllFunctionsGUI(con, prof_id);
            });

            most_popular.addActionListener(e -> {
                try {
                    if(mostPop.next()){
                       // model.remove(); -- Here we should reove all the data from the model or show a different model.
                        int id = mostPop.getInt("id");
                        String fn = mostPop.getString("first_name");
                        String ln = mostPop.getString("last_name");
                        model.addRow(new Object[]{id, fn, ln});


                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            });


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        delete.addActionListener(e -> {
            String sp1 = "{call upsDeleteAFriend(?,?)}";
            boolean flag = false;

            if (text.getText().length() == 0) return;

            int other_id = Integer.parseInt(text.getText());
            try {
                flag = manageFriend(other_id, prof_id, sp1, con);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            if (flag)
                JOptionPane.showMessageDialog(null, "Friend deleted :(\n", "Job Done!", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Friend did not deleted :)\n", "Error", JOptionPane.ERROR_MESSAGE);

            frame.setVisible(false);
            new ShowFriendsGUI(con, prof_id);

        });
    }

    private boolean manageFriend(int other_id, int prof_id, String sp, Connection con) throws SQLException {
        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, other_id);
        cs.setInt(2, prof_id);
        cs.execute();

        return true;
    }

    private static ResultSet retrieveFriends(int prof_id, String sp, Connection con) throws SQLException {
        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, prof_id);
        return (cs.executeQuery());

    }

    private static ResultSet retrieveMostPopular(int profile_id, Connection con) throws SQLException {
        String sp1 = "{call upsFindMostPopularFriend(?)}";
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(sp1);
            cs.setInt(1, profile_id);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return (cs.executeQuery());
    }
}
