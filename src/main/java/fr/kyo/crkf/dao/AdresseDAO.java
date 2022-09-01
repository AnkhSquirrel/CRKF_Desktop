package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;

import java.sql.*;
import java.util.ArrayList;

public class AdresseDAO extends DAO<Adresse> {
    protected AdresseDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Adresse getByID(int id) {
        Adresse adresse= null;
        try{

            String strCmd = "SELECT id_adresse, adresse, id_ville from Adresse as a where id_adresse = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();

            adresse = new Adresse(rs.getInt(1), rs.getString(2),DAOFactory.getVilleDAO().getByID(rs.getInt(3)));

            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return adresse;
    }

    @Override
    public ArrayList<Adresse> getAll() {
        ArrayList<Adresse> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {


            // Determine the column set colum
            String strCmd = "SELECT id_adresse, adresse, id_villefrom Adresse as a order by adresse";
            ResultSet rs = stmt.executeQuery(strCmd);

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
    public boolean insert(Adresse objet) {
        try {
            String requete = "INSERT INTO adresse (adresse,id_ville) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getAdresse());
            preparedStatement.setInt(2, objet.getVille().getId_ville());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
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
