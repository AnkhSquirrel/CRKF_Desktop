package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Cycle;
import fr.kyo.crkf.Entity.Ecole;
import fr.kyo.crkf.Entity.Instrument;
import fr.kyo.crkf.Searchable.SearchableEcole;
import fr.kyo.crkf.Searchable.SearchableInstrument;

import java.sql.*;
import java.util.ArrayList;

public class EcoleDAO extends DAO<Ecole> {
    protected EcoleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Ecole getByID(int id) {
        Ecole ecole = null;
        try {

            // Determine the column set column

            String strCmd = "SELECT id_ecole, Nom, id_adresse from Ecole where id_ecole = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();

            rs.next();
            ecole =  new Ecole(rs.getInt(1), rs.getString(2),DAOFactory.getAdresseDAO().getByID(rs.getInt(3)));

            rs.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return ecole;
    }

    @Override
    public ArrayList<Ecole> getAll() {
        ArrayList<Ecole> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id_ecole, Nom, id_adresse from Ecole order by Nom";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(new Ecole(rs.getInt(1), rs.getString(2), DAOFactory.getAdresseDAO().getByID(rs.getInt(3))));
            }
            rs.close();
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public ArrayList<Ecole> getLike(SearchableEcole searchableEcole) {
        ArrayList<Ecole> liste = new ArrayList<>();
        try {

            // Determine the column set column

            String strCmd = "exec SP_ECOLE_FILTER  @nom = ?, @ville = ?, @departement = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setString(1,searchableEcole.getNom());
            s.setInt(2,searchableEcole.getVille().getId_ville());
            s.setInt(3, searchableEcole.getDepartement().getId_departement());
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Ecole ecole = (new Ecole(rs.getInt(1),rs.getString(2),DAOFactory.getAdresseDAO().getByID(rs.getInt(3))));

                liste.add(ecole);
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
    public boolean insert(Ecole objet) {
        try {
            String requete = "INSERT INTO Ecole (Nom,id_adresse) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getNom());
            preparedStatement.setInt(2, objet.getAdresse().getId_adresse());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Ecole object) {
        try {
            String requete = "UPDATE Ecole SET Nom = ?, id_adresse = ? WHERE id_ecole = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getNom());
            preparedStatement.setInt(2, object.getAdresse().getId_adresse());
            preparedStatement.setInt(3, object.getId_ecole());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Ecole object) {
        try {
            String requete = "DELETE FROM Ecole WHERE id_ecole=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_ecole());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
