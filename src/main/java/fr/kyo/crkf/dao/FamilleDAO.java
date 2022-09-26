package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.searchable.SearchableFamille;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamilleDAO extends DAO<Famille> {

    protected FamilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Famille getByID(int id) {
        String requete = "SELECT id_famille, famille, id_classification from Famille where id_famille = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete);){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Famille> getByClassification(int id) {
        List<Famille> list = new ArrayList<>();
        String requete = "SELECT id_famille, famille, id_classification from Famille where id_classification = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) list.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Famille> getLike(SearchableFamille searchableFamille, int page) {
        List<Famille> list = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_famille, famille, id_classification from Famille");
        requeteParClassificationEtParNom(searchableFamille, requete);
        requete.append(" order by famille OFFSET 25 * (").append(page).append(" - 1)  ROWS FETCH NEXT 25 ROWS ONLY");
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) list.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNumberOfFamilles(SearchableFamille searchableFamille) {
        StringBuilder requete = new StringBuilder("SELECT COUNT(id_famille) from Famille");
        requeteParClassificationEtParNom(searchableFamille, requete);
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<Famille> getAll(int page) {
        ArrayList<Famille> liste = new ArrayList<>();
        String requete = "SELECT id_famille, famille, id_classification from Famille order by famille";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Famille(rs.getInt(1),rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Famille objet) {
        String requete = "INSERT INTO Famille (famille,id_classification) VALUES (?,?)";
        try (PreparedStatement  preparedStatement = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString( 1 , objet.getfamille());
            preparedStatement.setInt(2, objet.getclassification().getClassificationId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Famille object) {
        String requete = "UPDATE Famille SET famille = ?, id_classification = ? WHERE id_famille = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setString(1, object.getfamille());
            preparedStatement.setInt(2, object.getclassification().getClassificationId());
            preparedStatement.setInt(3, object.getFamilleId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Famille object) {
        String requete = "DELETE FROM Famille WHERE id_famille=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setInt(1, object.getFamilleId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void requeteParClassificationEtParNom(SearchableFamille searchableFamille, StringBuilder requete) {
        if (searchableFamille.getClassification() != 0 && !searchableFamille.getNom().isEmpty())
            requete.append(" where id_classification = ").append(searchableFamille.getClassification()).append(" and famille like '%").append(searchableFamille.getNom()).append("%'");
        else if(searchableFamille.getClassification() != 0 && searchableFamille.getNom().isEmpty())
            requete.append(" where id_classification = ").append(searchableFamille.getClassification());
        else if(searchableFamille.getClassification() == 0 && !searchableFamille.getNom().isEmpty())
            requete.append(" where famille like '%").append(searchableFamille.getNom()).append("%'");
    }

}
