package com.mycompany.model.database.dao;




import com.mycompany.model.database.domain.Appointment;
import com.mycompany.model.exceptions.DatabaseException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Interface responsible for gaining appointments data from database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public interface AppointmentDao {
    /**
     * Method reponsible for getting all of appointments
     * @return
     */
    public List<Appointment> getAppointments() throws DatabaseException;
    /**
     * Method responsible for inserting appointments
     * @return
     */
    public boolean insertAppointment(Appointment appointment) throws DatabaseException;

    /**
     * Method responsible for getting appointments on certain day
     * @param date
     * @return
     */
    public List<Appointment> getAppointmentTimesOnCertainDay(Date date) throws DatabaseException;

    /**
     * Method responsible for deleting appointment
     * @param appointment
     * @return
     */
    public boolean deleteAppointment(Appointment appointment) throws DatabaseException;

    /**
     * Method responsible for getting certain appointment
     * @param date
     * @param time
     * @return
     */
    public Appointment getAppointmentOnCertainDayAndTime(Date date, Time time) throws DatabaseException;

    public List<Appointment> getPatientAppointments(long pesel) throws DatabaseException;
}
