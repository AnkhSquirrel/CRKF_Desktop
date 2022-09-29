package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.tools.Pair;
import fr.kyo.crkf.searchable.SearchableEcole;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EcoleDAO extends DAO<Ecole> {

    private static final int LG_PAGE = 25;
    private static final int DISTANCE_MAX = 50;

    protected EcoleDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Ecole getByID(int id) {
        String requete = "SELECT id_ecole, Nom, id_adresse from Ecole where id_ecole = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return new Ecole(rs.getInt(1), rs.getString(2),rs.getInt(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ecole> getAll(int page) {
        List<Ecole> liste = new ArrayList<>();
        String requete = "SELECT id_ecole, Nom, id_adresse from Ecole order by Nom OFFSET " + LG_PAGE + " * (? -1)  ROWS FETCH NEXT " + LG_PAGE + " ROWS ONLY";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1,page);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Ecole(rs.getInt(1), rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Ecole> getLike(SearchableEcole searchableEcole, int page) {
        List<Ecole> liste = new ArrayList<>();
        String strCmd = "exec SP_ECOLE_FILTER  @nom = ?, @ville = ?, @departement = ?,@idinstrument = ?, @lgpage = 25, @page = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(strCmd)){
            preparedStatement.setString(1,searchableEcole.getNom());
            preparedStatement.setInt(2,searchableEcole.getIdVille());
            preparedStatement.setInt(3, searchableEcole.getIdDepartement());
            preparedStatement.setInt(4, searchableEcole.getIdInstrument());
            preparedStatement.setInt(5, page);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())liste.add(new Ecole(rs.getInt(1),rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Ecole> getLikeAllEcole(SearchableEcole searchableEcole) {
        List<Ecole> liste = new ArrayList<>();
        String requete = "exec SP_ECOLE_FILTER  @nom = ?, @ville = ?, @departement = ?,@idinstrument = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setString(1,searchableEcole.getNom());
            preparedStatement.setInt(2,searchableEcole.getIdVille());
            preparedStatement.setInt(3, searchableEcole.getIdDepartement());
            preparedStatement.setInt(4, searchableEcole.getIdInstrument());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Ecole(rs.getInt(1),rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Ecole> getByDepartement(int id) {
        List<Ecole> list = new ArrayList<>();
        String strCmd = "SELECT id_ecole, Nom, id_adresse from ecole where id_adresse in (select id_adresse from Adresse where id_ville in (select id_ville from Ville where id_departement = ? ))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(strCmd)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) list.add(new Ecole(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Pair<Ecole, Double>> getBySmallestDistance(float latitudePointA, float longitudePointA, float latitudePointB, float longitudePointB) {
        List<Pair<Ecole, Double>> ecolesEtDistances = new ArrayList<>();
        String requete = "SELECT id_ecole, Nom, id_adresse from Ecole";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                double distanceA = getDistanceBetweentwoCoordinates(latitudePointA, longitudePointA, DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLatitude(), DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLongitude());
                double distanceB = getDistanceBetweentwoCoordinates(latitudePointB, longitudePointB, DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLatitude(), DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLongitude());
                double smallestDistance = Math.min(distanceA, distanceB);
                if (smallestDistance <= DISTANCE_MAX){
                    Ecole ecole = (new Ecole(rs.getInt(1), rs.getString(2),rs.getInt(3)));
                    ecolesEtDistances.add(new Pair<>(ecole, ( Math.round(smallestDistance * 100.0) / 100.0 )));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ecolesEtDistances.sort(Comparator.comparingDouble(Pair<Ecole,Double>::getSecond));
        return ecolesEtDistances;
    }

    public List<Ecole> getByDistanceAndInstrument(float latitudePointA, float longitudePointA, int familleId) {
        List<Ecole> ecoles = new ArrayList<>();
        StringBuilder requete = new StringBuilder("SELECT id_ecole, Nom, id_adresse from Ecole as e");
        if (familleId > 0)
            requete.append(" where ").append(familleId).append(" in (select distinct id_famille from Instrument_Famille where id_instrument in (select distinct(id_instrument) from Personne_Diplome where id_personne in (select id_personne from personne where id_ecole = e.id_ecole)))");
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                double distance = getDistanceBetweentwoCoordinates(latitudePointA, longitudePointA, DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLatitude(), DAOFactory.getAdresseDAO().getByID(rs.getInt(3)).getVille().getLongitude());
                if (distance <= DISTANCE_MAX) ecoles.add(new Ecole(rs.getInt(1), rs.getString(2),rs.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ecoles;
    }

    private double getDistanceBetweentwoCoordinates(float latA, float longA, float latB, float longB) {
        return 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((Math.toRadians(latB - latA)) / 2), 2) + Math.pow(Math.sin((Math.toRadians(longB - longA)) / 2), 2) * Math.cos((Math.toRadians(latA))) * Math.cos(Math.toRadians(latB)))) * 6371.009;
    }

    @Override
    public int insert(Ecole objet) {
        String requete = "INSERT INTO Ecole (Nom,id_adresse) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            preparedStatement.setString( 1 , objet.getEcoleNom());
            preparedStatement.setInt(2, objet.getIdAdresse());
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
    public boolean update(Ecole object) {
        String requete = "UPDATE Ecole SET Nom = ?, id_adresse = ? WHERE id_ecole = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, object.getEcoleNom());
            preparedStatement.setInt(2, object.getEcoleAdresse().getAdresseId());
            preparedStatement.setInt(3, object.getEcoleId());
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
    public boolean delete(Ecole object) {
        String requete = "DELETE FROM Ecole WHERE id_ecole=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, object.getEcoleId());
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
}
