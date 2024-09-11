package service;

import domain.Consomation;
import domain.User;
import repositorie.UserRepositoy;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    public double consomationTotal(User user){
        return user
                .getConsommations()
                .stream()
                .mapToDouble(Consomation::calculerImpact)
                .sum();

    }

    public List<User> filterByConsuption(int nombre ){

        return this.findAll()
                .stream()
                .filter(e -> consomationTotal(e) > nombre)
                .collect(Collectors.toList());


    }

}


