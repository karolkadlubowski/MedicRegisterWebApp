package com.mycompany.model.database.domain;

import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;

//import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Time;

/**
 * Class represententing Appointment
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class Appointment {
    /**
     * Int representing ID
     */
    private int Id;
    /**
    *Long representing pesel number of patient
     */
    private long PatientPesel;
    /**
     * Date representing date of appointment
     */
    private java.sql.Date Date;
    /**
     * Time representing of appointment
     */
    private java.sql.Time Time;


    public int getId() {
        return Id;
    }



    public void setDate(java.sql.Date date) {
        Date = date;
    }



    public java.sql.Date getDate() {
        return Date;
    }

    public void setPatientPesel(long patientPesel) {
        PatientPesel = patientPesel;
    }

    public long getPatientPesel() {
        return PatientPesel;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public Appointment(){}

    public Appointment(long patientPesel, Date date, Time time) {
        PatientPesel = patientPesel;
        Date = date;
        Time = time;
    }

    public void setId(int id) {
        Id = id;
    }

    public final StringProperty getDateProperty(){
        return new SimpleStringProperty(Date.toString());
    }

    public final StringProperty getTimeProperty() {
        return new SimpleStringProperty(Time.toString());
    }
}
