package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Classification;

import java.sql.*;
import java.util.ArrayList;

public class ClassificationDAO extends DAO<Classification> {
    protected ClassificationDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Classification getByID(int id) {
        Classification classification= null;
        try{

            String strCmd = "SELECT id_classifiaction, classification from Classification where id_classifiaction = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery(strCmd);

            rs.next();

            classification = new Classification(rs.getInt(1),rs.getString(2));

            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classification;
    }

    @Override
    public ArrayList<Classification> getAll() {
        ArrayList<Classification> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_classifiaction, classification from Classification order by classification";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(new Classification(rs.getInt(1), rs.getString(2)));
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
    public boolean insert(Classification objet) {
        try {
            String requete = "INSERT INTO Classification (classification) VALUES (?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getclassification());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Classification object) {
        try {
            String requete = "UPDATE Classification SET classification = ? WHERE id_classifiaction = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getclassification());
            preparedStatement.setInt(3, object.getId_classification());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Classification object) {
        try {
            String requete = "DELETE FROM Classification WHERE id_classifiaction=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_classification());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
