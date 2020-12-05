
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddEditShowGUI extends JFrame {
	AllFunctionsGUI afGUI;
	AddAlbumGUI aaGUI;
	AddPhotoGUI apGUI;
	AddVideoGUI avGUI;
	AddBookmarkGUI abGUI;
	AddEventGUI aeGUI;
	
	ShowProfileGUI spGUI;
	ShowAlbumsGUI saGUI;
	ShowPhotoGUI sphGUI;
	ShowVideosGUI svGUI;
	ShowBookmarksGUI sbGUI;
	ShowEventGUI seGUI;

	public AddEditShowGUI(Connection con, int prof_id) {
		JFrame frame = new JFrame("ADD / EDIT / SHOW FUNCTIONS");
		JPanel panel;

		JButton add_album, add_photo, add_video, add_bookmark, add_event, show_profile, show_albums, show_photos,
				show_videos, show_bookmarks, show_events, back;

		panel = new JPanel(new GridLayout(6, 1));
		add_album = new JButton("ADD ALBUM");
		add_photo = new JButton("ADD PHOTO");
		add_video = new JButton("ADD VIDEO");
		add_bookmark = new JButton("ADD BOOKMARK");
		add_event = new JButton("ADD EVENT");
		show_profile = new JButton("SHOW PROFILE");
		show_albums = new JButton("SHOW ALBUMS");
		show_photos = new JButton("SHOW PHOTOS");
		show_videos = new JButton("SHOW VIDEOS");
		show_bookmarks = new JButton("SHOW BOOKMARKS");
		show_events = new JButton("SHOW EVENTS");
		back = new JButton("BACK");

		panel = new JPanel(new GridLayout(4, 1));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(add_album);
		panel.add(add_photo);
		panel.add(add_video);
		panel.add(add_bookmark);
		panel.add(add_event);
		panel.add(show_profile);
		panel.add(show_albums);
		panel.add(show_photos);
		panel.add(show_videos);
		panel.add(show_bookmarks);
		panel.add(show_events);
		panel.add(back);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		setTitle("Social Life - Add & Edit");

		frame.setSize(480, 480);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.show();
		
		add_album.addActionListener(e -> {
			frame.setVisible(false);
			try {
				aaGUI = new AddAlbumGUI(con, prof_id);
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}
		});

		add_photo.addActionListener(e -> {

			frame.hide();
			apGUI = new AddPhotoGUI(con, prof_id);

		});

		add_video.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				frame.hide();
				avGUI = new AddVideoGUI(con, prof_id);

			}

		});

		add_bookmark.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				frame.hide();
				abGUI = new AddBookmarkGUI(con, prof_id);

			}

		});

		add_event.addActionListener(e -> {
			frame.setVisible(false);
			try {
				aeGUI = new AddEventGUI(con, prof_id);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		});

		show_profile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					spGUI = new ShowProfileGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		show_albums.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();
				try {
					saGUI = new ShowAlbumsGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		show_photos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();

				try {
					sphGUI = new ShowPhotoGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		show_videos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();

				try {
					svGUI = new ShowVideosGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		show_bookmarks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();

				try {
					sbGUI = new ShowBookmarksGUI(con, prof_id);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});



		show_events.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.hide();

				try {
					seGUI = new ShowEventGUI(con, prof_id);
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

}
