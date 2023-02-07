package com.mycompany.model;

import com.mycompany.model.database.dao.AppointmentDao;
import com.mycompany.model.database.dao.AppointmentDaoImplJdbc;
import com.mycompany.model.database.dao.PatientDao;
import com.mycompany.model.database.dao.PatientDaoImplJdbc;
import com.mycompany.model.database.domain.Appointment;
import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.database.util.DbSchemaDef;
import com.mycompany.model.database.util.JdbcUtils;
import com.mycompany.model.exceptions.DatabaseException;
import com.mycompany.model.exceptions.InsertException;
import com.mycompany.model.exceptions.NoPatientException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Class responsible for handling database in pattern of singleton
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class DatabaseService {
    /**
     * Singleton instance of DatabaseService
     */
    private static DatabaseService instance;
    /**
     * {@link PatientDao} type variable responsible for getting patients data
     */
    private static PatientDao patientDao;
    /**
     * {@link AppointmentDao} type variable responsible for getting appointments data
     */
    private static AppointmentDao appointmentDao;

    /**
     * List containing all the patients in the database
     */
    public List<Patient> patientList;

    public List<Appointment> selectedAppointmentList;

    /**
     * Constructor of {@link DatabaseService}
     */
    private DatabaseService() throws DatabaseException {
        patientDao = new PatientDaoImplJdbc();
        appointmentDao = new AppointmentDaoImplJdbc();
        JdbcUtils.restoreDbSchema(new DbSchemaDef());
        patientList = this.getPatients();
        selectedAppointmentList = new ArrayList<>();
    }

    /**
     * Method responsible for creating or passing the {@link DatabaseService} instance
     *
     * @return
     */
    public static DatabaseService getInstance() throws DatabaseException {
        DatabaseService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DatabaseService.class) {
            if (instance == null) {
                instance = new DatabaseService();
            }
            return instance;
        }
    }

    public  Patient getPatientByPesel(long pesel) throws NoPatientException, DatabaseException {
        var patient =patientDao.getPatient(pesel);
        if(patient!=null)
            return patient;
        else
            throw new NoPatientException();
    }

    /**
     * Method responsible for adding patient to the database
     *
     * @param patient
     * @return
     */
    public boolean insertPatient(Patient patient) throws DatabaseException {

        if (ifPatientDataAreCorrect(patient)) {
            if (patientDao.insertPatient(patient)) {
                patientList = this.getPatients();
                return true;
            } else
                return false;
        } else
            return false;


    }

    /**
     * Method responsible for validation of patients data
     * @param patient
     * @return
     */
    private boolean ifPatientDataAreCorrect(Patient patient) {
        if (patient.getName()==null || patient.getName().isBlank() || /*(int) (Math.log10(patient.getPesel()) + 1) != 11*/ String.valueOf(patient.getPesel()).length()!=11 || patient.getPesel()<0 || checkIfPeselRepeated(patient.getPesel())) {
            return false;
        } else
            return true;
    }

    /**
     * Method responsible for checking if there is a patient with passed pesel number
     *
     * @param pesel
     * @return
     */
    public boolean checkIfPeselRepeated(long pesel) {
        return patientList.stream().anyMatch(patient -> patient.getPesel() == pesel);
    }

    /**
     * Method responsible for getting list of patients
     *
     * @return
     */
    public List<Patient> getPatients() throws DatabaseException {
        return patientDao.getPatients();
    }


    /**
     * Method responsible for deleting patient
     *
     * @param pesel
     * @return
     */
    public boolean deletePatient(long pesel) throws DatabaseException {
        if (checkIfPeselRepeated(pesel))
            return patientDao.deletePatient(pesel);
        else
            return false;
    }

    /**
     * Method responsible for adding appointment
     *
     * @param appointment
     * @return
     */
    public boolean insertAppointment(Appointment appointment) throws DatabaseException {

        if(ifAppointmentTermIsPossible(appointment))
            return appointmentDao.insertAppointment(appointment);
        else
            return false;
    }

    public boolean insertAppointment(String pesel, String date,String time) throws InsertException {
        try{
            Appointment appointment = new Appointment(Long.parseLong(pesel), Date.valueOf(date), Time.valueOf(time));
            if(ifAppointmentTermIsPossible(appointment))
                return appointmentDao.insertAppointment(appointment);
            else
                return false;
        }
        catch (Exception e){
            throw new InsertException("Adding to database not completed, reason:" + e.getMessage());
        }
    }

    /**
     * Method responsible for checking if the term is available
     * @return
     */
    private boolean ifAppointmentTermIsPossible(Appointment appointment) throws DatabaseException {
        boolean peselCorrect=checkIfPeselRepeated(appointment.getPatientPesel());
        boolean dateAndHourCorrect=!checkIfSuchAppointmentExists(appointment);

        if(peselCorrect&&dateAndHourCorrect){
            var timeNow =new Time(Calendar.getInstance().getTime().getTime());
            var dateNow = new Date(Calendar.getInstance().getTime().getTime());
            if(appointment.getDate().after(dateNow))//checks if term is not in the past
                return true;
            else if(appointment.getDate().toString().compareTo(dateNow.toString())==0){
                if(appointment.getTime().getHours()>timeNow.getHours())
                {
                    return true;
                }else if(appointment.getTime().getHours()==timeNow.getHours()){
                    if(appointment.getTime().getMinutes()>timeNow.getMinutes())
                        return true;
                    else if(appointment.getTime().getMinutes()==timeNow.getMinutes())
                    {
                        if(appointment.getTime().getSeconds()>timeNow.getSeconds())
                            return true;
                    }
                }

            }

        }
        return false;
    }

    /**
     * Method checking if the term and time are free to register
     *
     * @param appointment
     * @return
     */
    public boolean checkIfSuchAppointmentExists(Appointment appointment) throws DatabaseException {
        Appointment scheduledAppointment = getAppointmentOnCertainDayAndTime(appointment.getDate(), appointment.getTime());
        if (scheduledAppointment == null)
            return false;
        else
            return true;
    }

    /**
     * Method returns appointment on certain day and time
     *
     * @param date
     * @param time
     * @return
     */
    public Appointment getAppointmentOnCertainDayAndTime(Date date, Time time) throws DatabaseException {
        return appointmentDao.getAppointmentOnCertainDayAndTime(date, time);
    }


    /**
     * Method responsible for deleting appointment
     *
     * @param appointment
     * @return
     */
    public boolean deleteAppointment(Appointment appointment) throws DatabaseException {
        return appointmentDao.deleteAppointment(appointment);
    }

    /**
     * Method returns all appointments on certain day
     *
     * @param date
     * @return
     */
    public List<Appointment> getAppointmentsOnCertainDay(Date date) throws DatabaseException {
        return appointmentDao.getAppointmentTimesOnCertainDay(date);
    }



    /**
     * Method returns list of all scheduled appointments
     *
     * @return
     */
    public List<Appointment> getAppointments() throws DatabaseException {
        return appointmentDao.getAppointments();
    }

    public List<Appointment> getPatientAppointments(long pesel) throws DatabaseException {
        return appointmentDao.getPatientAppointments(pesel);
    }

    public ArrayList<String> initTimes() {
        var scheduledAppointmentsTimes = new ArrayList<String>();
        int hour = 10;
        for (int i = 1; i < 9; i++) {

            //System.out.println(i + ". " + hour + ":" + "00" + ":" + "00");
            scheduledAppointmentsTimes.add(String.valueOf(Time.valueOf(hour + ":" + "00" + ":" + "00")));
            //System.out.println(++i + ". " + hour + ":" + 30 + ":" + "00");
            scheduledAppointmentsTimes.add(String.valueOf(Time.valueOf(hour + ":" + "30" + ":" + "00")));
            hour++;
        }
        return scheduledAppointmentsTimes;
    }
}
