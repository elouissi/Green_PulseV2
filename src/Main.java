 import config.Connection_DB;
 import domain.User;
import service.ConsomationService;
import service.UserService;

 import java.sql.Connection;
 import java.util.*;

public class Main {
    public static void main(String[] args) {
        Connection_DB dbInstance = Connection_DB.getInstance();
        Connection conn = dbInstance.Connect_to_DB("GreenPulse", "GreenPulse", "");

        /*Connection_DB c = new Connection_DB();
        c.Connect_to_DB("GreenPulse","GreenPulse","");*/

        int choix;
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, User> users = new HashMap<>();

        // Passer users et scanner aux classes de gestion
        UserService management = new UserService(users, scanner);
        ConsomationService consomationM = new ConsomationService(users, scanner);

        long consommationParJour = 0;
        double consommationTotale = 0;
        long difference =0;
        do {
            System.out.println("-----------------------//veuillez selectionner votre choix//--------------");
            System.out.println("1 - Ajouter un utilisateur");
            System.out.println("2 - Modifier un utilisateur");
            System.out.println("3 - Supprimer un utilisateur");
            System.out.println("4 - Afficher tous les utilisateurs");
            System.out.println("5 - entrer la consomation d'un utilisateur ");
            System.out.println("6 - afficher la consomation d'un utilisateur");
            System.out.println("7- générer le rapport d'un utilisateur");
            System.out.println("8 - afficher par id");
            System.out.println("9 - Exit");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:

                    management.AddUser();

                    break;


                case 2:
                    management.UpdateUser();
                    break;

                case 3:

                    management.DeleteUser();

                    break;

                case 4:

                    management.showAllUsers();

                    break;

                case 5:
                    consomationM.AddConsomation();
                    break;

                case 6:
                    consomationM.ShowConsommation();
                    break;

                case 7:
                    consomationM.Rapport();

                    break;

                case 8:

                    management.afficherParId();
                    break;
                case 9:

                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }

        } while (choix != 9) ;

    }

}
