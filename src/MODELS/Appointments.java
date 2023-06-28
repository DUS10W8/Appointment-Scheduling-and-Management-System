package MODELS;

import java.time.LocalDate;
import java.time.LocalDateTime;

    /**
    * the appointment class.
     */
public class Appointments {

        private int Appointment_ID;
        private String Title;
        private String Description;
        private String Location;
        private String Type;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDate startDate;
        private LocalDate endDate;
        public int Customer_ID;
        public int User_ID;
        public int Contact_ID;
        private String contactName;


        /**
         *contructs appointments.
         *
         * @param Appointment_ID
         * @param Title
         * @param Description
         * @param Location
         * @param Type
         * @param startTime
         * @param endTime
         * @param startDate
         * @param endDate
         * @param Customer_ID
         * @param User_ID
         * @param Contact_ID
         * @param contactName
         */
        public Appointments(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime startTime, LocalDateTime endTime, LocalDate startDate, LocalDate endDate,
                            int Customer_ID, int User_ID, int Contact_ID, String contactName) {

            this.Appointment_ID = Appointment_ID;
            this.Title = Title;
            this.Description = Description;
            this.Location = Location;
            this.Type = Type;
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.Customer_ID = Customer_ID;
            this.User_ID = User_ID;
            this.Contact_ID = Contact_ID;
            this.contactName = contactName;

        }

        /**
         *constructs appointments for alerts.
         *
         * @param appointment_ID
         * @param startDate
         * @param startTime
         */
        public Appointments(int appointment_ID, LocalDate startDate, LocalDateTime startTime) {

            this.Appointment_ID = appointment_ID;
            this.startDate = startDate;
            this.startTime = startTime;
        }

        /**
         *gets appointment_ID.
         * @return Appointment_ID
         */
        public int getAppointment_ID() {
            return Appointment_ID;
        }

        /**
         *gets contact_ID.
         * @return Contact_ID
         */
        public int getContact_ID() {
            return Contact_ID;
        }

        /**
         *gets customer_ID.
         * @return Customer_ID
         */
        public int getCustomer_ID() {
            return Customer_ID;
        }

        /**
         *gets user_ID.
         * @return User_ID
         */
        public int getUser_ID() {
            return User_ID;
        }

        /**
         *gets end time.
         * @return endTime
         */
        public LocalDateTime getEndTime() {
            return endTime;
        }

        /**
         *gets start time.
         * @return startTime
         */
        public LocalDateTime getStartTime() {
            return startTime;
        }

        /**
         *gets end date.
         * @return endDate
         */
        public LocalDate getEndDate() {
            return endDate;
        }

        /**
         *gets start date of appointment.
         * @return startDate
         */
        public LocalDate getStartDate() {
            return startDate;
        }

        /**
         *gets description of appointment.
         * @return Description
         */
        public String getDescription() {
            return Description;
        }

        /**
         *gets location of appointment.
         * @return Location
         */
        public String getLocation() {
            return Location;
        }

        /**
         *gets title of appointment.
         * @return Title
         */
        public String getTitle() {
            return Title;
        }

        /**
         *gets type of appointment.
         * @return Type
         */
        public String getType() {
            return Type;
        }

        /**
         *gets contact name of appointment.
         * @return Contact_Name
         */
        public String getContactName() {
            return contactName;
        }
    }


