package fr.kyo.crkf.DAO;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;

import java.sql.*;
import java.util.ArrayList;

public class VilleDAO extends DAO<Ville> {
    protected VilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Ville getByID(int id) {
        Ville ville = null;
        try {

            // Determine the column set column

            String strCmd = "SELECT id, ville, id_departement,(select departement from Departement where id = v.id) from Ville as v where id = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery(strCmd);

            rs.next();
            ville =  new Ville(rs.getInt(1), rs.getString(2), new Departement(rs.getInt(3), rs.getString(4)));

            rs.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return ville;
    }

    @Override
    public ArrayList<Ville> getAll() {
        ArrayList<Ville> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id, ville, id_departement,(select departement from Departement where id = v.id) from Ville as v order by ville";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(new Ville(rs.getInt(1), rs.getString(2), new Departement(rs.getInt(3), rs.getString(4))));
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
    public boolean insert(Ville objet) {
        try {
            String requete = "INSERT INTO Ville (ville,id_departement) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getVille());
            preparedStatement.setInt(2, objet.getDepartement().getId_departement());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Ville object) {
        try {
            String requete = "UPDATE Ville SET ville = ?, id_departement = ? WHERE id = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getVille());
            preparedStatement.setInt(2, object.getDepartement().getId_departement());
            preparedStatement.setInt(3, object.getId_ville());
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
            String requete = "DELETE FROM Ville WHERE id=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_ville());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
