package fr.kyo.crkf.DAO;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.Entity.Ville;

import java.sql.*;
import java.util.ArrayList;

public class FamilleDAO extends DAO<Famille> {
    protected FamilleDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Famille getByID(int id) {
        Famille famille = null;
        try {

            // Determine the column set column

            String strCmd = "SELECT id, famille, id_classification,(select classification from Classification where id = f.id_classification) from Famille as f where id = ?";
            PreparedStatement s = connexion.prepareStatement(strCmd);
            s.setInt(1,id);
            ResultSet rs = s.executeQuery(strCmd);

            rs.next();
            famille =  new Famille(rs.getInt(1),rs.getString(2),new Classification(rs.getInt(3),rs.getString(4)));

            rs.close();

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return famille;
    }

    @Override
    public ArrayList<Famille> getAll() {
        ArrayList<Famille> liste = new ArrayList<>();
        try (Statement stmt = connexion.createStatement()) {

            // Determine the column set column

            String strCmd = "SELECT id, famille, id_classification,(select classification from Classification where id = f.id_classification) from Famille as f order by famille";
            ResultSet rs = stmt.executeQuery(strCmd);

            while (rs.next()) {
                liste.add(new Famille(rs.getInt(1),rs.getString(2),new Classification(rs.getInt(3),rs.getString(4))));
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
    public boolean insert(Famille objet) {
        try {
            String requete = "INSERT INTO Famille (famille,id_classification) VALUES (?,?)";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1 , objet.getfamille());
            preparedStatement.setInt(2, objet.getclassification().getId_classification());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Famille object) {
        try {
            String requete = "UPDATE Famille SET famille = ?, id_classification = ? WHERE id = ?";
            PreparedStatement  preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setString(1, object.getfamille());
            preparedStatement.setInt(2, object.getclassification().getId_classification());
            preparedStatement.setInt(3, object.getId_famille());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Famille object) {
        try {
            String requete = "DELETE FROM Famille WHERE id=?";
            PreparedStatement preparedStatement = connexion().prepareStatement(requete);
            preparedStatement.setInt(1, object.getId_famille());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
