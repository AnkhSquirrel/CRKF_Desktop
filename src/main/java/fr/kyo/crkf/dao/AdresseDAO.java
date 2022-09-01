package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Adresse;

import java.sql.*;
import java.util.ArrayList;

public class AdresseDAO extends DAO<Adresse> {
    private final int lgpage = 25;
    protected AdresseDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Adresse getByID(int id) {
        Adresse adresse= null;
        try{

            String strCmd = "SELECT id_adresse, adresse, id_ville from Adresse where id_adresse = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            if(rs.next())
                adresse = new Adresse(rs.getInt(1), rs.getString(2),DAOFactory.getVilleDAO().getByID(rs.getInt(3)));

            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return adresse;
    }

    @Override
    public ArrayList<Adresse> getAll(int page) {
        ArrayList<Adresse> liste = new ArrayList<>();
        try {


            // Determine the column set colum
            String strCmd = "SELECT id_adresse, adresse, id_ville from Adresse order by adresse OFFSET " + lgpage + " * (? -1)  ROWS FETCH NEXT " + lgpage + " ROWS ONLY";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,page);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                liste.add(new Adresse(rs.getInt(1), rs.getString(2),DAOFactory.getVilleDAO().getByID(rs.getInt(3))));
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Adresse objet) {
        try {
            String requete = "INSERT INTO adresse (adresse,id_ville) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getAdresse());
            preparedStatement.setInt(2, objet.getVille().getId_ville());
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
    public boolean update(Adresse object) {
        try {
            String requete = "UPDATE Adresse SET adresse = ?, id_ville = ? WHERE id_adresse = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getAdresse());
            preparedStatement.setInt(2, object.getVille().getId_ville());
            preparedStatement.setInt(3, object.getId_adresse());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Adresse object) {
        try {
            String requete = "DELETE FROM Adresse WHERE id_adresse=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_adresse());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
