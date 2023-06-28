package MODELS;

    /**
    *class for first level divisions.
     */
public class FirstLevelDivisions {

    private int Division_ID;
    private String Division;
    public int countryID;

    /**
    *constructor for first level divisions.
    *
    * @param Division_ID
    * @param Division
    * @param Country_ID
     */
    public FirstLevelDivisions(int Division_ID, String Division, int Country_ID) {

        this.countryID = Country_ID;
        this.Division = Division;
        this.Division_ID = Division_ID;

    }

    /**
    *gets division id for flds.
    * @return Division_ID
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
    *gets country id for first level divisions.
    * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
    *gets division for first level division.
    * @return Division
     */
    public String getDivision() {
        return Division;
    }

}
