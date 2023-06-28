package MODELS;

/**
* class for Users.
 */
public class Users {

    public int User_ID;
    public String User_Name;
    public String Password;

    /**
    * constructs Users.
    *
    * @param User_ID
    * @param User_Name
    * @param Password
     */
    public Users ( int User_ID, String User_Name, String Password ) {

        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;

    }

    /**
    *gets user id for user.
    *
   * @return User_ID
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
    *gets password for user.
    *
    * @return Password
     */
    public String getPassword() {
        return Password;
    }

    /**
    *gets user name for user.
    *
    * @return User_Name
     */
    public String getUser_Name() {
        return User_Name;
    }
}
