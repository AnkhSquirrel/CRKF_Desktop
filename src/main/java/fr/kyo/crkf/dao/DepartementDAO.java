package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Departement;

import java.sql.*;
import java.util.ArrayList;

public class DepartementDAO extends DAO<Departement> {

    protected DepartementDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Departement getByID(int id) {
        try {

            // Determine the column set column

            String strCmd = "SELECT id_departement, departement from Departement as v where id_departement = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery(strCmd);

            rs.next();
            Departement departement = new Departement(rs.getInt(1), rs.getString(2));

            rs.close();

            return departement;
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Departement> getAll() {
        ArrayList<Departement> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {


            // Determine the column set column

            String strCmd = "SELECT id_departement, departement from Departement order by departement";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                //TO DO Add Ville
                //new Ville(rs.getInt(1), rs.getString(2))
                liste.add(new Departement(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean insert(Departement objet) {
        try {
            String requete = "INSERT INTO Departement (departement) VALUES (?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getDepartement());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Departement object) {
        try {
            String requete = "UPDATE Departement SET departement = ? WHERE id_departement = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getDepartement());
            preparedStatement.setInt(3, object.getId_departement());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Departement object) {
        try {
            String requete = "DELETE FROM Departement WHERE id_departement=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_departement());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
