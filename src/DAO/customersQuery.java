package DAO;

import MAIN.JDBC;
import MODELS.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
handles all database queries for customers.
 */
public class customersQuery extends Customers {

    /**
    *constructor and super constructor for customers.
    *
    * @param Customer_ID
    * @param Customer_Name
    * @param Address
    * @param Postal_Code
    * @param Phone
    * @param Division_ID
    * @param Division
     */
    public customersQuery(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID, String Division) {
        super(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, Division, Division);
    }

    /**
    *gets customer data for associated tables, fields, and combo boxes.
    *
    * @throws SQLException
    * @return custObservableList
     */
    public static ObservableList<Customers> getCustomers() throws SQLException {

        ObservableList<Customers> custObservableList = FXCollections.observableArrayList();

        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                "customers.Phone, countries.Country, customers.Division_ID, first_level_divisions.Division FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN " +
                "countries ON first_level_divisions.Country_ID = countries.Country_ID;";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int Customer_ID = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");
            String Division = rs.getString("Division");
            String Country = rs.getString("Country");

            Customers cust = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, Division, Country);
            custObservableList.add(cust);

        }

        return custObservableList;

    }

}
