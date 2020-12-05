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

public class ShowAlbumCommnetsGUI  extends JFrame {
    AddEditShowGUI aesGUI;
    EditVideoGUI epGUI;
    
    public ShowAlbumCommnetsGUI(Connection con, int prof_id, int album_id) throws SQLException {
        String[]header={"COMMENT", "FIRST NAME", "LAST NAME"};
        DefaultTableModel model=new DefaultTableModel(header,0);
        String sp="{call upsShowAlbumComments(?,?)}";
        JFrame frame=new JFrame("Show Comments");
        JPanel panel = new JPanel();
        JTable table=new JTable();
        JButton back=new JButton("BACK");
        ResultSet rs = null;
        rs = retrieveCommetns(prof_id,album_id,sp,con);
        rs.next();
        String comment = null;
        String fn = null;
        String ln = null;
        model.addRow(header);
        while(rs.next()){
            comment = rs.getString("comment");
            fn = rs.getString("first_name");
            ln = rs.getString("last_name");
            model.addRow(new Object[]{fn,ln,comment});
        }
        
        panel = new JPanel(new GridLayout(1, 2));
        table.setModel(model);
        table.setSize(720,720);
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
					new ShowAlbumsGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            }

        });

    }
    private ResultSet retrieveCommetns(int prof_id,int album_id,String sp,Connection con)throws SQLException{

        CallableStatement cs;
        cs=con.prepareCall(sp);
        cs.setInt(1,prof_id);
        cs.setInt(2,album_id);
        ResultSet rs=cs.executeQuery();

        return rs;
    }
}
