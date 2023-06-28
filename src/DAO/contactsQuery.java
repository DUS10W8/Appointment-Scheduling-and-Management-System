package DAO;

import MAIN.JDBC;
import MODELS.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
    /**
    handles all contact queries.
    */
public class contactsQuery {

    /**
    *gets all contact data for associated combo boxes, fields, or tables.
    * @throws SQLException
    * @return contactsList
     */
    public static ObservableList<Contacts> getContacts() throws SQLException {

        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        String s = "SELECT * from contacts";
        ResultSet rs = JDBC.getConnection().prepareStatement(s).executeQuery();

        while (rs.next()) {

            int Contact_ID = rs.getInt("Contact_ID");
            String Contact_Name = rs.getString("Contact_Name");
            String Contact_Email = rs.getString("Email");
            Contacts contact = new Contacts(Contact_ID, Contact_Name, Contact_Email);
            contactsList.add(contact);

        }

        return contactsList;

    }
}