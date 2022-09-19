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

            String strCmd = "SELECT id_classification, classification from Classification where id_classification = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

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
    public ArrayList<Classification> getAll(int page) {
        ArrayList<Classification> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_classification, classification from Classification order by classification";
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
    public int insert(Classification objet) {
        try {
            String requete = "INSERT INTO Classification (classification) VALUES (?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getclassification());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);
            preparedStatement.close();
            return id;
        }catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean update(Classification object) {
        try {
            String requete = "UPDATE Classification SET classification = ? WHERE id_classification = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getclassification());
            preparedStatement.setInt(2, object.getId_classification());
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
            String requete = "DELETE FROM Classification WHERE id_classification=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_classification());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<Classification> getLike(String classification, int page) {
        ArrayList<Classification> list = new ArrayList<>();
        try{

            String strCmd = "SELECT id_classification, classification from Classification";
            if(!classification.isEmpty())
                strCmd += " where classification like '%" + classification + "%'";
            strCmd += " order by classification OFFSET 25 * (" + page + " - 1)  ROWS FETCH NEXT 25 ROWS ONLY";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();

            while(rs.next())
                list.add(new Classification(rs.getInt(1),rs.getString(2)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
