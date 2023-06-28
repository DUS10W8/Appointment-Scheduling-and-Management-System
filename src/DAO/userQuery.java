package DAO;

import MAIN.JDBC;
import MODELS.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    /**
    *handles all database queries for users.
     */
public class userQuery {

    /**
    *confirms fields are fields on login and authenticates usernames and passwords are saved in database for login.
    *
    * @param username
    * @param password
    *
    * @return boolean
     */
    public static boolean confirmUser(String username, String password){

        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

       else try{

            String sql = "SELECT * FROM users WHERE User_Name = '"+ username +"' AND Password = '"+ password +"'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();

            if(rs.getString("User_Name").equals(username)){
                if(rs.getString("Password").equals(password)){
                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
    *gets all user data stored in database for associated combo boxes, fields, or tables.
    *
    * @throws SQLException
    * @return usersObservableList
     */
    public static ObservableList<Users> getUsers() throws SQLException {

        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");

            Users user = new Users(userId, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }
}