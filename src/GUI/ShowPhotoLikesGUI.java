package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowPhotoLikesGUI {
        AddEditShowGUI aesGUI;
        EditVideoGUI epGUI;
        
        public ShowPhotoLikesGUI(Connection con, int prof_id,int photo_id) throws SQLException {
        	String[] header = new String[] {"First Name", "Last Name"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sp="{call upsShowLikes(?,?)}";
            JFrame frame=new JFrame("Show Comments");
            JPanel panel = new JPanel();
            JTable table=new JTable();
            JButton back=new JButton("BACK");
            ResultSet rs = null;
            rs = retrieveLikes(prof_id,photo_id,sp,con);
            rs.next();
            model.addRow(header);

           
            while(rs.next()){
                String fn = rs.getString("first_name");
                String ln = rs.getString("last_name");
                model.addRow(new Object[]{fn, ln});
            }
            table.setModel(model);
            table.setSize(720,720);
            
            panel = new JPanel(new GridLayout(1, 2));
            panel.setSize(720, 720);
            panel.add(table);
            panel.add(back);
            frame.setSize(1080, 720);
            frame.setLocationRelativeTo(null);
            frame.add(panel);
            frame.show();
            
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    frame.hide();
                    try {
						new ShowPhotoGUI(con, prof_id);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

                }

            });

        }
    private ResultSet retrieveLikes(int prof_id,int photo_id,String sp,Connection con)throws SQLException{

        CallableStatement cs;
        cs=con.prepareCall(sp);
        cs.setInt(1,prof_id);
        cs.setInt(2,photo_id);
        ResultSet rs=cs.executeQuery();

        return rs;
    }
}
