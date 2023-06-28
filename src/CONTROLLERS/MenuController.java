package CONTROLLERS;

import DAO.utilTools;
import MODELS.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public Button custButton;
    public Button apptButton;
    public Button repButton;
    public Label alertDescription;
    Stage stage;
    Parent scene;

    /**
    *directs user to customer page.
    * @actionEvent
    * @throws IOException
    */
    public void custButtonPress(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
    *directs user to appointment page.
    * @param actionEvent
    * @throws IOException
     */
    public void apptButtonPress(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/AppointmentsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
    *directs user to reports page.
    *
    * @param actionEvent
    * @throws IOException
     */
    public void repButtonPress(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/ReportScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
    *LAMBDA #2: an ObservableList is filled with all appointments within the next 15 min
    *the lambda quickly iterates the appointments and alerts are displayed on alert label.
    *@param url
    *@param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            ObservableList<Appointments> filteredAlertList = FXCollections.observableArrayList();
            ObservableList<Appointments> alertList = utilTools.upcomingAlerts();

            if(alertList != null) {

                alertList.forEach(appointments -> {
                    filteredAlertList.add(appointments);
                    alertDescription.setText("appointment in 15 minutes for:\n" +
                            "Appt ID:" + appointments.getAppointment_ID() +
                            " Date & Time: " + appointments.getStartTime() + "\n");
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
