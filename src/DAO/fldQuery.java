package DAO;

import MAIN.JDBC;
import MODELS.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    /**
    *handles all database queries for first level divisions.
    */
public class fldQuery extends FirstLevelDivisions {

    /**
    *constructor and super constructor for first level divisions.
    *
    * @param Division_ID
    * @param Division
    * @param countryID
     */
    public fldQuery(int Division_ID, String Division, int countryID) { super(Division_ID, Division, countryID); }

    /**
    *gets first level division data for all associated combo boxes, fields, and tables.
    *
    * @throws SQLException
    * @return FLDObservableList
     */
    public static ObservableList<fldQuery> getFLDs () throws SQLException {

        ObservableList<fldQuery> FLDObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {

            int Division_ID = rs.getInt("Division_ID");
            String Division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");

            fldQuery FLD = new fldQuery(Division_ID, Division, countryId);
            FLDObservableList.add(FLD);

        }

    return FLDObservableList;

    }

}
