package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
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
import javax.swing.table.DefaultTableModel;


public class ShowBookmarksGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;
    EditBookmarkGUI epGUI;

    public ShowBookmarksGUI(Connection con, int prof_id) throws FileNotFoundException {
        String [] header={"ID","LINK","NAME","CAPTION","DESCRIPTION","MESSAGE","PRIVACY"};
        DefaultTableModel model = new DefaultTableModel(header, 1);

        JFrame frame = new JFrame("Show Bookmarks");
        JPanel panel, panel1, panel2;

        JTable table = new JTable();
        JButton back = new JButton();
        JButton edit = new JButton("EDIT");

        JTextField edit_text = new JTextField("Insert the Bookmark ID to edit an album");

        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };


        model.addRow(header);
        ResultSet rs = null;
        String sp = "{call upsShowBookmarks(?)}";
        try {
            rs = retrieveBookmarks(prof_id, sp, con);
            while(rs.next())
            {
                int id = rs.getInt("id");
                String link = rs.getString("link");
                String name = rs.getString("name");
                String caption = rs.getString("caption");
                String description = rs.getString("description");
                String message = rs.getString("message");
                String p = choices[rs.getInt("privacy")-1];

                model.addRow(new Object[]{id, link,name,caption,description,message ,p});
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        add(js);


        back.setText("BACK");

        panel = new JPanel(new GridLayout(1, 1));
        panel.setBackground(Color.LIGHT_GRAY);

        panel1 = new JPanel(new GridLayout(1,3));
        panel1.setBackground(Color.LIGHT_GRAY);

        panel2 = new JPanel(new GridLayout(2,1));
        panel2.setBackground(Color.LIGHT_GRAY);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Social Life - Show Bookmark Data");
        table.setModel(model);
        table.setSize(720,720);
        panel.setSize(720, 720);
        panel.add(table);
        panel1.add(edit_text);
        panel1.add(edit);
        panel1.add(back);
        panel2.add(panel);
        panel2.add(panel1);
        frame.add(panel2);
        frame.setSize(1080, 720);
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

        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    epGUI = new EditBookmarkGUI(con, prof_id, Integer.parseInt(edit_text.getText()));
                    frame.hide();
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });

    }

    private ResultSet retrieveBookmarks(int prof_id, String sp, Connection con) throws SQLException {

        CallableStatement cs;
        cs = con.prepareCall(sp);
        cs.setInt(1, prof_id);
        ResultSet rs = cs.executeQuery();

        return rs;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
