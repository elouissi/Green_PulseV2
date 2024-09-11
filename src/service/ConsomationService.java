package service;

import Util.Util;
import domain.*;
import domain.Enum.*;
import domain.Enum.TypeDeVehicule;
import repositorie.ConsomationRepository;
import repositorie.UserRepositoy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ConsomationService {


    long consommationParJour = 0;
    double consommationTotale = 0;
    long difference =0;
    private HashMap<Integer, User> users;
    private Scanner scanner;

     UserRepositoy userRepositoy = new UserRepositoy();
    ConsomationRepository consomationRepository = new ConsomationRepository();

    public ConsomationService(HashMap<Integer, User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }
    public void AddConsomation(){

        System.out.println("Entrez l'utilisateur que vous voulez ajouter à la consommation de carbone --|>");
        int selectId = scanner.nextInt();
        scanner.nextLine();
        String type = null;
        if (userRepositoy.checkUserExists(selectId)) {
             System.out.println("choisir le type de consomation : ");
            System.out.println("1-alimentation");
            System.out.println("2-logement ");
            System.out.println("3-transport");
            int choixType = scanner.nextInt();
             
            scanner.nextLine();
            if (choixType==1){
                 type="alimentation";

            } else if (choixType==2) {
                type="logement";
            }else if (choixType==3){
                type="transport";
            }else {
                System.out.println("choix invalid");
            }
            System.out.println("Entrez la valeur du carbone en kg --|>");
            int valeur = scanner.nextInt();
            scanner.nextLine();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {

                System.out.println("Entrez la date de début (dd/MM/yyyy) :");
                String inputS = scanner.nextLine();
                LocalDate startDate = LocalDate.parse(inputS, format);

                System.out.println("Entrez la date de fin (dd/MM/yyyy) :");
                String inputE = scanner.nextLine();
                LocalDate endDate = LocalDate.parse(inputE, format);

                difference = ChronoUnit.DAYS.between(startDate, endDate);


                if ( difference < 0) {
                    System.out.println("Erreur : La date de début et la date de fin sont identiques.");
                } else {
                    consommationParJour = valeur / difference;
                    System.out.println("Nombre de jours de différence: " + difference);
                    System.out.println("Consommation par jour : " + consommationParJour);
                    List<LocalDate> listDate = new ArrayList<>();
                    //Optional<User> utilisateur = userRepositoy.getUserById(selectId);
                    List<Consomation> consomations = consomationRepository.getCOnsomtionOfUser(selectId);

                    for (Consomation co : consomations) {
                        for (LocalDate date = co.getStartDate(); !date.isAfter(co.getEndDate()) ;date =  date.plusDays(1)){
                            listDate.add(date);
                        }
                    }
                    if(Util.verifydates(startDate,endDate, listDate )){
                        if (type.equals("transport")) {
                            System.out.println("entrer la distance parcourue");
                            double distanceParcourue = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("entrer le type de véhicule");
                            System.out.println("1-train");
                            System.out.println("2-voiture");
                            String typeDeVehicule = scanner.nextLine();
                            TypeDeVehicule type_V = null;

                            if (typeDeVehicule.equals("1")) {
                                type_V = TypeDeVehicule.Train;
                            } else if (typeDeVehicule.equals("2")) {
                                type_V = TypeDeVehicule.Voiture;
                            } else {
                                System.out.println("Type de véhicule non valide.");
                            }

                            if (type_V != null) {
                                Transport transport = new Transport(startDate, endDate, valeur, TypeConsomation.transport, distanceParcourue, type_V);
                                consomationRepository.AddConsomtion(transport, selectId, type);
                                System.out.println("La consommation de transport a bien été ajoutée.");
                            }
                        } else if (type.equals("logement")) {
                            System.out.println("entrer la consommationEnergie");
                            double consommationEnergie =scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("entrer le type de Energie");
                            System.out.println("1-Electricité");
                            System.out.println("2-Gaz");
                            String typeEnergie = scanner.nextLine();
                            TypeEnergie type_I = null; // Initialiser la variable
                            if(typeEnergie.equals( "1")){
                                  type_I = TypeEnergie.Electricité;
                            } else if (typeEnergie.equals("2")) {
                                  type_I = TypeEnergie.Gaz;
                            }
                            Logement logement = new Logement(startDate,endDate,valeur, TypeConsomation.logement,consommationEnergie,type_I);
                            consomationRepository.AddConsomtion(logement,selectId,type);
                            System.out.println("la consomation de logement est bien ajouté");
                            
                        } else if (type.equals("alimentation")) {
                            System.out.println("entrer la poids");
                            double poids =scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("entrer le type de Aliment");
                            System.out.println("1-Viande");
                            System.out.println("2-Légume");
                            String typeAliment = scanner.nextLine();
                            TypeAliment type_A = null; // Initialiser la variable
                            if(typeAliment.equals("1")){
                                  type_A = TypeAliment.Viande;
                            } else if (typeAliment.equals("2")) {
                                  type_A = TypeAliment.Légume;
                            }
                            System.out.println(type_A);
                            Alimentation alimentation = new Alimentation(startDate,endDate,valeur, TypeConsomation.alimentation,poids,type_A);
                            consomationRepository.AddConsomtion(alimentation,selectId,type);
                            System.out.println("la consomation d'alimentation est bien ajouté");

                        }else {
                            
                        }
                        System.out.println("La consommation de l'utilisateur a bien été ajoutée.");

                    }else {
                        System.out.println("s'il vous plait entre une autre date ");
                    }


                }
            } catch (DateTimeParseException e) {
                System.out.println("Erreur : Format de date incorrect. Veuillez entrer la date au format dd/MM/yyyy.");
            } catch (ArithmeticException e) {
                System.out.println("Erreur : Division par zéro lors du calcul de la consommation par jour.");
            }

        } else {
            System.out.println("Erreur : ID d'utilisateur non trouvé.");
        }
    }
//    public void ShowConsommation(){
//
//        System.out.println("Entrez l'ID de l'utilisateur pour afficher ses détails et consommations :");
//        int userId = scanner.nextInt();
//        scanner.nextLine();  // Consomme la nouvelle ligne
//
//        if (userRepositoy.checkUserExists(userId)) {
//            Optional<User> utilisateur = userRepositoy.getUserById(userId);
//
//            System.out.println(utilisateur.toString());
//           List<Consomation> consommations = consomationRepository.getCOnsomtionOfUser(userId);
//
//
//
//            System.out.println("Consommations de l'utilisateur :");
//            for (Consomation consomation : consommations) {
//                System.out.println(consomation.toString());
//            }
//
//
//        } else {
//            System.out.println("Erreur : ID d'utilisateur non trouvé.");
//        }
//
//    }

    public void getAllConsomtion(){

        List<Consomation> consommations = consomationRepository.getAll();



        System.out.println("Consommations de l'utilisateur :");
        for (Consomation consomation : consommations) {
            System.out.println(consomation.toString());
        }


    }

    public void Rapport(){
        System.out.println("--Type :  1");
        System.out.println("--Type :  2");
        int choixF = scanner.nextInt();

        System.out.println("s'il vous plait choisir l'utilisateur que vous voulez");
        int  slectedId = scanner.nextInt();
        scanner.nextLine();


        scanner.nextLine();
        switch (choixF){
            case 1:


                User utilisateur = users.get(slectedId);
                List<Consomation> consomations = utilisateur.getConsommations();


                for (Consomation consomation : consomations) {
                    consommationTotale = consomation.getValueOfCarbon();
                }

                consommationParJour = (long) (consommationTotale / difference);


                float consomationParSemaine = consommationParJour * 7;
                float consommationParMois = consommationParJour * 30;
                float consommationParAnnée = consommationParJour * 365;

                System.out.println("/------------------------------------------------------------------------/");
                System.out.println("/--Par Jour  -------- Par Semaine ------  Par Mois   --------  Par Année /");
                System.out.println("/--   " + consommationParJour + "    ------    " + consomationParSemaine + " ------   " + consommationParMois + " -----    " + consommationParAnnée + "--/");
                break;
            case 2:


                User utilisa = users.get(slectedId);
                if (utilisa != null ) {
                    for (Consomation consomation : utilisa.getConsommations()) {
                        LocalDate stDate = consomation.getStartDate();
                        LocalDate edDate = consomation.getEndDate();

                        long differenceDays = ChronoUnit.DAYS.between(stDate, edDate) + 1;
                        float differenceConsomation = consomation.getValueOfCarbon() / differenceDays;
                        for (LocalDate date = stDate; date.isBefore(edDate); date = date.plusDays(1)) {
                            System.out.println(date + " la consomation par la journée :" + differenceConsomation);

                        }
                    }


                    break;
                }
        }
    }

    public List<Consomation> getConsomation(User user){

        return consomationRepository.getCOnsomtionOfUser(user.getId());


    }
    public void getUsersWithConomations(){
      List<User> users = userRepositoy.getAllUsers();
      users.forEach(e-> {
          System.out.println(e.toString());
          getConsomation(e).forEach(System.out::println);
      } );
    }

}
