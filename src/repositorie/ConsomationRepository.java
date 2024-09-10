package repositorie;

import config.Connection_DB;
import domain.*;
import domain.Enum.TypeAliment;
import domain.Enum.TypeConsomation;
import domain.Enum.TypeDeVehicule;
import domain.Enum.TypeEnergie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsomationRepository {

    private Connection conn;

    public ConsomationRepository() {
        conn = Connection_DB.getInstance().Connect_to_DB("GreenPulse","GreenPulse","");
    }
    public void AddConsomtion(Consomation consomation, int userId, String table) {
        try {
            // Construire la partie des colonnes
            String query = "INSERT INTO " + table + " (user_id, date_start, date_end, valeur, type";

             if (consomation instanceof Transport) {
                query += ", distanceparcourue, typedevehicule";
            } else if (consomation instanceof Logement) {
                query += ", consommationenergie, typeenergie";
            } else if (consomation instanceof Alimentation) {
                query += ", poids, typealiment";
            }

            // Compléter la requête
            query += ") VALUES (?, ?, ?, ?, ?";

            // Ajouter les valeurs spécifiques selon le type de consommation
            if (consomation instanceof Transport) {
                query += ", ?, ?";
            } else if (consomation instanceof Logement) {
                query += ", ?, ?";
            } else if (consomation instanceof Alimentation) {
                query += ", ?, ?";
            }

            // Compléter la requête
            query += ")";

            System.out.println(query);

            // Préparer la requête
           PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(consomation.getStartDate()));
            pstmt.setDate(3, java.sql.Date.valueOf(consomation.getEndDate()));
            pstmt.setFloat(4, consomation.getValueOfCarbon());
            pstmt.setString(5, consomation.getTypeConsomation().name());

             if (consomation instanceof Transport) {
                Transport transport = (Transport) consomation;
                pstmt.setDouble(6, transport.getDistanceParcourue());
                pstmt.setString(7, transport.getTypeDeVehicule().name());
            } else if (consomation instanceof Logement) {
                Logement logement = (Logement) consomation;
                pstmt.setDouble(6, logement.getConsommationEnergie());
                pstmt.setString(7, logement.getTypeEnergie().name());
            } else if (consomation instanceof Alimentation) {
                Alimentation alimentation = (Alimentation) consomation;
                pstmt.setDouble(6, alimentation.getPoids());
                pstmt.setString(7, alimentation.getTypeAliment().name());
            }

            // Exécuter la requête
            pstmt.executeUpdate();


            System.out.println("Consommation ajoutée avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la consommation: " + e.getMessage());
        }
    }
    public List<Consomation> getCOnsomtionOfUser(int id_user) {
        List<Consomation> consomations = new ArrayList<>();
        try {
             String query = "SELECT * FROM consomations WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id_user);

            ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {
                 String type = rs.getString("type");

                if (type.equals("transport")) {
                     Transport transport = new Transport(
                            rs.getInt("id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("value"),
                            TypeConsomation.transport,
                            rs.getDouble("distance"),
                            TypeDeVehicule.valueOf(rs.getString("type_de_vehicule"))
                    );
                    consomations.add(transport);

                } else if (type.equals("logement")) {
                    // Création d'un objet Logement
                    Logement logement = new Logement(
                            rs.getInt("id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("value"),
                            TypeConsomation.logement,
                            rs.getDouble("consommation_energie"),
                            TypeEnergie.valueOf(rs.getString("type_energie"))
                    );
                    consomations.add(logement);

                } else if (type.equals("alimentation")) {
                    // Création d'un objet Alimentation
                    Alimentation alimentation = new Alimentation(
                            rs.getInt("id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("value"),
                            TypeConsomation.alimentation,
                            rs.getDouble("poids"),
                            TypeAliment.valueOf(rs.getString("type_aliment"))
                    );
                    consomations.add(alimentation);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting consommations: " + e);
        }
        return consomations;
    }


}
