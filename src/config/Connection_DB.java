package config;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connection_DB {
     private static Connection_DB instance;

     private static Connection conn;

     public static Connection_DB getInstance() {
        if (instance == null) {
            instance = new Connection_DB();
        }
        return instance;
    }

    public Connection_DB() {
    }

    public Connection Connect_to_DB(String dbname, String user, String pass) {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);

                if (conn != null) {
                    System.out.println("Connected successfully");
                } else {
                    System.out.println("Connection failed");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }
}
