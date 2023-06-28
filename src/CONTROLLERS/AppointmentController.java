package CONTROLLERS;

import DAO.*;
import MAIN.JDBC;
import MODELS.Appointments;
import MODELS.Contacts;
import MODELS.Customers;
import MODELS.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
*appointment controller.
 */
public class AppointmentController implements Initializable {

    public Button menuButton;
    public Button createButton;
    public Button deleteButton;
    public ComboBox contactBox;
    public TableView apptTable;
    public TableColumn apptIdColumn;
    public TableColumn titleColumn;
    public TableColumn descColumn;
    public TableColumn locationColumn;
    public TableColumn contactNameColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn custIdColumn;
    public TableColumn userIdColumn;
    public TextField locationField;
    public TextField titleField;
    public TextField typeField;
    public TextArea descField;
    public TextField apptIdField;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo;
    public ComboBox customerBox;
    public ComboBox userBox;
    public RadioButton allRadioBustton;
    public RadioButton monthlyRadioButton;
    public RadioButton weeklyRadioButton;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public Button saveCreate;

    Stage stage;
    Parent scene;

    /** populates text fields and combo boxes to modify a selected customer.
    *@param mouseEvent click in appointment table
     */
    public void selectAppointment(MouseEvent mouseEvent) {

        Appointments selectedItem = (Appointments) apptTable.getSelectionModel().getSelectedItem();

        //pop text fields
        locationField.setText(selectedItem.getLocation());
        startDatePicker.setValue(LocalDate.parse(String.valueOf(selectedItem.getStartDate())));
        endDatePicker.setValue(LocalDate.parse(String.valueOf(selectedItem.getEndDate())));
        titleField.setText(selectedItem.getTitle());
        typeField.setText(selectedItem.getType());
        descField.setText(selectedItem.getDescription());
        apptIdField.setText(Integer.toString(selectedItem.getAppointment_ID()));
        //pop combo boxes
        contactBox.setValue(String.valueOf(selectedItem.getContact_ID()));
        customerBox.setValue(String.valueOf(selectedItem.getCustomer_ID()));
        userBox.setValue(String.valueOf(selectedItem.getUser_ID()));
        startTimeCombo.setValue(String.valueOf(selectedItem.getStartTime().toLocalTime()));
        endTimeCombo.setValue(String.valueOf(selectedItem.getEndTime().toLocalTime()));

    }
    /**
    *populates appointment table with all appointments.
    *
    *@param actionEvent radio button click
    *@throws SQLException
    */
    public void allRadioClick(ActionEvent actionEvent) throws SQLException {

        apptTable.setItems(appointmentsQuery.getAppointments());
    }

    /**
    displays all appointments within the next week.
    @param actionEvent radio button click
    */
    public void monthlyRadioClick(ActionEvent actionEvent) {

        apptTable.setItems(utilTools.getMonthlyAppointments());
    }

    /** displays all appointments within the next month.
    * @param actionEvent radio button click
    * */
    public void weeklyRadioClick(ActionEvent actionEvent) {

        apptTable.setItems(utilTools.getWeeklyAppointments());
    }

    /** extracts customer_ID from the selected combo box and replaces all string character will blank fields.
    * @return int of customer_ID without string
     */
    public int extractCustomerID() {

        String raw = String.valueOf(customerBox.getValue());
        String filtered = raw.replaceAll("\\D+", "");
        System.out.println("filtered");
        return Integer.parseInt(filtered);

    }

    /**extracts User_ID from the selected combo box band replaces all string character will blank fields.
    * @return int of user ID
     */
    public int extractUserID() {

        String raw = String.valueOf(userBox.getValue());
        String filtered = raw.replaceAll("\\D+", "");
        return Integer.parseInt(filtered);

    }

    /**extracts Contact_ID from the selected combo box and replaces all string character will blank fields.
    * @return int of Contact ID
    * */
    public int extractContactID() {

        String raw = String.valueOf(contactBox.getValue());
        String filtered = raw.replaceAll("\\D+", "");
        return Integer.parseInt(filtered);

    }

