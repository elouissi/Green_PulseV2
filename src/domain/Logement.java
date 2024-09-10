package domain;

import domain.Enum.TypeConsomation;
import domain.Enum.TypeDeVehicule;
import domain.Enum.TypeEnergie;

import java.time.LocalDate;

public class Logement extends Consomation {
    public double consommationEnergie;
    public TypeEnergie typeEnergie;

    public Logement(int id, LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation, double consommationEnergie, TypeEnergie typeEnergie) {
        super(id, startDate, endDate, value, typeConsomation);
        this.consommationEnergie = consommationEnergie;
        this.typeEnergie = typeEnergie;
    }
    public Logement(LocalDate startDate, LocalDate endDate, float value, TypeConsomation typeConsomation, double consommationEnergie, TypeEnergie TypeEnergie) {
        super(startDate, endDate, value, typeConsomation);
        this.consommationEnergie = consommationEnergie;
        this.typeEnergie = TypeEnergie;
    }


    public double getConsommationEnergie() {
        return consommationEnergie;
    }

    public void setConsommationEnergie(double consommationEnergie) {
        this.consommationEnergie = consommationEnergie;
    }

    public TypeEnergie getTypeEnergie() {
        return typeEnergie;
    }

    public void setTypeEnergie(TypeEnergie typeEnergie) {
        this.typeEnergie = typeEnergie;
    }
    @Override
    public double calculerImpact(){
        double impactConsomation = 0;

        if (this.typeEnergie == TypeEnergie.valueOf("electricité")) impactConsomation = 1.5;

        else impactConsomation = 2.0;


        return impactConsomation*super.valueOfCarbon*this.consommationEnergie;
    }
}
