package com.mycompany.model.database.dao;

import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.database.util.JdbcUtils;
import com.mycompany.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for gaining patients data from database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class PatientDaoImplJdbc implements PatientDao {
    @Override
    public Patient getPatient(long pesel) throws DatabaseException {
        Patient patient = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM PATIENTS WHERE Pesel = ?");
            preparedStatement.setLong(1, pesel);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patient = readPatient(resultSet);
            }
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        }

        return patient;
    }

    private Patient readPatient(final ResultSet rs) throws SQLException {

        final Patient patient = new Patient();

        patient.setId(rs.getInt("ID"));
        patient.setName(rs.getString("NAME"));
        patient.setPesel(rs.getLong("PESEL"));
        return patient;
    }

    @Override
    public List<Patient> getPatients() throws DatabaseException {
        final List<Patient> patients = new ArrayList<Patient>();
        Connection con = null;
        Statement stmt = null;
        try {
            con = JdbcUtils.getConnection();
            stmt = con.createStatement();
            final ResultSet resultSet = stmt.executeQuery("SELECT * FROM PATIENTS");
            while (resultSet.next()) {
                final Patient patient = readPatient(resultSet);
                patients.add(patient);
            }
        } catch (SQLException ex) {
            JdbcUtils.handleSqlException(ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            JdbcUtils.closeSilently(stmt, con);
        }
        return patients;
    }

    @Override
    public boolean insertPatient(Patient patient) throws DatabaseException {
        Connection con = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            con = JdbcUtils.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement("INSERT INTO PATIENTS VALUES(NULL,?,?)");
            stmt.setString(1, patient.getName());
            stmt.setLong(2, patient.getPesel());
            result = stmt.executeUpdate();
            con.commit();
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(stmt, con);
        }
        return result > 0 ? true : false;
    }

    @Override
    public boolean deletePatient(long pesel) throws DatabaseException {
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try{
            con = JdbcUtils.getConnection();
            stmt = con.createStatement();
            String qry = "DELETE FROM PATIENTS WHERE PESEL = "+pesel;
            result = stmt.executeUpdate(qry);
            con.commit();
        }catch (SQLException exception){
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        }finally {
            JdbcUtils.closeSilently(stmt,con);
        }
        return result > 0 ? true :false;
    }

}
