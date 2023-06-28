package CONTROLLERS;

import DAO.userQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import MAIN.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
*the login screen controller.
 */
public class LogInController implements Initializable {

    public Button logInButton;
    public Label LoginLabel;
    public Label PasswordLabel;
    public Label UsernameLabel;
    public javafx.scene.control.PasswordField PasswordField;
    public TextField UsernameField;
    public Label locationDisplay;
    public Label locationLbl;
    Stage stage;
    Parent scene;
    ResourceBundle rb;

    /**
     *prepares login format and logic for login attempts.
     *@param loginString
     *@throws IOException
     */
    private void loginRecord(String loginString) throws IOException {

        try ( FileWriter fw = new FileWriter("login_activity.txt", true)) {
            Date loginDate = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            fw.write(loginString + format.format(loginDate) + "\n");

        }
    }

    /**
     *calls the confirmUser function to verify username and password.
     *displays error if username or password are incorrect
     *writes login attempts to login_activity.txt.txt
     *
     * @param event log in buttonClick
     * @throws IOException
     */
    @FXML
    void buttonClick(ActionEvent event) throws IOException {

        String usernameField = UsernameField.getText();
        String passwordField = PasswordField.getText();

        //calls the confirmUser function to verify username and password
        if (userQuery.confirmUser(usernameField, passwordField)) {

            loginRecord(usernameField + " logged on at ");

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/VIEW/MenuScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        //displays error if username and password are incorrect
        if (userQuery.confirmUser(usernameField, passwordField) == false) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("ERROR"));
            alert.setHeaderText(rb.getString("INCORRECT"));
            alert.setContentText(rb.getString("ENTER"));
            alert.showAndWait();

            loginRecord(usernameField + " failed log on at ");

        }
    }

    /**
    *initializes the page labels with dynamic language dependant on locality.
    *
    * @param url
    * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ZoneId zoneId = ZoneId.systemDefault();
        locationDisplay.setText(String.valueOf(zoneId));
        this.rb = rb;
        System.out.println(Locale.getDefault());
        locationLbl.setText((rb.getString("LOCATION")));
        logInButton.setText(rb.getString("LOGIN"));
        LoginLabel.setText(rb.getString("LOGIN"));
        PasswordLabel.setText(rb.getString("PASSWORD"));
        UsernameLabel.setText(rb.getString("USERNAME"));
        Main.getStage().setTitle(rb.getString("LOGIN"));

    }
}
