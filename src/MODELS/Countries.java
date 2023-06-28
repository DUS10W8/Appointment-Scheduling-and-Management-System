package MODELS;

/**
*class for countries.
 */
public class Countries {


    private int Country_ID;
    private String Country;

    /**
    *constructor for countries.
    * @param Country_ID
    * @param Country
     */
    public Countries(int Country_ID, String Country) {

        this.Country_ID = Country_ID;
        this.Country = Country;

    }

    /**
    *gets country id for countries.
    *
    * @return Country_ID
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
    *gets country name for countries.
    * @return Country
     */
    public String getCountry() {
        return Country;
    }
}
