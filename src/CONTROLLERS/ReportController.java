package CONTROLLERS;

import DAO.appointmentsQuery;
import DAO.contactsQuery;
import MODELS.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Collections;
import java.util.ResourceBundle;

/**
* controls logic for report window
 */
public class ReportController implements Initializable {

    public Button menuButton;
    public ComboBox contactCombo;
    public TableView contactTable;
    public TableColumn apptIdCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custIdCol;
    public ComboBox typeCombo;
    public ComboBox monthCombo;
    public Label totalApptCount;
    public ComboBox locationCombo;
    public Label apptCountLocation;

    public TableView monthTypeTable;
    public TableColumn monthCol;
    public TableColumn monthTypeCol;
    public TableColumn countCol;

    public TableView<LocationReport> locationCountTable;
    public TableColumn locationCol;
    public TableColumn secondCountCol;
    public TableView<MonthReport> monthTable;
    public TableColumn locationCountCol;
    public TableView<TypeReport> typeTable;
    public TableColumn typeCountCol;
    public TableColumn typeReportCol;

    Stage stage;
    Parent scene;

    /**
    *directs user to menu page.
    * @param actionEvent
    * @throws IOException
     */
    public void menuButtonClick(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/MenuScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
    *populates table with contacts schedule, and pertinent appointment info.
    * @actionEvent
    * @throws SQLException
     */
    public void contactComboClick(ActionEvent actionEvent) throws SQLException {

        ObservableList<Appointments> apptList = appointmentsQuery.getAppointments();
        ObservableList<Appointments> filteredApptsList = FXCollections.observableArrayList();
        ObservableList<Contacts> contactsList = contactsQuery.getContacts();

        Appointments contactAppts = null;

        String selectedContact = (String) contactCombo.getSelectionModel().getSelectedItem();

        int contact_ID = 0;

        for (Contacts contacts : contactsList) {
            if (selectedContact.equals(contacts.getContact_Name())) {
                contact_ID = contacts.getContact_ID();
            }
        }

        for (Appointments appointments : apptList) {
            if (appointments.getContact_ID() == contact_ID) {
                contactAppts = appointments;
                filteredApptsList.add(contactAppts);
            }
        }
        contactTable.setItems(filteredApptsList);
    }

    /**
    *populates the contacts combo box with contacts
    *populates the month combo box with unique months using OLs and lambdas
    *LAMBDA #3: for each lambda iterates through observable list full of contact, month, and type names
    *every unique name is set into the combo box
    *this is more efficient and saves compute.
     *
    *
    * @param url
    * @param resouceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            //populates the contacts combo box with contacts
            ObservableList<Contacts> contactsList = contactsQuery.getContacts();
            ObservableList<String> contactsNameList = FXCollections.observableArrayList();
            contactsList.forEach(Contacts -> contactsNameList.add(Contacts.getContact_Name()));
            contactCombo.setItems(contactsNameList);

            //populates the month combo box with unique months using OLs and lambdas
            ObservableList<Appointments> apptList = appointmentsQuery.getAppointments();

            ObservableList<String> apptMonthList = FXCollections.observableArrayList();
            ObservableList<String> filteredMonthList = FXCollections.observableArrayList();

            apptList.forEach(Appointments -> apptMonthList.add(String.valueOf(Appointments.getStartDate().getMonth())));

            apptMonthList.stream().filter(month -> {
                return !filteredMonthList.contains(month);
            }).forEach(filteredMonthList::add);

            //populates the types combo box with unique types using lambdas
            ObservableList<String> typeNameList = FXCollections.observableArrayList();
            ObservableList<String> filteredTypeList = FXCollections.observableArrayList();

            apptList.forEach(Appointments -> typeNameList.add(Appointments.getType()));
            typeNameList.stream().filter(type -> {
                return !filteredTypeList.contains(type);
            }).forEach(filteredTypeList::add);

            //populates the location combo box with unique locations using lambdas
            ObservableList<String> locationNameList = FXCollections.observableArrayList();
            ObservableList<String> filteredLocationList = FXCollections.observableArrayList();
            apptList.forEach(Appointments -> locationNameList.add(Appointments.getLocation()));
            locationNameList.stream().filter(location -> {
                return !filteredLocationList.contains(location);
            }).forEach(filteredLocationList::add);

            ObservableList<MonthReport> monthReportList = FXCollections.observableArrayList();

            for (String string: filteredMonthList) {

                String monthName = string;
                int monthCount = Collections.frequency(apptMonthList, string);
                MonthReport apptMonth = new MonthReport(monthName, monthCount);
                monthReportList.add(apptMonth);

            }

            ObservableList<TypeReport> typeReportList = FXCollections.observableArrayList();

            for (String string: filteredTypeList) {

                String typeName = string;
                int typeCount = Collections.frequency(typeNameList, string);
                TypeReport typeAppt = new TypeReport(typeName, typeCount);
                typeReportList.add(typeAppt);

            }

            ObservableList<LocationReport> locationReportList = FXCollections.observableArrayList();

            for (String string: filteredLocationList) {

                String locationName = string;
                int locationCount = Collections.frequency(locationNameList, string);
                LocationReport locationAppt = new LocationReport(locationName, locationCount);
                locationReportList.add(locationAppt);

            }

            //sets info in table
            locationCountTable.setItems(locationReportList);
            typeTable.setItems(typeReportList);
            monthTable.setItems(monthReportList);


            //sets Columns
            locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
            locationCountCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));

            typeReportCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            typeCountCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));

            monthCol.setCellValueFactory(new PropertyValueFactory<>("apptMonth"));
            countCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));

            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}



