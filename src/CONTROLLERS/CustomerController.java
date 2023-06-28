package CONTROLLERS;


import DAO.appointmentsQuery;
import DAO.countryQuery;
import DAO.customersQuery;
import DAO.fldQuery;
import MAIN.JDBC;
import MODELS.Appointments;
import MODELS.Countries;
import MODELS.Customers;
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
import MODELS.FirstLevelDivisions;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
* Customer Controller. .
 */
public class CustomerController implements Initializable {

    public ComboBox FLDComboBox;
    public ComboBox countryComboBox;
    public Button menuButton;
    public Button createButton;
    public Button updateButton;
    public Button deleteButton;
    public TableView customerTable;
    public TableColumn customerIdCol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneNumberCol;
    public TableColumn divisionCol;
    public TextField IDTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;
    public TableColumn countryCol;
    public Button createButton1;

    Stage stage;
    Parent scene;

/**
* directs user to the menu page upon clicking the menu button in the top right corner.
*
* @param actionEvent button click
* @throws IOException
 */
    public void menuButtonClick(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/VIEW/MenuScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
    *clears fields and generates a near random customer_ID field.
    *
    *@param actionEvent button click
     */
    public void startCreateClick(ActionEvent actionEvent) throws SQLException {

        IDTextField.clear();
        nameTextField.clear();
        addressTextField.clear();
        postalTextField.clear();
        phoneTextField.clear();

        int customer_ID = (int) (Math.random() * 11111);
        IDTextField.setText(Integer.toString(customer_ID));

    }

/** finds the Division_ID by comparing the fld name selected in the fld combobox.
*ensures all fields are filled
*inserts new customer data into the database and table
* @throws SQLException
 */
    public void saveCreateClick() throws SQLException {

        try {

            int customerId = Integer.parseInt(IDTextField.getText());
            int FLD = 0;
            String name = nameTextField.getText();
            String phone = phoneTextField.getText();
            String postal = postalTextField.getText();
            String address = addressTextField.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

            //finds the selected fld related ID by comparing names
            for (fldQuery FirstLevelDivisions : fldQuery.getFLDs()) {

                if (FLDComboBox.getSelectionModel().getSelectedItem().equals(FirstLevelDivisions.getDivision())) {
                    FLD = FirstLevelDivisions.getDivision_ID();

                }
            }

            //ensures all fields are filled
            if (FLDComboBox != null && !name.equals("") && !phone.equals("") && !postal.equals("") && !address.equals("")) {

                //adds customer to database
                String Query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                        "Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";

                JDBC.makePreparedStatement(Query, JDBC.getConnection());
                PreparedStatement ps = JDBC.getPreparedStatement();

                ps.setInt(1, customerId);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setString(4, postal);
                ps.setString(5, phone);
                ps.setTimestamp(6, createDate);
                ps.setString(7, "admin");
                ps.setTimestamp(8, createDate);
                ps.setString(9, "admin");
                ps.setInt(10, FLD);
                ps.execute();

                ObservableList<Customers> updateCustomersList = customersQuery.getCustomers();
                customerTable.setItems(updateCustomersList);

                IDTextField.clear();
                nameTextField.clear();
                phoneTextField.clear();
                postalTextField.clear();
                addressTextField.clear();

            }

        } catch (SQLException throwables) {

        }
    }

    /**
    *deletes selected customer and alerts if a customer isn't selected.
    *confirms if user would like to delete all associated appointments
    *
    *@param actionEvent
    *@throws SQLException
     */
    public void deleteButtonClick(ActionEvent actionEvent) throws SQLException {

        Customers selected = (Customers) customerTable.getSelectionModel().getSelectedItem();

        if(selected != null) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Deleting all customer Data and associated appointments");
            Optional<ButtonType> r = a.showAndWait();

            if (r.get() == ButtonType.OK) {

                int customer_ID = selected.getCustomer_ID();

                String sql = "DELETE FROM appointments WHERE Customer_ID = ?;";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, customer_ID);
                ps.execute();

                String SQL = "DELETE FROM customers WHERE Customer_ID = ?;";
                PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
                PS.setInt(1, customer_ID);
                PS.execute();

                IDTextField.clear();
                nameTextField.clear();
                phoneTextField.clear();
                postalTextField.clear();
                addressTextField.clear();

                ObservableList<Customers> updateCustomersList = customersQuery.getCustomers();
                customerTable.setItems(updateCustomersList);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("customer is not selected for deletion");
        }
    }


