package fr.kyo.crkf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CRKFConnect {

    private static Connection connexion;

    private CRKFConnect(){}

    public static Connection getInstance() {
        try {
            while (connexion == null || connexion.isClosed()) {
                String dbURL = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=CRKF;encrypt=false";
                String user = "sa";
                String pass = "azerty@123456";
                try {
                    connexion = DriverManager.getConnection(dbURL, user, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connexion;
    }

}
