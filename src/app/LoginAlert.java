package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginAlert {

//    private static String dbURL = "jdbc:sqlserver://APOLLO.IN.CS.UCY.AC.CY:1433;";
//    private static String dbName;
//    private static String dbUser;
//    private static String dbPassword;
    private static final int min_width = 350;
    private static final int min_height = 400;


    public static void displayLogin_alert() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login to the DATABASE!");
        window.setMinWidth(min_width);
        window.setMinHeight(min_height);

        Label label = new Label("Enter database credentials");
        label.setFont(new Font("Comic Sans", 20));

        // --- User input text fields --- //


        // --- Buttons needed --- ///
        Button login_btn, back_btn, exit_btn;
        login_btn = new Button("Submit");
        login_btn.setOnAction(event -> {
            System.out.println("LOGIN BUTTON PRESSED");
            System.out.println("Run authentication procedure");
        });

        back_btn = new Button("Go Back");
        back_btn.setOnAction(event -> window.close());
        exit_btn = new Button("Exit Application");
        exit_btn.setOnAction(event -> {
            System.out.println("APPLICATION WILL EXIT");
            System.exit(0);
        });

        VBox login_box = new VBox(10);
        login_box.setAlignment(Pos.CENTER);
        login_box.getChildren().addAll(label, login_btn, back_btn, exit_btn);

        Scene scene = new Scene(login_box);
        window.setScene(scene);
        window.showAndWait();

    }

}
