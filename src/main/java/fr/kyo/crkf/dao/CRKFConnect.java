package fr.kyo.crkf.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class CRKFConnect {

    private static Connection connexion;

    private CRKFConnect(){}

    public static Connection getInstance() {
        if (connexion == null) {
            String dbURL = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=CRKF;encrypt=false";
            String user = "sa";
            String pass = "azerty@123456";
            try {
                connexion = DriverManager.getConnection(dbURL, user, pass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connexion;
    }
}
