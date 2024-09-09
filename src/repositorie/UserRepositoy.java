package repositorie;

import config.Connection_DB;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoy {

    private Connection conn;

    public UserRepositoy() {

        conn = Connection_DB.getInstance().Connect_to_DB("GreenPulse","GreenPulse","");
    }

    public void addUser(User user){
        try {
            String query = "Insert into users (id,name,age) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,user.getId());
            pstmt.setString(2,user.getName());
            pstmt.setInt(3,user.getAge());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public User getUserById(int id) {
        User user = null;
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            }
        } catch (Exception e) {
            System.out.println("Error getting user: " + e);
        }
        return user;
    }

    // UPDATE: Mettre à jour un utilisateur
    public void updateUser(User user) {
        try {
            String query = "UPDATE users SET nom = ?, age = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setInt(3, user.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating user: " + e);
        }
    }

    // DELETE: Supprimer un utilisateur
    public void deleteUser(int id) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e);
        }
    }

     public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
            }

             return users;

        } catch (Exception e) {
            System.out.println("Error getting users: " + e);
        }
        return users;
    }}
