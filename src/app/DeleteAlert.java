package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteAlert {

    private static boolean answer;
    private static final int min_width = 450;
    private static final int min_height = 200;

    public static boolean displayDelete_alert() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirmation window");
        window.setMinWidth(min_width);
        window.setMinHeight(min_height);

        Label label = new Label("Are you sure you want to delete the database?");
        label.setFont(new Font("Comic Sans", 20));

        // -- Buttons needed -- //
        Button yes_btn, no_btn;

        yes_btn = new Button("Yes!");
        yes_btn.setOnAction(event -> {
            answer = true;
            window.close();
        });

        no_btn = new Button("No!");
        no_btn.setOnAction(event -> {
            answer = false;
            window.close();
        });


        VBox login_box = new VBox(10);
        login_box.setAlignment(Pos.CENTER);
        login_box.getChildren().addAll(label, yes_btn, no_btn);

        Scene scene = new Scene(login_box);
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }
}
