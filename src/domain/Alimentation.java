package domain;

import domain.Enum.TypeAliment;
import domain.Enum.TypeConsomation;

import java.time.LocalDate;

public class Alimentation extends Consomation {

    public double poids;
    public TypeAliment typeAliment;

    public Alimentation(int id, LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation,double poids, TypeAliment typeAliment) {
        super(id, startDate, endDate, value, typeConsomation);
        this.poids = poids;
        this.typeAliment = typeAliment;
    }
    public Alimentation(LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation,double poids, TypeAliment typeAliment) {
        super(startDate, endDate, value, typeConsomation);
        this.poids = poids;
        this.typeAliment = typeAliment;
    }

    public Alimentation() {
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public TypeAliment getTypeAliment() {
        return typeAliment;
    }

    public void setTypeAliment(TypeAliment typeAliment) {
        this.typeAliment = typeAliment;
    }
    @Override
    public double calculerImpact(){

        double impactConsomation = 0;

        if (this.typeAliment == TypeAliment.valueOf("Légume")) impactConsomation = 0.5;

        else impactConsomation = 5.0;

        return super.valueOfCarbon*this.poids*impactConsomation;
    }

    @Override
    public String toString() {
        return "Alimentation{" +
                "poids=" + poids +
                ", typeAliment=" + typeAliment +
                ", id=" + id +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", valueOfCarbon=" + valueOfCarbon +
                ", typeConsomation=" + typeConsomation +
                '}';
    }
}
