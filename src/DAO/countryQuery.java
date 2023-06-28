package DAO;


import MAIN.JDBC;
import MODELS.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    /**
    *handles all database queries for countries.
    */
public class countryQuery extends Countries {

    /**
    *super constructor for country.
    * @param country_id
    * @param country
     */
    public countryQuery(int country_id, String country) {
        super (country_id, country);
    }

    /**
    *gets all country data for associated combo boxes, fields, or tables.
    * @throws SQLException
    * @return countriesList
     */
    public static ObservableList<countryQuery> getCountries() throws SQLException {

            ObservableList<countryQuery> countriesList = FXCollections.observableArrayList();

            String sql = "SELECT * from countries;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                countryQuery country = new countryQuery(country_ID, Country);

                countriesList.add(country);

            }
            return countriesList;
        }


}
