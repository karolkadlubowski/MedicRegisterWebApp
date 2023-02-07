package com.mycompany.model.database.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class represententing Patient
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public class Patient {
    /**
     * Int representing ID
     */
    private int Id;
    /**
     *String representing name of patient
     */
    private String Name;
    /**
     *Long representing pesel number of patient
     */
    private long Pesel;


    public int getId() {
        return Id;
    }
    public void setId(int id) {Id = id;}

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public long getPesel() {
        return Pesel;
    }
    public void setPesel(long pesel) {
        Pesel = pesel;
    }


    public Patient(){}

    public Patient(String name, long pesel) {
        Name = name;
        Pesel = pesel;
    }

    public final StringProperty getNameProperty(){
        return new SimpleStringProperty(Name);
    }

    public final StringProperty getPeselProperty(){
        return new SimpleStringProperty(String.valueOf(Pesel));
    }



}
