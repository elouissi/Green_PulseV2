package domain;

import domain.Enum.TypeConsomation;

import java.time.LocalDate;

public abstract class Consomation {

    int id ;
    LocalDate StartDate;
    LocalDate EndDate;
    float valueOfCarbon;
    TypeConsomation typeConsomation;

    public Consomation(int id,LocalDate startDate, LocalDate endDate, float value,TypeConsomation typeConsomation ) {
        StartDate = startDate;
        EndDate = endDate;
        this.valueOfCarbon = value;
        this.id = id;
        this.typeConsomation =typeConsomation;
    }
    public Consomation(LocalDate startDate, LocalDate endDate, float value,TypeConsomation typeConsomation ) {
        StartDate = startDate;
        EndDate = endDate;
        this.valueOfCarbon = value;
         this.typeConsomation =typeConsomation;
    }

    public Consomation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeConsomation getTypeConsomation() {
        return typeConsomation;
    }

    public void setTypeConsomation(TypeConsomation typeConsomation) {
        this.typeConsomation = typeConsomation;
    }

    public LocalDate getStartDate() {

        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {

        StartDate = startDate;
    }

    public LocalDate getEndDate() {

        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {

        EndDate = endDate;
    }

    public float getValueOfCarbon() {
        return valueOfCarbon;
    }

    public void setValueOfCarbon(float valueOfCarbon) {

        this.valueOfCarbon = valueOfCarbon;
    }

    public String toString() {
        return "Consomation{" +
                " id = " +  id +
                " startDate=" + StartDate +
                ", endDate=" + EndDate +
                ", valeur=" + valueOfCarbon + "kg" +
                ",type"+typeConsomation+
                '}';
    }
    public abstract double  calculerImpact();
}
