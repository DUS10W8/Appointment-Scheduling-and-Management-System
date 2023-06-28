package MODELS;

/**
* class for TypeReport.
 */
public class TypeReport {

    public String apptType;
    public int apptCount;

    /**
    * constructs type report.
     */
    public TypeReport(String apptType, int apptCount) {

        this.apptType = apptType;
        this.apptCount = apptCount;

    }

    /**
    * gets appointment type.
    *
    * @return appointment type
     */
    public String getApptType() { return apptType; }

    /**
    *gets appointment count.
    *
    * @return appointment count
     */
    public int getApptCount() { return apptCount; }

}
