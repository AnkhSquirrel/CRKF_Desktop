package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Ville;

import java.sql.*;
import java.util.ArrayList;

public class VilleDAO extends DAO<Ville> {
    private final int lgpage = 25;
    protected VilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Ville getByID(int id) {
        Ville ville = null;
        try {

            // Determine the column set column

            String strCmd = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville where id_ville = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();

            ville =  new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,DAOFactory.getDepartementDAO().getByID(rs.getInt(5)));

            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return ville;
    }
    @Override
    public ArrayList<Ville> getAll(int page) {
        ArrayList<Ville> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville order by ville OFFSET " + lgpage + " * (? - 1)  ROWS FETCH NEXT " + lgpage + " ROWS ONLY";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,page);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,DAOFactory.getDepartementDAO().getByID(rs.getInt(5))));
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
    public int insert(Ville objet) {
        try {
            String requete = "INSERT INTO Ville (ville,longitude,latitude,id_departement) VALUES (?,?,?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getVille());
            preparedStatement.setFloat(2,objet.getLongitude());
            preparedStatement.setFloat(3,objet.getLatitude());
            preparedStatement.setInt(4, objet.getDepartement().getId_departement());
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
    public boolean update(Ville object) {
        try {
            String requete = "UPDATE Ville SET ville = ?,longitude = ? ,latitude = ?,id_departement = ? WHERE id_ville = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getVille());
            preparedStatement.setFloat(2,object.getLongitude());
            preparedStatement.setFloat(3,object.getLatitude());
            preparedStatement.setInt(4, object.getDepartement().getId_departement());
            preparedStatement.setInt(5, object.getId_ville());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Ville object) {
        try {
            String requete = "DELETE FROM Ville WHERE id_ville=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_ville());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
