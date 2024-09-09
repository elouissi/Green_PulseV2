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

    public User addUser(User user) {
        try {
             String query = "INSERT INTO users (id, nom, age) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setInt(3, user.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
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

    public User updateUser(User user) {
        User updatedUser = null;
        try {
             String updateQuery = "UPDATE users SET nom = ?, age = ? WHERE id = ?";
            PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
            updatePstmt.setString(1, user.getName());
            updatePstmt.setInt(2, user.getAge());
            updatePstmt.setInt(3, user.getId());
            updatePstmt.executeUpdate();
            String selectQuery = "SELECT * FROM users WHERE id = ?";
            PreparedStatement selectPstmt = conn.prepareStatement(selectQuery);
            selectPstmt.setInt(1, user.getId());
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                updatedUser = new User(rs.getInt("id"), rs.getString("nom"), rs.getInt("age"));
            }
        } catch (Exception e) {
            System.out.println("Error updating user: " + e);
        }
        return updatedUser;
    }

     public User deleteUser(int id) {
        User user = null;
        try {
             String selectQuery = "SELECT * FROM users WHERE id = ?";
            PreparedStatement selectPstmt = conn.prepareStatement(selectQuery);
            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("nom"), rs.getInt("age"));
            }

             String deleteQuery = "DELETE FROM users WHERE id = ?";
            PreparedStatement deletePstmt = conn.prepareStatement(deleteQuery);
            deletePstmt.setInt(1, id);
            deletePstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e);
        }
        return user;
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
