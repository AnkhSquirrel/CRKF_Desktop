package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Searchable.SearchableFamille;

import java.sql.*;
import java.util.ArrayList;

public class FamilleDAO extends DAO<Famille> {
    protected FamilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Famille getByID(int id) {
        Famille famille = null;
        try {

            // Determine the column set column

            String strCmd = "SELECT id_famille, famille, id_classification from Famille where id_famille = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            famille =  new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3));

            rs.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return famille;
    }
    public ArrayList<Famille> getByClassification(int id) {
        ArrayList<Famille> list = new ArrayList<>();
        try {

            // Determine the column set column

            String strCmd = "SELECT id_famille, famille, id_classification from Famille where id_classification = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            while (rs.next())
                list.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
            rs.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Famille> getLike(SearchableFamille searchableFamille, int page) {
        ArrayList<Famille> list = new ArrayList<>();
        try {

            // Determine the column set column

            String strCmd = "SELECT id_famille, famille, id_classification from Famille";

            if(searchableFamille.getClassification() != 0 && !searchableFamille.getNom().isEmpty())
                strCmd += " where id_classification = " + searchableFamille.getClassification() + " and famille like '%" + searchableFamille.getNom() + "%'";
            else if(searchableFamille.getClassification() != 0 && searchableFamille.getNom().isEmpty())
                strCmd += " where id_classification = " + searchableFamille.getClassification();
            else if(searchableFamille.getClassification() == 0 && !searchableFamille.getNom().isEmpty())
                strCmd += " where famille like '%" + searchableFamille.getNom() + "%'";
            strCmd += " order by famille OFFSET 25 * (" + page + " - 1)  ROWS FETCH NEXT 25 ROWS ONLY";

            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();

            while (rs.next()){
                list.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getAllFamille(SearchableFamille searchableFamille) {
        try{
            String strCmd = "SELECT COUNT(id_famille) from Famille";
            if(searchableFamille.getClassification() != 0 && !searchableFamille.getNom().isEmpty())
                strCmd += " where id_classification = " + searchableFamille.getClassification() + " and famille like '%" + searchableFamille.getNom() + "%'";
            else if(searchableFamille.getClassification() != 0 && searchableFamille.getNom().isEmpty())
                strCmd += " where id_classification = " + searchableFamille.getClassification();
            else if(searchableFamille.getClassification() == 0 && !searchableFamille.getNom().isEmpty())
                strCmd += " where famille like '%" + searchableFamille.getNom() + "%'";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();
            rs.next();
            int familles = rs.getInt(1);
            rs.close();
            return familles;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<Famille> getAll(int page) {
        ArrayList<Famille> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_famille, famille, id_classification from Famille order by famille";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
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
    public int insert(Famille objet) {
        try {
            String requete = "INSERT INTO Famille (famille,id_classification) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getfamille());
            preparedStatement.setInt(2, objet.getclassification().getId_classification());
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
    public boolean update(Famille object) {
        try {
            String requete = "UPDATE Famille SET famille = ?, id_classification = ? WHERE id_famille = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getfamille());
            preparedStatement.setInt(2, object.getclassification().getId_classification());
            preparedStatement.setInt(3, object.getId_famille());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Famille object) {
        try {
            String requete = "DELETE FROM Famille WHERE id_famille=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_famille());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
