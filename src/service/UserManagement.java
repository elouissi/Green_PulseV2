package service;

import domain.User;
import repositorie.UserRepositoy;

import java.util.HashMap;
import java.util.Scanner;

public class UserManagement {


    private HashMap<Integer, User> users;
    private Scanner scanner;

    public UserManagement(HashMap<Integer, User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }


    public void AddUser() {

         UserRepositoy userRepositoy = new UserRepositoy();

        // L'input d'id
        System.out.println("Entrez votre ID:");
        int id = scanner.nextInt();
        scanner.nextLine();


        // L'input du nom
        System.out.println("Quel est votre nom?");
        String nameUser = scanner.nextLine();

        // L'input de l'âge
        System.out.println("Quel âge avez-vous?");
        int age = scanner.nextInt();
        scanner.nextLine();

        User Newuser = new User( nameUser, age);
        //users.put(id, user);
        userRepositoy.addUser(Newuser);

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


        if (users.containsKey(updatedId)) {
            User Newuser = new User( newName, newAge);
            users.put(updatedId, Newuser);

            System.out.println("l'utilisateur rst bien modifier");

        } else {
            System.out.println("l'id ne trouve pas!");
        }

    }
    public void DeleteUser(){
        System.out.println("entre l'id d'utilisateur que vous voulez supprimé");
        int deleteId = scanner.nextInt();
        scanner.nextLine();

        if (users.containsKey(deleteId)) {
            users.remove(deleteId);
            System.out.println("l'untilisteur a été bien supprimé");
        } else {
            System.out.println("l'id que que vous avez entrer est incorrect");
        }
    }
    public void Showing(){
        System.out.println("Liste des utilisateurs:");
        for (User u : users.values()) {
            System.out.println(u.toString());
        }
    }

}


