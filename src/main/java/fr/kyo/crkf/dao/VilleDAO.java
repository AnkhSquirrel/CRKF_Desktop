package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Ville;

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

            String strCmd = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville where id_ville = ?";

            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            ville =  new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4), rs.getInt(5));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ville;
    }

    public ArrayList<Ville> getByDepartementID(int id) {
        ArrayList<Ville> liste = new ArrayList<>();
        try {
            PreparedStatement ps = connexion.prepareStatement("SELECT id_ville, ville, longitude,latitude,id_departement from Ville where id_departement = ? ");
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
                liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
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
                liste.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
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
            preparedStatement.setString( 1 , objet.getVilleLibelle());
            preparedStatement.setFloat(2,objet.getLongitude());
            preparedStatement.setFloat(3,objet.getLatitude());
            preparedStatement.setInt(4, objet.getDepartement().getDepartementId());
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
            preparedStatement.setString(1, object.getVilleLibelle());
            preparedStatement.setFloat(2,object.getLongitude());
            preparedStatement.setFloat(3,object.getLatitude());
            preparedStatement.setInt(4, object.getDepartement().getDepartementId());
            preparedStatement.setInt(5, object.getVilleId());
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
            preparedStatement.setInt(1, object.getVilleId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<Ville> getLike(String nom, int departement_id) {
        ArrayList<Ville> list = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()){

            String strCmd = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville where ville like '%" + nom + "%'";
            if(departement_id != 0)
                strCmd += " and id_departement = " + departement_id;
            strCmd += " ORDER BY VILLE OFFSET 0 ROWS FETCH NEXT 25 ROWS ONLY";
            ResultSet rs = stmt.executeQuery(strCmd);


            while(rs.next())
                list.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getAllVille(String nom, int departement_id) {
        try{
            String strCmd = "SELECT COUNT(id_ville) from Ville where ville like '%" + nom + "%'";
            if(departement_id != 0)
                strCmd += " and id_departement = " + departement_id;
            PreparedStatement s = connexion.prepareStatement(strCmd);
            ResultSet rs = s.executeQuery();
            rs.next();
            int villes = rs.getInt(1);
            rs.close();
            return villes;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Ville> getLikeForGestion(String nom, int departement_id, int page) {
        ArrayList<Ville> list = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()){

            String strCmd = "SELECT id_ville, ville, longitude,latitude,id_departement from Ville where ville like '%" + nom + "%'";
            if(departement_id != 0)
                strCmd += " and id_departement = " + departement_id;
            strCmd += " order by VILLE OFFSET 25 * (" + page + " - 1)  ROWS FETCH NEXT 25 ROWS ONLY";
            ResultSet rs = stmt.executeQuery(strCmd);


            while(rs.next())
                list.add(new Ville(rs.getInt(1), rs.getString(2),rs.getFloat(3),rs.getFloat(4) ,rs.getInt(5)));
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
