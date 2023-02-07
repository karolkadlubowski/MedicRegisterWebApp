package com.mycompany.model.database.dao;

import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.exceptions.DatabaseException;

import java.util.List;

/**
 * Interface responsible for gaining patients data from database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public interface PatientDao {
    /**
     * Method responsible for getting patient by ID
     * @param id
     * @return
     */
    public Patient getPatient(long pesel) throws DatabaseException;

    /**
     * Method responsible for getting patient list
     * @return
     */
    public List<Patient> getPatients() throws DatabaseException;
    public boolean insertPatient(Patient patient) throws DatabaseException;
    public boolean deletePatient(long pesel) throws DatabaseException;
}
