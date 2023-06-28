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
*variety of functions that are called from controllers.
 */
public class utilTools {

    /**
    *queries the database for an appointment that starts in the next 15 minutes.
    *
    * @throws SQLException
    * @return appt start times
     */
    public static ObservableList<Appointments> upcomingAlerts() throws SQLException {

        ObservableList<Appointments> appts = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Start FROM appointments a WHERE a.start >= now() and a.start <= date_add(now(), interval 15 minute);";
        ResultSet rs = JDBC.getConnection().prepareStatement(sql).executeQuery();

        while (rs.next()) {

               int appointment_ID = rs.getInt("Appointment_ID");
               LocalDate startDate = rs.getDate("Start").toLocalDate();
               LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();

            Appointments appt = new Appointments(appointment_ID, startDate, startTime);
            appts.add(appt);
        }
        return appts;
    }

    /**
    *gets customer ID from database to reference and compare against appts in database.
    * @param Customer_ID
    *
    * @return apptsObservableList
     */
    public static ObservableList<Appointments> getCustomer_ID(int Customer_ID) {

        ObservableList<Appointments> apptsObservableList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type," +
                    " appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID," +
                    " contacts.Contact_Name FROM appointments, contacts WHERE appointments.Customer_ID = ?;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, Customer_ID);
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

    /**
    *gets all appointments within the current week.
    * @return appointment info
     */
    public static ObservableList<Appointments> getWeeklyAppointments() {

        ObservableList<Appointments> appts = FXCollections.observableArrayList();

        try {

            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name," +
                    " appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID " +
                    "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE week(Start) = week(now());";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDate startDate = rs.getDate("Start").toLocalDate();
                LocalDate endDate = rs.getDate("End").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");

                Appointments appt = new Appointments(appointment_ID, title, description, location, type, startTime, endTime, startDate, endDate, customer_ID, user_ID, contact_ID, contactName);
                appts.add(appt);

            }
            return appts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
    *gets all appointments within the current month.
    * @return appointment info
     */
    public static ObservableList<Appointments> getMonthlyAppointments() {

        ObservableList<Appointments> appts = FXCollections.observableArrayList();

        try {

            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name," +
                    " appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID " +
                    "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE month(Start) = month(now());";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDate startDate = rs.getDate("Start").toLocalDate();
                LocalDate endDate = rs.getDate("End").toLocalDate();
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");

                Appointments appt = new Appointments(appointment_ID, title, description, location, type, startTime, endTime,
                        startDate, endDate, customer_ID, user_ID, contact_ID, contactName);
                appts.add(appt);

            }
            return appts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}