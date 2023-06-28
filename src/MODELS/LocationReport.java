package MODELS;

/**
* class for LocationReport.
 */
public class LocationReport {

    public String apptLocation;
    public int apptCount;

    /**
    * constructor for Location Report.
    *
    * @param apptLocation
    * @param apptCount
     */
    public LocationReport (String apptLocation, int apptCount) {

        this.apptCount = apptCount;
        this.apptLocation = apptLocation;
    }

    /**
    * gets appointment location.
    *
    * @return apptLocation
     */
    public String getApptLocation() {return apptLocation;}

    /**
    * gets appointment count.
    * @return apptCount
     */
    public int getApptCount() {return apptCount;}

}
