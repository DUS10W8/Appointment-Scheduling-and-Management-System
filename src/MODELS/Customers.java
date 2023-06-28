package MODELS;

/**
*class for customers.
 */
public class Customers {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private int Division_ID;
    private String Division;
    private String Country;

    /**
    *constructor for customers.
      *
      * @param Customer_ID
      * @param Customer_Name
      * @param Address
      * @param Postal_Code
      * @param Phone
      * @param Division_ID
      * @param Division
      * @param Country
     */
    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID, String Division, String Country) {

        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country = Country;

    }

    /**
    *gets customer id for customer.
    * @return Customer_ID
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
    *gets customer name for customer.
    * @return Customer_Name
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
    *gets division id for customer.
    * @return Division_ID
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**gets
    *address for customer.
    *@return Address
     */
    public String getAddress() {
        return Address;
    }

    /**
    *gets phone number for customer.
    * @return Phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
    *gets postal code for customer.
    * @return Postal_Code
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
    *gets first level division for customer.
    * @return Postal_Code
     */
    public String getDivision() { return Division; }

    /**
    *gets country name.
    * @return Country
     */
    public String getCountry() {return Country;}
}
