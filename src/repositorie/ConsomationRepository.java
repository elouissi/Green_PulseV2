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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsomationRepository {

    private Connection conn;

    public ConsomationRepository() {
        conn = Connection_DB.getInstance().Connect_to_DB("GreenPulse", "GreenPulse", "");
    }

    public void AddConsomtion(Consomation consomation, int userId, String table) {
        try {
            String query = "INSERT INTO " + table + " (user_id, date_start, date_end, valeur, type";

            if (consomation instanceof Transport) {
                query += ", distanceparcourue, typedevehicule";
            } else if (consomation instanceof Logement) {
                query += ", consommationenergie, typeenergie";
            } else if (consomation instanceof Alimentation) {
                query += ", poids, typealiment";
            }

            query += ") VALUES (?, ?, ?, ?, ?";

            if (consomation instanceof Transport) {
                query += ", ?, ?";
            } else if (consomation instanceof Logement) {
                query += ", ?, ?";
            } else if (consomation instanceof Alimentation) {
                query += ", ?, ?";
            }

            query += ")";


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
            System.out.println(query);

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
                int consomationId = rs.getInt("id");
                LocalDate dateStart = rs.getDate("date_start").toLocalDate();
                LocalDate dateEnd = rs.getDate("date_end").toLocalDate();
                float valeur = rs.getFloat("valeur");

                if (type.equals("transport")) {
                    String transportQuery = "SELECT * FROM transport WHERE id = ?";
                    PreparedStatement transportStmt = conn.prepareStatement(transportQuery);
                    transportStmt.setInt(1, consomationId);
                    ResultSet transportRs = transportStmt.executeQuery();

                    if (transportRs.next()) {
                        double distanceParcourue = transportRs.getDouble("distanceparcourue");
                        TypeDeVehicule typeVehicule = TypeDeVehicule.valueOf(transportRs.getString("typedevehicule"));
                        Transport transport = new Transport(consomationId, dateStart, dateEnd, valeur, TypeConsomation.transport, distanceParcourue, typeVehicule);
                        consomations.add(transport);
                    }
                } else if (type.equals("logement")) {
                    String logementQuery = "SELECT * FROM logement WHERE id = ?";
                    PreparedStatement logementStmt = conn.prepareStatement(logementQuery);
                    logementStmt.setInt(1, consomationId);
                    ResultSet logementRs = logementStmt.executeQuery();

                    if (logementRs.next()) {
                        double consommationEnergie = logementRs.getDouble("consommationenergie");
                        TypeEnergie typeEnergie = TypeEnergie.valueOf(logementRs.getString("typeenergie"));
                        Logement logement = new Logement(consomationId, dateStart, dateEnd, valeur, TypeConsomation.logement, consommationEnergie, typeEnergie);
                        consomations.add(logement);
                    }
                } else if (type.equals("alimentation")) {
                    String alimentationQuery = "SELECT * FROM alimentation WHERE id = ?";
                    PreparedStatement alimentationStmt = conn.prepareStatement(alimentationQuery);
                    alimentationStmt.setInt(1, consomationId);
                    ResultSet alimentationRs = alimentationStmt.executeQuery();

                    if (alimentationRs.next()) {
                        double poids = alimentationRs.getDouble("poids");
                        TypeAliment typeAliment = TypeAliment.valueOf(alimentationRs.getString("typealiment"));
                        Alimentation alimentation = new Alimentation(consomationId, dateStart, dateEnd, valeur, TypeConsomation.alimentation, poids, typeAliment);
                        consomations.add(alimentation);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting consommations: " + e);
        }
        return consomations;
    }

    public List<Consomation> getAll() {
        try {
            List<Consomation> consomations = new ArrayList<>();

             String queryTransport = "SELECT * FROM transport";
            PreparedStatement pstmtTransport = conn.prepareStatement(queryTransport);
            ResultSet rsTransport = pstmtTransport.executeQuery();
            while (rsTransport.next()) {
                Transport transport = new Transport(
                        rsTransport.getInt("id"),
                        rsTransport.getDate("date_start").toLocalDate(),
                        rsTransport.getDate("date_end").toLocalDate(),
                        rsTransport.getFloat("valeur"),
                        TypeConsomation.transport,
                        rsTransport.getDouble("distanceparcourue"),
                        TypeDeVehicule.valueOf(rsTransport.getString("typedevehicule"))
                );
                consomations.add(transport);
            }

             String queryLogement = "SELECT * FROM logement";
            PreparedStatement pstmtLogement = conn.prepareStatement(queryLogement);
            ResultSet rsLogement = pstmtLogement.executeQuery();
            while (rsLogement.next()) {
                Logement logement = new Logement(
                        rsLogement.getInt("id"),
                        rsLogement.getDate("date_start").toLocalDate(),
                        rsLogement.getDate("date_end").toLocalDate(),
                        rsLogement.getFloat("valeur"),
                        TypeConsomation.logement,
                        rsLogement.getDouble("consommationenergie"),
                        TypeEnergie.valueOf(rsLogement.getString("typeenergie"))
                );
                consomations.add(logement);
            }

             String queryAlimentation = "SELECT * FROM alimentation";
            PreparedStatement pstmtAlimentation = conn.prepareStatement(queryAlimentation);
            ResultSet rsAlimentation = pstmtAlimentation.executeQuery();
            while (rsAlimentation.next()) {
                Alimentation alimentation = new Alimentation(
                        rsAlimentation.getInt("id"),
                        rsAlimentation.getDate("date_start").toLocalDate(),
                        rsAlimentation.getDate("date_end").toLocalDate(),
                        rsAlimentation.getFloat("valeur"),
                        TypeConsomation.alimentation,
                        rsAlimentation.getDouble("poids"),
                        TypeAliment.valueOf(rsAlimentation.getString("typealiment"))
                );
                consomations.add(alimentation);
            }

            return consomations;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}




