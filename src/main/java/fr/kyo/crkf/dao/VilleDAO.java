package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Ville;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VilleDAO extends DAO<Ville> {

    private static final int LG_PAGE = 25;

    protected VilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Ville getByID(int id) {
        String requete = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville where id_ville = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4), rs.getInt(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ville> getAll(int page) {
        List<Ville> liste = new ArrayList<>();
        String requete = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville order by ville OFFSET " + LG_PAGE + " * (? - 1)  ROWS FETCH NEXT " + LG_PAGE + " ROWS ONLY";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1,page);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Ville objet) {
        String requete = "INSERT INTO Ville (ville,longitude,latitude,id_departement) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            preparedStatement.setString( 1 , objet.getVilleLibelle());
            preparedStatement.setFloat(2,objet.getLongitude());
            preparedStatement.setFloat(3,objet.getLatitude());
            preparedStatement.setInt(4, objet.getDepartement().getDepartementId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
            connection.commit();
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public boolean update(Ville object) {
        String requete = "UPDATE Ville SET ville = ?,longitude = ? ,latitude = ?,id_departement = ? WHERE id_ville = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, object.getVilleLibelle());
            preparedStatement.setFloat(2,object.getLongitude());
            preparedStatement.setFloat(3,object.getLatitude());
            preparedStatement.setInt(4, object.getDepartement().getDepartementId());
            preparedStatement.setInt(5, object.getVilleId());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Ville object) {
        String requete = "DELETE FROM Ville WHERE id_ville=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, object.getVilleId());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public List<Ville> getLike(String nom, int departementId) {
        List<Ville> list = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_ville, ville, longitude,latitude,id_departement from Ville where ville like '%" + nom + "%'");
        if(departementId != 0)
            requete.append(" and id_departement = ").append(departementId);
        requete.append(" ORDER BY VILLE OFFSET 0 ROWS FETCH NEXT 25 ROWS ONLY");
        return getVilles(list, requete);
    }

    public int getNumberOfVilles(String nom, int departementId) {
        StringBuilder requete = new StringBuilder("SELECT COUNT(id_ville) from Ville where ville like '%" + nom + "%'");
        if(departementId != 0)
            requete.append(" and id_departement = ").append(departementId);
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Ville> getLikeForGestion(String nom, int departementId, int page) {
        List<Ville> list = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_ville, ville, longitude,latitude,id_departement from Ville where ville like '%" + nom + "%'");
        if(departementId != 0)
            requete.append(" and id_departement = ").append(departementId);
        requete.append(" order by VILLE OFFSET 25 * (").append(page).append(" - 1)  ROWS FETCH NEXT 25 ROWS ONLY");
        return getVilles(list, requete);
    }

    private List<Ville> getVilles(List<Ville> list, StringBuilder requete) {
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(requete.toString());
            while(rs.next()) list.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
