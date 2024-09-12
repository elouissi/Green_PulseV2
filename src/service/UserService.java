package service;

import domain.Consomation;
import domain.User;
import repositorie.ConsomationRepository;
import repositorie.UserRepositoy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static Util.Util.dateListRange;
import static Util.Util.verifydates;

public class UserService {


    private HashMap<Integer, User> users;
    private Scanner scanner;

    public UserService(HashMap<Integer, User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }
    UserRepositoy userRepositoy = new UserRepositoy();



    public void AddUser() {



        // L'input du nom
        System.out.println("Quel est votre nom?");
        String nameUser = scanner.nextLine();

        // L'input de l'âge
        System.out.println("Quel âge avez-vous?");
        int age = scanner.nextInt();
        scanner.nextLine();

        User Newuser = new User( nameUser, age);
        //users.put(id, user);
        System.out.println(userRepositoy.addUser(Newuser));

        System.out.println("Utilisateur ajouté");


    }
    public void UpdateUser(){
        System.out.println("enter l'id d'utilisateur que vous voulez le modifier ");

        int updatedId = scanner.nextInt();
        scanner.nextLine();


        System.out.println("entrer le nouveau nom");
        String newName = scanner.nextLine();

        System.out.println("entrer le nouveau age");
        int newAge = scanner.nextInt();
        scanner.nextLine();


        if (userRepositoy.checkUserExists(updatedId)) {
            User Newuser = new User( newName, newAge);
            System.out.println(userRepositoy.updateUser(Newuser,updatedId));
            //users.put(updatedId, Newuser);

            System.out.println("l'utilisateur est bien modifier");

        } else {
            System.out.println("l'id ne trouve pas!");
        }

    }
    public void DeleteUser(){
        System.out.println("entre l'id d'utilisateur que vous voulez supprimé");
        int deleteId = scanner.nextInt();
        scanner.nextLine();

        if (userRepositoy.checkUserExists(deleteId)) {
            //users.remove(deleteId);
            System.out.println(userRepositoy.deleteUser(deleteId));
            System.out.println("l'untilisteur a été bien supprimé");
        } else {
            System.out.println("l'id que que vous avez entrer est incorrect");
        }
    }
    public void afficherParId() {
        System.out.println("Entrez l'ID de l'utilisateur que vous voulez voir :");
        int afficherId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        if (userRepositoy.checkUserExists(afficherId)) {
            Optional<User> optionalUser = userRepositoy.getUserById(afficherId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                System.out.println("Voici l'utilisateur :");
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Age: " + user.getAge());
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        } else {
            System.out.println("L'ID que vous avez entré est incorrect.");
        }
    }

    public void showAllUsers() {
        System.out.println("Liste des utilisateurs:");
        UserRepositoy userRepositoy = new UserRepositoy();
        List<User> users = userRepositoy.getAllUsers();

        // Vérification s'il y a des utilisateurs à afficher
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            // Affichage en format tableau
            System.out.printf("%-5s %-20s %-5s%n", "ID", "Nom", "Age");
            System.out.println("------------------------------------");
            for (User user : users) {
                System.out.printf("%-5d %-20s %-5d%n", user.getId(), user.getName(), user.getAge());
            }
        }
    }

    public List<User> findAll(){

        return userRepositoy.getAllUsers();
    }
    public double consomationTotal(User user) {
        ConsomationRepository conR = new ConsomationRepository();
        List<Consomation> consomationList = conR.getCOnsomtionOfUser(user.getId());
        double totalImpact = consomationList
                .stream()
                .mapToDouble(Consomation::calculerImpact)
                .sum();
        System.out.println("Total impact pour l'utilisateur " + user.getId() + ": " + totalImpact);
        return totalImpact;
    }

    public double ClassementConsomation(User user) {
        ConsomationRepository conR = new ConsomationRepository();
        List<Consomation> consomationList = conR.getCOnsomtionOfUser(user.getId());
        double totalConsomation = consomationList
                .stream()
                .mapToDouble(Consomation::getValueOfCarbon)
                .sum();
         return totalConsomation;
    }
    public void filterByConsuption() {
        List<User> allUsers = this.findAll();
        List<User> filteredUsers = allUsers
                .stream()
                .filter(e -> consomationTotal(e) > 310000)
                .collect(Collectors.toList());
        System.out.println("Utilisateurs avec une consommation totale > 31000 :");
        for (User user : filteredUsers) {
            System.out.println(user);
        }

    }

    public void filterByInactivite() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Demander la date de début
        System.out.println("Entrez la date de début (dd/MM/yyyy) :");
        String inputS = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(inputS, format);

        // Demander la date de fin
        System.out.println("Entrez la date de fin (dd/MM/yyyy) :");
        String inputE = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(inputE, format);

        ConsomationRepository conR = new ConsomationRepository();

         List<User> users = findAll().stream()
                .filter(user -> {
                    List<Consomation> consomations = conR.getCOnsomtionOfUser(user.getId());

                     List<LocalDate> consomationDates = consomations.stream()
                            .flatMap(consomation -> dateListRange(consomation.getStartDate(), consomation.getEndDate()).stream())
                            .collect(Collectors.toList());

                     return verifydates(startDate, endDate, consomationDates);
                })
                .collect(Collectors.toList());
        System.out.println(users);
    }
    public void classementByTotal() {
        List<User> ClassementUsers = findAll()
                .stream()
                .sorted((o1, o2) -> Double.compare(ClassementConsomation(o2), ClassementConsomation(o1)))
                .collect(Collectors.toList());

         System.out.println("Classement des utilisateurs par consommation totale de carbone :");
        for (User user : ClassementUsers) {
            System.out.println("Utilisateur ID: " + user.getId() + ", Consommation totale : " + ClassementConsomation(user));
        }
    }



}


