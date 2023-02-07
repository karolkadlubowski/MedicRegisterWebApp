package com.mycompany.model.database.util;

/**
 * Class responsible for a database schema
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class DbSchemaDef {
    /**
     * String responsible for construction of PATIENTS table
     */
    private final String PATIENTS_TABLE_DEF = "PATIENTS " +
            "(ID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "NAME varchar(50) NOT NULL, " +
            "PESEL bigint(11) NOT NULL UNIQUE)";
    /**
     * String responsible for construction of APPOINTMENTS table
     */
    private final String APPOINTMENTS_TABLE_DEF = "APPOINTMENTS " +
            "(ID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "PATIENT_PESEL bigint(11) NOT NULL, " +
            "DATE date NOT NULL, " +
            "TIME time NOT NULL, " +
            "FOREIGN KEY (PATIENT_PESEL) REFERENCES PATIENTS (PESEL) ON DELETE CASCADE ON UPDATE CASCADE)";

    /**
     * Method responsible for getting table names
     * @return
     */
    public String[] getTablesNames() {
        return new String[]{"APPOINTMENTS", "PATIENTS"}; // drop order
    }
    /**
     * Method responsible for getting table definitions
     * @return
     */
    public String[] getTablesDef() {
        return new String[]{PATIENTS_TABLE_DEF, APPOINTMENTS_TABLE_DEF}; // create order
    }

}

