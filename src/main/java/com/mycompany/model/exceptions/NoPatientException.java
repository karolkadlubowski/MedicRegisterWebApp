package com.mycompany.model.exceptions;

/**
 * Class responsible for throwing exception in case of wrong patient data.
 */
public class NoPatientException extends Exception{
    public NoPatientException(){
        super("There is no such a patient");
    }
}
