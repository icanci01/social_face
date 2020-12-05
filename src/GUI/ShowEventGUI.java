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


public class ShowEventGUI extends JFrame implements ActionListener {
    AddEditShowGUI aesGUI;
    EditEventGUI eeGUI;
    
    public ShowEventGUI(Connection con, int prof_id) throws FileNotFoundException {
    	String [] header={"ID","NAME","DESCRIPTION","START TIME","END TIME", "LOCATION", "VENUE", "PRIVACY"};
        DefaultTableModel model = new DefaultTableModel(header, 1);
        
        JFrame frame = new JFrame("Show Events");
        JPanel panel, panel1, panel2;
        
        JTable table = new JTable();
        JButton back = new JButton();
        JButton edit = new JButton("EDIT");
        
        JTextField edit_text = new JTextField("Insert the Event ID to edit an event");
        
        String[] choices = { "OPEN", "CLOSED", "FRIEND", "NETWROK", };

        Scanner inFile1 = new Scanner(new File("CITY.txt")).useDelimiter(",\\s*");
        String token1 = "";
        ArrayList city = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
            token1 = inFile1.nextLine();
            city.add(token1);
        }

        String[] cit = new String[city.size()];
        for (int i = 0; i < city.size(); i++)
            cit[i] = city.get(i).toString();
        final JComboBox<String> location = new JComboBox<String>(cit);

        
       

        inFile1 = new Scanner(new File("LOCATION.txt")).useDelimiter(",\\s*");
        token1 = "";
        ArrayList locations = new ArrayList<>();
        inFile1.nextLine();
        while (inFile1.hasNextLine()) {
            // find next line
        	token1 = inFile1.nextLine();
        	String[] split = token1.split("\t");
        	String tok = split[0] + ", " + split[1] + ", " + split[2];
        	
            locations.add(tok);
        }
        String[] loc = new String[locations.size()];
        for (int i = 0; i < locations.size(); i++)
            loc[i] = locations.get(i).toString();
        final JComboBox<String> venue = new JComboBox<String>(loc);
        
        
        model.addRow(header);
        ResultSet rs = null;
        String sp = "{call upsShowEvents(?)}";
        try {
            rs = retrieveEvent(prof_id, sp, con);
            while(rs.next())
            {
            	int id = rs.getInt("id");
            	String name = rs.getString("name");
            	String desc = rs.getString("description"); 
            	String start = rs.getString("start_time");
            	String end = rs.getString("end_time");
            	String lo = loc[rs.getInt("venue") - 1];
            	String ven = cit[rs.getInt("location") - 1];
            	String priv = choices[rs.getInt("privacy") - 1];
            	
                model.addRow(new Object[]{id, name, desc,start, end, lo, ven, priv});
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
        setTitle("Social Life - Show Profile Data");
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
				frame.hide();
				try {
					eeGUI = new EditEventGUI(con, prof_id, Integer.parseInt(edit_text.getText()));
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
    
    private ResultSet retrieveEvent(int prof_id, String sp, Connection con) throws SQLException {

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
