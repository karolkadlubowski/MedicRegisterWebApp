package com.mycompany.model.database.dao;

//import model.database.domain.Appointment;
//import model.database.domain.Patient;
//import model.database.util.JdbcUtils;

import com.mycompany.model.database.domain.Appointment;
import com.mycompany.model.database.util.JdbcUtils;
import com.mycompany.model.exceptions.DatabaseException;
import com.mycompany.model.exceptions.InsertException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for getting appointments data from database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class AppointmentDaoImplJdbc implements AppointmentDao {

    @Override
    public List<Appointment> getAppointments() throws DatabaseException {
        final var appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM APPOINTMENTS");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Appointment appointment = readAppointment(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(preparedStatement, con);
        }
        return appointments;
    }

    @Override
    public boolean insertAppointment(Appointment appointment) throws DatabaseException {
        Connection con = null;
        PreparedStatement stmt = null;
        int result = 0;
        try {
            con = JdbcUtils.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement("INSERT INTO APPOINTMENTS VALUES(NULL,?,?,?)");
            stmt.setLong(1, appointment.getPatientPesel());
            stmt.setDate(2, appointment.getDate());
            stmt.setTime(3, appointment.getTime());
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
    public List<Appointment> getAppointmentTimesOnCertainDay(Date date) throws DatabaseException {
        final var appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM APPOINTMENTS WHERE DATE = ?");
            preparedStatement.setDate(1, date);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Appointment appointment = readAppointment(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(preparedStatement, con);
        }
        return appointments;
    }

    private Appointment readAppointment(final ResultSet rs) throws SQLException {
        final var appointment = new Appointment();
        appointment.setId(rs.getInt("ID"));
        appointment.setPatientPesel(rs.getLong("PATIENT_PESEL"));
        appointment.setDate(rs.getDate("DATE"));
        appointment.setTime(rs.getTime("TIME"));
        return appointment;
    }

    @Override
    public boolean deleteAppointment(Appointment appointment) throws DatabaseException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("DELETE FROM APPOINTMENTS WHERE PATIENT_PESEL= ? AND DATE = ? AND TIME = ?");
            preparedStatement.setLong(1, appointment.getPatientPesel());
            preparedStatement.setDate(2, appointment.getDate());
            preparedStatement.setTime(3, appointment.getTime());
            result = preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(preparedStatement, con);
        }
        return result > 0 ? true : false;
    }

    @Override
    public Appointment getAppointmentOnCertainDayAndTime(Date date, Time time) throws DatabaseException {
        Appointment appointment = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM APPOINTMENTS WHERE DATE = ? AND TIME = ?");
            preparedStatement.setDate(1, date);
            preparedStatement.setTime(2,time);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointment = readAppointment(resultSet);
            }
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(preparedStatement, con);
        }
        return appointment;
    }

    public List<Appointment> getPatientAppointments(long pesel) throws DatabaseException {
        final var appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = JdbcUtils.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM APPOINTMENTS WHERE PATIENT_PESEL = ? ORDER BY DATE ASC");
            preparedStatement.setLong(1, pesel);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Appointment appointment = readAppointment(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException exception) {
            JdbcUtils.handleSqlException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            JdbcUtils.closeSilently(preparedStatement, con);
        }
        return appointments;
    }
}
