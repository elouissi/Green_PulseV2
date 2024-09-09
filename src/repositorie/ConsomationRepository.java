package repositorie;

import config.Connection_DB;
import domain.Consomation;

import java.sql.Connection;

public class ConsomationRepository {

    private Connection conn;

    public ConsomationRepository(Connection conn) {
        conn = Connection_DB.getInstance().Connect_to_DB("GreenPulse","GreenPulse","");
    }
    public void AddConsomtion(Consomation consomation,int id){
        try {

        }catch (Exception e){

        }


    }
}
