
package project2;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpWindow {

    private Stage helpWindow;

    public void display() {
        
        helpWindow = new Stage();
        helpWindow.setTitle("User Help");
        
        Label userLabel = new Label();
        userLabel.setText("To use the program you must create an account and then click the same button to sign in.");
        
        Label userLabel2 = new Label();
        userLabel2.setText("If the user name and password do not match you will need to try again.");
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userLabel, userLabel2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");
        
        Scene scene = new Scene(vBox);
        helpWindow.setScene(scene);
        helpWindow.show();
    }
}
