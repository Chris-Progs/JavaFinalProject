
package project2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class SignInWindow {
    private Stage SignInWindow;
    private TextField nameTextField;
    private TextField pwTextField;
    private boolean success = false;
    
    public boolean display() {

        SignInWindow = new Stage();
        SignInWindow.setTitle("Create Account & Sign In");

        Label userLabel = new Label();
        userLabel.setText("Username:");

        Label pwLabel = new Label();
        pwLabel.setText("Password:");

        nameTextField = new TextField();
        pwTextField = new PasswordField();

        Button loginBtn = new Button();
        loginBtn.setText("Sign in");
        loginBtn.setOnAction(e -> {
            try {
                if(Users.signIn(nameTextField.getText(), pwTextField.getText())){
                    Project2.addBtn.setDisable(false);
                    Project2.saveBtn.setDisable(false);
                    Project2.loadBtn.setDisable(false);
                    Project2.searchBtn.setDisable(false);
                    Project2.playBtn.setDisable(false);
                }
            } catch (Exception ex) {
                // If user name is incorrect
                SignInWindow.close();
            }
            SignInWindow.close();           
        });

        Button createButton = new Button();
        createButton.setText("Create account");
        createButton.setOnAction(e -> {
            try {
                Users.createAccount(nameTextField.getText(), pwTextField.getText());
            } catch (Exception exc) {
                System.out.println(exc);
            }
            SignInWindow.close();
        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setSpacing(160);
        hBox.getChildren().addAll(loginBtn, createButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(userLabel, nameTextField, pwLabel, pwTextField, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");

        Scene scene = new Scene(vBox);
        SignInWindow.setScene(scene);
        SignInWindow.show();
        return success;
    }
}
