package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainGUI {

	private final String url = "jdbc:sqlserver://APOLLO.IN.CS.UCY.AC.CY:1433;databaseName=gproko01;user=gproko01;password=3cp2e7UW;";
	private Connection con = null;
	private FirstGUI fGUI;
	
	/**
	 * Initiate connection 
	 */
	private void initiateCon() {
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.printf("Connected Successfully\n");
	}
	
	private void run() {
		// -- MAIN FRAME -- //
		fGUI = new FirstGUI(con);
		new AllFunctionsGUI(con,1);
	}
	public static void main (String[] args) {
		MainGUI obj = new MainGUI();
		
		obj.initiateCon();
		obj.run();
	}
}
