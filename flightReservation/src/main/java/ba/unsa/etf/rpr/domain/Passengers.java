package ba.unsa.etf.rpr.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
public class Passengers implements Idable{

    private int passengerID;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;   /* TRY TO USE DATE INSTEAD OF THIS */
    private String adress;
    private String email;


    public int getId() {
        return passengerID;
    }

    public void setId(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Passengr{" +
                "passengerID=" + passengerID +
                ", name:'" + name + '\'' +
                ", surname:'" + surname + "'"+
                ", dateOfBirth:" + dateOfBirth +
                ", adress:'" + adress + '\'' +
                ", email: "+ email +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passengers passenger = (Passengers) o;
        return passengerID == passenger.passengerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerID, name, surname, dateOfBirth,adress,email);
    }

}