    /** populates fields and combo boxes with selected customers info.
    *
    * @param mouseEvent select table row
     */
    public void selectCustomer(MouseEvent mouseEvent) throws SQLException {

        Customers selectedItem = (Customers) customerTable.getSelectionModel().getSelectedItem();
        IDTextField.setText(Integer.toString(selectedItem.getCustomer_ID()));
        nameTextField.setText(selectedItem.getCustomer_Name());
        addressTextField.setText(selectedItem.getAddress());
        postalTextField.setText(selectedItem.getPostal_Code());
        phoneTextField.setText(selectedItem.getPhone());

        ObservableList<countryQuery> countriesList = countryQuery.getCountries();
        ObservableList<fldQuery> FLDList = fldQuery.getFLDs();
        String FLDLabel = "";
        String countryLabel = "";

        for (FirstLevelDivisions FLD: FLDList) {

            if (FLD.getDivision_ID() == selectedItem.getDivision_ID()) {

                FLDLabel = FLD.getDivision();
                int countryID = (FLD.getCountryID());

                for(Countries country: countriesList) {

                    if (country.getCountry_ID() == countryID) {

                        countryLabel = country.getCountry();
                    }
                }
            }
        }
        countryComboBox.setValue(countryLabel);
        FLDComboBox.setValue(FLDLabel);
    }

    /** updates database with selected customer info.
    *
     */
    public void updateButtonClick() {

        try {

            int customerId = Integer.parseInt(IDTextField.getText());
            int FLD = 0;
            String name = nameTextField.getText();
            String phone = phoneTextField.getText();
            String postal = postalTextField.getText();
            String address = addressTextField.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

            for (fldQuery FirstLevelDivisions : fldQuery.getFLDs()) {

                if (FLDComboBox.getSelectionModel().getSelectedItem().equals(FirstLevelDivisions.getDivision())) {

                    FLD = FirstLevelDivisions.getDivision_ID();

                }
            }

            if (FLDComboBox != null && !name.equals("") && !phone.equals("") && !postal.equals("") && !address.equals("")) {

                //adds customer to database
                String Query = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?," +
                        " Phone = ?, Last_Update = ?,  Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

                JDBC.makePreparedStatement(Query, JDBC.getConnection());
                PreparedStatement ps = JDBC.getPreparedStatement();

                ps.setInt(1, customerId);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setString(4, postal);
                ps.setString(5, phone);
                ps.setTimestamp(6, createDate);
                ps.setString(7, "admin");
                ps.setInt(8, FLD);
                ps.setInt(9, customerId);
                ps.execute();

                ObservableList<Customers> updateCustomersList = customersQuery.getCustomers();
                customerTable.setItems(updateCustomersList);

                IDTextField.clear();
                nameTextField.clear();
                phoneTextField.clear();
                postalTextField.clear();
                addressTextField.clear();

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /** filters and populates the FLD combo box with the countries associated states or divisions.
    *
    *@param event combobox select
    *@throws SQLException
    * */
    public void countrySelect(ActionEvent event) throws SQLException {
        try {
            ObservableList<fldQuery> FLDsList = fldQuery.getFLDs();

            ObservableList<String> USList = FXCollections.observableArrayList();
            ObservableList<String> UKList = FXCollections.observableArrayList();
            ObservableList<String> CAList = FXCollections.observableArrayList();

            String selCountry = countryComboBox.getSelectionModel().getSelectedItem().toString();

            FLDsList.forEach(FirstLevelDivisions -> {
                if (FirstLevelDivisions.getCountryID() == 1) {
                    USList.add(FirstLevelDivisions.getDivision());
                } else if (FirstLevelDivisions.getCountryID() == 2) {
                    UKList.add(FirstLevelDivisions.getDivision());
                } else if (FirstLevelDivisions.getCountryID() == 3) {
                    CAList.add(FirstLevelDivisions.getDivision());
                }
            });

            if (selCountry.equals("U.S")) {
                FLDComboBox.setItems(USList);

            } else if (selCountry.equals("UK")) {
                FLDComboBox.setItems(UKList);

            } else if (selCountry.equals("Canada")) {
                FLDComboBox.setItems(CAList);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
    *populates all of the combo boxes and tables with customer data, uses a for each observable list.
    *@param url
    *@param resourceBundle
    */
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            try {

                /* populates Customers Table with customer info*/
                ObservableList<Customers> customersList = customersQuery.getCustomers();
                customerTable.setItems(customersList);

                /* fills countriesName list with strings converted from objects in countriesList, countries name populates countryComboBox*/
                ObservableList<countryQuery> countriesList = countryQuery.getCountries();
                ObservableList<String> countriesName = FXCollections.observableArrayList();
                countriesList.stream().map(Countries::getCountry).forEach(countriesName::add);
                countryComboBox.setItems(countriesName);

                /* converts and and populates the first level divisions combo box*/
                ObservableList<fldQuery> FLDsList = fldQuery.getFLDs();
                ObservableList<String> FLDsName = FXCollections.observableArrayList();
                FLDsList.stream().map(FirstLevelDivisions::getDivision).forEach(FLDsName::add);
                FLDComboBox.setItems(FLDsName);

                /* populates customer table columns*/
                customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
                addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
                postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
                phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
                divisionCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
                countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
