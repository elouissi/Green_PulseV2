package domain;

import domain.Enum.TypeAliment;
import domain.Enum.TypeConsomation;
import domain.Enum.TypeDeVehicule;

import java.time.LocalDate;

public class Transport extends Consomation  {

    public double distanceParcourue;
    public TypeDeVehicule typeDeVehicule;

    public Transport(int id, LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation, double distanceParcourue, TypeDeVehicule typeDeVehicule) {
        super(id, startDate, endDate, value, typeConsomation);
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }
    public Transport(LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation, double distanceParcourue, TypeDeVehicule typeDeVehicule) {
        super(startDate, endDate, value, typeConsomation);
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }

    public Transport() {
    }

    public double getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(double distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public TypeDeVehicule getTypeDeVehicule() {
        return typeDeVehicule;
    }

    public void setTypeDeVehicule(TypeDeVehicule typeDeVehicule) {
        this.typeDeVehicule = typeDeVehicule;
    }
    @Override
    public double calculerImpact(){
        double impactConsomation = 0;

        if (this.typeDeVehicule == TypeDeVehicule.valueOf("Voiture")) impactConsomation = 0.5;

            else impactConsomation = 0.1;

            return super.valueOfCarbon*impactConsomation*this.distanceParcourue ;


    }
    @Override
    public String toString() {
        return "Transport{" +
                "distanceParcourue=" + distanceParcourue +
                ", typeDeVehicule=" + typeDeVehicule +
                ", id=" + id +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", valueOfCarbon=" + valueOfCarbon +
                ", typeConsomation=" + typeConsomation +
                '}';
    }
}
