package com.mycompany.model.exceptions;

/**
 * Class responsible for throwing exception in case of error during adding to the database.
 */
public class InsertException extends Exception{
    public InsertException(String message){
        super(message);
    }
}
