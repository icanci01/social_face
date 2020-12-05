package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ShowVideosGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;
    EditVideoGUI epGUI;

    public ShowVideosGUI(Connection con, int prof_id) throws FileNotFoundException {
        String [] header={"ID","LENGTH","DESCRIPTION","MESSAGE","PRIVACY"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        JFrame frame = new JFrame("Show Videos");
        JPanel panel, panel1, panel2;

        JTable table = new JTable();
        JButton back = new JButton();
        JButton edit = new JButton("EDIT");
        JButton comments = new JButton("SHOW COMMENTS");
        JTextField edit_text = new JTextField("Insert the Video ID to edit an video or see comments");

        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };


        model.addRow(header);
        ResultSet rs = null;
        String sp = "{call upsShowVideos(?)}";
        try {
            rs = retrieveVideos(prof_id, sp, con);
            while(rs.next())
            {
                int id = rs.getInt("id");
                int length = rs.getInt("length");
                String dis = rs.getString("description");
                String mes = rs.getString("message");
                String p = choices[rs.getInt("privacy")-1];

                model.addRow(new Object[]{id, length, dis ,mes, p});
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
        setTitle("Social Life - Show Video Data");
        table.setModel(model);
        table.setSize(720,720);
        panel.setSize(720, 720);
        panel.add(table);
        panel1.add(edit_text);
        panel1.add(edit);
        panel1.add(back);
        panel1.add(comments);
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
                    epGUI = new EditVideoGUI(con, prof_id, Integer.parseInt(edit_text.getText()));
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
        
        comments.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					new ShowVideoCommentsGUI(con, prof_id, Integer.parseInt(edit_text.getText()));
				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });

    }

    private ResultSet retrieveVideos(int prof_id, String sp, Connection con) throws SQLException {

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
