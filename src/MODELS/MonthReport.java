package MODELS;
/**
*Month Report Class.
 */
public class MonthReport {

    public String apptMonth;
    public int apptCount;

    /**
    * constructs MonthReport.
     */
    public MonthReport (String apptMonth, int apptCount) {

        this.apptMonth = apptMonth;
        this.apptCount = apptCount;

    }
    /**
    * gets Appointment Count.
    * @return appointment count
     */
    public int getApptCount() {
        return apptCount;
    }
    /**
    *gets month of appointment.
    * @return appointment month
     */
    public String getApptMonth() {
        return apptMonth;
    }

}
