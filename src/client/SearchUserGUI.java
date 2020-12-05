package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SearchUserGUI extends JFrame implements ActionListener{
	AllFunctionsGUI afGUI;
	SearchAdvanceUserGUI sauGUI;
	SearchSimpleUserGUI ssuGUI;

	public SearchUserGUI(Connection con, int prof_id) {
		
		JFrame frame = new JFrame();
		JPanel panel;
		
		JButton simple_search, advance_search, back;
		simple_search = new JButton("Simple Search");
		advance_search = new JButton("Advance Search");
		back = new JButton("BACK");
		
        panel = new JPanel(new GridLayout(3, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.LIGHT_GRAY);
		
		panel.add(simple_search);
		panel.add(advance_search);
		panel.add(back);
	
		frame.setTitle("Search for Profiles");
		frame.setSize(480, 480);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.show();
		
		simple_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					ssuGUI = new SearchSimpleUserGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});

		advance_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					sauGUI = new SearchAdvanceUserGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				afGUI = new AllFunctionsGUI(con, prof_id);
			}
			
		});

		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