    /**
    ensures the same customer doesn't have more than one appointment at a time.
    @throws SQLException
    @return boolean, appointment overlap
     */
    public boolean overlapApptCheck() throws SQLException {

        int appointment_ID;

        //collects date and time, combines to compare
        LocalDateTime apptStart = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime apptEnd = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));

        //collects customer ID from form to reference and compare against appts in database
        ObservableList<Appointments> apptCustomer_ID = utilTools.getCustomer_ID(extractCustomerID());

        if (apptIdField.getText().isEmpty()) {
            appointment_ID = 0;
        } else {
            appointment_ID = Integer.parseInt(apptIdField.getText());
        }

        if (apptCustomer_ID != null) {

            for (Appointments appointments : apptCustomer_ID) {
                if (appointment_ID == appointments.getAppointment_ID()) {

                    return false;
                }

                if (appointments.getStartTime().isBefore(apptEnd) && apptStart.isBefore(appointments.getEndTime())) {

                    return true;
                }
            }
        }return false;
    }

    /**
    *ensures the schedules end time doesn't start before start time and vice versa.
    *
    *@return endTime
    */
    public boolean timeError() {

        LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(),LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime endTime = LocalDateTime.of(endDatePicker.getValue(),LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));
        return endTime.isBefore(startTime);

    }

    /** clears fields.
    * @param actionEvent button click
    * @throws IOException
    * */
    public void createButtonClick(ActionEvent actionEvent) throws IOException {

        locationField.clear();
        titleField.clear();
        typeField.clear();
        descField.clear();
        apptIdField.clear();
        startTimeCombo.setValue(null);
        endTimeCombo.setValue(null);
        customerBox.setValue(null);
        userBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        contactBox.setValue(null);

    }

    /**
    *deletes appointment from database and table.
     *@param actionEvent button click
     *@throws SQLException
     */
    public void deleteButtonClick(ActionEvent actionEvent) throws SQLException {

        Appointments selected = (Appointments) apptTable.getSelectionModel().getSelectedItem();

        int appointment_id = selected.getAppointment_ID();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("are you sure you would like to delete the selected appointment?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointment_id);
            ps.execute();

            ObservableList<Appointments> updateAppts = appointmentsQuery.getAppointments();
            apptTable.setItems(updateAppts);
        }
    }

    /**
    *updates selected appointment or saves newly created appointment
    *@param actionEvent
    *@throws SQLException
    */
    public void saveCreateClick(ActionEvent actionEvent) throws SQLException {

        try {

            if (titleField.getText().isEmpty() || descField.getText().isEmpty() || locationField.getText().isEmpty() || contactBox.getValue() == null
                    || typeField.getText().isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null || startTimeCombo.getValue() == null ||
                    endTimeCombo.getValue() == null || customerBox.getValue() == null || userBox.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("fields missing");
                alert.showAndWait();
                return;
            } else if (timeError()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("the appointments end time begins before start time");
                alert.showAndWait();
                return;

            } else if (overlapApptCheck()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("conflicting appointment times");
                alert.showAndWait();
                return;

            } else if (apptIdField.getText().isEmpty()) {

                String title = titleField.getText();
                String desc = descField.getText();
                String location = locationField.getText();
                int customer_ID = extractCustomerID();
                String type = typeField.getText();
                LocalDateTime startDateTime = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
                LocalDateTime endDateTime = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));
                int contact_ID = extractContactID();
                int user_ID = extractUserID();
                // gives appointment ID to appointment
                int appt_ID = (int) (Math.random() * 11111);

                String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type," +
                        " Start, End, Customer_ID, User_ID, Contact_ID)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

                PreparedStatement rs = JDBC.connection.prepareStatement(sql);

                rs.setInt(1, appt_ID);
                rs.setString(2, title);
                rs.setString(3, desc);
                rs.setString(4, location);
                rs.setString(5, type);
                rs.setTimestamp(6, Timestamp.valueOf(startDateTime));
                rs.setTimestamp(7, Timestamp.valueOf(endDateTime));
                rs.setInt(8, customer_ID);
                rs.setInt(9, user_ID);
                rs.setInt(10, contact_ID);
                rs.execute();
                System.out.println("check");

                ObservableList<Appointments> updateAppointments = appointmentsQuery.getAppointments();
                apptTable.setItems(updateAppointments);

                locationField.clear();
                titleField.clear();
                typeField.clear();
                descField.clear();
                apptIdField.clear();
                startTimeCombo.setValue(null);
                endTimeCombo.setValue(null);
                customerBox.setValue(null);
                userBox.setValue(null);
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
                contactBox.setValue(null);

            } else try {

                int appt_ID = Integer.parseInt(apptIdField.getText());
                String title = titleField.getText();
                String desc = descField.getText();
                String location = locationField.getText();
                Integer customer_ID = extractCustomerID();
                String type = typeField.getText();
                LocalDateTime startDateTime = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
                LocalDateTime endDateTime = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));
                int contact_ID = extractContactID();
                int user_ID = extractUserID();

                //updates appointments to database
                String Query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?," +
                        " Customer_ID = ?, Contact_ID = ?, User_ID = ?, Type = ?, Start = ?, End = ? WHERE Appointment_ID = ?";
                JDBC.makePreparedStatement(Query, JDBC.getConnection());
                PreparedStatement ps = JDBC.getPreparedStatement();

                ps.setString(1, title);
                ps.setString(2, desc);
                ps.setString(3, location);
                ps.setInt(4, customer_ID);
                ps.setInt(5, contact_ID);
                ps.setInt(6, user_ID);
                ps.setString(7, type);
                ps.setTimestamp(8, Timestamp.valueOf(startDateTime));
                ps.setTimestamp(9, Timestamp.valueOf(endDateTime));
                ps.setInt(10, appt_ID);
                ps.execute();

                ObservableList<Appointments> updateAppointments = appointmentsQuery.getAppointments();
                apptTable.setItems(updateAppointments);

                locationField.clear();
                titleField.clear();
                typeField.clear();
                descField.clear();
                apptIdField.clear();
                startTimeCombo.setValue(null);
                endTimeCombo.setValue(null);
                customerBox.setValue(null);
                userBox.setValue(null);
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
                contactBox.setValue(null);

            }catch (Exception e) {
                e.printStackTrace();
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
    *returns user back to menu screen.
     * @throws IOException
    */
    public void clickMenuBttn(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/MenuScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
    *initializes page with appointments in a table
    *LAMBDA #1: used to populate the contact, customer, and users with relevant info
    *corresponding combo boxes are populated using two observable lists
    *one list is populated with all relevant info and filtered using a lambda and for each
    *filtered information is deposited into the second observable list
    *that info is then set into combo box
    *this method is quick and efficient saving compute and time.
    *
    * @param url
    * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Appointments> apptList = appointmentsQuery.getAppointments();

            apptTable.setItems(apptList);

            apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
            contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));

            //populate combo boxes
            ObservableList<String> contactCombo = FXCollections.observableArrayList();
            ObservableList<String> usersCombo = FXCollections.observableArrayList();
            ObservableList<String> timeCombo = FXCollections.observableArrayList();
            ObservableList<String> customerCombo = FXCollections.observableArrayList();

            //lambda to populate contacts combo box
            ObservableList<Contacts> contactsList = contactsQuery.getContacts();
            contactsList.forEach(Contacts -> contactCombo.add(Contacts.getContact_Name() + " " + Contacts.getContact_ID()));

            ObservableList<Users> usersList = userQuery.getUsers();
            usersList.forEach(Users -> usersCombo.add(Users.getUser_Name() + " " + Users.getUser_ID()));

            ObservableList<Customers> customersList = customersQuery.getCustomers();
            customersList.forEach(Customers -> customerCombo.add(Customers.getCustomer_Name() + " " + Customers.getCustomer_ID()));

            //populate time combo boxes
            LocalTime startTime = LocalTime.of(8,0);
            LocalTime endTime = LocalTime.of(22,0);
            timeCombo.add(startTime.toString());

                while (startTime.isBefore(endTime)) {

                startTime = startTime.plusMinutes(15);
                timeCombo.add(startTime.toString());
            }

            startTimeCombo.setItems(timeCombo);
            endTimeCombo.setItems(timeCombo);
            userBox.setItems(usersCombo);
            contactBox.setItems(contactCombo);
            customerBox.setItems(customerCombo);

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }
    }
}