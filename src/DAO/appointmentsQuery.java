package DAO;

import MAIN.JDBC;
import MODELS.Appointments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

    /**
    *handles all database queries for appointments.
     */
public class appointmentsQuery {

    /**
    *gets appointment data from database for appointment table.
    *
    * @return apptsObservableList
     */
        public static ObservableList<Appointments> getAppointments() {

            ObservableList<Appointments> apptsObservableList = FXCollections.observableArrayList();
            try {

                String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type," +
                        " appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID," +
                        " contacts.Contact_Name FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID;";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    apptsObservableList.add(new Appointments(
                            rs.getInt("Appointment_ID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("Location"),
                            rs.getString("Type"),
                            rs.getTimestamp("Start").toLocalDateTime(),
                            rs.getTimestamp("End").toLocalDateTime(),
                            rs.getDate("Start").toLocalDate(),
                            rs.getDate("End").toLocalDate(),
                            rs.getInt("Customer_ID"),
                            rs.getInt("User_ID"),
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name")));
                   

                }
                return apptsObservableList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

}