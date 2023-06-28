package MODELS;

    /**
    *class for contacts.
     */
public class Contacts {

    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    /**
    *constructs contacts.
    * @param Contact_ID
    * @param Contact_Name
    * @param Email
     */
    public Contacts(int Contact_ID, String Contact_Name, String Email) {

        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;

    }

    /**
    *gets contact id.
    * @return Contact_ID
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
    *gets contact name.
    * @return Contact_Name
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
    *gets contact email.
    * @return Email
     */
    public String getEmail() {
        return Email;
    }
}
