package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Ville;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO extends DAO<Departement> {

    protected DepartementDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Departement getByID(int id) {
        String strCmd = "SELECT id_departement, numero_departement,departement from Departement as v where id_departement = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(strCmd)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return new Departement(rs.getInt(1), rs.getString(2),rs.getString(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Departement> getAll(int page) {
        ArrayList<Departement> liste = new ArrayList<>();
        String requete = "SELECT id_departement, numero_departement,departement from Departement order by departement";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Departement(rs.getInt(1), rs.getString(2),rs.getString(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<String> getNumDepartement() {
        ArrayList<String> liste = new ArrayList<>();
        String requete = "SELECT numero_departement from Departement order by numero_departement";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Departement> getLike(String departement, int page) {
        ArrayList<Departement> list = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_departement, numero_departement ,Departement from Departement");
        if(!departement.isEmpty())
            requete.append(" where departement like '%").append(departement).append("%'");
        if(page > 0)
            requete.append(" order by departement OFFSET 25 * (").append(page).append(" - 1)  ROWS FETCH NEXT 25 ROWS ONLY");
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) list.add(new Departement(rs.getInt(1), rs.getString(2) , rs.getString(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getNumberOfDepartements(String departement) {
        StringBuilder requete = new StringBuilder("SELECT COUNT(id_departement) from Departement");
        if(!departement.isEmpty())
            requete.append(" where departement like '%").append(departement).append("%'");
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Ville> getVilleByDepartement(int id) {
        ArrayList<Ville> liste = new ArrayList<>();
        String requete = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville as V where id_departement = ? ";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Departement objet) {
        String requete = "INSERT INTO Departement (numero_departement, departement) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);){
            preparedStatement.setString( 1 , objet.getDepartementNumero());
            preparedStatement.setString( 2 , objet.getDepartementLibelle());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Departement object) {
        String requete = "UPDATE Departement SET numero_departement = ?, departement = ? WHERE id_departement = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
            preparedStatement.setString(1,object.getDepartementNumero());
            preparedStatement.setString(2, object.getDepartementLibelle());
            preparedStatement.setInt(3, object.getDepartementId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Departement object) {
        String requete = "DELETE FROM Departement WHERE id_departement=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete);){
            preparedStatement.setInt(1, object.getDepartementId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
