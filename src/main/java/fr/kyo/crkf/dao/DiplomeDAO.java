package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Diplome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiplomeDAO {

    protected Connection connection;

    protected DiplomeDAO(Connection connexion){
        this.connection = connexion;
    }

    protected Connection connection(){
        return connection;
    }

    public void deleteDiplome(int personneId, int cycleId, int instrumentId) {
        String requete = "DELETE FROM Personne_Diplome WHERE id_personne = " + personneId + " and id_libelle = " + cycleId + " and id_instrument = " + instrumentId;
        executeUpdate(requete);
    }

    public void personneAddDiplome(int id, int instrumentId, int cycleId) {
        String requete = "Insert into Personne_Diplome (id_personne, id_instrument, id_libelle) VALUES (" + id + "," + instrumentId + "," + cycleId + ")";
        executeUpdate(requete);
    }

    public List<Diplome> getPersonneDiplomeLike (int id, int instrumentId, int cycleId) {
        StringBuilder requete = new StringBuilder("SELECT id_libelle, id_instrument from Personne_Diplome where id_personne = " + id);
        if(cycleId != 0)
            requete.append(" and id_libelle = ").append(cycleId);
        if(instrumentId != 0)
            requete.append(" and id_instrument = ").append(instrumentId);
        ArrayList<Diplome> liste = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete.toString())){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Diplome(rs.getInt(1), rs.getInt(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<Diplome> getDiplomeSupriorOf (int id, int instrumentId, int cycleId) {
        ArrayList<Diplome> liste = new ArrayList<>();
        String requete = "SELECT id_libelle, id_instrument from Personne_Diplome where id_personne = " + id + " and id_libelle > " + cycleId + " and id_instrument = " + instrumentId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) liste.add(new Diplome(rs.getInt(1), rs.getInt(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    private void executeUpdate(String requete) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

}
