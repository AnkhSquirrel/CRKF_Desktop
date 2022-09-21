package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Adresse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresseDAO extends DAO<Adresse> {

    private static final int LG_PAGE = 25;

    protected AdresseDAO(Connection connexion) {
        super(connexion);
    }

    @Override
    public Adresse getByID(int id) {
        String strCmd = "SELECT id_adresse, adresse, id_ville from Adresse where id_adresse = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(strCmd)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) return new Adresse(rs.getInt(1), rs.getString(2),rs.getInt(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adresse> getAll(int page) {
        List<Adresse> liste = new ArrayList<>();
        String strCmd = "SELECT id_adresse, adresse, id_ville from Adresse order by adresse OFFSET " + LG_PAGE + " * (? - 1)  ROWS FETCH NEXT " + LG_PAGE + " ROWS ONLY";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(strCmd)){
            preparedStatement.setInt(1,page);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Adresse(rs.getInt(1), rs.getString(2),rs.getInt(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public int insert(Adresse objet) {
        String requete = "INSERT INTO Adresse (adresse,id_ville) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString( 1 , objet.getAdresseLibelle());
            preparedStatement.setInt(2, objet.getVille().getVilleId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean update(Adresse object) {
        String requete = "UPDATE Adresse SET adresse = ?, id_ville = ? WHERE id_adresse = ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setString(1, object.getAdresseLibelle());
            preparedStatement.setInt(2, object.getVille().getVilleId());
            preparedStatement.setInt(3, object.getAdresseId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adresse object) {
        String requete = "DELETE FROM Adresse WHERE id_adresse=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)){
            preparedStatement.setInt(1, object.getAdresseId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
